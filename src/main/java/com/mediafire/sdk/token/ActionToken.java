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

    @Override
    public String toString() {
        return "ActionToken{" +
                "tokenString='" + tokenString + '\'' +
                ", expirationMillis=" + expirationMillis +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionToken that = (ActionToken) o;

        if (expirationMillis != that.expirationMillis) return false;
        if (tokenString != null ? !tokenString.equals(that.tokenString) : that.tokenString != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tokenString != null ? tokenString.hashCode() : 0;
        result = 31 * result + (int) (expirationMillis ^ (expirationMillis >>> 32));
        return result;
    }
}
