package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class FileClient {
    private final ApiClient apiClient;
    private final Instructions mInstructions;

    public FileClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mInstructions = new UseSessionToken(tokenManager);
        apiClient = new ApiClient(httpInterface);
    }

    public Result getInfo(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/get_info.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getInfo(Map<String, Object> requestParams) {
        return getInfo(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result delete(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/delete.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result delete(Map<String, Object> requestParams) {
        return delete(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result copy(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/copy.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result copy(Map<String, Object> requestParams) {
        return copy(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getVersion(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/get_version.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getVersion(Map<String, Object> requestParams) {
        return getVersion(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result move(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/move.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result move(Map<String, Object> requestParams) {
        return move(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result update(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/update.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result update(Map<String, Object> requestParams) {
        return update(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getLinks(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/get_links.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getLinks(Map<String, Object> requestParams) {
        return getLinks(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    private void addParams(Request request, Map<String, Object> requestParams) {
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }
    }
}
