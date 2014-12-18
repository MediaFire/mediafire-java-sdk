package com.mediafire.sdk.client_core;

import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public abstract class BaseClient {

    private IHttp mHttpWorker;

    public BaseClient(IHttp httpInterface) {
        mHttpWorker = httpInterface;
    }

    public abstract Result doRequest(Instructions instructions, Request request);

    public final Response doGet(Request request) {
        String url = new UrlHelper(request).getUrlForRequest();
        Map<String, Object> headers = request.getHeaders();

        return mHttpWorker.doGet(url, headers);
    }

    public final Response doPost(Request request) {
        UrlHelper urlHelper = new UrlHelper(request);
        String url = urlHelper.getUrlForRequest();

        byte[] payload = request.getPayload();
        Map<String, Object> headers = request.getHeaders();

        return mHttpWorker.doPost(url, headers, payload);
    }
}
