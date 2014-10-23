package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class ResponseApiClientError extends Response {

    private String mMessage;
    private Throwable mThrowable;

    public ResponseApiClientError(String message, Throwable throwable) {
        super(-1, null);
        mMessage = message;
        mThrowable = throwable;
    }

    public ResponseApiClientError(String message) {
        this(message, null);
    }

    public String getErrorMessage() {
        return mMessage;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }
}
