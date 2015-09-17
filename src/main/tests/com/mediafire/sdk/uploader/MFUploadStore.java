package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.response_models.MediaFireApiResponse;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class MFUploadStore implements MediaFireUploadStore {

    private final LinkedBlockingQueue<MediaFireUpload> uploads = new LinkedBlockingQueue<>();
    private final AtomicInteger currentId = new AtomicInteger(0);
    private final int count;
    private int success = 0;
    private int fail = 0;

    public MFUploadStore(int count) {
        this.count = count;
    }

    @Override
    public void insert(MediaFireWebUpload upload) {
        int id = currentId.addAndGet(1);
        upload.setId(id);
        uploads.offer(upload);
    }

    @Override
    public void insert(MediaFireFileUpload upload) {
        int id = currentId.addAndGet(1);
        upload.setId(id);
        uploads.offer(upload);
    }

    public boolean isWaitingForUploads() {
        return success + fail != count;
    }

    @Override
    public MediaFireUpload getNextUpload() {
        return uploads.poll();
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
        success++;
    }

    @Override
    public void uploadFinished(MediaFireFileUpload upload, String quickKey, String fileName) {
        success++;
    }

    @Override
    public void pollingError(MediaFireWebUpload upload, int statusCode, int errorStatus, String description) {
        fail++;
    }

    @Override
    public void pollingError(MediaFireFileUpload upload, int fileErrorCode, int resultCode, int statusCode, String description) {
        fail++;
    }

    @Override
    public void sdkException(MediaFireWebUpload upload, MediaFireException e) {
        fail++;
    }

    @Override
    public void sdkException(MediaFireFileUpload upload, MediaFireException e) {
        fail++;
    }

    @Override
    public void pollingInterrupted(MediaFireWebUpload upload, InterruptedException e) {
        fail++;
    }

    @Override
    public void pollingInterrupted(MediaFireFileUpload upload, InterruptedException e) {

    }

    @Override
    public void pollingLimitExceeded(MediaFireWebUpload upload) {
        fail++;
    }

    @Override
    public void apiError(MediaFireFileUpload upload, MediaFireApiResponse response) {
        fail++;
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
        fail++;
    }

    @Override
    public void fileIOException(MediaFireFileUpload upload, IOException e) {
        fail++;
    }

    @Override
    public void pollingLimitExceeded(MediaFireFileUpload upload) {
        fail++;
    }

    @Override
    public void resumableUploadStarting(MediaFireFileUpload upload) {

    }

    @Override
    public void instantUploadStarting(MediaFireFileUpload upload) {

    }
}
