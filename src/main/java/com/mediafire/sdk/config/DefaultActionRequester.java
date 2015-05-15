package com.mediafire.sdk.config;

import com.mediafire.sdk.MFException;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.requests.ImageRequest;
import com.mediafire.sdk.requests.UploadRequest;
import com.mediafire.sdk.token.ActionToken;

public class DefaultActionRequester implements MFActionRequester {
    private final MFHttpRequester httpRequester;
    private final MFSessionRequester sessionRequester;
    private final MFStore<ActionToken> imageStore;
    private final MFStore<ActionToken> uploadStore;

    private boolean sessionStarted;

    public DefaultActionRequester(MFHttpRequester httpRequester, MFSessionRequester sessionRequester,
                                  MFStore<ActionToken> imageStore, MFStore<ActionToken> uploadStore) {
        this.httpRequester = httpRequester;
        this.sessionRequester = sessionRequester;
        this.imageStore = imageStore;
        this.uploadStore = uploadStore;
    }

    @Override
    public void endSession() {
        sessionStarted = false;
        imageStore.clear();
        uploadStore.clear();
    }

    @Override
    public void sessionStarted() {
        sessionStarted = true;
    }

    @Override
    public <T extends ApiResponse> T doImageRequest(ImageRequest imageRequest, Class<T> classOfT) throws MFException {
        if (!sessionStarted) {
            throw new MFException("cannot call doImageRequest() if session isn't started");
        }
        return null;
    }

    @Override
    public <T extends ApiResponse> T doUploadRequest(UploadRequest uploadRequest, Class<T> classOfT) throws MFException {
        if (!sessionStarted) {
            throw new MFException("cannot call doImageRequest() if session isn't started");
        }
        return null;
    }
}
