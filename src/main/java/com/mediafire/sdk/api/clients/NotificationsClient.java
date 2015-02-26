package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
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
public class NotificationsClient {
    private final ApiClient apiClient;
    private final Instructions mInstructions;

    public NotificationsClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mInstructions = new UseSessionToken(tokenManager);
        apiClient = new ApiClient(httpInterface);
    }

    public Result getCache(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("notifications/get_cache.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result peekCache(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("notifications/peek_cache.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result sendMessage(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("notifications/send_message.php");

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
