package com.mediafire.sdk.config;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.requests.ApiRequest;

import java.util.List;

public interface MFSessionRequester {
    void startSessionWithEmail(String apiKey, String appId, String email, String password, List<OnStartSessionCallback> sessionCallback) throws MFApiException;
    void startSessionWithEkey(String apiKey, String appId, String ekey, String password, List<OnStartSessionCallback> sessionCallback) throws MFApiException;
    void startSessionWithFacebook(String apiKey, String appId, String facebookAccessToken, List<OnStartSessionCallback> sessionCallback) throws MFApiException;

    void endSession();
    void sessionStarted();

    public <T extends ApiResponse> T doApiRequest(ApiRequest apiRequest, Class<T> classOfT) throws MFException, MFApiException;

    interface OnStartSessionCallback {
        public void onSessionStarted();
        public void onSessionFailed(int code, String message);
    }
}
