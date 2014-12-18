package com.mediafire.sdk.client_core;

import com.mediafire.sdk.config.HttpInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

import java.util.Map;

/**
 * Created by Chris on 11/12/2014.
 */
public abstract class BaseClient {

    private HttpInterface mHttpWorker;

    public BaseClient(HttpInterface httpInterface) {
        mHttpWorker = httpInterface;
    }

    public abstract Result doRequest(Request request);

    public final Response doGet(Request request) {
        String url = new UrlHelper(request).getUrlForRequest();
        Map<String, String> headers = request.getHeaders();

        return mHttpWorker.doGet(url, headers);
    }

    public final Response doPost(Request request) {
        UrlHelper urlHelper = new UrlHelper(request);
        String url = urlHelper.getUrlForRequest();

        byte[] payload = request.getPayload();
        Map<String, String> headers = request.getHeaders();

        return mHttpWorker.doPost(url, headers, payload);
    }
}
