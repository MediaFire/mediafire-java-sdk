package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.response_models.MediaFireApiResponse;

import java.io.IOException;

public interface MediaFireUploadStore {
    void insert(MediaFireWebUpload upload);

    void insert(MediaFireFileUpload upload);

    MediaFireUpload getNextUpload();

    void uploadQueued(MediaFireWebUpload upload);

    void uploadQueued(MediaFireFileUpload upload);

    void polling(MediaFireWebUpload upload, int statusCode, String description);

    void polling(MediaFireFileUpload upload, int statusCode, String description);

    void uploadFinished(MediaFireWebUpload upload, String quickKey, String filename);

    void uploadFinished(MediaFireFileUpload upload, String quickKey, String fileName);

    void pollingError(MediaFireWebUpload upload, int statusCode, int errorStatus, String description);

    void pollingError(MediaFireFileUpload upload, int fileErrorCode, int resultCode, int statusCode, String description);

    void sdkException(MediaFireWebUpload upload, MediaFireException e);

    void sdkException(MediaFireFileUpload upload, MediaFireException e);

    void fileIOException(MediaFireFileUpload upload, IOException e);

    void pollingInterrupted(MediaFireWebUpload upload, InterruptedException e);

    void pollingInterrupted(MediaFireFileUpload upload, InterruptedException e);

    void pollingLimitExceeded(MediaFireWebUpload upload);

    void pollingLimitExceeded(MediaFireFileUpload upload);

    void apiError(MediaFireFileUpload upload, MediaFireApiResponse response);

    void pollingReady(MediaFireWebUpload upload, String uploadKey);

    void pollingReady(MediaFireFileUpload upload, String uploadKey);

    void checkFinished(MediaFireFileUpload upload);

    void uploadProgress(MediaFireFileUpload upload, double percentFinished);

    void resumableFinishedWithoutAllUnitsReady(MediaFireFileUpload upload);

    void resumableUploadStarting(MediaFireFileUpload upload);

    void instantUploadStarting(MediaFireFileUpload upload);
}
