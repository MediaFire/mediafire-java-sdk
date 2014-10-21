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

    private final CredentialsInterface mDeveloperCredentials;
    private ApiClientSessionTokenManager mApiClient;

    private static final int MIN_SESSION_TOKEN = 1;
    private static final int MAX_SESSION_TOKEN = 3;
    private static final BlockingQueue<SessionToken> mSessionTokens = new LinkedBlockingQueue<SessionToken>(MAX_SESSION_TOKEN);

    public DefaultSessionTokenManager(HttpWorkerInterface httpWorker, CredentialsInterface userCredentials, CredentialsInterface developerCredentials){
        mDeveloperCredentials = developerCredentials;
        mApiClient = new ApiClientSessionTokenManager(httpWorker, this, userCredentials, developerCredentials);
    }

    @Override
    public void receiveSessionToken(SessionToken token) {
        addNewSessionToken(token);
    }

    @Override
    public SessionToken borrowSessionToken() {
        return getNewSessionToken();
    }

    @Override
    public void getNewSessionTokenFailed(Response response) {

    }

    private synchronized void addNewSessionToken(SessionToken token) {
        if(mSessionTokens.size() < MAX_SESSION_TOKEN) {
            mSessionTokens.offer(token);
        }
    }

    private synchronized SessionToken getNewSessionToken() {
        if(mSessionTokens.size() <= MIN_SESSION_TOKEN) {
            for(int i = mSessionTokens.size(); i <= MIN_SESSION_TOKEN + 1; i++) {
                requestNewSessionToken();
            }
        }

        try {
            return mSessionTokens.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    private void requestNewSessionToken() {
        Request request = new RequestGenerator().generateRequestObject("1.2", "user", "get_session_token.php");
        request.addQueryParameter("application_id", mDeveloperCredentials.getCredentials().get("application_id"));
        mApiClient.doRequest(request);
    }
}
