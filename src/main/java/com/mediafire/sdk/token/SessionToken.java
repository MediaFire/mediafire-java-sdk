package com.mediafire.sdk.token;

public final class SessionToken extends Token {
    private final String time;
    private final String permanentToken;
    private String secretKey;
    private final String pkey;
    private final String ekey;

    public SessionToken(String tokenString, String secretKey, String time) {
        this(tokenString, secretKey, time, null, null);
    }

    public SessionToken(String tokenString, String secretKey, String time, String pkey, String ekey) {
        this(tokenString, secretKey, time, pkey, ekey, null);
    }

    public SessionToken(String tokenString, String secretKey, String time, String pkey, String ekey, String permanentToken) {
        super(tokenString);
        this.secretKey = secretKey;
        this.time = time;
        this.pkey = pkey;
        this.ekey = ekey;
        this.permanentToken = permanentToken;
    }

    public String getTime() {
        return time;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getPkey() {
        return pkey;
    }

    public String getEkey() {
        return ekey;
    }

    public String getPermanentToken() {
        return permanentToken;
    }

    public static void updateSessionToken(SessionToken sessionToken) {
        long newKey = Long.valueOf(sessionToken.secretKey) * 16807;
        newKey = newKey % 2147483647;
        String newSecretKey = String.valueOf(newKey);
        sessionToken.secretKey = newSecretKey;
    }
}
