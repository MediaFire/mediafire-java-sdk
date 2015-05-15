package com.mediafire.sdk.requests;

import com.mediafire.sdk.util.RequestUtil;

import java.util.Map;

public class PostRequest {
    private final String url;
    private final Map<String, String> headers;
    private final byte[] payload;

    public static PostRequest fromApiRequest(ApiRequest apiRequest) {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(apiRequest);
        byte[] payload = RequestUtil.makeQueryPayloadFromApiRequest(apiRequest);
        return new PostRequest(apiRequest, headers, payload);
    }

    public static PostRequest fromUploadRequest(UploadRequest uploadRequest) {
        Map<String, String> headers = RequestUtil.makeHeadersFromUploadRequest(uploadRequest);
        byte[] payload = uploadRequest.getPayload();
        return new PostRequest(uploadRequest, headers, payload);
    }

    public PostRequest(ApiRequest apiRequest, Map<String, String> headers, byte[] payload) {
        this.url = RequestUtil.makeUrlFromApiRequest(apiRequest);
        this.headers = headers;
        this.payload = payload;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getPayload() {
        return payload;
    }
}
