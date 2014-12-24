package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class FolderClient {
    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;
    private final Instructions mInstructions;

    public FolderClient(IHttp httpInterface, ITokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new UseSessionToken(tokenManager);
        apiClient = new ApiClient(httpInterface);
    }

    public Result copy(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/copy.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result create(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/create.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result move(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/move.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result delete(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/delete.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result purge(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/purge.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result update(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/update.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getInfo(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/get_info.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getContent(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/get_content.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getRevision(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/get_revision.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result search(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/search.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return apiClient.doRequest(mInstructions, request);
    }
}