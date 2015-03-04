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

    public Result getCache(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("notifications/get_cache.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getCache(Map<String, Object> requestParams) {
        return getCache(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result peekCache(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("notifications/peek_cache.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result peekCache(Map<String, Object> requestParams) {
        return peekCache(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result sendMessage(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("notifications/send_message.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result sendMessage(Map<String, Object> requestParams) {
        return sendMessage(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result sendNotification(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("notifications/send_notification.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result sendNotification(Map<String, Object> requestParams) {
        return sendNotification(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    private void addParams(Request request, Map<String, Object> requestParams) {
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }
    }
}
