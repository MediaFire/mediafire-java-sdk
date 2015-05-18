package com.mediafire.sdk.config;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.requests.DocumentRequest;
import com.mediafire.sdk.requests.HttpApiResponse;
import com.mediafire.sdk.requests.ImageRequest;
import com.mediafire.sdk.requests.UploadPostRequest;

public interface MFActionRequester {
    void endSession();
    void sessionStarted();

    public HttpApiResponse doConversionRequest(ImageRequest imageRequest) throws MFException, MFApiException;
    public HttpApiResponse doConversionRequest(DocumentRequest imageRequest) throws MFException, MFApiException;
    public <T extends ApiResponse> T doUploadRequest(UploadPostRequest uploadRequest, Class<T> classOfT) throws MFException, MFApiException;
}
