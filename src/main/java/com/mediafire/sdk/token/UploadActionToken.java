package com.mediafire.sdk.token;

/**
 * Created by Chris Najar on 10/18/2014.
 * UploadActionToken is an ActionToken
 */
public class UploadActionToken extends ActionToken {

    /**
     * UploadActionToken Constructor
     * @param tokenString String for the token
     * @param expirationMillis long expiration time for the token
     */
    public UploadActionToken(String tokenString, long expirationMillis) {
        super(tokenString, expirationMillis);
    }
}
