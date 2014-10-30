package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/19/2014.
 * ResponseApiClientError is an error class for the Response Object
 */
public class ResponseApiClientError extends Response {

    private String mMessage;
    private Throwable mThrowable;

    /**
     * ResponseApiClientError Constructor
     * @param message String message for the error
     * @param throwable Throwable throwable for the error
     */
    public ResponseApiClientError(String message, Throwable throwable) {
        super(-1, null);
        mMessage = message;
        mThrowable = throwable;
    }

    /**
     * ResponseApiClientError Constructor
     * @param message String message for the error
     */
    public ResponseApiClientError(String message) {
        this(message, null);
    }

    /**
     * Gets the error message
     * @return String message
     */
    public String getErrorMessage() {
        return mMessage;
    }

    /**
     * Gets the throwable
     * @return Throwable throwable
     */
    public Throwable getThrowable() {
        return mThrowable;
    }
}
