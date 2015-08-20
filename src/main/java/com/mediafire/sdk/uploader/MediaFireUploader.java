package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MediaFireException;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Uploader which handles MediaFireUpload requests. Pausable.
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

    /**
     * adds a handler to be notified of upload state changes (@see MediaFireUploadHandler}
     *
     * @param handler MediaFireUploadHandler
     */
    public void addHandler(MediaFireUploadHandler handler) {
        handlers.add(handler);
    }

    /**
     * removes a handler to be notified of upload state changes (@see MediaFireUploadHandler}
     *
     * @param handler MediaFireUploadHandler
     */
    public void removeHandler(MediaFireUploadHandler handler) {
        handlers.remove(handler);
    }

    /**
     * gets the current queue size
     *
     * @return int
     */
    public int getQueueSize() {
        return queue.size();
    }

    /**
     * adds an upload to the queue
     *
     * @param mediaFireUpload a MediaFireUpload
     */
    public void schedule(MediaFireUpload mediaFireUpload) {
        queue.offer(mediaFireUpload);
    }

    /**
     * pauses uploading from the queue
     */
    public void pause() {
        executor.pause();
    }

    /**
     * resumes uploading from the queue (must be called to start uploading initially.
     */
    public void resume() {
        if (!coreThreadsStarted) {
            coreThreadsStarted = true;
            executor.prestartAllCoreThreads();
        }
        executor.resume();
    }

    /**
     * if the uploader is paused
     * @return true if paused
     */
    public boolean isPaused() {
        return executor.isPaused();
    }

    /**
     * if the uploader is running
     * @return true if running
     */
    public boolean isRunning() {
        return executor.isRunning();
    }

    /**
     * clears the upload queue
     */
    public void clear() {
        queue.clear();
    }

    /**
     * whether or not the upload queue is empty
     *
     * @return true if the upload queue is empty
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void uploadFailed(long id, MediaFireException e) {
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
