package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class ContactClient {
    private final ApiClient mApiClient;
    private final Instructions mInstructions;

    public ContactClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mInstructions = new UseSessionToken(tokenManager);
        mApiClient = new ApiClient(httpInterface);
    }

    public Result add(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("contact/add.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result add(Map<String, Object> requestParams) {
        return add(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result delete(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("contact/delete.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result delete(Map<String, Object> requestParams) {
        return delete(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result fetch(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("contact/fetch.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result fetch(Map<String, Object> requestParams) {
        return fetch(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getAvatar(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("contact/get_avatar.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getAvatar(Map<String, Object> requestParams) {
        return getAvatar(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getSources(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("contact/get_sources.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getSources(Map<String, Object> requestParams) {
        return getSources(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result setAvatar(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("contact/set_avatar.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result setAvatar(Map<String, Object> requestParams) {
        return setAvatar(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result summary(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("contact/summary.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result summary(Map<String, Object> requestParams) {
        return summary(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    private void addParams(Request request, Map<String, Object> requestParams) {
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }
    }
}
