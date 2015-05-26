package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.MFActionRequester;
import com.mediafire.sdk.config.MFCredentials;
import com.mediafire.sdk.config.MFSessionRequester;
import com.mediafire.sdk.requests.*;
import com.mediafire.sdk.token.ActionToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * object used to start a mediafire session and make api requests.
 */
public class MediaFire implements MFSessionRequester.OnStartSessionCallback {

    private final String alternateDomain;
    private final MFCredentials credentials;
    private final MFSessionRequester sessionRequester;
    private final MFActionRequester actionRequester;
    private final Logger logger = Logger.getLogger("com.mediafire.sdk.MediaFire");
    private boolean sessionStarted;


    public MediaFire(Configuration configuration) {
        this.credentials = configuration.getCredentials();
        this.sessionRequester = configuration.getSessionRequester();
        this.actionRequester = configuration.getActionRequester();
        this.alternateDomain = configuration.getAlternateDomain();
    }

    public MediaFire(String appId, String apiKey) {
        this(Configuration.createConfiguration(appId, apiKey));
    }

    public MediaFire(String appId) {
        this(Configuration.createConfiguration(appId));
    }

    public void addLoggerHandler(Handler handler) {
        this.logger.addHandler(handler);
    }

    public void endSession() {
        logger.info("endSession()");
        sessionStarted = false;
        sessionRequester.endSession();
        actionRequester.endSession();
        credentials.invalidate();
    }

    public boolean isSessionStarted() {
        logger.info("isSessionStarted()");
        return sessionStarted;
    }

    public void startSessionWithEmail(String email, String password, MFSessionRequester.OnStartSessionCallback sessionCallback) throws MFApiException, MFException {
        logger.info("startSessionWithEmail()");
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
        logger.info("startSessionWithEkey()");
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
        logger.info("startSessionWithFacebook()");
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

    public <T extends ApiResponse> T doApiRequest(ApiPostRequest apiPostRequest, Class<T> classOfT) throws MFException, MFApiException, MFSessionNotStartedException {
        logger.info("doApiRequest()");
        if (!sessionStarted) {
            logger.severe("doApiRequest() called without session being started");
            throw new MFSessionNotStartedException();
        }

        if (alternateDomain == null || alternateDomain.isEmpty()) {
            return sessionRequester.doApiRequest(apiPostRequest, classOfT);
        } else {
            logger.info("doApiRequest() making request with alternate domain: " + alternateDomain);
            ApiPostRequest apiPostRequestWithAlternateDomain =
                    new ApiPostRequest(apiPostRequest, alternateDomain);
            return sessionRequester.doApiRequest(apiPostRequestWithAlternateDomain, classOfT);
        }
    }

    public <T extends ApiResponse> T doApiRequestWithoutSession(ApiPostRequest apiPostRequest, Class<T> classOfT) throws MFException, MFApiException {
        logger.info("doApiRequestWithoutSession()");
        if (alternateDomain == null || alternateDomain.isEmpty()) {
            return sessionRequester.doApiRequestWithoutSession(apiPostRequest, classOfT);
        } else {
            logger.info("doApiRequestWithoutSession() making request with alternate domain: " + alternateDomain);
            ApiPostRequest apiPostRequestWithAlternateDomain =
                    new ApiPostRequest(apiPostRequest, alternateDomain);
            return sessionRequester.doApiRequestWithoutSession(apiPostRequestWithAlternateDomain, classOfT);
        }
    }

    public <T extends ApiResponse> T doUploadRequest(UploadPostRequest uploadRequest, Class<T> classOfT) throws MFException, MFApiException, MFSessionNotStartedException {
        logger.info("isSessionStarted()");
        if (!sessionStarted) {
            logger.severe("doUploadRequest() called without session being started");
            throw new MFSessionNotStartedException();
        }

        return actionRequester.doUploadRequest(uploadRequest, classOfT);
    }

    public HttpApiResponse doImageRequest(ImageRequest imageRequest) throws MFException, MFApiException, MFSessionNotStartedException {
        logger.info("isSessionStarted()");
        if (!sessionStarted) {
            logger.severe("doImageRequest() called without session being started");
            throw new MFSessionNotStartedException();
        }

        return actionRequester.doConversionRequest(imageRequest);
    }

    public HttpApiResponse doDocumentRequest(DocumentRequest documentRequest) throws MFException, MFApiException, MFSessionNotStartedException {
        logger.info("isSessionStarted()");
        if (!sessionStarted) {
            logger.severe("doDocumentRequest() called without session being started");
            throw new MFSessionNotStartedException();
        }

        return actionRequester.doConversionRequest(documentRequest);
    }

    public ActionToken borrowImageToken() throws MFException, MFApiException, MFSessionNotStartedException {
        logger.info("borrowImageToken()");
        if (!sessionStarted) {
            logger.severe("borrowImageToken() called without session being started");
            throw new MFSessionNotStartedException();
        }

        return actionRequester.borrowImageToken();
    }

    @Override
    public void onSessionStarted() {
        logger.info("onSessionStarted()");
        sessionStarted = true;
        actionRequester.sessionStarted();
        sessionRequester.sessionStarted();
        credentials.setValid();
    }

    @Override
    public void onSessionFailed(int code, String message) {
        logger.info("onSessionFailed()");
        endSession();
    }
}
