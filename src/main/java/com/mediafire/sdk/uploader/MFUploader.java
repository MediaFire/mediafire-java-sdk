package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MediaFireClient;
import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.response_models.MediaFireApiResponse;
import com.mediafire.sdk.response_models.data_models.ResumableUploadModel;
import com.mediafire.sdk.response_models.upload.UploadCheckResponse;
import com.mediafire.sdk.response_models.upload.UploadPollUploadResponse;
import com.mediafire.sdk.uploader.MFRunnablePollUpload.OnPollUploadStatusListener;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Uploader which handles MediaFireUploadRunnable requests. Pausable.
 */
public class MFUploader implements MediaFireRunnableUploadStatusListener, OnPollUploadStatusListener {

    private final ExecutorService executor;

    private final MediaFireClient mediaFire;
    private final MediaFireUploadStore store;


    public MFUploader(MediaFireClient mediaFire, MediaFireUploadStore store, ExecutorService executor) {
        this.mediaFire = mediaFire;
        this.store = store;
        this.executor = executor;
    }

    public void schedule(MediaFireWebUpload upload) {
        this.store.insert(upload);
        startWebUpload(upload);
    }

    public void schedule(MediaFireFileUpload upload) {
        this.store.insert(upload);
        startFileUpload(upload);
    }

    @Override
    public void onGetWebUploadsProgress(MediaFireWebUpload upload, int statusCode, String description) {
        this.store.polling(upload, statusCode, description);
    }

    @Override
    public void onGetWebUploadsFinished(MediaFireWebUpload upload, String quickKey, String filename) {
        this.store.uploadFinished(upload, quickKey, filename);
    }

    @Override
    public void onGetWebUploadsError(MediaFireWebUpload upload, int statusCode, int errorStatus, String description) {
        this.store.pollingError(upload, statusCode, errorStatus, description);
    }

    @Override
    public void onGetWebUploadsSdkException(MediaFireWebUpload upload, MediaFireException e) {
        this.store.sdkException(upload, e);
    }

    @Override
    public void onGetWebUploadsInterrupted(MediaFireWebUpload upload, InterruptedException e) {
        this.store.pollingInterrupted(upload, e);
    }

    @Override
    public void onGetWebUploadsLimitExceeded(MediaFireWebUpload upload) {
        this.store.pollingLimitExceeded(upload);
    }

    @Override
    public void onWebUploadException(MediaFireWebUpload upload, MediaFireException e) {
        this.store.sdkException(upload, e);
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
                    }
                    break;
                case DO_NOT_UPLOAD:
                default:
                    this.store.uploadFinished(upload, duplicateQuickKey, null);
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
    }

    @Override
    public void onCheckUploadApiError(MediaFireFileUpload upload, MediaFireApiResponse response) {
        this.store.apiError(upload, response);
    }

    @Override
    public void onInstantUploadSdkException(MediaFireFileUpload upload, MediaFireException e) {
        this.store.sdkException(upload, e);
    }

    @Override
    public void onInstantUploadApiError(MediaFireFileUpload upload, MediaFireApiResponse response) {
        this.store.apiError(upload, response);
    }

    @Override
    public void onInstantUploadFinished(MediaFireFileUpload upload, String quickKey, String fileName) {
        this.store.uploadFinished(upload, quickKey, fileName);
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
    }

    @Override
    public void onResumableUploadFinishedIncomplete(MediaFireFileUpload upload) {
        this.store.resumableFinishedWithoutAllUnitsReady(upload);
    }

    @Override
    public void onResumableUploadIOException(MediaFireFileUpload upload, IOException e) {
        this.store.fileIOException(upload, e);
    }

    @Override
    public void onResumableUploadApiError(MediaFireFileUpload upload, MediaFireApiResponse response) {
        this.store.apiError(upload, response);
    }

    @Override
    public void onPollUploadFinished(MediaFireFileUpload upload, String quickKey, String fileName) {
        this.store.uploadFinished(upload, quickKey, fileName);
    }

    @Override
    public void onPollUploadProgress(MediaFireFileUpload upload, int statusCode, String description) {
        this.store.polling(upload, statusCode, description);
    }

    @Override
    public void onPollUploadError(MediaFireFileUpload upload, int fileErrorCode, int resultCode, int statusCode, String description) {
        this.store.pollingError(upload, fileErrorCode, resultCode, statusCode, description);
    }

    @Override
    public void onPollUploadSdkException(MediaFireFileUpload upload, MediaFireException e) {
        this.store.sdkException(upload, e);
    }

    @Override
    public void onPollUploadThreadInterrupted(MediaFireFileUpload upload, InterruptedException e) {
        this.store.pollingInterrupted(upload, e);
    }

    @Override
    public void onPollUploadApiError(MediaFireFileUpload upload, UploadPollUploadResponse response) {
        this.store.apiError(upload, response);
    }

    @Override
    public void onPollUploadLimitExceeded(MediaFireFileUpload upload) {
        this.store.pollingLimitExceeded(upload);
    }

    private void startWebUpload(MediaFireWebUpload upload) {
        this.store.uploadQueued(upload);
        MFRunnableWebUpload runnableWebUpload = new MFRunnableWebUpload(this.mediaFire, upload, this);
        this.executor.execute(runnableWebUpload);
    }

    private void startFileUpload(MediaFireFileUpload upload) {
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

}
