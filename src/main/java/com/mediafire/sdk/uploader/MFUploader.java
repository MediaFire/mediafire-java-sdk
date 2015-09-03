package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MediaFireClient;
import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.response_models.upload.UploadCheckResponse;
import com.mediafire.sdk.response_models.upload.UploadInstantResponse;
import com.mediafire.sdk.response_models.upload.UploadPollUploadResponse;
import com.mediafire.sdk.response_models.upload.UploadResumableResponse;
import com.mediafire.sdk.response_models.data_models.ResumableUploadModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Uploader which handles MediaFireUploadRunnable requests. Pausable.
 */
public class MFUploader implements MediaFireRunnableUploadStatusListener, MFRunnablePollUpload.OnPollUploadStatusListener {
    private final Logger logger = LoggerFactory.getLogger(MFUploader.class);

    private final PausableExecutor executor;
    private final LinkedBlockingDeque<Runnable> queue;

    private boolean coreThreadsStarted;
    private final MediaFireClient mediaFire;
    private final MediaFireUploadStore store;


    public MFUploader(MediaFireClient mediaFire, MediaFireUploadStore store, int concurrentUploads) {
        this.mediaFire = mediaFire;
        this.store = store;
        this.queue = new LinkedBlockingDeque<>();
        this.executor = new PausableExecutor(concurrentUploads, concurrentUploads, 10, TimeUnit.SECONDS, queue);
    }

    public int getQueueSize() {
        return queue.size();
    }

    public void schedule(MediaFireWebUpload upload) {
        this.store.insert(upload);
        if (canStartNewUpload()) {
            signalStartNewUpload();
        }
    }

    public void schedule(MediaFireFileUpload upload) {
        this.store.insert(upload);
        if (canStartNewUpload()) {
            signalStartNewUpload();
        }
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

    public boolean areJobsInProgress() {
        return executor.getActiveCount() > 0;
    }

    @Override
    public void onGetWebUploadsProgress(MediaFireWebUpload upload, int statusCode, String description) {
        logger.info("web upload polling in progress: " + upload + ", status:" + statusCode + ", description: " + description);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.STATUS_CODE, statusCode);
        valuesMap.put(MediaFireUploadStore.POLL_STATUS_DESCRIPTION, statusCode);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.WEB_UPLOAD_POLLING);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsFinished(MediaFireWebUpload upload, String quickKey, String filename) {
        logger.info("web upload finished polling: " + upload + ", quickKey:" + quickKey + ", filename: " + filename);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.QUICK_KEY, quickKey);
        valuesMap.put(MediaFireUploadStore.FILE_NAME, filename);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.WEB_UPLOAD_FINISHED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsError(MediaFireWebUpload upload, int statusCode, int errorStatus, String description) {
        logger.info("web upload error while polling: " + upload + ", status:" + statusCode + ", description: " + description);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.STATUS_CODE, statusCode);
        valuesMap.put(MediaFireUploadStore.ERROR_CODE, errorStatus);
        valuesMap.put(MediaFireUploadStore.POLL_STATUS_DESCRIPTION, description);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.WEB_UPLOAD_ERROR);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsMediaFireException(MediaFireWebUpload upload, MediaFireException e) {
        logger.info("web upload MediaFireException while polling: " + upload + ", exception:" + e);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.WEB_UPLOAD_MEDIAFIRE_EXCEPTION_THROWN);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsInterrupted(MediaFireWebUpload upload, InterruptedException e) {
        logger.info("web upload interrupted thread while polling: " + upload + ", exception:" + e);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.WEB_UPLOAD_THREAD_INTERRUPTED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsFinishedWithoutVerificationOfCompletedUpload(MediaFireWebUpload upload) {
        logger.info("web upload finished without verification of completion: " + upload);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.WEB_UPLOAD_FINISHED_WITHOUT_VERIFICATION_OF_COMPLETED_UPLOADS);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onWebUploadException(MediaFireWebUpload upload, MediaFireException e) {
        logger.info("web upload MediaFireException in web upload thread: " + upload + ", exception:" + e);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.WEB_UPLOAD_MEDIAFIRE_EXCEPTION_THROWN);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onWebUploadFinish(MediaFireWebUpload upload, String uploadKey) {
        logger.info("web upload finished: " + upload + ", upload key:" + uploadKey);
        MFRunnableGetWebUpload runnableGetWebUpload = new MFRunnableGetWebUpload(mediaFire, upload, uploadKey, this, 99);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_POLL_KEY, uploadKey);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.WEB_UPLOAD_READY_TO_POLL);
        this.store.update(upload, valuesMap);

        executor.execute(runnableGetWebUpload);
    }

    @Override
    public void onCheckUploadFinished(MediaFireFileUpload upload, UploadCheckResponse response) {
        logger.info("file upload finished check thread: " + upload + ", response:" + response);

        String hashExists = response.getHashExists();
        String inAccount = response.getInAccount();
        String inFolder = response.getInFolder();
        String duplicateQuickKey = response.getDuplicateQuickkey();

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.QUICK_KEY, duplicateQuickKey);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_CHECK_UPLOAD_FINISHED);
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
            ResumableUploadModel resumableUpload = response.getResumableUpload();
            resumableUpload(upload, resumableUpload);
        }
    }

    @Override
    public void onCheckUploadMediaFireException(MediaFireFileUpload upload, MediaFireException e) {
        logger.info("file upload MediaFireException in check thread: " + upload + ", exception:" + e);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_CHECK_UPLOAD_MEDIAFIRE_EXCEPTION);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onCheckUploadApiError(MediaFireFileUpload upload, UploadCheckResponse response) {
        logger.info("file upload api error during check: " + upload + ", error: " + response.getMessage() + "(" + response.getError() + ")");
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.API_ERROR_NUMBER, response.getError());
        valuesMap.put(MediaFireUploadStore.API_ERROR_MESSAGE, response.getMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_API_ERROR);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onInstantUploadMediaFireException(MediaFireFileUpload upload, MediaFireException e) {
        logger.info("file upload MediaFireException in instant thread: " + upload + ", exception:" + e);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_INSTANT_UPLOAD_MEDIAFIRE_EXCEPTION);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onInstantUploadApiError(MediaFireFileUpload upload, UploadInstantResponse response) {
        logger.info("file upload api error during instant upload: " + upload + ", error: " + response.getMessage() + "(" + response.getError() + ")");
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.API_ERROR_NUMBER, response.getError());
        valuesMap.put(MediaFireUploadStore.API_ERROR_MESSAGE, response.getMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_API_ERROR);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onInstantUploadFinished(MediaFireFileUpload upload, String quickKey, String fileName) {
        logger.info("file upload instant upload finished: " + upload + ", quick key:" + quickKey + ", file name: " + fileName);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.QUICK_KEY, quickKey);
        valuesMap.put(MediaFireUploadStore.FILE_NAME, fileName);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_FINISHED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onResumableUploadReadyToPoll(MediaFireFileUpload upload, String uploadKey) {
        logger.info("file upload ready to poll: " + upload + ", upload key:" + uploadKey);

        MFRunnablePollUpload runnablePollUpload = new MFRunnablePollUpload(mediaFire, upload, uploadKey, this, 99);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_POLL_KEY, uploadKey);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_READY_TO_POLL);
        this.store.update(upload, valuesMap);

        this.executor.execute(runnablePollUpload);
    }

    @Override
    public void onResumableUploadProgress(MediaFireFileUpload upload, double percentFinished) {
        logger.info("file upload in progress: " + upload + ", percent finished:" + percentFinished + "%");

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.PERCENT_UPLOADED, percentFinished);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_RESUMABLE_UPLOADING);
        this.store.update(upload, valuesMap);
    }

    @Override
    public void onResumableUploadMediaFireException(MediaFireFileUpload upload, MediaFireException e) {
        logger.info("file upload MediaFireException during resumable: " + upload + ", exception:" + e);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_MEDIAFIRE_EXCEPTION);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onResumableUploadFinishedUploadingWithoutAllUnitsReady(MediaFireFileUpload upload) {
        logger.info("file upload resumable thread ended without all units ready: " + upload);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_RESUMABLE_FINISHED_WITHOUT_ALL_UNITS_READY);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onResumableUploadIOException(MediaFireFileUpload upload, IOException e) {
        logger.info("file upload IOException during resumable: " + upload + ", exception:" + e);

        signalStartNewUpload();
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_IO_EXCEPTION);
        this.store.update(upload, valuesMap);
    }

    @Override
    public void onResumableUploadApiError(MediaFireFileUpload upload, UploadResumableResponse response) {
        logger.info("file upload api error during resumable: " + upload + ", error: " + response.getMessage() + "(" + response.getError() + ")");
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.API_ERROR_NUMBER, response.getError());
        valuesMap.put(MediaFireUploadStore.API_ERROR_MESSAGE, response.getMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_API_ERROR);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onPollUploadFinished(MediaFireFileUpload upload, String quickKey, String fileName) {
        logger.info("file upload polling finished: " + upload + ", quick key:" + quickKey + ", file name: " + fileName);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.QUICK_KEY, quickKey);
        valuesMap.put(MediaFireUploadStore.FILE_NAME, fileName);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_FINISHED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onPollUploadProgress(MediaFireFileUpload upload, int statusCode, String description) {
        logger.info("file upload polling progress: " + upload + ", status:" + statusCode + ", description: " + description);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.STATUS_CODE, statusCode);
        valuesMap.put(MediaFireUploadStore.POLL_STATUS_DESCRIPTION, description);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_POLLING);
        this.store.update(upload, valuesMap);
    }

    @Override
    public void onPollUploadError(MediaFireFileUpload upload, int fileErrorCode, int resultCode, int statusCode, String description) {
        logger.info("file upload error while polling: " + upload + ", file error code:" + fileErrorCode + ", result code: " + resultCode + ", status code: " + statusCode + ", description: " + description);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.FILE_ERROR_CODE, fileErrorCode);
        valuesMap.put(MediaFireUploadStore.STATUS_CODE, statusCode);
        valuesMap.put(MediaFireUploadStore.RESULT_CODE, resultCode);
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_ERROR);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onPollUploadMediaFireException(MediaFireFileUpload upload, MediaFireException e) {
        logger.info("file upload MediaFireException while polling: " + upload + ", exception:" + e);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.EXCEPTION, e);
        valuesMap.put(MediaFireUploadStore.EXCEPTION_MESSAGE, e.getMessage());
        valuesMap.put(MediaFireUploadStore.EXCEPTION_LOCALIZED_MESSAGE, e.getLocalizedMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_MEDIAFIRE_EXCEPTION);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onPollUploadThreadInterrupted(MediaFireFileUpload upload) {
        logger.info("file upload interrupted while polling: " + upload);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_THREAD_INTERRUPTED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onPollUploadApiError(MediaFireFileUpload upload, UploadPollUploadResponse response) {
        logger.info("file upload api error while polling: " + upload + ", error: " + response.getMessage() + "(" + response.getError() + ")");
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.API_ERROR_NUMBER, response.getError());
        valuesMap.put(MediaFireUploadStore.API_ERROR_MESSAGE, response.getMessage());
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_API_ERROR);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    @Override
    public void onPollUploadReachedMaxPollsWithoutConfirmationOfCompletion(MediaFireFileUpload upload) {
        logger.info("file upload api error while polling: " + upload);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_POLLING_FINISHED_WITHOUT_ALL_UNITS_READY);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    private void webUpload(MediaFireWebUpload upload) {
        MFRunnableWebUpload runnableWebUpload = new MFRunnableWebUpload(mediaFire, upload, this);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.WEB_UPLOAD_NEW);
        this.store.update(upload, valuesMap);

        executor.execute(runnableWebUpload);
    }

    private void checkUpload(MediaFireFileUpload upload) {
        MFRunnableCheckUpload runnableCheckUpload = new MFRunnableCheckUpload(mediaFire, upload, this);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_NEW);
        this.store.update(upload, valuesMap);

        executor.execute(runnableCheckUpload);
    }

    private void resumableUpload(MediaFireFileUpload upload, ResumableUploadModel resumableUpload) {
        MFRunnableResumableUpload runnableResumableUpload = new MFRunnableResumableUpload(mediaFire, upload, resumableUpload, this);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_RESUMABLE_STARTING);
        this.store.update(upload, valuesMap);

        executor.execute(runnableResumableUpload);
    }

    private void instantUpload(MediaFireFileUpload upload) {
        MFRunnableInstantUpload runnableInstantUpload = new MFRunnableInstantUpload(mediaFire, upload, this);

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_INSTANT_STARTING);
        this.store.update(upload, valuesMap);

        executor.execute(runnableInstantUpload);
    }

    private void fileUploadFinished(MediaFireFileUpload upload, String duplicateQuickKey) {
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(MediaFireUploadStore.UPLOAD_STATUS, MediaFireUploadStore.MediaFireUploadStatus.FILE_UPLOAD_FINISHED);
        this.store.update(upload, valuesMap);

        signalStartNewUpload();
    }

    private void signalStartNewUpload() {
        if (!canStartNewUpload()) {
            return;
        }

        MediaFireUpload upload = store.getNextUpload();

        if (upload == null) {
            return;
        }

        switch (upload.getType()) {
            case MediaFireUpload.TYPE_FILE_UPLOAD:
                checkUpload((MediaFireFileUpload) upload);
                break;
            case MediaFireUpload.TYPE_WEB_UPLOAD:
                webUpload((MediaFireWebUpload) upload);
                break;
            default:
                throw new IllegalStateException("MediaFireUploadStore has an invalid value for upload type: " + upload.getType());
        }
    }

    private boolean canStartNewUpload() {
        int coreSize = executor.getCorePoolSize();
        int activeCount = executor.getActiveCount();
        return activeCount < coreSize;
    }
}
