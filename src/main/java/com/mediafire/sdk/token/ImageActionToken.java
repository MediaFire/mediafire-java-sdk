package com.mediafire.sdk.token;

/**
 * Created by Chris Najar on 10/18/2014.
 * ImageActionToken is an ActionToken
 */
public class ImageActionToken extends ActionToken {

    /**
     * ImageActionToken Constructor
     * @param tokenString String for the token
     * @param expirationMillis long expiration time for the token
     */
    public ImageActionToken(String tokenString, long expirationMillis) {
        super(tokenString, expirationMillis);
    }

}
