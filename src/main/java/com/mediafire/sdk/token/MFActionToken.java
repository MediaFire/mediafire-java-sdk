package com.mediafire.sdk.token;

public class MFActionToken extends MFToken {
    private final long expiration;
    private final Type type;

    public MFActionToken(String tokenString, Type type, long expiration) {
        super(tokenString);
        this.type = type;
        this.expiration = System.currentTimeMillis() + 86400000;
    }

    /**
     * copy constructor.
     * @param mfUploadActionToken
     */
    public MFActionToken(MFActionToken mfUploadActionToken) {
        super(mfUploadActionToken.getTokenString());
        type = mfUploadActionToken.type;
        expiration = mfUploadActionToken.expiration;
    }

    /**
     * determines if this action token is expired.
     * @return - true if expired, false otherwise.
     */
    public boolean isExpired() {
        return (System.currentTimeMillis() + 3600000) >= expiration;
    }

    /**
     * gets the type of this Token.
     * @return the type of this token (image or upload).
     */
    public String getType() {
        return type.getValue();
    }

    public enum Type {
        UPLOAD("upload"),
        IMAGE("image");

        private final String value;
        private Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return "MFActionToken token [" + tokenString + "], type [" + type.getValue() + "], expiry [" + expiration + "]";
    }
}
