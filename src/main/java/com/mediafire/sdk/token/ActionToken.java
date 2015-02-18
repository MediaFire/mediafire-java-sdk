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
        mExpiration = System.currentTimeMillis() + expirationMillis;
    }

    /**
     * Gets the expiration time of the token
     * @return long expiration
     */
    public final long getExpiration() {
        return mExpiration;
    }

    @Override
    public String toString() {
        return "[type:" + getClass().getSimpleName() + "]" +
                "[token:" + getTokenString() + "]" +
                "[expiration:" + getExpiration() + "]";
    }
}
