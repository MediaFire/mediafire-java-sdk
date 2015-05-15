package com.mediafire.sdk.config;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.UserGetSessionTokenResponse;
import com.mediafire.sdk.requests.ApiRequest;
import com.mediafire.sdk.requests.HttpApiResponse;
import com.mediafire.sdk.requests.PostRequest;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.util.ResponseUtil;

import java.util.List;
import java.util.Map;

public class DefaultSessionRequester implements MFSessionRequester {

    private MFCredentials credentials;
    private final MFHttpRequester http;
    private final MFStore<SessionToken> sessionStore;
    private boolean sessionStarted;
    private final Object sessionLock = new Object();
    private String apiKey;
    private String appId;

    public DefaultSessionRequester(MFCredentials credentials, MFHttpRequester http, MFStore<SessionToken> sessionStore) {
        this.credentials = credentials;
        this.http = http;
        this.sessionStore = sessionStore;
    }

    @Override
    public void startSessionWithEmail(String apiKey, String appId, String email, String password, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException {
        synchronized (sessionLock) {
            // create api request
            ApiRequest apiRequest = ApiRequest.newSessionRequestWithEmail(apiKey, appId, email, password);
            // make request using http to get a response
            makeNewSessionRequest(apiRequest, sessionCallbacks);
        }
    }

    @Override
    public void startSessionWithEkey(String apiKey, String appId, String ekey, String password, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException {
        // create api request
        ApiRequest apiRequest = ApiRequest.newSessionRequestWithEkey(apiKey, appId, ekey, password);
        // make request using http to get a response
        makeNewSessionRequest(apiRequest, sessionCallbacks);
    }

    @Override
    public void startSessionWithFacebook(String apiKey, String appId, String facebookAccessToken, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException {
        // create api request
        ApiRequest apiRequest = ApiRequest.newSessionRequestWithFacebook(apiKey, appId, facebookAccessToken);
        // make request using http to get a response
        makeNewSessionRequest(apiRequest, sessionCallbacks);
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
    public <T extends ApiResponse> T doApiRequest(ApiRequest apiRequest, Class<T> classOfT) throws MFException, MFApiException {
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

        HttpApiResponse httpResponse = http.doApiRequest(PostRequest.fromApiRequest(apiRequest));
        ResponseUtil.validateHttpResponse(httpResponse);

        // return token
        synchronized (sessionStore) {
            sessionStore.put(sessionToken);
        }
        return ResponseUtil.makeApiResponseFromHttpResponse(httpResponse, classOfT);
    }

    private void doNewSessionRequestWithCredentials() throws MFException, MFApiException {
        if (!credentials.isValid()) {
            throw new MFException("cannot make requests if credentials are not valid");
        }

        Map<String, String> credentials = this.credentials.getCredentials();

        if (credentials.containsKey("email")) {
            String email = credentials.get("email");
            String password = credentials.get("password");
            ApiRequest apiRequest = ApiRequest.newSessionRequestWithEmail(apiKey, appId, email, password);
            makeNewSessionRequest(apiRequest, null);
        } else if (credentials.containsKey("ekey")) {
            String ekey = credentials.get("ekey");
            String password = credentials.get("password");
            ApiRequest apiRequest = ApiRequest.newSessionRequestWithEkey(apiKey, appId, ekey, password);
            makeNewSessionRequest(apiRequest, null);
        } else if (credentials.containsKey("fb_access_token")) {
            String facebook = credentials.get("fb_access_token");
            ApiRequest apiRequest = ApiRequest.newSessionRequestWithFacebook(apiKey, appId, facebook);
            makeNewSessionRequest(apiRequest, null);
        } else {
            this.credentials.invalidate();
            throw new MFException("invalid credentials stored");
        }
    }

    private <T extends ApiResponse> T doNewSessionRequest(ApiRequest apiRequest, Class<T> classOfT) throws MFException {
        HttpApiResponse httpResponse = http.doApiRequest(PostRequest.fromApiRequest(apiRequest));
        ResponseUtil.validateHttpResponse(httpResponse);
        return ResponseUtil.makeApiResponseFromHttpResponse(httpResponse, classOfT);
    }

    private void makeNewSessionRequest(ApiRequest apiRequest, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException {
        try {
            UserGetSessionTokenResponse apiResponse = doNewSessionRequest(apiRequest, UserGetSessionTokenResponse.class);
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
