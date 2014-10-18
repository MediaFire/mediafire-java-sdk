package com.mediafire.sdk.config;

import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public interface SessionTokenManagerInterface {

    public void returnSessionToken(SessionToken sessionToken);

    public void sessionTokenSpoiled(SessionToken sessionToken);

    public void receiveNewSessionToken(SessionToken sessionToken);

    public SessionToken borrowMFSessionToken();
}
