package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class DeviceClient {
    
    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient mApiClient;
    private final Instructions mInstructions;

    public DeviceClient(IHttp httpInterface, ITokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new UseSessionToken(tokenManager);
        mApiClient = new ApiClient(httpInterface);
    }

    public Result getChanges(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_changes.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getStatus(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_status.php");

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
}
