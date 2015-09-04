package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.response_models.MediaFireApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MFUploadStore implements MediaFireUploadStore {
    private final Logger logger = LoggerFactory.getLogger(MFUploadStore.class);

    private final LinkedBlockingQueue<MediaFireUpload> uploads = new LinkedBlockingQueue<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public void insert(MediaFireWebUpload upload) {
        int id = currentId.addAndGet(1);
        upload.setId(id);
        boolean added = uploads.offer(upload);
        logger.info("inserted new web upload: " + upload + ": " + added);
    }

    @Override
    public void insert(MediaFireFileUpload upload) {
        int id = currentId.addAndGet(1);
        upload.setId(id);
        uploads.offer(upload);
        boolean added = uploads.offer(upload);
        logger.info("inserted new file upload: " + upload + ": " + added);
    }


    @Override
    public MediaFireUpload getNextUpload() {
        MediaFireUpload upload =  uploads.poll();

        logger.info("getting next upload: " + upload);

        return upload;
    }

    @Override
    public void uploadQueued(MediaFireWebUpload upload) {

    }

    @Override
    public void uploadQueued(MediaFireFileUpload upload) {

    }

    @Override
    public void polling(MediaFireWebUpload upload, int statusCode, String description) {

    }

    @Override
    public void polling(MediaFireFileUpload upload, int statusCode, String description) {

    }

    @Override
    public void uploadFinished(MediaFireWebUpload upload, String quickKey, String filename) {

    }

    @Override
    public void uploadFinished(MediaFireFileUpload upload, String quickKey, String fileName) {

    }

    @Override
    public void pollingError(MediaFireWebUpload upload, int statusCode, int errorStatus, String description) {

    }

    @Override
    public void pollingError(MediaFireFileUpload upload, int fileErrorCode, int resultCode, int statusCode, String description) {

    }

    @Override
    public void sdkException(MediaFireWebUpload upload, MediaFireException e) {

    }

    @Override
    public void sdkException(MediaFireFileUpload upload, MediaFireException e) {

    }

    @Override
    public void pollingInterrupted(MediaFireWebUpload upload, InterruptedException e) {

    }

    @Override
    public void pollingInterrupted(MediaFireFileUpload upload, InterruptedException e) {

    }

    @Override
    public void pollingLimitExceeded(MediaFireWebUpload upload) {

    }

    @Override
    public void apiError(MediaFireFileUpload upload, MediaFireApiResponse response) {

    }

    @Override
    public void pollingReady(MediaFireWebUpload upload, String uploadKey) {

    }

    @Override
    public void pollingReady(MediaFireFileUpload upload, String uploadKey) {

    }

    @Override
    public void checkFinished(MediaFireFileUpload upload) {

    }

    @Override
    public void uploadProgress(MediaFireFileUpload upload, double percentFinished) {

    }

    @Override
    public void resumableFinishedWithoutAllUnitsReady(MediaFireFileUpload upload) {

    }

    @Override
    public void fileIOException(MediaFireFileUpload upload, IOException e) {

    }

    @Override
    public void pollingLimitExceeded(MediaFireFileUpload upload) {

    }

    @Override
    public void resumableUploadStarting(MediaFireFileUpload upload) {

    }

    @Override
    public void instantUploadStarting(MediaFireFileUpload upload) {

    }
}
