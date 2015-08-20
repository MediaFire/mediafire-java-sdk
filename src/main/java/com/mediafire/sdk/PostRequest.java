package com.mediafire.sdk;

import java.util.LinkedHashMap;

/**
 * a POST request used by MFHttpRequester
 */
public class PostRequest {
    private static final String CHARSET = "UTF-8";
    private final LinkedHashMap<String, Object> headers = new LinkedHashMap<String, Object>();


    public PostRequest(byte[] payload) {
        this.headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARSET);
        this.headers.put("Content-Length", payload.length);
        this.headers.put("Accept-Charset", "UTF-8");
    }

    public PostRequest(Object o, byte[] payload) {
        this.headers.put("Content-Type", "application/octet-stream");
        this.headers.put("Content-Length", payload.length);
        this.headers.put("Accept-Charset", "UTF-8");
    }
}
