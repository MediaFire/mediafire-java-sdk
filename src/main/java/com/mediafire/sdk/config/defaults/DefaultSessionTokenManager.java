package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.clients.ApiClientSessionTokenManager;
import com.mediafire.sdk.clients.RequestGenerator;
import com.mediafire.sdk.config.CredentialsInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.SessionToken;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Chris Najar on 10/21/2014.
 */
public class DefaultSessionTokenManager implements SessionTokenManagerInterface {
    private static final String TAG = DefaultSessionTokenManager.class.getCanonicalName();
    private final CredentialsInterface mDeveloperCredentials;
    private ApiClientSessionTokenManager mApiClient;

    private static final int MIN_SESSION_TOKEN = 1;
    private static final int MAX_SESSION_TOKEN = 3;
    private static final BlockingQueue<SessionToken> mSessionTokens = new LinkedBlockingQueue<SessionToken>(MAX_SESSION_TOKEN);
    private final Object lock = new Object();

    public DefaultSessionTokenManager(HttpWorkerInterface httpWorker, CredentialsInterface userCredentials, CredentialsInterface developerCredentials){
        mDeveloperCredentials = developerCredentials;
        mApiClient = new ApiClientSessionTokenManager(httpWorker, this, userCredentials, developerCredentials);
    }

    @Override
    public void receiveSessionToken(SessionToken token) {
        DefaultLogger.log().v(TAG, "receiveSessionToken");
        if (token != null) {
            mSessionTokens.offer(token);
        }
    }

    @Override
    public SessionToken borrowSessionToken() {
        DefaultLogger.log().v(TAG, "borrowSessionToken");
        synchronized (lock) {
            if (mSessionTokens.size() < MIN_SESSION_TOKEN) {
                for (int i = 0; i < MIN_SESSION_TOKEN - mSessionTokens.size(); i++) {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            requestNewSessionToken();
                        }
                    };
                    Thread thread = new Thread(runnable);
                    thread.start();
                }
            }

            while(mSessionTokens.isEmpty()) {
                DefaultLogger.log().v(TAG, "borrowSessionToken - waiting for token");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                SessionToken sessionToken = mSessionTokens.take();
                return sessionToken;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void getNewSessionTokenFailed(Response response) {
        DefaultLogger.log().v(TAG, "getNewSessionTokenFailed");
    }

    private void requestNewSessionToken() {
        DefaultLogger.log().v(TAG, "requestNewSessionToken");
        Request request = new RequestGenerator().generateRequestObject("1.2", "user", "get_session_token.php");
        request.addQueryParameter("application_id", mDeveloperCredentials.getCredentials().get("application_id"));
        request.addQueryParameter("response_format", "json");
        request.addQueryParameter("token_version", 2);
        mApiClient.doRequest(request);
    }
}
