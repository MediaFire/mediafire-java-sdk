package com.mediafire.sdk.config;

import com.mediafire.sdk.http.Response;

import java.util.Map;

public interface HttpInterface {

    public Response doGet(String url);

    public Response doPost(String url, Map<String, String> headers, byte[] payload);
}
