package com.mediafire.sdk.config;

import com.mediafire.sdk.MFException;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.requests.ImageRequest;
import com.mediafire.sdk.requests.UploadRequest;

public interface MFActionRequester {
    void endSession();
    void sessionStarted();

    public <T extends ApiResponse> T doImageRequest(ImageRequest imageRequest, Class<T> classOfT) throws MFException;
    public <T extends ApiResponse> T doUploadRequest(UploadRequest uploadRequest, Class<T> classOfT) throws MFException;
}
