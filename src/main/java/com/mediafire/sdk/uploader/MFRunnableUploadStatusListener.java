package com.mediafire.sdk.uploader;

/**
 * Created by christophernajar on 9/2/15.
 */
public interface MFRunnableUploadStatusListener extends
        MFRunnableWebUpload.OnWebUploadStatusListener,
        MFRunnableCheckUpload.OnCheckUploadStatusListener,
        MFRunnableGetWebUpload.OnGetWebUploadStatusListener,
        MFRunnableInstantUpload.OnInstantUploadStatusListener,
        MFRunnableResumableUpload.OnResumableUploadStatusListener {
}
