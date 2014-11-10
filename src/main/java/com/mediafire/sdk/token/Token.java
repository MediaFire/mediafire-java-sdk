package com.mediafire.sdk.token;

/**
 * Token is an abstract class used to represent a token String
 */
public abstract class Token {
    protected final String mTokenString;

    /**
     * Token Constructor
     * @param tokenString String for the token
     */
    protected Token(String tokenString) {
        mTokenString = tokenString;
    }

    /**
     * Gets the token string
     * @return String token string
     */
    public String getTokenString() {
        return mTokenString;
    }
}
