package com.mediafire.sdk.requests;

import java.util.Map;

public class UploadRequest extends ApiRequest {
    private final byte[] payload;
    private final Map<String, String> headers;

    public UploadRequest(String scheme, String domain, String path, Map<String, Object> query, byte[] payload, Map<String, String> headers) {
        super(scheme, domain, path, query);
        this.payload = payload;
        this.headers = headers;
    }

    public UploadRequest(String path, Map<String, Object> query, byte[] payload, Map<String, String> headers) {
        super(path, query);
        this.payload = payload;
        this.headers = headers;
    }

    public byte[] getPayload() {
        return payload;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
