package com.mediafire.sdk.uploader;

import com.mediafire.sdk.uploader.uploaditem.MFUploadItem;

public interface MFUploadListenerInterface {

    public void onCancelled(MFUploadItem mfUploadItem);

    public void onProgressUpdate(MFUploadItem mfUploadItem, int currentChunk, int totalChunks);

    public void onPolling(MFUploadItem mfUploadItem, String message);

    public void onStarted(MFUploadItem mfUploadItem);

    public void onCompleted(MFUploadItem mfUploadItem);
}
