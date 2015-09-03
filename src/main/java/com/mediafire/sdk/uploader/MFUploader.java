package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MediaFireClient;
import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.api.responses.UploadCheckResponse;
import com.mediafire.sdk.api.responses.data_models.ResumableUpload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Uploader which handles MediaFireUploadRunnable requests. Pausable.
 */
public class MFUploader implements MFRunnableUploadStatusListener, MFRunnablePollUpload.OnPollUploadStatusListener {
    private final PausableExecutor executor;
    private final LinkedBlockingDeque<Runnable> queue;

    private boolean coreThreadsStarted = false;
    private final MediaFireClient mediaFire;
    private final MediaFireUploadStore store;
    private final int concurrentUploads;


    public MFUploader(MediaFireClient mediaFire, MediaFireUploadStore store, int concurrentUploads) {
        this.mediaFire = mediaFire;
        this.store = store;
        this.concurrentUploads = concurrentUploads;
        this.queue = new LinkedBlockingDeque<Runnable>();
        this.executor = new PausableExecutor(concurrentUploads, 10, 10, TimeUnit.SECONDS, queue);
    }

    public int getQueueSize() {
        return queue.size();
    }

    public void schedule(MediaFireWebUpload upload) {
        newWebUpload(upload);
    }

    public void schedule(MediaFireFileUpload upload) {
        newFileUpload(upload);
    }

    public void pause() {
        executor.pause();
    }

    public void resume() {
        if (!coreThreadsStarted) {
            coreThreadsStarted = true;
            executor.prestartAllCoreThreads();
        }
        executor.resume();
    }

    public boolean isPaused() {
        return executor.isPaused();
    }

    public boolean isRunning() {
        return executor.isRunning();
    }

    public void clear() {
        queue.clear();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void onGetWebUploadsProgress(MediaFireWebUpload upload, int statusCode, String description) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.STATUS_CODE, statusCode);
        valuesMap.put(MediaFireUploadStore.POLL_STATUS_DESCRIPTION, statusCode);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.WEB_UPLOAD_POLLING);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsFinished(MediaFireWebUpload upload, String quickKey, String filename) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.QUICK_KEY, quickKey);
        valuesMap.put(MediaFireUploadStore.FILE_NAME, filename);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.WEB_UPLOAD_FINISHED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsError(MediaFireWebUpload upload, int statusCode, int errorStatus, String description) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.STATUS_CODE, statusCode);
        valuesMap.put(MediaFireUploadStore.ERROR_CODE, errorStatus);
        valuesMap.put(MediaFireUploadStore.POLL_STATUS_DESCRIPTION, description);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.WEB_UPLOAD_ERROR);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsMediaFireException(MediaFireWebUpload upload, MediaFireException e) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.WEB_UPLOAD_MEDIAFIRE_EXCEPTION_THROWN);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsInterrupted(MediaFireWebUpload upload, InterruptedException e) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.WEB_UPLOAD_THREAD_INTERRUPTED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsFinishedWithoutVerificationOfCompletedUpload(MediaFireWebUpload upload) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.GET_WEB_UPLOAD_FINISHED_WITHOUT_VERIFICATION_OF_COMPLETED_UPLOADS);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onWebUploadException(MediaFireWebUpload upload, MediaFireException e) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.WEB_UPLOAD_MEDIAFIRE_EXCEPTION_THROWN);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onWebUploadFinish(MediaFireWebUpload upload, String uploadKey) {
        MFRunnableGetWebUpload runnableGetWebUpload = new MFRunnableGetWebUpload(mediaFire, upload, uploadKey, this, 99);

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_POLL_KEY, uploadKey);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.WEB_UPLOAD_READY_TO_POLL);
        this.store.update(upload, valuesMap);

        executor.execute(runnableGetWebUpload);
    }

    @Override
    public void onCheckUploadFinished(MediaFireFileUpload upload, UploadCheckResponse response) {

        String hashExists = response.getHashExists();
        String inAccount = response.getInAccount();
        String inFolder = response.getInFolder();
        String duplicateQuickKey = response.getDuplicateQuickkey();

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.QUICK_KEY, duplicateQuickKey);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_CHECK_UPLOAD_FINISHED);
        this.store.update(upload, valuesMap);

        if ("yes".equals(inAccount)) {
            switch (upload.getActionOnInAccount()) {
                case UPLOAD_ALWAYS:
                    instantUpload(upload);
                    break;
                case UPLOAD_IF_NOT_IN_FOLDER:
                    if ("no".equals(inFolder)) {
                        instantUpload(upload);
                    } else {
                        fileUploadFinished(upload, duplicateQuickKey);
                    }
                    break;
                case DO_NOT_UPLOAD:
                default:
                    fileUploadFinished(upload, duplicateQuickKey);
                    break;
            }
            return;
        }

        if ("yes".equals(hashExists)) {
            instantUpload(upload);
        } else {
            ResumableUpload resumableUpload = response.getResumableUpload();
            resumableUpload(upload, resumableUpload);
        }
    }

    @Override
    public void onCheckUploadMediaFireException(MediaFireFileUpload upload, MediaFireException e) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_CHECK_UPLOAD_MEDIAFIRE_EXCEPTION);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onInstantUploadMediaFireException(MediaFireFileUpload upload, MediaFireException e) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_INSTANT_UPLOAD_MEDIAFIRE_EXCEPTION);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onInstantUploadFinished(MediaFireFileUpload upload, String quickKey, String fileName) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.QUICK_KEY, quickKey);
        valuesMap.put(MediaFireUploadStore.FILE_NAME, fileName);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_FINISHED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onResumableUploadReadyToPoll(MediaFireFileUpload upload, String uploadKey) {
        MFRunnablePollUpload runnablePollUpload = new MFRunnablePollUpload(mediaFire, upload, uploadKey, this, 99);
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_POLL_KEY, uploadKey);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_READY_TO_POLL);
        this.store.update(upload, valuesMap);

        this.executor.execute(runnablePollUpload);
    }

    @Override
    public void onResumableUploadProgress(MediaFireFileUpload upload, double percentFinished) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.PERCENT_UPLOADED, percentFinished);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_RESUMABLE_UPLOADING);
        this.store.update(upload, valuesMap);
    }

    @Override
    public void onResumableUploadMediaFireException(MediaFireFileUpload upload, MediaFireException e) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_MEDIAFIRE_EXCEPTION);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onResumableUploadFinishedUploadingWithoutAllUnitsReady(MediaFireFileUpload upload) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_RESUMABLE_FINISHED_WITHOUT_ALL_UNITS_READY);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onResumableUploadIOException(MediaFireFileUpload upload, IOException e) {
        signalStartNewUpload();
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_IO_EXCEPTION);
        this.store.update(upload, valuesMap);
    }

    @Override
    public void onPollUploadFinished(MediaFireFileUpload upload, String quickKey, String fileName) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.QUICK_KEY, quickKey);
        valuesMap.put(MediaFireUploadStore.FILE_NAME, fileName);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_FINISHED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onPollUploadProgress(MediaFireFileUpload upload, int statusCode, String description) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.STATUS_CODE, statusCode);
        valuesMap.put(MediaFireUploadStore.POLL_STATUS_DESCRIPTION, description);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_POLLING);
        this.store.update(upload, valuesMap);
    }

    @Override
    public void onPollUploadError(MediaFireFileUpload upload, int fileErrorCode, int resultCode, int statusCode) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.FILE_ERROR_CODE, fileErrorCode);
        valuesMap.put(MediaFireUploadStore.STATUS_CODE, statusCode);
        valuesMap.put(MediaFireUploadStore.RESULT_CODE, resultCode);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_ERROR);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onPollUploadMediaFireException(MediaFireFileUpload upload, MediaFireException e) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_MEDIAFIRE_EXCEPTION);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onPollUploadThreadInterrupted(MediaFireFileUpload upload) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_THREAD_INTERRUPTED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    private void webUpload(MediaFireWebUpload upload) {
        MFRunnableWebUpload runnableWebUpload = new MFRunnableWebUpload(mediaFire, upload, this);

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.WEB_UPLOAD_NEW);
        this.store.update(upload, valuesMap);

        executor.execute(runnableWebUpload);
    }

    private void checkUpload(MediaFireFileUpload upload) {
        MFRunnableCheckUpload runnableCheckUpload = new MFRunnableCheckUpload(mediaFire, upload, this);

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_NEW);
        this.store.update(upload, valuesMap);

        executor.execute(runnableCheckUpload);
    }

    private void resumableUpload(MediaFireFileUpload upload, ResumableUpload resumableUpload) {
        MFRunnableResumableUpload runnableResumableUpload = new MFRunnableResumableUpload(mediaFire, upload, resumableUpload, this);

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_RESUMABLE_STARTING);
        this.store.update(upload, valuesMap);

        executor.execute(runnableResumableUpload);
    }

    private void instantUpload(MediaFireFileUpload upload) {
        MFRunnableInstantUpload runnableInstantUpload = new MFRunnableInstantUpload(mediaFire, upload, this);

        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_INSTANT_STARTING);
        this.store.update(upload, valuesMap);

        executor.execute(runnableInstantUpload);
    }

    private void fileUploadFinished(MediaFireFileUpload upload, String duplicateQuickKey) {
        Map<String, Object> valuesMap = new HashMap<String, Object>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.FILE_UPLOAD_FINISHED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    private void signalStartNewUpload() {
        // TODO
    }


    private void newWebUpload(MediaFireWebUpload upload) {
        this.store.insert(upload);
    }

    private void newFileUpload(MediaFireFileUpload upload) {
        this.store.insert(upload);
    }
}
