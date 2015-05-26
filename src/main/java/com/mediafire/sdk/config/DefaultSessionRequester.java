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
import java.util.logging.Handler;
import java.util.logging.Logger;

public class DefaultSessionRequester implements MFSessionRequester {

    private static final int MIN_TOKENS = 2;
    private static final int MAX_TOKENS = 6;
    private MFCredentials credentials;
    private final MFHttpRequester http;
    private final MFStore<SessionToken> sessionStore;
    private boolean sessionStarted;
    private final Object sessionLock = new Object();
    private final String apiKey;
    private final String appId;
    private final Logger logger;

    public DefaultSessionRequester(MFCredentials credentials, String appId, String apiKey, MFHttpRequester http, MFStore<SessionToken> sessionStore) {
        this.credentials = credentials;
        this.apiKey = apiKey;
        this.appId = appId;
        this.http = http;
        this.sessionStore = sessionStore;
        this.logger = Logger.getLogger("com.mediafire.sdk.config.DefaultSessionRequester");
    }

    public DefaultSessionRequester(MFCredentials credentials, String appId, MFHttpRequester http, MFStore<SessionToken> sessionStore) {
        this(credentials, appId, null, http, sessionStore);
    }

    @Override
    public void startSessionWithEmail(String email, String password, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException, MFException {
        logger.info("startSessionWithEmail()");
        if (sessionStarted) {
            if (sessionCallbacks != null) {
                for (OnStartSessionCallback callback : sessionCallbacks) {
                    callback.onSessionStarted();
                }
            }
            return;
        }
        synchronized (sessionLock) {
            // create api request
            ApiPostRequest apiPostRequest = ApiPostRequest.newSessionRequestWithEmail(apiKey, appId, email, password);
            // make request using http to get a response
            makeNewSessionRequest(apiPostRequest, sessionCallbacks);
        }
    }

    @Override
    public void startSessionWithEkey(String ekey, String password, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException, MFException {
        logger.info("startSessionWithEkey()");
        if (sessionStarted) {
            if (sessionCallbacks != null) {
                for (OnStartSessionCallback callback : sessionCallbacks) {
                    callback.onSessionStarted();
                }
            }
            return;
        }
        synchronized (sessionLock) {
            // create api request
            ApiPostRequest apiPostRequest = ApiPostRequest.newSessionRequestWithEkey(apiKey, appId, ekey, password);
            // make request using http to get a response
            makeNewSessionRequest(apiPostRequest, sessionCallbacks);
        }
    }

    @Override
    public void startSessionWithFacebook(String facebookAccessToken, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException, MFException {
        logger.info("startSessionWithFacebook()");
        if (sessionStarted) {
            if (sessionCallbacks != null) {
                for (OnStartSessionCallback callback : sessionCallbacks) {
                    callback.onSessionStarted();
                }
            }
            return;
        }
        synchronized (sessionLock) {
            // create api request
            ApiPostRequest apiPostRequest = ApiPostRequest.newSessionRequestWithFacebook(apiKey, appId, facebookAccessToken);
            // make request using http to get a response
            makeNewSessionRequest(apiPostRequest, sessionCallbacks);
        }
    }

    @Override
    public void endSession() {
        logger.info("endSession()");
        sessionStarted = false;
        sessionStore.clear();
    }

    @Override
    public void sessionStarted() {
        logger.info("sessionStarted()");
        sessionStarted = true;
        credentials.setValid();
        getNewTokens(MIN_TOKENS);
    }

    @Override
    public <T extends ApiResponse> T doApiRequest(ApiPostRequest apiPostRequest, Class<T> classOfT) throws MFException, MFApiException {
        logger.info("doApiRequest()");
        if (!sessionStarted) {
            logger.severe("doApiRequest() called but session has not been started");
            throw new MFException("cannot call doApiRequest() if session has not been started");
        }

        SessionToken sessionToken = null;
        if (apiPostRequest.requiresToken()) {
            logger.info("doApiRequest() borrowing session token");
            //borrow token if available
            synchronized (sessionStore) {
                if (!sessionStore.available()) {
                    logger.info("doApiRequest() no session tokens are available from store");
                    if (sessionStore.getAvailableCount() < MIN_TOKENS) {
                        getNewTokens(MIN_TOKENS);
                    }
                    doNewSessionRequestWithCredentials();
                }
                sessionToken = sessionStore.get();

                if (sessionToken == null) {
                    logger.severe("doApiRequest() called but could not get a session token from store");
                    throw new MFException("could not get session token from store");
                }
            }
            apiPostRequest.addSessionToken(sessionToken.getToken());
            apiPostRequest.addSignature(RequestUtil.makeSignatureForApiRequest(sessionToken.getSecretKey(), sessionToken.getTime(), apiPostRequest));
            logger.info("doApiRequest() added session token and signature to request");
        }

        PostRequest postRequest = new PostRequest(apiPostRequest);
        HttpApiResponse httpResponse = http.doApiRequest(postRequest);
        ResponseUtil.validateHttpResponse(httpResponse);

        // return token
        synchronized (sessionStore) {
            logger.info("doApiRequest() returning session token to store if there is space");
            if (sessionStore.getAvailableCount() < MAX_TOKENS) {
                logger.info("doApiRequest() session token store has space for token");
                ApiResponse apiResponse = ResponseUtil.makeApiResponseFromHttpResponse(httpResponse, classOfT);
                if (!apiResponse.hasError() && apiResponse.needNewKey() && sessionToken != null) {
                    logger.info("doApiRequest() api response made with token requires token update before storing");
                    SessionToken updatedSessionToken = SessionToken.updateSessionToken(sessionToken);
                    sessionStore.put(updatedSessionToken);
                } else if (!apiResponse.hasError() && sessionToken != null) {
                    sessionStore.put(sessionToken);
                }
            }
        }
        return ResponseUtil.makeApiResponseFromHttpResponse(httpResponse, classOfT);
    }

    @Override
    public boolean hasSession() {
        logger.info("hasSession()");
        return sessionStore.available();
    }

    @Override
    public <T extends ApiResponse> T doApiRequestWithoutSession(ApiPostRequest apiPostRequest, Class<T> classOfT) throws MFException, MFApiException {
        logger.info("doApiRequestWithoutSession()");
        PostRequest postRequest = new PostRequest(apiPostRequest);
        HttpApiResponse httpResponse = http.doApiRequest(postRequest);
        ResponseUtil.validateHttpResponse(httpResponse);
        return ResponseUtil.makeApiResponseFromHttpResponse(httpResponse, classOfT);
    }

    @Override
    public void addLoggerHandler(Handler loggerHandler) {
        logger.addHandler(loggerHandler);
    }

    @Override
    public void setCredentials(MFCredentials credentials) {
        this.credentials = credentials;
    }

    private void doNewSessionRequestWithCredentials() throws MFException, MFApiException {
        logger.info("doNewSessionRequestWithCredentials()");
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

    private <T extends ApiResponse> T doNewSessionRequest(ApiPostRequest apiPostRequest, Class<T> classOfT) throws MFException, MFApiException {
        logger.info("doNewSessionRequest()");
        PostRequest postRequest = new PostRequest(apiPostRequest);
        HttpApiResponse httpResponse = http.doApiRequest(postRequest);
        ResponseUtil.validateHttpResponse(httpResponse);
        return ResponseUtil.makeApiResponseFromHttpResponse(httpResponse, classOfT);
    }

    private void makeNewSessionRequest(ApiPostRequest apiPostRequest, List<OnStartSessionCallback> sessionCallbacks) throws MFApiException {
        logger.info("makeNewSessionRequest()");
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
        logger.info("handleApiResponse()");
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

    private void getNewTokens(int numberOfNewTokensToGet) {
        logger.info("getNewTokens()");
        for (int i = 0; i < numberOfNewTokensToGet; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        doNewSessionRequestWithCredentials();
                    } catch (MFException e) {
                        e.printStackTrace();
                    } catch (MFApiException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

}
