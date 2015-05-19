package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.MFActionRequester;
import com.mediafire.sdk.config.MFCredentials;
import com.mediafire.sdk.config.MFSessionRequester;
import com.mediafire.sdk.requests.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration contains a set of interface objects used to handle api requests
 */
public class MediaFire implements MFSessionRequester.OnStartSessionCallback {

    private final String alternateDomain;
    private final MFCredentials credentials;
    private final MFSessionRequester sessionRequester;
    private final MFActionRequester actionRequester;
    private boolean sessionStarted;


    public MediaFire(Configuration configuration) {
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

    public void startSessionWithEmail(String email, String password, MFSessionRequester.OnStartSessionCallback sessionCallback) throws MFApiException, MFException {
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("email", email);
        credentials.put("password", password);
        this.credentials.setCredentials(credentials);
        List<MFSessionRequester.OnStartSessionCallback> sessionCallbacks = new ArrayList<MFSessionRequester.OnStartSessionCallback>();
        if (sessionCallback != null) {
            sessionCallbacks.add(sessionCallback);
        }
        sessionCallbacks.add(this);
        sessionRequester.startSessionWithEmail(email, password, sessionCallbacks);
    }

    public void startSessionWithEkey(String ekey, String password, MFSessionRequester.OnStartSessionCallback sessionCallback) throws MFApiException, MFException {
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("ekey", ekey);
        credentials.put("password", password);
        this.credentials.setCredentials(credentials);
        List<MFSessionRequester.OnStartSessionCallback> sessionCallbacks = new ArrayList<MFSessionRequester.OnStartSessionCallback>();
        if (sessionCallback != null) {
            sessionCallbacks.add(sessionCallback);
        }
        sessionCallbacks.add(this);
        sessionRequester.startSessionWithEkey(ekey, password, sessionCallbacks);
    }

    public void startSessionWithFacebook(String facebookAccessToken, MFSessionRequester.OnStartSessionCallback sessionCallback) throws MFApiException, MFException {
        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("fb_access_token", facebookAccessToken);
        this.credentials.setCredentials(credentials);
        List<MFSessionRequester.OnStartSessionCallback> sessionCallbacks = new ArrayList<MFSessionRequester.OnStartSessionCallback>();
        if (sessionCallback != null) {
            sessionCallbacks.add(sessionCallback);
        }
        sessionCallbacks.add(this);
        sessionRequester.startSessionWithFacebook(facebookAccessToken, sessionCallbacks);
    }

    public <T extends ApiResponse> T doApiRequest(ApiPostRequest apiPostRequest, Class<T> classOfT) throws MFException, MFApiException {
        if (!sessionStarted) {
            throw new MFException("cannot call doRequest() if session has not been started");
        }

        if (alternateDomain == null || alternateDomain.isEmpty()) {
            return sessionRequester.doApiRequest(apiPostRequest, classOfT);
        } else {
            ApiPostRequest apiPostRequestWithAlternateDomain =
                    new ApiPostRequest(apiPostRequest, alternateDomain);
            return sessionRequester.doApiRequest(apiPostRequestWithAlternateDomain, classOfT);
        }
    }

    public <T extends ApiResponse> T doUploadRequest(UploadPostRequest uploadRequest, Class<T> classOfT) throws MFException, MFApiException {
        if (!sessionStarted) {
            throw new MFException("cannot call doUploadRequest() if session has not been started");
        }

        return actionRequester.doUploadRequest(uploadRequest, classOfT);
    }

    public HttpApiResponse doImageRequest(ImageRequest imageRequest) throws MFException, MFApiException {
        if (!sessionStarted) {
            throw new MFException("cannot call doConversionRequest() if session has not been started");
        }

        return actionRequester.doConversionRequest(imageRequest);
    }

    public HttpApiResponse doDocumentRequest(DocumentRequest documentRequest) throws MFException, MFApiException {
        if (!sessionStarted) {
            throw new MFException("cannot call doConversionRequest() if session has not been started");
        }

        return actionRequester.doConversionRequest(documentRequest);
    }

    @Override
    public void onSessionStarted() {
        sessionStarted = true;
        actionRequester.sessionStarted();
        sessionRequester.sessionStarted();
        credentials.setValid();
    }

    @Override
    public void onSessionFailed(int code, String message) {
        endSession();
    }
}
