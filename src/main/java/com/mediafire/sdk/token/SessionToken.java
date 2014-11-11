package com.mediafire.sdk.token;

/**
 * SessionToken is a Token with additional parameters
 */
public class SessionToken extends Token {
    private final String mTime;
    private final String mPermanentToken;
    private String mSecretKey;
    private final String mPkey;
    private final String mEkey;

    /**
     * SessionToken Constructor
     * @param tokenString String for the token
     * @param secretKey String for the secret key
     * @param time String for the time
     */
    public SessionToken(String tokenString, String secretKey, String time) {
        this(tokenString, secretKey, time, null, null);
    }

    /**
     * SessionToken Constructor
     * @param tokenString String for the token
     * @param secretKey String for the secret key
     * @param time String for the time
     * @param pkey String for the mPkey
     * @param ekey String for the mEkey
     */
    public SessionToken(String tokenString, String secretKey, String time, String pkey, String ekey) {
        this(tokenString, secretKey, time, pkey, ekey, null);
    }

    /**
     * SessionToken Constructor
     * @param tokenString String for the token
     * @param secretKey String for the secret key
     * @param time String for the time
     * @param pkey String for the mPkey
     * @param ekey String for the mEkey
     * @param permanentToken String for the permanent token
     */
    public SessionToken(String tokenString, String secretKey, String time, String pkey, String ekey, String permanentToken) {
        super(tokenString);
        mSecretKey = secretKey;
        mTime = time;
        mPkey = pkey;
        mEkey = ekey;
        mPermanentToken = permanentToken;
    }

    private SessionToken(Builder builder) {
        super(builder.mTokenString);
        mTime = builder.mTime;
        mPermanentToken = builder.mPermanentToken;
        mSecretKey = builder.mSecretKey;
        mPkey = builder.mPkey;
        mEkey = builder.mEkey;
    }

    /**
     * Gets the time for the token
     * @return String time
     */
    public final String getTime() {
        return mTime;
    }

    /**
     * Gets the secret key for the token
     * @return String secret key
     */
    public final String getSecretKey() {
        return mSecretKey;
    }

    /**
     * Gets the mPkey for the token
     * @return String mPkey
     */
    public final String getPkey() {
        return mPkey;
    }

    /**
     * Gets the mEkey for the token
     * @return String mEkey
     */
    public final String getEkey() {
        return mEkey;
    }

    /**
     * Gets the permanent token for the token
     * @return String permanent token
     */
    public final String getPermanentToken() {
        return mPermanentToken;
    }

    /**
     * updated the session token based on the secret key
     */
    public final void updateSessionToken() {
        long newKey = Long.valueOf(mSecretKey) * 16807L;
        newKey %= 2147483647L;
        mSecretKey = String.valueOf(newKey);
    }

    public static class Builder {
        private final String mTokenString;
        private String mTime;
        private String mPermanentToken;
        private String mSecretKey;
        private String mPkey;
        private String mEkey;

        public Builder(String tokenString) {
            mTokenString = tokenString;
        }

        public Builder time(String value) {
            mTime = value;
            return this;
        }

        public Builder permanentToken(String value) {
            mPermanentToken = value;
            return this;
        }

        public Builder secretKey(String value) {
            mSecretKey = value;
            return this;
        }

        public Builder pkey(String value) {
            mPkey = value;
            return this;
        }

        public Builder ekey(String ekey) {
            mEkey = ekey;
            return this;
        }

        public SessionToken build() {
            return new SessionToken(this);
        }
    }
}
