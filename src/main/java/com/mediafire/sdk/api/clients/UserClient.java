package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class UserClient {

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;
    private final Instructions mInstructions;

    public UserClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new UseSessionToken(tokenManager);
        apiClient = new ApiClient(httpInterface);
    }

    public Result getAvatar(String apiVersion) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_avatar.php", apiVersion);
        return apiClient.doRequest(mInstructions, request);
    }
    
    public Result getAvatar() {
        return getAvatar(ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result setAvatar(Map<String, Object> requestParams, String apiVersion) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/set_avatar.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }
    
    public Result setAvatar(Map<String, Object> requestParams) {
        return setAvatar(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getInfo(String apiVersion) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_info.php", apiVersion);

        return apiClient.doRequest(mInstructions, request);
    }
    
    public Result getInfo() {
        return getInfo(ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getSettings(String apiVersion) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_settings.php", apiVersion);

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getSettings() {
        return getSettings(ApiRequestGenerator.LATEST_STABLE_VERSION);
    }
    
    public Result setSettings(Map<String, Object> requestParams, String apiVersion) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/set_settings.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }
    
    public Result setSettings(Map<String, Object> requestParams) {
        return setSettings(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result destroyActionToken(Map<String, Object> requestParams, String apiVersion) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/destroy_action_token.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }
    
    public Result destroyActionToken(Map<String, Object> requestParams) {
        return destroyActionToken(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    private void addParams(Request request, Map<String, Object> requestParams) {
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }
    }
}
