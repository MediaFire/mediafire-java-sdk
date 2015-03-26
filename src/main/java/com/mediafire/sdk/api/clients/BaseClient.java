package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.RequestHelper;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public abstract class BaseClient {

    private HttpHandler mHttpWorker;

    public BaseClient(HttpHandler httpInterface) {
        mHttpWorker = httpInterface;
    }

    public abstract Result doRequest(Instructions instructions, Request request);

    public final Response doGet(Request request) {
        String url = new RequestHelper(request).getUrlForRequest();
        Map<String, Object> headers = request.getHeaders();

        return mHttpWorker.doGet(url, headers);
    }

    public final Response doPost(Request request) {
        RequestHelper requestHelper = new RequestHelper(request);
        String url = requestHelper.getUrlForRequest();

        byte[] payload = request.getPayload();
        Map<String, Object> headers = request.getHeaders();

        return mHttpWorker.doPost(url, headers, payload);
    }
}
