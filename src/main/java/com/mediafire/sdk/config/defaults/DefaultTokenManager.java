package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.token.UploadActionToken;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class DefaultTokenManager implements SessionTokenManagerInterface, ActionTokenManagerInterface {

    @Override
    public void receiveImageActionToken(ImageActionToken mfImageActionToken) {

    }

    @Override
    public void receiveUploadActionToken(UploadActionToken mfUploadActionToken) {

    }

    @Override
    public UploadActionToken borrowUploadActionToken() {
        return null;
    }

    @Override
    public ImageActionToken borrowImageActionToken() {
        return null;
    }

    @Override
    public void tokensFailed() {

    }

    @Override
    public void receiveSessionToken(SessionToken token) {

    }

    @Override
    public SessionToken borrowSessionToken() {
        return null;
    }
}
