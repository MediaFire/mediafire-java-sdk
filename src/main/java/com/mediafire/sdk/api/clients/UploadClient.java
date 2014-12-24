package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.NoToken;
import com.mediafire.sdk.api.helpers.UseActionToken;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class UploadClient {

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient mApiClient;
    private final Instructions mInstructionsActionToken;
    private final Instructions mInstructionsNoToken;

    public UploadClient(IHttp httpInterface, ITokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructionsNoToken = new NoToken();
        mInstructionsActionToken = new UseActionToken("upload", tokenManager);
        mApiClient = new ApiClient(httpInterface);
    }

    public Result check(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/check.php");


        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return mApiClient.doRequest(mInstructionsActionToken, request);
    }

    public Result instant(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/instant.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return mApiClient.doRequest(mInstructionsActionToken, request);
    }

    public Result pollUpload(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/poll_upload.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return mApiClient.doRequest(mInstructionsNoToken, request);
    }

    public Result resumable(Map<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/resumable.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        request.addPayload(payload);

        for (String key : headerParameters.keySet()) {
            Object value = requestParams.get(key);
            request.addHeader(key, value);
        }

        return mApiClient.doRequest(mInstructionsActionToken, request);
    }
}
