package com.mediafire.sdk.config;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.UserGetSessionTokenResponse;
import com.mediafire.sdk.requests.ApiPostRequest;
import com.mediafire.sdk.requests.HttpApiResponse;
import com.mediafire.sdk.requests.PostRequest;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.util.RequestUtil;
import com.mediafire.sdk.util.ResponseUtil;

import java.util.List;
import java.util.Map;

public class DefaultSessionRequester implements MFSessionRequester {

    private final MFCredentials credentials;
    private final MFHttpRequester http;
    private final MFStore<SessionToken> sessionStore;
    private boolean sessionStarted;
    private final Object sessionLock = new Object();
    private final String apiKey;
    private final String appId;

    public DefaultSessionRequester(MFCredentials credentials, String appId, String apiKey, MFHttpRequester http, MFStore<SessionToken> sessionStore) {
        this.credentials = credentials;
        this.apiKey = apiKey;
        this.appId = appId;
        this.http = http;
        this.sessionStore = sessionStore;
    }

    public DefaultSessionRequester(MFCredentials credentials, String appId, MFHttpRequester http, MFStore<SessionToken> sessionStore) {
        this(credentials, appId, null, http, sessionStore);
    }

    @Override
    public void startSessionWithEmail(String email, String password, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException {
        synchronized (sessionLock) {
            // create api request
            ApiPostRequest apiPostRequest = ApiPostRequest.newSessionRequestWithEmail(apiKey, appId, email, password);
            // make request using http to get a response
            makeNewSessionRequest(apiPostRequest, sessionCallbacks);
        }
    }

    @Override
    public void startSessionWithEkey(String ekey, String password, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException {
        // create api request
        ApiPostRequest apiPostRequest = ApiPostRequest.newSessionRequestWithEkey(apiKey, appId, ekey, password);
        // make request using http to get a response
        makeNewSessionRequest(apiPostRequest, sessionCallbacks);
    }

    @Override
    public void startSessionWithFacebook(String facebookAccessToken, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException {
        // create api request
        ApiPostRequest apiPostRequest = ApiPostRequest.newSessionRequestWithFacebook(apiKey, appId, facebookAccessToken);
        // make request using http to get a response
        makeNewSessionRequest(apiPostRequest, sessionCallbacks);
    }

    @Override
    public void endSession() {
        sessionStarted = false;
        sessionStore.clear();
    }

    @Override
    public void sessionStarted() {
        sessionStarted = true;
    }

    @Override
    public <T extends ApiResponse> T doApiRequest(ApiPostRequest apiPostRequest, Class<T> classOfT) throws MFException, MFApiException {
        if (!sessionStarted) {
            throw new MFException("cannot call doApiRequest() if session has not been started");
        }

        SessionToken sessionToken;
        //borrow token if available
        synchronized (sessionStore) {
            if (!sessionStore.available()) {
                doNewSessionRequestWithCredentials();
            }
            sessionToken = sessionStore.get();

        }

        apiPostRequest.getQueryMap().put("session_token", sessionToken.getToken());
        apiPostRequest.getQueryMap().put("signature", RequestUtil.makeSignatureForApiRequest(apiPostRequest));
        PostRequest postRequest = new PostRequest(apiPostRequest);
        HttpApiResponse httpResponse = http.doApiRequest(postRequest);
        ResponseUtil.validateHttpResponse(httpResponse);

        // return token
        synchronized (sessionStore) {
            SessionToken updatedSessionToken = SessionToken.updateSessionToken(sessionToken);
            sessionStore.put(updatedSessionToken);
        }
        return ResponseUtil.makeApiResponseFromHttpResponse(httpResponse, classOfT);
    }

    @Override
    public boolean hasSession() {
        return sessionStore.available();
    }

    private void doNewSessionRequestWithCredentials() throws MFException, MFApiException {
        if (!credentials.isValid()) {
            throw new MFException("cannot make requests if credentials are not valid");
        }

        Map<String, String> credentials = this.credentials.getCredentials();

        if (credentials.containsKey("email")) {
            String email = credentials.get("email");
            String password = credentials.get("password");
            ApiPostRequest apiPostRequest = ApiPostRequest.newSessionRequestWithEmail(apiKey, appId, email, password);
            makeNewSessionRequest(apiPostRequest, null);
        } else if (credentials.containsKey("ekey")) {
            String ekey = credentials.get("ekey");
            String password = credentials.get("password");
            ApiPostRequest apiPostRequest = ApiPostRequest.newSessionRequestWithEkey(apiKey, appId, ekey, password);
            makeNewSessionRequest(apiPostRequest, null);
        } else if (credentials.containsKey("fb_access_token")) {
            String facebook = credentials.get("fb_access_token");
            ApiPostRequest apiPostRequest = ApiPostRequest.newSessionRequestWithFacebook(apiKey, appId, facebook);
            makeNewSessionRequest(apiPostRequest, null);
        } else {
            this.credentials.invalidate();
            throw new MFException("invalid credentials stored");
        }
    }

    private <T extends ApiResponse> T doNewSessionRequest(ApiPostRequest apiPostRequest, Class<T> classOfT) throws MFException {
        PostRequest postRequest = new PostRequest(apiPostRequest);
        HttpApiResponse httpResponse = http.doApiRequest(postRequest);
        ResponseUtil.validateHttpResponse(httpResponse);
        return ResponseUtil.makeApiResponseFromHttpResponse(httpResponse, classOfT);
    }

    private void makeNewSessionRequest(ApiPostRequest apiPostRequest, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException {
        try {
            UserGetSessionTokenResponse apiResponse = doNewSessionRequest(apiPostRequest, UserGetSessionTokenResponse.class);
            // handle the api response by notifying callback and (if successful) set session to started
            handleApiResponse(apiResponse, sessionCallbacks);
        } catch (MFException e) {
            if (sessionCallbacks == null) {
                return;
            }
            for (OnStartSessionCallback sessionCallback : sessionCallbacks) {
                sessionCallback.onSessionFailed(-1, e.getMessage());
            }
        }
    }

    private void handleApiResponse(UserGetSessionTokenResponse apiResponse, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException {
        // throw ApiException if request has api error
        if (apiResponse.hasError()) {
            if (sessionCallbacks == null) {
                throw new MFApiException(apiResponse.getError(), apiResponse.getMessage());
            }

            for (OnStartSessionCallback sessionCallback : sessionCallbacks) {
                sessionCallback.onSessionFailed(apiResponse.getError(), apiResponse.getMessage());
            }
            throw new MFApiException(apiResponse.getError(), apiResponse.getMessage());
        }
        // set session to started state
        sessionStarted = true;
        // store token
        SessionToken sessionToken = SessionToken.makeSessionTokenFromApiResponse(apiResponse);
        sessionStore.put(sessionToken);
        // notify callback session started successfully

        if (sessionCallbacks == null) {
            return;
        }

        for (OnStartSessionCallback sessionCallback : sessionCallbacks) {
            sessionCallback.onSessionStarted();
        }
    }

}
