package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class Result {
    private final Response mResponse;
    private final Request mRequest;

    public Result(final Response response, final Request request) {
        mResponse = response;
        mRequest = request;
    }

    public Response getResponse() {
        return mResponse;
    }

    public Request getRequest() {
        return mRequest;
    }
}
