package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.config.*;
import com.mediafire.sdk.requests.ApiPostRequest;
import com.mediafire.sdk.requests.ImageRequest;
import com.mediafire.sdk.requests.UploadPostRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration contains a set of interface objects used to handle api requests
 */
public class MediaFire implements MFSessionRequester.OnStartSessionCallback {
    private String alternateDomain;
    private final String appId;
    private final String apiKey;
    private final MFCredentials credentials;
    private final MFSessionRequester sessionRequester;
    private final MFActionRequester actionRequester;
    private boolean sessionStarted;


    public MediaFire(Configuration configuration) {
        this.appId = configuration.getAppId();
        this.apiKey = configuration.getApiKey();
        this.credentials = configuration.getCredentials();
        this.sessionRequester = configuration.getSessionRequester();
        this.actionRequester = configuration.getActionRequester();
        this.alternateDomain = configuration.getAlternateDomain();
    }

    public MediaFire(String appId, String apiKey) {
        this(Configuration.getDefault(appId, apiKey));
    }

    public MediaFire(String appId) {
        this(Configuration.getDefault(appId));
    }

    public void endSession() {
        sessionStarted = false;
        sessionRequester.endSession();
        actionRequester.endSession();
        credentials.invalidate();
    }

    public boolean isSessionStarted() {
        return sessionStarted;
    }

    public void startSessionWithEmail(String email, String password, MFSessionRequester.OnStartSessionCallback sessionCallback) throws MFApiException {
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("email", email);
        credentials.put("password", password);
        this.credentials.setCredentials(credentials);
        List<MFSessionRequester.OnStartSessionCallback> sessionCallbacks = new ArrayList<MFSessionRequester.OnStartSessionCallback>();
        sessionCallbacks.add(sessionCallback);
        sessionCallbacks.add(this);
        sessionRequester.startSessionWithEmail(email, password, sessionCallbacks);
    }

    public void startSessionWithEkey(String ekey, String password, MFSessionRequester.OnStartSessionCallback sessionCallback) throws MFApiException {
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("ekey", ekey);
        credentials.put("password", password);
        this.credentials.setCredentials(credentials);
        List<MFSessionRequester.OnStartSessionCallback> sessionCallbacks = new ArrayList<MFSessionRequester.OnStartSessionCallback>();
        sessionCallbacks.add(sessionCallback);
        sessionCallbacks.add(this);
        sessionRequester.startSessionWithEkey(ekey, password, sessionCallbacks);
    }

    public void startSessionWithFacebook(String facebookAccessToken, MFSessionRequester.OnStartSessionCallback sessionCallback) throws MFApiException {
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("fb_access_token", facebookAccessToken);
        this.credentials.setCredentials(credentials);
        List<MFSessionRequester.OnStartSessionCallback> sessionCallbacks = new ArrayList<MFSessionRequester.OnStartSessionCallback>();
        sessionCallbacks.add(sessionCallback);
        sessionCallbacks.add(this);
        sessionRequester.startSessionWithFacebook(facebookAccessToken, sessionCallbacks);
    }

    public <T extends ApiResponse> T doApiRequest(ApiPostRequest apiPostRequest, Class<T> classOfT) throws MFException, MFApiException {
        if (!sessionStarted) {
            throw new MFException("cannot call doRequest() if session has not been started");
        }
        return sessionRequester.doApiRequest(apiPostRequest, classOfT);
    }

    public <T extends ApiResponse> T doUploadRequest(UploadPostRequest uploadRequest, Class<T> classOfT) throws MFException, MFApiException {
        if (!sessionStarted) {
            throw new MFException("cannot call doUploadRequest() if session has not been started");
        }

        return actionRequester.doUploadRequest(uploadRequest, classOfT);
    }

    public <T extends ApiResponse> T doImageRequest(ImageRequest imageRequest, Class<T> classOfT) throws MFException, MFApiException {
        if (!sessionStarted) {
            throw new MFException("cannot call doImageRequest() if session has not been started");
        }

        return actionRequester.doImageRequest(imageRequest, classOfT);
    }

    @Override
    public void onSessionStarted() {
        sessionStarted = true;
        credentials.setValid();
    }

    @Override
    public void onSessionFailed(int code, String message) {
        endSession();
    }
}
