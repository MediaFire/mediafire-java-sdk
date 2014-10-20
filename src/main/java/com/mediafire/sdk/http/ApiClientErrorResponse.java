package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class ApiClientErrorResponse extends Response {

    private String mMessage;

    public ApiClientErrorResponse(String message) {
        super(0, null, null);
        mMessage = message;
    }

    public String getErrorMessage() {
        return mMessage;
    }
}
