package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.*;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

/**
 *  ApiClient is a wrapper for the methods required to make an request the the MediaFire API
 */
public class ApiClient {
    private ClientHelper mClientHelper;
    private HttpWorkerInterface mHttpWorker;


    public ApiClient(ClientHelper clientHelper, HttpWorkerInterface httpWorker) {
        mClientHelper = clientHelper;
        mHttpWorker = httpWorker;
    }

    /**
     * Performs a http request based on a Request, setting up before and cleaning up afterwards
     * @param request the request parameter to base the http request off of
     * @return returns a Result object after the http response is cleaned up
     */
    public Result doRequest(Request request) {
        mClientHelper.setup(request);

        String httpMethod = request.getHttpMethod();

        Response response = doRequest(request, httpMethod);

        mClientHelper.cleanup(response, request);

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
