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
        String token = "0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";
        String key = "15005654";
        String time = "1415665191.4128";
        String pkey = "12345";
        String ekey = "12345";
        String permToken = "12345";
        SessionToken.Builder builder = new SessionToken.Builder(token);
        builder.secretKey(key).time(time).pkey(pkey).ekey(ekey).permanentToken(permToken);
        return builder.build();
    }
}
