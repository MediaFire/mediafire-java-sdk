package com.mediafire.sdk.token;

public abstract class ActionToken extends Token {
    private final long mExpiration;

    public ActionToken(String tokenString, long expiration) {
        super(tokenString);
        mExpiration = expiration;
    }

    public ActionToken(ActionToken token) {
        super(token.getTokenString());
        mExpiration = System.currentTimeMillis() + token.mExpiration;
    }

    public long getExpiration() {
        return mExpiration;
    }
}
