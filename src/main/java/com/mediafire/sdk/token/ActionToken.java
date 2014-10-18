package com.mediafire.sdk.token;

public class ActionToken extends Token {
    private final long mExpiration;
    private final Type mType;

    public ActionToken(String tokenString, Type type, long expiration) {
        super(tokenString);
        mType = type;
        mExpiration = expiration;
    }

    public ActionToken(ActionToken token) {
        super(token.getTokenString());
        mType = token.mType;
        mExpiration = token.mExpiration;
    }

    public long getExpiration() {
        return mExpiration;
    }

    public Type getType() {
        return mType;
    }

    public enum Type {
        UPLOAD("upload"),
        IMAGE("image");

        private final String mType;

        private Type(String type) {
            mType = type;
        }

        public String getType() {
            return mType;
        }
    }
}
