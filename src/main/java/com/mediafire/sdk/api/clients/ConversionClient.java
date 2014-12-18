package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseActionToken;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class ConversionClient {

    private final ApiClient imageClient;
    private final Instructions mInstructions;

    public ConversionClient(IHttp httpInterface, ITokenManager ITokenManager) {
        mInstructions = new UseActionToken("image", ITokenManager);
        imageClient = new ApiClient(httpInterface);
    }

    public Result doConversion(Map<String, Object> requestParams) {

        Request request = makeBaseRequest();

        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value == null ? "" : value);
        }

        return imageClient.doRequest(mInstructions, request);
    }

    private Request makeBaseRequest() {
        Request.Builder builder = new Request.Builder();
        builder.scheme("https").fullDomain("www.mediafire.com").path("conversion_server.php").httpMethod("get").postQuery(false);

        Request request = builder.build();
        return request;
    }
}
