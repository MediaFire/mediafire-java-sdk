package com.mediafire.sdk.http;

import java.util.List;
import java.util.Map;

public final class Response {
    private final int status;
    private final Map<String, List<String>> headers;
    private final byte[] bodyBytes;

    public Response(int status, Map<String, List<String>> headers, byte[] bodyBytes) {
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

    public byte[] getBytes() {
        return bodyBytes;
    }
}
