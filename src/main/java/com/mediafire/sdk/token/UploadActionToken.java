package com.mediafire.sdk.token;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class UploadActionToken extends ActionToken {
    public UploadActionToken(String tokenString, long expiration) {
        super(tokenString, expiration);
    }

    public UploadActionToken(ActionToken token) {
        super(token);
    }
}
