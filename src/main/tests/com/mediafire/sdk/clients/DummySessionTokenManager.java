package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris on 11/10/2014.
 */
public class DummySessionTokenManager implements SessionTokenManagerInterface {

    SessionToken sessionToken;

    @Override
    public void receiveSessionToken(SessionToken token) {
        sessionToken = token;
    }

    @Override
    public SessionToken borrowSessionToken() {
        if (sessionToken == null) {
            sessionToken = createNewSessionToken();
        }

        return sessionToken;
    }

    @Override
    public void getNewSessionTokenFailed(Response response) { }

    @Override
    public void initialize(Configuration configuration) { }

    @Override
    public void shutdown() { }

    private SessionToken createNewSessionToken() {
        String token = "verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz";
        String key = "12345";
        String time = "1234.56";
        String pkey = "pkeyvalue";
        String ekey = "ekeyvalue";
        String permToken = "permtokenvalue";
        return new SessionToken(token, key, time, pkey, ekey, permToken);
    }
}
