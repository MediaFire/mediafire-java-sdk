package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.Debug;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class ContactClient {

    private ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient mApiClient;
    private final Instructions mInstructions;
    private boolean mDebug;

    public ContactClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new UseSessionToken(tokenManager);
        mApiClient = new ApiClient(httpInterface);
    }

    public Result add(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/add.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result delete(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/delete.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result fetch(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/fetch.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getAvatar(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/get_avatar.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getSources(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/get_sources.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result setAvatar(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/set_avatar.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result summary(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("contact/summary.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    private void addParams(Request request, Map<String, Object> requestParams) {
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }
    }
}
