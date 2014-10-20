package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class DefaultTokenManager implements SessionTokenManagerInterface, ActionTokenManagerInterface {
    @Override
    public void receiveImageActionToken(ActionToken mfImageActionToken) {

    }

    @Override
    public void receiveUploadActionToken(ActionToken mfUploadActionToken) {

    }

    @Override
    public ActionToken borrowUploadActionToken() {
        return null;
    }

    @Override
    public ActionToken borrowImageActionToken() {
        return null;
    }

    @Override
    public void receiveSessionToken(SessionToken token) {

    }

    @Override
    public SessionToken borrowSessionToken() {
        return null;
    }
}
