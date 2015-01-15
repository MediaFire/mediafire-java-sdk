package com.mediafire.sdk.config;

import com.mediafire.sdk.http.Response;

import java.util.Map;

/**
 * Created by Chris Najar on 10/18/2014.
 * HttpWorkerInterface provides a interface to perform get and post http requests
 */
public interface HttpHandler {

    /**
     * Performs a http get request
     * @param url String url for the request
     * @param headers Map<String, String> headers for the request
     * @return Response from the request
     */
    public Response doGet(String url, Map<String, Object> headers);

    /**
     * Performs a http get request
     * @param url String url for the request
     * @param headers Map<String, String> headers for the request
     * @param payload byte array for the payload to be sent
     * @return Response from the request
     */
    public Response doPost(String url, Map<String, Object> headers, byte[] payload);

}
