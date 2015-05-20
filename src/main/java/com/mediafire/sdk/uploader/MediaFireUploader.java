package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Chris on 5/18/2015.
 */
public class MediaFireUploader implements MediaFireUploadHandler {
    private final MediaFireUploadExecutor executor;
    private final LinkedBlockingDeque<Runnable> queue;

    private final ArrayList<MediaFireUploadHandler> handlers = new ArrayList<MediaFireUploadHandler>();
    private boolean coreThreadsStarted = false;


    public MediaFireUploader(int concurrentUploads) {
        this.queue = new LinkedBlockingDeque<Runnable>();
        this.executor = new MediaFireUploadExecutor(concurrentUploads, 10, 10, TimeUnit.SECONDS, queue);
    }

    public void addHandler(MediaFireUploadHandler handler) {
        handlers.add(handler);
    }

    public void removeHandler(MediaFireUploadHandler handler) {
        handlers.remove(handler);
    }

    public int getQueueSize() {
        return queue.size();
    }

    public void schedule(MediaFireUpload runnable) {
        queue.offer(runnable);
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

    public void clear() {
        queue.clear();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
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
