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

    public Result getInfo(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/get_info.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result delete(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/delete.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result copy(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/copy.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getVersion(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/get_version.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result move(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/move.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result update(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/update.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getLinks(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("file/get_links.php");

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
