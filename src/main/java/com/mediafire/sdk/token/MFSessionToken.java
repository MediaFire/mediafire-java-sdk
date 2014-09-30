package com.mediafire.sdk.token;

public final class MFSessionToken extends MFToken {
    private final String time;
    private final String permanentToken;
    private String secretKey;
    private final String pkey;
    private final String ekey;

    public MFSessionToken(String tokenString, String secretKey, String time) {
        this(tokenString, secretKey, time, null, null);
    }

    public MFSessionToken(String tokenString, String secretKey, String time, String pkey, String ekey) {
        this(tokenString, secretKey, time, pkey, ekey, null);
    }

    public MFSessionToken(String tokenString, String secretKey, String time, String pkey, String ekey, String permanentToken) {
        super(tokenString);
        this.secretKey = secretKey;
        this.time = time;
        this.pkey = pkey;
        this.ekey = ekey;
        this.permanentToken = permanentToken;
    }

    /**
     * gets the time value for this Token.
     * @return the time value for this Token.
     */
    public String getTime() {
        return time;
    }

    /**
     * gets the secret key for this Token.
     * @return the secret key value for this Token.
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * gets the pkey value for this Token.
     * @return the pkey for this Token.
     */
    public String getPkey() {
        return pkey;
    }

    /**
     * gets the ekey value for this Token.
     * @return the ekey for this Token.
     */
    public String getEkey() {
        return ekey;
    }

    /**
     * gets the permanent token value for this Token.
     * @return the permanent token for this Token.
     */
    public String getPermanentToken() {
        return permanentToken;
    }

    /**
     * updates a session token using MediaFire calculation.
     */
    public void updateSessionToken() {
        long newKey = Long.valueOf(secretKey) * 16807;
        newKey = newKey % 2147483647;
        String newSecretKey = String.valueOf(newKey);
        this.secretKey = newSecretKey;
    }

    @Override
    public String toString() {
        return "MFSessionToken token [" + tokenString + "],  secret key [" + secretKey + "], time [" + time + "],pkey [" + pkey + "], ekey [" + ekey + "]";
    }
}
