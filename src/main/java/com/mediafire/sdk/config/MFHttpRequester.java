package com.mediafire.sdk.config;

import com.mediafire.sdk.MFException;
import com.mediafire.sdk.requests.GetRequest;
import com.mediafire.sdk.requests.HttpApiResponse;
import com.mediafire.sdk.requests.PostRequest;

public interface MFHttpRequester {
    public HttpApiResponse doApiRequest(PostRequest postRequest) throws MFException;

    public HttpApiResponse doApiRequest(GetRequest getRequest) throws MFException;
}
