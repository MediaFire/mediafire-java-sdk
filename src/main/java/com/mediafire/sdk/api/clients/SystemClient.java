package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.Debug;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.NoToken;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

public class SystemClient implements Debug {

    private final ApiClient apiClient;
    private final ApiRequestGenerator mApiRequestGenerator;
    private final Instructions mInstructions;
    private boolean mDebug;

    public SystemClient(HttpHandler httpInterface) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new NoToken();
        apiClient = new ApiClient(httpInterface);
    }

    public Result getInfo() {
        if (debugging()) {
            System.out.println(getClass() + " getInfo, params: " + null);
        }

        Request request = mApiRequestGenerator.createRequestObjectFromPath("system/get_info.php");
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
