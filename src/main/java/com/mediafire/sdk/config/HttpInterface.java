package com.mediafire.sdk.config;

import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;

public interface HttpInterface {

    public Response doRequest(Request request);
}
