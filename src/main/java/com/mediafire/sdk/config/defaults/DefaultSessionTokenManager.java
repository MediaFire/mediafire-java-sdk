package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ClientHelperNewSessionToken;
import com.mediafire.sdk.clients.ApiRequestGenerator;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.CredentialsInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
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
 * DefaultSessionTokenManager is a default implementation of the SessionTokenManagerInterface
 * A custom implementation is recommended
 */
public class DefaultSessionTokenManager implements SessionTokenManagerInterface {
    private static final String TAG = DefaultSessionTokenManager.class.getCanonicalName();
    private Configuration mConfiguration;
    private static final int MIN_SESSION_TOKEN = 1;
    private static final int MAX_SESSION_TOKEN = 3;
    private static final BlockingQueue<SessionToken> mSessionTokens = new LinkedBlockingQueue<SessionToken>(MAX_SESSION_TOKEN);
    private final Object lock = new Object();

    private static int outstandingRequests = 0;
    private static final Object outstandingRequestsLock = new Object();
    private CredentialsInterface mUserCredentials;
    private CredentialsInterface mDeveloperCredentials;
    private HttpWorkerInterface mHttpWorkerInterface;

    /**
     * DefaultSessionTokenManager Constructor
     */
    public DefaultSessionTokenManager(HttpWorkerInterface httpWorkerInterface, CredentialsInterface userCredentials, CredentialsInterface developerCredentials){
        mHttpWorkerInterface = httpWorkerInterface;
        mUserCredentials = userCredentials;
        mDeveloperCredentials = developerCredentials;
    }

    /**
     * Initialized this class, should be called before class methods are called
     * @param configuration Configuration Object to be used in class methods
     */
    @Override
    public void initialize(Configuration configuration) { }

    /**
     * A shutdown method for this class
     */
    @Override
    public void shutdown() {
        System.out.printf("%s - %s", TAG, "shutdown");
    }

    /**
     * Called whenever a new SessionToken Object is received
     * Adds the token to the BlockingQueue
     * @param token the SessionToken that was received
     */
    @Override
    public void receiveSessionToken(SessionToken token) {
        System.out.printf("%s - %s", TAG, "receiveSessionToken");
        subtractOutstandingRequest();
        if (token != null) {
            if(mSessionTokens.size() < MAX_SESSION_TOKEN) {
                mSessionTokens.offer(token);
            }
        }
    }

    /**
     * Called to borrow an SessionToken, asks server for a new one if not available (blocking)
     * @return a SessionToken once one is available
     */
    @Override
    public SessionToken borrowSessionToken() {
        System.out.printf("%s - %s", TAG, "borrowSessionToken");
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

    /**
     * Called whenever the process of getting a new session token fails
     * Returns a (checked for) bad token into the blocking queue to notify failure
     * @param response the Response associated with the failure
     */
    @Override
    public void getNewSessionTokenFailed(Response response) {
        System.out.printf("%s - %s", TAG, "getNewSessionTokenFailed");
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
            System.out.printf("%s - %s", TAG, "requestNewSessionToken");
            Request request = new ApiRequestGenerator("1.2").createRequestObjectFromPath("user/get_session_token.php");
            request.addQueryParameter("application_id", mConfiguration.getDeveloperCredentials().getCredentials().get("application_id"));
            request.addQueryParameter("response_format", "json");
            request.addQueryParameter("token_version", 2);
            addOutstandingRequest();

            ClientHelperNewSessionToken clientHelperNewSessionToken = new ClientHelperNewSessionToken(mUserCredentials, mDeveloperCredentials, DefaultSessionTokenManager.this);
            ApiClient apiClient = new ApiClient(clientHelperNewSessionToken, mHttpWorkerInterface);
            Result result = apiClient.doRequest(request);
            Response response = result.getResponse();

            if(response.getClass() == ResponseApiClientError.class) {
                ResponseApiClientError responseApiClientError = (ResponseApiClientError) result.getResponse();
                System.out.printf("%s - %s", TAG, responseApiClientError.getErrorMessage());
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
