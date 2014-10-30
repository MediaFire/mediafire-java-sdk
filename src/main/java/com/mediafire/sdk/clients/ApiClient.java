package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.*;
import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

/**
 *  ApiClient is a wrapper for the methods required to make an request the the MediaFire API
 */
public class ApiClient {
    private static final String TAG = ApiClient.class.getCanonicalName();
    protected final Configuration mConfiguration;

    /**
     * ApiClient Constructor
     * @param configuration a Configuration object used to perform the request and log
     */
    public ApiClient(Configuration configuration) {
        mConfiguration = configuration;
    }

    /**
     * Performs a http request based on a Request, setting up before and cleaning up afterwards
     * @param request the request parameter to base the http request off of
     * @return returns a Result object after the http response is cleaned up
     */
    public Result doRequest(Request request) {
        mConfiguration.getLogger().v(TAG, "doRequest");
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

        if (response != null) {
            mConfiguration.getLogger().v(TAG, "api response - " + new String(response.getBytes()));
        }

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
        mConfiguration.getLogger().v(TAG, "doGet");
        String url = new UrlHelper(request).makeUrlForGetRequest();
        // add headers to request
        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addGetHeaders();
        return mConfiguration.getHttpWorker().doGet(url, request.getHeaders());
    }

    private final Response doPost(Request request) {
        mConfiguration.getLogger().v(TAG, "doPost");
        UrlHelper urlHelper = new UrlHelper(request);
        String url = urlHelper.makeUrlForPostRequest();
        byte[] payload = urlHelper.getPayload();

        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addPostHeaders(payload);

        return mConfiguration.getHttpWorker().doPost(url, request.getHeaders(), payload);
    }
}
