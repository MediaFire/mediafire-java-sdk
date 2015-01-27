package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.Debug;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseActionToken;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class ConversionClient implements Debug {

    private final ApiClient imageClient;
    private final Instructions mInstructions;
    private boolean mDebug;

    public ConversionClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mInstructions = new UseActionToken("image", tokenManager);
        imageClient = new ApiClient(httpInterface);
    }

    public Result doConversion(Map<String, Object> requestParams) {
        if (debugging()) {
            System.out.println(getClass() + " doConversion, params: " + requestParams);
        }

        Request request = makeBaseRequest();

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return imageClient.doRequest(mInstructions, request);
    }

    private Request makeBaseRequest() {
        Request.Builder builder = new Request.Builder();
        builder.scheme("https").fullDomain("www.mediafire.com").path("conversion_server.php").httpMethod("get").postQuery(false);

        Request request = builder.build();
        return request;
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
