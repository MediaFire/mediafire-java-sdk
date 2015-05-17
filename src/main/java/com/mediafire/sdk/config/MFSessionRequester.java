package com.mediafire.sdk.config;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.List;

public interface MFSessionRequester {
    void startSessionWithEmail(String email, String password, List<OnStartSessionCallback> sessionCallback) throws MFApiException;
    void startSessionWithEkey(String ekey, String password, List<OnStartSessionCallback> sessionCallback) throws MFApiException;
    void startSessionWithFacebook(String facebookAccessToken, List<OnStartSessionCallback> sessionCallback) throws MFApiException;

    void endSession();
    void sessionStarted();

    public <T extends ApiResponse> T doApiRequest(ApiPostRequest apiPostRequest, Class<T> classOfT) throws MFException, MFApiException;

    public boolean hasSession();

    interface OnStartSessionCallback {
        public void onSessionStarted();
        public void onSessionFailed(int code, String message);
    }
}
