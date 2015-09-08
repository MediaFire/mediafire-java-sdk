package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MediaFireClient;
import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.response_models.MediaFireApiResponse;
import com.mediafire.sdk.response_models.data_models.ResumableUploadModel;
import com.mediafire.sdk.response_models.upload.UploadCheckResponse;
import com.mediafire.sdk.response_models.upload.UploadPollUploadResponse;
import com.mediafire.sdk.uploader.MFRunnablePollUpload.OnPollUploadStatusListener;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Uploader which handles MediaFireUploadRunnable requests. Pausable.
 */
public class MFUploader implements MediaFireRunnableUploadStatusListener, OnPollUploadStatusListener {
    public static final long KEEP_ALIVE_TIME = 10L;

    private final PausableExecutor executor;
    private final LinkedBlockingDeque<Runnable> queue;

    private boolean coreThreadsStarted;
    private final MediaFireClient mediaFire;
    private final MediaFireUploadStore store;


    public MFUploader(MediaFireClient mediaFire, MediaFireUploadStore store, int concurrentUploads) {
        this.mediaFire = mediaFire;
        this.store = store;
        this.queue = new LinkedBlockingDeque<>();
        this.executor = new PausableExecutor(concurrentUploads, concurrentUploads, MFUploader.KEEP_ALIVE_TIME, TimeUnit.SECONDS, this.queue);
    }

    public int getQueueSize() {
        return this.queue.size();
    }

    public void schedule(MediaFireWebUpload upload) {
        this.store.insert(upload);
        if (this.canStartNewUpload()) {
            this.signalStartNewUpload();
        }
    }

    public void schedule(MediaFireFileUpload upload) {
        this.store.insert(upload);
        if (this.canStartNewUpload()) {
            this.signalStartNewUpload();
        }
    }

    public void pause() {
        this.executor.pause();
    }

    public void resume() {
        if (!this.coreThreadsStarted) {
            this.coreThreadsStarted = true;
            this.executor.prestartAllCoreThreads();
        }
        this.executor.resume();
    }

    public boolean isPaused() {
        return this.executor.isPaused();
    }

    public boolean isRunning() {
        return this.executor.isRunning();
    }

    public void clear() {
        this.queue.clear();
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public boolean areJobsInProgress() {
        return this.executor.getActiveCount() > 0;
    }

    @Override
    public void onGetWebUploadsProgress(MediaFireWebUpload upload, int statusCode, String description) {
        this.store.polling(upload, statusCode, description);

        this.signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsFinished(MediaFireWebUpload upload, String quickKey, String filename) {
        this.store.uploadFinished(upload, quickKey, filename);

        this.signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsError(MediaFireWebUpload upload, int statusCode, int errorStatus, String description) {
        this.store.pollingError(upload, statusCode, errorStatus, description);

        this.signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsSdkException(MediaFireWebUpload upload, MediaFireException e) {
        this.store.sdkException(upload, e);

        this.signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsInterrupted(MediaFireWebUpload upload, InterruptedException e) {
        this.store.pollingInterrupted(upload, e);

        this.signalStartNewUpload();
    }

    @Override
    public void onGetWebUploadsLimitExceeded(MediaFireWebUpload upload) {
        this.store.pollingLimitExceeded(upload);

        this.signalStartNewUpload();
    }

    @Override
    public void onWebUploadException(MediaFireWebUpload upload, MediaFireException e) {
        this.store.sdkException(upload, e);

        this.signalStartNewUpload();
    }

    @Override
    public void onWebUploadFinish(MediaFireWebUpload upload, String uploadKey) {
        this.store.pollingReady(upload, uploadKey);
        MFRunnableGetWebUpload runnableGetWebUpload = new MFRunnableGetWebUpload(this.mediaFire, upload, uploadKey, this, 99);
        this.executor.execute(runnableGetWebUpload);
    }

    @Override
    public void onCheckUploadFinished(MediaFireFileUpload upload, UploadCheckResponse response) {

        String hashExists = response.getHashExists();
        String inAccount = response.getInAccount();
        String inFolder = response.getInFolder();
        String duplicateQuickKey = response.getDuplicateQuickkey();

        this.store.checkFinished(upload);

        if ("yes".equals(inAccount)) {
            switch (upload.getActionOnInAccount()) {
                case UPLOAD_ALWAYS:
                    this.startInstantUpload(upload);
                    break;
                case UPLOAD_IF_NOT_IN_FOLDER:
                    if ("no".equals(inFolder)) {
                        this.startInstantUpload(upload);
                    } else {
                        this.store.uploadFinished(upload, duplicateQuickKey, null);
                        this.signalStartNewUpload();
                    }
                    break;
                case DO_NOT_UPLOAD:
                default:
                    this.store.uploadFinished(upload, duplicateQuickKey, null);
                    this.signalStartNewUpload();
                    break;
            }
            return;
        }

        if ("yes".equals(hashExists)) {
            this.startInstantUpload(upload);
        } else {
            ResumableUploadModel resumableUpload = response.getResumableUpload();
            this.startResumableUpload(upload, resumableUpload);
        }
    }

    @Override
    public void onCheckUploadSdkException(MediaFireFileUpload upload, MediaFireException e) {
        this.store.sdkException(upload, e);

        this.signalStartNewUpload();
    }

    @Override
    public void onCheckUploadApiError(MediaFireFileUpload upload, MediaFireApiResponse response) {
        this.store.apiError(upload, response);

        this.signalStartNewUpload();
    }

    @Override
    public void onInstantUploadSdkException(MediaFireFileUpload upload, MediaFireException e) {
        this.store.sdkException(upload, e);

        this.signalStartNewUpload();
    }

    @Override
    public void onInstantUploadApiError(MediaFireFileUpload upload, MediaFireApiResponse response) {
        this.store.apiError(upload, response);

        this.signalStartNewUpload();
    }

    @Override
    public void onInstantUploadFinished(MediaFireFileUpload upload, String quickKey, String fileName) {
        this.store.uploadFinished(upload, quickKey, fileName);

        this.signalStartNewUpload();
    }

    @Override
    public void onResumableUploadReadyToPoll(MediaFireFileUpload upload, String uploadKey) {
        this.store.pollingReady(upload, uploadKey);
        MFRunnablePollUpload runnablePollUpload = new MFRunnablePollUpload(this.mediaFire, upload, uploadKey, this, 99);
        this.executor.execute(runnablePollUpload);
    }

    @Override
    public void onResumableUploadProgress(MediaFireFileUpload upload, double percentFinished) {
        this.store.uploadProgress(upload, percentFinished);
    }

    @Override
    public void onResumableUploadSdkException(MediaFireFileUpload upload, MediaFireException e) {
        this.store.sdkException(upload, e);

        this.signalStartNewUpload();
    }

    @Override
    public void onResumableUploadFinishedIncomplete(MediaFireFileUpload upload) {
        this.store.resumableFinishedWithoutAllUnitsReady(upload);

        this.signalStartNewUpload();
    }

    @Override
    public void onResumableUploadIOException(MediaFireFileUpload upload, IOException e) {
        this.store.fileIOException(upload, e);
        this.signalStartNewUpload();
    }

    @Override
    public void onResumableUploadApiError(MediaFireFileUpload upload, MediaFireApiResponse response) {
        this.store.apiError(upload, response);

        this.signalStartNewUpload();
    }

    @Override
    public void onPollUploadFinished(MediaFireFileUpload upload, String quickKey, String fileName) {
        this.store.uploadFinished(upload, quickKey, fileName);

        this.signalStartNewUpload();
    }

    @Override
    public void onPollUploadProgress(MediaFireFileUpload upload, int statusCode, String description) {
        this.store.polling(upload, statusCode, description);
    }

    @Override
    public void onPollUploadError(MediaFireFileUpload upload, int fileErrorCode, int resultCode, int statusCode, String description) {
        this.store.pollingError(upload, fileErrorCode, resultCode, statusCode, description);

        this.signalStartNewUpload();
    }

    @Override
    public void onPollUploadSdkException(MediaFireFileUpload upload, MediaFireException e) {
        this.store.sdkException(upload, e);

        this.signalStartNewUpload();
    }

    @Override
    public void onPollUploadThreadInterrupted(MediaFireFileUpload upload, InterruptedException e) {
        this.store.pollingInterrupted(upload, e);

        this.signalStartNewUpload();
    }

    @Override
    public void onPollUploadApiError(MediaFireFileUpload upload, UploadPollUploadResponse response) {
        this.store.apiError(upload, response);

        this.signalStartNewUpload();
    }

    @Override
    public void onPollUploadLimitExceeded(MediaFireFileUpload upload) {
        this.store.pollingLimitExceeded(upload);

        this.signalStartNewUpload();
    }

    private void startWebUpload(MediaFireWebUpload upload) {
        this.store.uploadQueued(upload);
        MFRunnableWebUpload runnableWebUpload = new MFRunnableWebUpload(this.mediaFire, upload, this);
        this.executor.execute(runnableWebUpload);
    }

    private void startCheckUpload(MediaFireFileUpload upload) {
        this.store.uploadQueued(upload);
        MFRunnableCheckUpload runnableCheckUpload = new MFRunnableCheckUpload(this.mediaFire, upload, this);
        this.executor.execute(runnableCheckUpload);
    }

    private void startResumableUpload(MediaFireFileUpload upload, ResumableUploadModel resumableUpload) {
        this.store.resumableUploadStarting(upload);
        MFRunnableResumableUpload runnableResumableUpload = new MFRunnableResumableUpload(this.mediaFire, upload, resumableUpload, this);
        this.executor.execute(runnableResumableUpload);
    }

    private void startInstantUpload(MediaFireFileUpload upload) {
        this.store.instantUploadStarting(upload);
        MFRunnableInstantUpload runnableInstantUpload = new MFRunnableInstantUpload(this.mediaFire, upload, this);
        this.executor.execute(runnableInstantUpload);
    }

    private void signalStartNewUpload() {
        if (!this.canStartNewUpload()) {
            return;
        }

        MediaFireUpload upload = this.store.getNextUpload();

        if (upload == null) {
            return;
        }

        switch (upload.getType()) {
            case MediaFireUpload.TYPE_FILE_UPLOAD:
                this.startCheckUpload((MediaFireFileUpload) upload);
                break;
            case MediaFireUpload.TYPE_WEB_UPLOAD:
                this.startWebUpload((MediaFireWebUpload) upload);
                break;
            default:
                throw new IllegalStateException("MediaFireUploadStore has an invalid value for upload type: " + upload.getType());
        }
    }

    private boolean canStartNewUpload() {
        int coreSize = this.executor.getCorePoolSize();
        int activeCount = this.executor.getActiveCount();
        return activeCount < coreSize;
    }
}
