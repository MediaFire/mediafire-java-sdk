package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class FolderClient {
    private final ApiClient apiClient;
    private final Instructions mInstructions;

    public FolderClient(HttpHandler httpInterface, TokenManager tokenManager) {

        mInstructions = new UseSessionToken(tokenManager);
        apiClient = new ApiClient(httpInterface);
    }

    public Result copy(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("folder/copy.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result copy(Map<String, Object> requestParams) {
        return copy(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result create(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("folder/create.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result create(Map<String, Object> requestParams) {
        return create(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result move(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("folder/move.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result move(Map<String, Object> requestParams) {
        return move(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result delete(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("folder/delete.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result delete(Map<String, Object> requestParams) {
        return delete(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result purge(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("folder/purge.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result purge(Map<String, Object> requestParams) {
        return purge(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result update(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("folder/update.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result update(Map<String, Object> requestParams) {
        return update(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getInfo(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("folder/get_info.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getInfo(Map<String, Object> requestParams) {
        return getInfo(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getContent(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("folder/get_content.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getContent(Map<String, Object> requestParams) {
        return getContent(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getRevision(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("folder/get_revision.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getRevision(Map<String, Object> requestParams) {
        return getRevision(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result search(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("folder/search.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result search(Map<String, Object> requestParams) {
        return search(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    private void addParams(Request request, Map<String, Object> requestParams) {
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }
    }
}
