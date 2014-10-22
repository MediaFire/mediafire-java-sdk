package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.RequestGenerator;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.ResponseApiClientError;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.token.SessionToken;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Jonathan Harrison on 10/21/2014.
 */
public class DefaultSessionTokenManager implements SessionTokenManagerInterface {
    private static final String TAG = DefaultSessionTokenManager.class.getCanonicalName();
    private Configuration mConfiguration;
    private ApiClient mApiClient;
    private static final int MIN_SESSION_TOKEN = 1;
    private static final int MAX_SESSION_TOKEN = 3;
    private static final BlockingQueue<SessionToken> mSessionTokens = new LinkedBlockingQueue<SessionToken>(MAX_SESSION_TOKEN);
    private final Object lock = new Object();

    private static int outstandingRequests = 0;
    private static final Object outstandingRequestsLock = new Object();

    public DefaultSessionTokenManager(){ }

    @Override
    public void initialize(Configuration configuration) {
        DefaultLogger.log().v(TAG, "initialize");
        mConfiguration = configuration;
        mApiClient = new ApiClient(configuration);
    }

    @Override
    public void shutdown() {
        DefaultLogger.log().v(TAG, "shutdown");
    }

    @Override
    public void receiveSessionToken(SessionToken token) {
        DefaultLogger.log().v(TAG, "receiveSessionToken");
        subtractOutstandingRequest();
        if (token != null) {
            if(mSessionTokens.size() < MAX_SESSION_TOKEN) {
                mSessionTokens.offer(token);
            }
        }
    }

    @Override
    public SessionToken borrowSessionToken() {
        DefaultLogger.log().v(TAG, "borrowSessionToken");
        synchronized (lock) {
            if(mSessionTokens.size() < MIN_SESSION_TOKEN) {
                for(int i = mSessionTokens.size(); i < MIN_SESSION_TOKEN; i++) {
                    new NewSessionTokenThread().start();
                }
            }

            try {
                SessionToken sessionToken = mSessionTokens.take();
                if(sessionToken.getTokenString() == null) {
                    return null;
                }
                return sessionToken;
            } catch (InterruptedException e) {
                return null;
            }
        }
    }

    @Override
    public void getNewSessionTokenFailed(Response response) {
        DefaultLogger.log().v(TAG, "getNewSessionTokenFailed");
        subtractOutstandingRequest();
        synchronized (outstandingRequestsLock) {
            if (outstandingRequests == 0 && mSessionTokens.isEmpty()) {
                SessionToken badToken = new SessionToken(null, null, null);
                mSessionTokens.offer(badToken);
            }
        }
    }

    private class NewSessionTokenThread extends Thread {
        @Override
        public void run() {
            DefaultLogger.log().v(TAG, "requestNewSessionToken");
            Request request = new RequestGenerator().generateRequestObject("1.2", "user", "get_session_token.php");
            request.addQueryParameter("application_id", mConfiguration.getDeveloperCredentials().getCredentials().get("application_id"));
            request.addQueryParameter("response_format", "json");
            request.addQueryParameter("token_version", 2);
            addOutstandingRequest();
            Result result = mApiClient.doRequest(request);
            Response response = result.getResponse();
            if(response.getClass() == ResponseApiClientError.class) {
                ResponseApiClientError responseApiClientError = (ResponseApiClientError) result.getResponse();
                DefaultLogger.log().e(TAG, responseApiClientError.getErrorMessage());
                SessionToken badToken = new SessionToken(null, null, null);
                mSessionTokens.offer(badToken);
            }
        }
    }

    private void addOutstandingRequest() {
        synchronized (outstandingRequestsLock) {
            outstandingRequests++;
        }
    }

    private void subtractOutstandingRequest() {
        synchronized (outstandingRequestsLock) {
            outstandingRequests--;
        }
    }

}
