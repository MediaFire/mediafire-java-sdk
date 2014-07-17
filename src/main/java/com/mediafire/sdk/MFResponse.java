package com.mediafire.sdk;

import java.util.List;
import java.util.Map;

/**
 * Created by Chris Najar on 7/16/2014.
 */
public class MFResponse {
    private int status;

    private Map<String, List<String>> headers;

    private byte[] bodyBytes;

    public MFResponse(int status, Map<String, List<String>> headers, byte[] bodyBytes) {
        this.status = status;
        this.headers = headers;
        this.bodyBytes = bodyBytes;
    }

    public int getStatus() {
        return status;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public byte[] getBodyBytes() {
        return bodyBytes;
    }

    public String getResponseString() {
        return new String(bodyBytes);
    }
}