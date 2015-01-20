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

/**
 * Created by Chris on 1/19/2015.
 */
public class NotificationsClient implements Debug {

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;
    private final Instructions mInstructions;
    private boolean mDebug;

    public NotificationsClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new UseSessionToken(tokenManager);
        apiClient = new ApiClient(httpInterface);
    }

    public Result getCache(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " getCache, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("notifications/get_info.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result peekCache(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " peekCache, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("notifications/delete.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result sendMessage(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " sendMessage, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("notifications/copy.php");

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
