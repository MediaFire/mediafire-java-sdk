package com.mediafire.sdk.config;

import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

public interface HttpInterface {

    public Result doRequest(final Request request);
}
