package com.mediafire.sdk.token;

/**
 * ActionToken is a Token that contains an expiration
 */
public abstract class ActionToken extends Token {
    private final long mExpiration;

    /**
     * ActionToken Constructor
     * @param tokenString String for the token
     * @param expirationMillis long expiration time for the token
     */
    protected ActionToken(String tokenString, long expirationMillis) {
        super(tokenString);
        mExpiration = expirationMillis;
    }

    /**
     * ActionToken Constructor
     * Adds the current system time to the expiration of the passed in token
     * @param token ActionToken to get the token string and expiration from
     */
    protected ActionToken(ActionToken token) {
        super(token.getTokenString());
        mExpiration = System.currentTimeMillis() + token.getExpiration();
    }

    /**
     * Gets the expiration time of the token
     * @return long expiration
     */
    public final long getExpiration() {
        return mExpiration;
    }
}
