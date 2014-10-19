package com.mediafire.sdk.config;

import com.mediafire.sdk.http.Response;

import java.util.Map;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public interface HttpWorkerInterface {
    public Response doGet(String url);

    public Response doPost(String url, Map<String, String> headers, byte[] payload);

}
