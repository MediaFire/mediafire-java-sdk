package com.mediafire.sdk;

/**
 * Created by Chris on 5/15/2015.
 */
public class MFException extends Exception {
    private static final long serialVersionUID = 9042001401553400722L;

    public MFException(String message) {
        super(message);
    }

    public MFException(String message, Throwable cause) {
        super(message, cause);
    }
}
