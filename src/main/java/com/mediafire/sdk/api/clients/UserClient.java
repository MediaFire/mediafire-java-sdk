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

    public Result getAvatar() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_avatar.php");
        return apiClient.doRequest(mInstructions, request);
    }

    public Result setAvatar(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/set_avatar.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getInfo() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_info.php");

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getSettings() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_settings.php");

        return apiClient.doRequest(mInstructions, request);
    }

    public Result setSettings(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/set_settings.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result destroyActionToken(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/destroy_action_token.php");

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
