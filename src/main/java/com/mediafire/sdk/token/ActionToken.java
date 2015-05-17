package com.mediafire.sdk.token;

import com.mediafire.sdk.api.responses.UserGetActionTokenResponse;

public class ActionToken {
    private final String tokenString;
    private final long expirationMillis;

    public ActionToken(String tokenString, long expirationMillis) {
        this.tokenString = tokenString;
        this.expirationMillis = expirationMillis;
    }

    /**
     * Gets the expiration time of the token
     * @return long expiration
     */
    public long getExpiration() {
        return expirationMillis;
    }

    public boolean isExpired() {
        return expirationMillis > System.currentTimeMillis();
    }

    public boolean isExpiringWithinMillis(long millis) {
        return expirationMillis - millis > System.currentTimeMillis();
    }

    public String getToken() {
        return tokenString;
    }

    public static ActionToken makeActionTokenFromApiResponse(UserGetActionTokenResponse apiResponse, long expirationTime) {
        return new ActionToken(apiResponse.getActionToken(), expirationTime);
    }
}
