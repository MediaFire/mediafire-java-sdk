package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

/**
 *  ApiClient is a wrapper for the methods required to make an request the the MediaFire API
 */
public class ApiClient {
    private BaseClientHelper mBaseClientHelper;
    private HttpWorkerInterface mHttpWorker;
    private final String CHARSET = "UTF-8";

    public ApiClient(BaseClientHelper baseClientHelper, HttpWorkerInterface httpWorker) {
        mBaseClientHelper = baseClientHelper;
        mHttpWorker = httpWorker;
    }

    /**
     * Performs a http request based on a Request, setting up before and cleaning up afterwards
     * @param request the request parameter to base the http request off of
     * @return returns a Result object after the http response is cleaned up
     */
    public Result doRequest(Request request) {
        mBaseClientHelper.setup(request);

        String httpMethod = request.getHttpMethod();

        Response response = doRequest(request, httpMethod);

        mBaseClientHelper.cleanup(response, request);

        return new Result(response, request);
    }

    private Response doRequest(Request request, String method) {
        // both get and post use Accept-Charset header
        request.addHeader("Accept-Charset", CHARSET);

        if ("get".equalsIgnoreCase(method)) {
            return doGet(request);
        } else if ("post".equalsIgnoreCase(method)) {
            return doPost(request);
        } else {
            throw new IllegalArgumentException("request method '" + method + "' not supported");
        }
    }

    private Response doGet(Request request) {
        String url = new UrlHelper(request).getUrlForRequest();
        // add headers to request
        request.addHeader("Accept-Charset", CHARSET);
        return mHttpWorker.doGet(url, request.getHeaders());
    }

    private Response doPost(Request request) {
        UrlHelper urlHelper = new UrlHelper(request);
        String url = urlHelper.getUrlForRequest();


        byte[] payload = request.getPayload();

        if (request.postQuery()) {
            request.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARSET);
        } else {
            request.addHeader("Content-Type", "application/octet-stream");
        }

        if (request.getPayload() != null) {
            request.addHeader("Content-Length", String.valueOf(payload.length));
        }

        return mHttpWorker.doPost(url, request.getHeaders(), payload);
    }
}
