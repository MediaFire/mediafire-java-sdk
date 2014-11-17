package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.client_core.BaseClient;
import com.mediafire.sdk.client_core.HeadersHelper;
import com.mediafire.sdk.client_helpers.BaseClientHelper;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

public class ApiClient extends BaseClient {
    private BaseClientHelper mBaseClientHelper;

    public ApiClient(BaseClientHelper baseClientHelper, HttpWorkerInterface httpWorker) {
        super(httpWorker);
        mBaseClientHelper = baseClientHelper;
    }

    /**
     * Performs a http request based on a Request, setting up before and cleaning up afterwards
     * @param request the request parameter to base the http request off of
     * @return returns a Result object after the http response is cleaned up
     */
    @Override
    public Result doRequest(Request request) {
        mBaseClientHelper.setup(request);

        String httpMethod = request.getHttpMethod();

        Response response = getResponseForRequest(request, httpMethod);

        mBaseClientHelper.cleanup(response, request);

        return new Result(response, request);
    }

    private Response getResponseForRequest(Request request, String method) {
        // both get and post use Accept-Charset header
        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addHeaders();

        if ("get".equalsIgnoreCase(method)) {
            return doGet(request);
        } else if ("post".equalsIgnoreCase(method)) {
            return doPost(request);
        } else {
            throw new IllegalArgumentException("request method '" + method + "' not supported");
        }
    }
}
