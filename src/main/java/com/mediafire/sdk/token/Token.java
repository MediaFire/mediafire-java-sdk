package com.mediafire.sdk.token;

public abstract class Token {
    protected final String mTokenString;

    public Token(String tokenString) {
        mTokenString = tokenString;
    }

    public String getTokenString() {
        return mTokenString;
    }
}
