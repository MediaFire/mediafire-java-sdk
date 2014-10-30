package com.mediafire.sdk.token;

/**
 * ActionToken is a Token that contains an expiration
 */
public abstract class ActionToken extends Token {
    private final long mExpiration;

    /**
     * ActionToken Constructor
     * @param tokenString String for the token
     * @param expiration long expiration time for the token
     */
    public ActionToken(String tokenString, long expiration) {
        super(tokenString);
        mExpiration = expiration;
    }

    /**
     * ActionToken Constructor
     * Adds the current system time to the expiration of the passed in token
     * @param token ActionToken to get the token string and expiration from
     */
    public ActionToken(ActionToken token) {
        super(token.getTokenString());
        mExpiration = System.currentTimeMillis() + token.mExpiration;
    }

    /**
     * Gets the expiration time of the token
     * @return long expiration
     */
    public long getExpiration() {
        return mExpiration;
    }
}
