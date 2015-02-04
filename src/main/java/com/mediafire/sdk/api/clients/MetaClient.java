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

public class MetaClient {
    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;
    private final Instructions mInstructions;

    public MetaClient(HttpHandler httpInterface, TokenManager tokenManager) {
        // init host object
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new UseSessionToken(tokenManager);
        apiClient = new ApiClient(httpInterface);
    }


    public Result addToList(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/add_to_list.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result removeFromList(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/remove_from_list.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result delete(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/delete_property.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getProperty(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/get.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getLinks(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/get_links.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result query(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/query.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result setProperty(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/set_property.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    private void addParams(Request request, Map<String, Object> requestParams) {
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }
    }
}
