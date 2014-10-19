package com.mediafire.sdk.client;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.http.Response;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class ResponseHelper {
    private Response mResponse;

    public ResponseHelper(Response response) {
        mResponse = response;
    }

    public ApiResponse getApiResponse() {
        return null;
    }
}
