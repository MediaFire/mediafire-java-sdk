package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Chris on 5/18/2015.
 */
public class MediaFireUploader implements MediaFireUploadHandler {
    private final MediaFire mediaFire;
    private final MediaFireUploadStore mediaFireUploadStore;
    private final int pollStatusToFinish;
    private final LinkedBlockingDeque<MediaFireUpload> uploadDeque = new LinkedBlockingDeque<MediaFireUpload>();
    private final ArrayList<MediaFireUploadHandler> handlers = new ArrayList<MediaFireUploadHandler>();

    public MediaFireUploader(MediaFire mediaFire, MediaFireUploadStore mediaFireUploadStore, int pollStatusToFinish) {
        this.mediaFire = mediaFire;
        this.mediaFireUploadStore = mediaFireUploadStore;
        this.pollStatusToFinish = pollStatusToFinish;
    }

    public void addHandler(MediaFireUploadHandler handler) {
        handlers.add(handler);
    }

    public void removeHandler(MediaFireUploadHandler handler) {
        handlers.remove(handler);
    }

    public void startUploading() {

    }

    public void pauseUploading() {

    }

    public long addToQueue(File file, MediaFireUpload.ActionOnInAccount actionOnInAccount, HashMap<String, Object> info) {
        long id =  mediaFireUploadStore.insert(file, actionOnInAccount, info);
        return id;
    }

    @Override
    public void uploadFailed(long id, MFException e) {
        for (MediaFireUploadHandler handler : handlers) {
            handler.uploadFailed(id, e);
        }
    }

    @Override
    public void uploadFailed(long id, MFApiException e) {
        for (MediaFireUploadHandler handler : handlers) {
            handler.uploadFailed(id, e);
        }
    }

    @Override
    public void uploadFailed(long id, IOException e) {
        for (MediaFireUploadHandler handler : handlers) {
            handler.uploadFailed(id, e);
        }
    }

    @Override
    public void uploadFailed(long id, InterruptedException e) {
        for (MediaFireUploadHandler handler : handlers) {
            handler.uploadFailed(id, e);
        }
    }

    @Override
    public void uploadProgress(long id, double percentFinished) {
        for (MediaFireUploadHandler handler : handlers) {
            handler.uploadProgress(id, percentFinished);
        }
    }

    @Override
    public void uploadFinished(long id, String quickKey, String fileName) {
        for (MediaFireUploadHandler handler : handlers) {
            handler.uploadFinished(id, quickKey, fileName);
        }
    }

    @Override
    public void uploadPolling(long id, int statusCode, String description) {
        for (MediaFireUploadHandler handler : handlers) {
            handler.uploadPolling(id, statusCode, description);
        }
    }
}
