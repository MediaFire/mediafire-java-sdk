package com.mediafire.sdk.requests;

import java.util.LinkedHashMap;

public class UploadPostRequest extends ApiPostRequest {
    private final byte[] payload;

    public UploadPostRequest(String scheme, String domain, String path, LinkedHashMap<String, Object> query, byte[] payload) {
        super(scheme, domain, path, query);
        this.payload = payload;
    }

    public UploadPostRequest(String path, LinkedHashMap<String, Object> query, byte[] payload) {
        this("https", "www.mediafire.com", path, query, payload);
    }

    public byte[] getPayload() {
        return payload;
    }
}
