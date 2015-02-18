package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.NoToken;
import com.mediafire.sdk.api.helpers.UseActionToken;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class UploadClient {

    private final ApiClient mApiClient;
    private final Instructions mInstructionsActionToken;
    private final Instructions mInstructionsNoToken;
    private final Instructions mInstructionsSessionToken;

    public UploadClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mInstructionsNoToken = new NoToken();
        mInstructionsActionToken = new UseActionToken("upload", tokenManager);
        mInstructionsSessionToken = new UseSessionToken(tokenManager);
        mApiClient = new ApiClient(httpInterface);
    }

    public Result check(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("upload/check.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructionsSessionToken, request);
    }

    public Result instant(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("upload/instant.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructionsSessionToken, request);
    }

    public Result pollUpload(Map<String, Object> requestParams) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("upload/poll_upload.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructionsNoToken, request);
    }

    public Result resumable(Map<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("upload/resumable.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        request.addPayload(payload);

        for (String key : headerParameters.keySet()) {
            Object value = headerParameters.get(key);
            request.addHeader(key, value);
        }

        return mApiClient.doRequest(mInstructionsActionToken, request);
    }

    public Result update(Map<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("upload/update.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        request.addPayload(payload);

        for (String key : headerParameters.keySet()) {
            Object value = headerParameters.get(key);
            request.addHeader(key, value);
        }

        return mApiClient.doRequest(mInstructionsActionToken, request);
    }

    private void addParams(Request request, Map<String, Object> requestParams) {
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }
    }
}
