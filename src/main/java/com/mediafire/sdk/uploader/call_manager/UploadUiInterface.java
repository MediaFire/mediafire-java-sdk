package com.mediafire.sdk.uploader.call_manager;

import com.mediafire.sdk.uploader.upload_items.Upload;

/**
 * Created by Chris on 11/13/2014.
 */
public interface UploadUiInterface {
    /**
     * Called when the upload is cancelled
     * @param upload UploadItem that was cancelled
     * @param message String reason for cancellation
     */
    public void onCancelled(Upload upload, String message);

    /**
     * Called when progress is made on an upload
     * @param upload UploadItem that progress was made on
     * @param currentChunk int for the current chunk uploaded
     * @param totalChunks int for the total number of chunks
     */
    public void onProgressUpdate(Upload upload, int currentChunk, int totalChunks);

    /**
     * Called the polling is made on an upload
     * @param upload UploadItem that polling happened on
     * @param message String message
     */
    public void onPolling(Upload upload, String message);

    /**
     * Called when an upload is started
     * @param upload UploadItem that was started
     */
    public void onStarted(Upload upload);

    /**
     * Called when an upload is completed
     * @param upload UploadItem that is complete
     * @param quickKey String quickKey of the uploaded item
     */
    public void onCompleted(Upload upload, String quickKey);
}
