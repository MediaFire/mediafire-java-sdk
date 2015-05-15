package com.mediafire.sdk;

import java.security.NoSuchAlgorithmException;

/**
 * Created by Chris on 5/15/2015.
 */
public class MFRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 6113319998777232529L;

    public MFRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
