package com.mediafire.sdk.token;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class ImageActionToken extends ActionToken {
    public ImageActionToken(String tokenString, long expiration) {
        super(tokenString, expiration);
    }

    public ImageActionToken(ActionToken token) {
        super(token);
    }
}
