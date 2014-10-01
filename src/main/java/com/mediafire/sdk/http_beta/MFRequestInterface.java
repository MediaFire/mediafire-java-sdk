package com.mediafire.sdk.http_beta;

import com.mediafire.sdk.api_responses.ApiResponse;

/**
 * Created by Chris Najar on 10/1/2014.
 */
public interface MFRequestInterface {
    public <T extends ApiResponse> T doRequest();
}
