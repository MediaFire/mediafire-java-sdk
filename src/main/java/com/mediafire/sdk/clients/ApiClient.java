package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.*;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

/**
 *  ApiClient is a wrapper for the methods required to make an request the the MediaFire API
 */
public class ApiClient {
    private static final String TAG = ApiClient.class.getCanonicalName();
    private ApiClientHelper mClientHelper;
    private HttpWorkerInterface mHttpWorker;


    public ApiClient(ApiClientHelper apiClientHelper, HttpWorkerInterface httpWorker) {
        mClientHelper = apiClientHelper;
        mHttpWorker = httpWorker;
    }

    /**
     * Performs a http request based on a Request, setting up before and cleaning up afterwards
     * @param request the request parameter to base the http request off of
     * @return returns a Result object after the http response is cleaned up
     */
    public Result doRequest(Request request) {
        // setup should handle the following:
        // 1. getting an ActionToken or SessionToken (if required) as per InstructionsObject
        // 2. calling Request.addToken() to add the token to the request.
        // 3. adding the session_token parameter to the query parameters via Request.addQueryParameter()
        // 4. calculate a signature (if required) as per InstructionsObject
        // 5. add signature parameters to the query parameters via Request.addQueryParameter()
        // 6. return Token or notify Token manager interfaces if a Token is invalid.
        mClientHelper.setup(request);

        String httpMethod = request.getHttpMethod();

        Response response = doRequest(request, httpMethod);

        mClientHelper.cleanup(response);

        return new Result(response, request);
    }

    private Response doRequest(Request request, String method) {
        if ("get".equalsIgnoreCase(method)) {
            return doGet(request);
        } else if ("post".equalsIgnoreCase(method)) {
            return doPost(request);
        } else {
            throw new IllegalArgumentException("request method '" + method + "' not supported");
        }
    }

    private Response doGet(Request request) {
        String url = new UrlHelper(request).makeUrlForGetRequest();
        // add headers to request
        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addGetHeaders();
        return mHttpWorker.doGet(url, request.getHeaders());
    }

    private Response doPost(Request request) {
        UrlHelper urlHelper = new UrlHelper(request);
        String url = urlHelper.makeUrlForPostRequest();
        byte[] payload = urlHelper.getPayload();

        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addPostHeaders(payload);

        return mHttpWorker.doPost(url, request.getHeaders(), payload);
    }
}
