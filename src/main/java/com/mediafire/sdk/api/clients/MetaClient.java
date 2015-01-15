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

public class MetaClient implements Debug {
    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;
    private final Instructions mInstructions;
    private boolean mDebug;

    public MetaClient(HttpHandler httpInterface, TokenManager tokenManager) {
        // init host object
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new UseSessionToken(tokenManager);
        apiClient = new ApiClient(httpInterface);
    }


    public Result addToList(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " addToList, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/add_to_list.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result removeFromList(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " removeFromList, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/remove_from_list.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result delete(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " delete, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/delete_property.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getProperty(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " getProperty, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/get.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getLinks(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " getLinks, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/get_links.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result query(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " query, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/query.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result setProperty(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " setProperty, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("meta/set_property.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    @Override
    public void debug(boolean debug) {
        mDebug = debug;
        mInstructions.debug(debug);
    }

    @Override
    public boolean debugging() {
        return mDebug;
    }
}
