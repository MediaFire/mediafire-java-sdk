package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/18/2014.
 * Result is a class that serves as a container for the Request and Response classes
 */
public class Result {
    private final Response mResponse;
    private final Request mRequest;

    /**
     * Result Constructor
     * @param response Response
     * @param request Request
     */
    public Result(final Response response, final Request request) {
        mResponse = response;
        mRequest = request;
    }

    /**
     * Gets the response for the result
     * @return Response
     */
    public Response getResponse() {
        return mResponse;
    }

    /**
     * Gets the request for the result
     * @return Request
     */
    public Request getRequest() {
        return mRequest;
    }
}
