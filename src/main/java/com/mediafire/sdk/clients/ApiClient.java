package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.*;
import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

public class ApiClient {
    private static final String TAG = ApiClient.class.getCanonicalName();
    private final Configuration mConfiguration;

    public ApiClient(Configuration configuration) {
        mConfiguration = configuration;
    }

    public Result doRequest(Request request) {
        DefaultLogger.log().v(TAG, "doRequest");
        ApiClientHelper apiClientHelper = new ApiClientHelper(mConfiguration);

        // setup should handle the following:
        // 1. getting an ActionToken or SessionToken (if required) as per InstructionsObject
        // 2. calling Request.addToken() to add the token to the request.
        // 3. adding the session_token parameter to the query parameters via Request.addQueryParameter()
        // 4. calculate a signature (if required) as per InstructionsObject
        // 5. add signature parameters to the query parameters via Request.addQueryParameter()
        // 6. return Token or notify Token manager interfaces if a Token is invalid.
        apiClientHelper.setup(request);

        String httpMethod = request.getHostObject().getHttpMethod();

        Response response = doRequest(request, httpMethod);

        apiClientHelper.cleanup(response);

        return new Result(response, request);
    }

    private final Response doRequest(Request request, String method) {
        if (method.equalsIgnoreCase("get")) {
            return doGet(request);
        } else if (method.equalsIgnoreCase("post")) {
            return doPost(request);
        } else {
            throw new IllegalArgumentException("request method '" + method + "' not supported");
        }
    }

    private final Response doGet(Request request) {
        DefaultLogger.log().v(TAG, "doGet");
        String url = new UrlHelper(request).makeUrlForGetRequest();
        // add headers to request
        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addGetHeaders();
        return mConfiguration.getHttpWorker().doGet(url, request.getHeaders());
    }

    private final Response doPost(Request request) {
        DefaultLogger.log().v(TAG, "doPost");
        UrlHelper urlHelper = new UrlHelper(request);
        String url = urlHelper.makeUrlForPostRequest();
        byte[] payload = urlHelper.getPayload();

        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addPostHeaders(payload);

        return mConfiguration.getHttpWorker().doPost(url, request.getHeaders(), payload);
    }
}
