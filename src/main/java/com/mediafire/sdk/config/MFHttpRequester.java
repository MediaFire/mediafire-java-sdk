package com.mediafire.sdk.config;

import com.mediafire.sdk.MFException;
import com.mediafire.sdk.requests.GetRequest;
import com.mediafire.sdk.requests.HttpApiResponse;
import com.mediafire.sdk.requests.PostRequest;

import java.util.logging.Handler;

public interface MFHttpRequester {
    /**
     * makes a POST request
     * @param postRequest  the PostRequest to make
     * @return an HttpApiResponse
     * @throws MFException if an exception is thrown. (e.g. SocketTimeoutException, IOException)
     */
    public HttpApiResponse doApiRequest(PostRequest postRequest) throws MFException;

    /**
     * makes a GET request
     * @param getRequest the GetRequest to make
     * @return an HttpApiResponse
     * @throws MFException if an exception is thrown. (e.g. SocketTimeoutException, IOException)
     */
    public HttpApiResponse doApiRequest(GetRequest getRequest) throws MFException;

    /**
     * sets the Handler for logging
     *
     * @param loggerHandler a Handler
     */
    public void setLoggerHandler(Handler loggerHandler);
}
