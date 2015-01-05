package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.Debug;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class UserClient implements Debug {

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;
    private final Instructions mInstructions;
    private boolean mDebug;

    public UserClient(IHttp httpInterface, ITokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new UseSessionToken(tokenManager);
        apiClient = new ApiClient(httpInterface);
    }

    public Result getAvatar() {
        if (debugging()) {
            System.out.println(getClass() + " getAvatar");
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_avatar.php");
        return apiClient.doRequest(mInstructions, request);
    }

    public Result setAvatar(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " setAvatar, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/set_avatar.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getInfo() {
        if (debugging()) {
            System.out.println(getClass() + " getInfo");
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_info.php");

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getSettings() {
        if (debugging()) {
            System.out.println(getClass() + " getSettings");
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_settings.php");

        return apiClient.doRequest(mInstructions, request);
    }

    public Result setSettings(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " setSettings, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/set_settings.php");

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
