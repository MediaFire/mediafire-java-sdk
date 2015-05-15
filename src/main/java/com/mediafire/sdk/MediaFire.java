package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.config.*;
import com.mediafire.sdk.requests.ApiRequest;
import com.mediafire.sdk.requests.ImageRequest;
import com.mediafire.sdk.requests.UploadRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration contains a set of interface objects used to handle api requests
 */
public class MediaFire implements MFSessionRequester.OnStartSessionCallback {
    private static final Configuration DEFAULT_CONFIG = Configuration.getDefault();
    private String alternateDomain;
    private final String appId;
    private final String apiKey;
    private final MFCredentials credentials;
    private final MFSessionRequester sessionRequester;
    private final MFHttpRequester httpRequester;
    private final MFActionRequester actionRequester;
    private boolean sessionStarted;


    public MediaFire(String appId, String apiKey, Configuration configuration) {
        this.appId = appId;
        this.apiKey = apiKey;
        this.credentials = configuration.getCredentials();
        this.httpRequester = configuration.getHttpRequester();
        this.sessionRequester = configuration.getSessionRequester();
        this.actionRequester = configuration.getActionRequester();
        this.alternateDomain = configuration.getAlternateDomain();
    }

    public MediaFire(String appId, Configuration configuration) {
        this(appId, null, configuration);
    }

    public MediaFire(String appId, String apiKey) {
        this(appId, apiKey, DEFAULT_CONFIG);
    }

    public MediaFire(String appId) {
        this(appId, DEFAULT_CONFIG);
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
        Map<String, Object> credentials = new HashMap<String, Object>();
        credentials.put("email", email);
        credentials.put("password", password);
        this.credentials.setCredentials(credentials);
        List<MFSessionRequester.OnStartSessionCallback> sessionCallbacks = new ArrayList<MFSessionRequester.OnStartSessionCallback>();
        sessionCallbacks.add(sessionCallback);
        sessionCallbacks.add(this);
        sessionRequester.startSessionWithEmail(apiKey, appId, email, password, sessionCallbacks);
    }

    public void startSessionWithEkey(String ekey, String password, MFSessionRequester.OnStartSessionCallback sessionCallback) throws MFApiException {        Map<String, Object> credentials = new HashMap<String, Object>();
        credentials.put("ekey", ekey);
        credentials.put("password", password);
        this.credentials.setCredentials(credentials);
        List<MFSessionRequester.OnStartSessionCallback> sessionCallbacks = new ArrayList<MFSessionRequester.OnStartSessionCallback>();
        sessionCallbacks.add(sessionCallback);
        sessionCallbacks.add(this);
        sessionRequester.startSessionWithEkey(apiKey, appId, ekey, password, sessionCallbacks);
    }

    public void startSessionWithFacebook(String facebookAccessToken, MFSessionRequester.OnStartSessionCallback sessionCallback) throws MFApiException {
        Map<String, Object> credentials = new HashMap<String, Object>();
        credentials.put("fb_access_token", facebookAccessToken);
        this.credentials.setCredentials(credentials);
        List<MFSessionRequester.OnStartSessionCallback> sessionCallbacks = new ArrayList<MFSessionRequester.OnStartSessionCallback>();
        sessionCallbacks.add(sessionCallback);
        sessionCallbacks.add(this);
        sessionRequester.startSessionWithFacebook(apiKey, appId, facebookAccessToken, sessionCallbacks);
    }

    public <T extends ApiResponse> T doApiRequest(ApiRequest apiRequest, Class<T> classOfT) throws MFException, MFApiException {
        if (!sessionStarted) {
            throw new MFException("cannot call doRequest() if session has not been started");
        }

        return sessionRequester.doApiRequest(apiRequest, classOfT);
    }

    public <T extends ApiResponse> T doUploadRequest(UploadRequest uploadRequest, Class<T> classOfT) throws MFException {
        if (!sessionStarted) {
            throw new MFException("cannot call doUploadRequest() if session has not been started");
        }

        return actionRequester.doUploadRequest(uploadRequest, classOfT);
    }

    public <T extends ApiResponse> T doImageRequest(ImageRequest imageRequest, Class<T> classOfT) throws MFException {
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
