package com.mediafire.sdk.uploader;

public interface MediaFireRunnableUploadStatusListener extends
        MFRunnableWebUpload.OnWebUploadStatusListener,
        MFRunnableCheckUpload.OnCheckUploadStatusListener,
        MFRunnableGetWebUpload.OnGetWebUploadStatusListener,
        MFRunnableInstantUpload.OnInstantUploadStatusListener,
        MFRunnableResumableUpload.OnResumableUploadStatusListener {
}
