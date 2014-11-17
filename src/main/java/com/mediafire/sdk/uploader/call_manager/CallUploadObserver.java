package com.mediafire.sdk.uploader.call_manager;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.uploader.upload_items.Upload;

/**
 * Created by Chris on 11/13/2014.
 */
public interface CallUploadObserver {
    public void stateChange(Upload upload, ApiResponse apiResponse);
}
