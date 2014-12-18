package com.mediafire.sdk.config_impl;

import com.mediafire.sdk.config.IUserCredentials;

/**
 *
 */
public class UserCredentialsImpl implements IUserCredentials {
    private String mCredentialsString;
    private Credentials mCredentialsObject;

    @Override
    public void setCredentials(Email email) {
        mCredentialsString = email.getCredentialsString();
        mCredentialsObject = email;
    }

    @Override
    public void setCredentials(Ekey ekey) {
        mCredentialsString = ekey.getCredentialsString();
        mCredentialsObject = ekey;
    }

    @Override
    public void setCredentials(Facebook facebook) {
        mCredentialsString = facebook.getCredentialsString();
        mCredentialsObject = facebook;
    }

    @Override
    public void setCredentials(Twitter twitter) {
        mCredentialsString = twitter.getCredentialsString();
        mCredentialsObject = twitter;
    }

    @Override
    public String getCredentialsString() {
        return mCredentialsString;
    }

    @Override
    public Credentials getCredentials() {
        return mCredentialsObject;
    }


    @Override
    public void clearCredentials() {

    }
}
