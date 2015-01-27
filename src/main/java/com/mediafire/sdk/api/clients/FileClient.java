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

public class FileClient implements Debug {

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;
    private final Instructions mInstructions;
    private boolean mDebug;

    public FileClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new UseSessionToken(tokenManager);
        apiClient = new ApiClient(httpInterface);
    }

    public Result getInfo(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " getInfo, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("file/get_info.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result delete(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " delete, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("file/delete.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result copy(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " copy, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("file/copy.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getVersion(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " getVersion, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("file/get_version.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result move(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " move, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("file/move.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result update(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " update, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("file/update.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return apiClient.doRequest(mInstructions, request);
    }

    public Result getLinks(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " getLinks, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("file/get_links.php");

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
