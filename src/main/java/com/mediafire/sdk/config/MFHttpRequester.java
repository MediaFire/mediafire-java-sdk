package com.mediafire.sdk.config;

import com.mediafire.sdk.MFException;
import com.mediafire.sdk.requests.HttpApiResponse;
import com.mediafire.sdk.requests.PostRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public interface MFHttpRequester {
    public HttpApiResponse doApiRequest(PostRequest postRequest) throws MFException;

}
