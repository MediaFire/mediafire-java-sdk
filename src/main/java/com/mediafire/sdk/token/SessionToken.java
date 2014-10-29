package com.mediafire.sdk.token;

/**
 * SessionToken is a Token with additional parameters
 */
public class SessionToken extends Token {
    private final String time;
    private final String permanentToken;
    private String secretKey;
    private final String pkey;
    private final String ekey;

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
     * @param pkey String for the pkey
     * @param ekey String for the ekey
     */
    public SessionToken(String tokenString, String secretKey, String time, String pkey, String ekey) {
        this(tokenString, secretKey, time, pkey, ekey, null);
    }

    /**
     * SessionToken Constructor
     * @param tokenString String for the token
     * @param secretKey String for the secret key
     * @param time String for the time
     * @param pkey String for the pkey
     * @param ekey String for the ekey
     * @param permanentToken String for the permanent token
     */
    public SessionToken(String tokenString, String secretKey, String time, String pkey, String ekey, String permanentToken) {
        super(tokenString);
        this.secretKey = secretKey;
        this.time = time;
        this.pkey = pkey;
        this.ekey = ekey;
        this.permanentToken = permanentToken;
    }

    /**
     * Gets the time for the token
     * @return String time
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets the secret key for the token
     * @return String secret key
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Gets the pkey for the token
     * @return String pkey
     */
    public String getPkey() {
        return pkey;
    }

    /**
     * Gets the ekey for the token
     * @return String ekey
     */
    public String getEkey() {
        return ekey;
    }

    /**
     * Gets the permanent token for the token
     * @return String permanent token
     */
    public String getPermanentToken() {
        return permanentToken;
    }

    /**
     * updated the session token based on the secret key
     */
    public void updateSessionToken() {
        long newKey = Long.valueOf(secretKey) * 16807;
        newKey %= 2147483647;
        String newSecretKey = String.valueOf(newKey);
        secretKey = newSecretKey;
    }
}
