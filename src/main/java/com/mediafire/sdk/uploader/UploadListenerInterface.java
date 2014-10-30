package com.mediafire.sdk.uploader;

import com.mediafire.sdk.uploader.uploaditem.UploadItem;

/**
 * UploadListenerInterface is an interface to listen for certain upload events
 */
public interface UploadListenerInterface {

    /**
     * Called when the upload is cancelled
     * @param uploadItem UploadItem that was cancelled
     * @param reason String reason for cancellation
     */
    public void onCancelled(UploadItem uploadItem, String reason);

    /**
     * Called when progress is made on an upload
     * @param uploadItem UploadItem that progress was made on
     * @param currentChunk int for the current chunk uploaded
     * @param totalChunks int for the total number of chunks
     */
    public void onProgressUpdate(UploadItem uploadItem, int currentChunk, int totalChunks);

    /**
     * Called the polling is made on an upload
     * @param uploadItem UploadItem that polling happened on
     * @param message String message
     */
    public void onPolling(UploadItem uploadItem, String message);

    /**
     * Called when an upload is started
     * @param uploadItem UploadItem that was started
     */
    public void onStarted(UploadItem uploadItem);

    /**
     * Called when an upload is completed
     * @param uploadItem UploadItem that is complete
     * @param quickKey String quickKey of the uploaded item
     */
    public void onCompleted(UploadItem uploadItem, String quickKey);
}
