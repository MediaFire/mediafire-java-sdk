package com.mediafire.sdk;

/**
 * Created by Chris on 5/15/2015.
 */
public class MFApiException extends Exception {
    private static final long serialVersionUID = -9078735624341950919L;
    private final int errorCode;

    public MFApiException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public MFApiException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
