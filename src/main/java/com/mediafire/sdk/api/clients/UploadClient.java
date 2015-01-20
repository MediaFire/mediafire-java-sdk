package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.Debug;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.NoToken;
import com.mediafire.sdk.api.helpers.UseActionToken;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class UploadClient implements Debug {

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient mApiClient;
    private final Instructions mInstructionsActionToken;
    private final Instructions mInstructionsNoToken;
    private final Instructions mInstructionsSessionToken;
    private boolean mDebug;

    public UploadClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructionsNoToken = new NoToken();
        mInstructionsActionToken = new UseActionToken("upload", tokenManager);
        mInstructionsSessionToken = new UseSessionToken(tokenManager);
        mApiClient = new ApiClient(httpInterface);
    }

    public Result check(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " check, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/check.php");


        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return mApiClient.doRequest(mInstructionsSessionToken, request);
    }

    public Result instant(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " instant, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/instant.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return mApiClient.doRequest(mInstructionsSessionToken, request);
    }

    public Result pollUpload(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " pollUpload, params: " + requestParams);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/poll_upload.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return mApiClient.doRequest(mInstructionsNoToken, request);
    }

    public Result resumable(Map<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload) {
        if (debugging()) {
            System.out.println(getClass() + " resumable, requestParams: " + requestParams);
            System.out.println(getClass() + " resumable, headerParams: " + headerParameters);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/resumable.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        request.addPayload(payload);

        for (String key : headerParameters.keySet()) {
            Object value = headerParameters.get(key);
            request.addHeader(key, value);
        }

        return mApiClient.doRequest(mInstructionsActionToken, request);
    }

    public Result update(Map<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload) {
        if (debugging()) {
            System.out.println(getClass() + " resumable, requestParams: " + requestParams);
            System.out.println(getClass() + " resumable, headerParams: " + headerParameters);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/update.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        request.addPayload(payload);

        for (String key : headerParameters.keySet()) {
            Object value = headerParameters.get(key);
            request.addHeader(key, value);
        }

        return mApiClient.doRequest(mInstructionsActionToken, request);
    }

    @Override
    public void debug(boolean debug) {
        mDebug = debug;
        mInstructionsNoToken.debug(debug);
        mInstructionsActionToken.debug(debug);
    }

    @Override
    public boolean debugging() {
        return mDebug;
    }
}
