package com.mediafire.sdk.uploader;

import com.mediafire.sdk.uploader.uploaditem.UploadItem;

public interface UploadListenerInterface {

    public void onCancelled(UploadItem uploadItem, String reason);

    public void onProgressUpdate(UploadItem uploadItem, int currentChunk, int totalChunks);

    public void onPolling(UploadItem uploadItem, String message);

    public void onStarted(UploadItem uploadItem);

    public void onCompleted(UploadItem uploadItem, String quickKey);
}
