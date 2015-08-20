package com.mediafire.sdk;

import java.util.Map;

public class MFApiRequest implements MediaFireApiRequest {
    private final String path;
    private final Map<String, Object> queryParameters;
    private final String version;
    private final byte[] payload;
    private final Map<String, Object> headers;

    public MFApiRequest(String path, Map<String, Object> queryParameters, String version, byte[] payload, Map<String, Object> headers) {
        this.version = version;
        this.payload = payload;
        this.path = path;
        this.queryParameters = queryParameters;
        this.headers = headers;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public Map<String, Object> getQueryParameters() {
        return queryParameters;
    }

    @Override
    public Map<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public byte[] getPayload() {
        return payload;
    }
}
