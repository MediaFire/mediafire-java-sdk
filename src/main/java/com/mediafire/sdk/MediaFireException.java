package com.mediafire.sdk;

public class MediaFireException extends Exception {

    private static final long serialVersionUID = -3122830390839807822L;

    // errors from client
    public static final int CLIENT_RECEIVED_INVALID_HASH_FOR_CONVERSION_REQUEST = 10;
    public static final int CLIENT_COULD_NOT_OBTAIN_ACTION_TOKEN = 20;
    public static final int CLIENT_COULD_NOT_OBTAIN_SESSION_TOKEN = 30;
    public static final int CLIENT_NO_CREDENTIALS_STORED = 40;
    public static final int CLIENT_RECEIVED_INVALID_ACTION_TOKEN_TYPE = 50;
    public static final int CLIENT_COULD_NOT_UTF8_ENCODE_STRING = 60;
    // parser errors
    public static final int RESPONSE_PARSER_RECEIVED_NULL_OR_EMPTY_RESPONSE = 100;
    public static final int RESPONSE_PARSER_RECEIVED_MALFORMED_JSON = 110;
    // session store errors
    public static final int SESSION_STORE_RECEIVED_INVALID_TOKEN_TYPE = 200;
    // errors from http requester
    public static final int HTTP_REQUESTER_IO_EXCEPTION = 300;
    public static final int HTTP_REQUESTER_MALFORMED_URL = 310;

    private int error;

    public MediaFireException(String message, int error) {
        super(message);
    }

    public MediaFireException(String message, int error, Throwable cause) {
        super(message, cause);
    }

    public int getError() {
        return error;
    }
}
