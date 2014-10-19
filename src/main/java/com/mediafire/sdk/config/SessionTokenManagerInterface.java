package com.mediafire.sdk.config;

import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public interface SessionTokenManagerInterface {

    public void receiveSessionToken(SessionToken token);

    public SessionToken borrowSessionToken();
}
