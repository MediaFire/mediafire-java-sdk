package com.mediafire.sdk.token;

import com.mediafire.sdk.api.responses.UserGetSessionTokenResponse;

public class SessionToken {
    private final String time;
    private final String pToken;
    private final String token;
    private final long sKey;
    private final String pKey;
    private final String eKey;

    private SessionToken(Builder builder) {
        token = builder.token;
        time = builder.time;
        pToken = builder.pToken;
        sKey = builder.sKey;
        pKey = builder.pKey;
        eKey = builder.eKey;
    }

    public static SessionToken makeSessionTokenFromApiResponse(UserGetSessionTokenResponse apiResponse) {
        Builder builder = new Builder(apiResponse.getSessionToken());
        builder.time(apiResponse.getTime());
        builder.secretKey(apiResponse.getSecretKey());
        builder.ekey(apiResponse.getEkey());
        builder.pkey(apiResponse.getPkey());
        builder.permanentToken(apiResponse.getPermanentToken());
        return builder.build();
    }

    public static SessionToken updateSessionToken(SessionToken token) {
        long newKey = token.sKey * 16807L;
        newKey %= 2147483647L;

        Builder builder = new Builder(token.getToken());
        builder.time(token.getTime());
        builder.secretKey(newKey);
        builder.ekey(token.getEkey());
        builder.pkey(token.getPkey());
        builder.permanentToken(token.getPermanentToken());
        return builder.build();
    }

    public final String getTime() {
        return time;
    }

    public final long getSecretKey() {
        return sKey;
    }

    public final String getPkey() {
        return pKey;
    }

    public final String getEkey() {
        return eKey;
    }

    public final String getPermanentToken() {
        return pToken;
    }

    public String getToken() {
        return token;
    }

    public static class Builder {
        private final String token;
        private String time;
        private String pToken;
        private long sKey;
        private String pKey;
        private String eKey;

        public Builder(String tokenString) {
            token = tokenString;
        }

        public final Builder time(String value) {
            time = value;
            return this;
        }

        public final Builder permanentToken(String value) {
            pToken = value;
            return this;
        }

        public final Builder secretKey(long value) {
            sKey = value;
            return this;
        }

        public final Builder pkey(String value) {
            pKey = value;
            return this;
        }

        public final Builder ekey(String ekey) {
            eKey = ekey;
            return this;
        }

        public SessionToken build() {
            return new SessionToken(this);
        }
    }
}
