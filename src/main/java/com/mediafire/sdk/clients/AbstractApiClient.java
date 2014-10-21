package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris Najar on 10/20/2014.
 */
public abstract class AbstractApiClient {
    private static final String TAG = AbstractApiClient.class.getCanonicalName();
    protected final HttpWorkerInterface mHttpWorker;

    public AbstractApiClient(HttpWorkerInterface httpWorker) {
        mHttpWorker = httpWorker;
    }

    public abstract Result doRequest(Request request);

    protected final Response doRequest(Request request, String method) {
        if (method.equalsIgnoreCase("get")) {
            return doGet(request);
        } else if (method.equalsIgnoreCase("post")) {
            return doPost(request);
        } else {
            throw new IllegalArgumentException("request method '" + method + "' not supported");
        }
    }

    protected final Response doGet(Request request) {
        DefaultLogger.log().v(TAG, "doGet");
        String url = new UrlHelper(request).makeUrlForGetRequest();
        // add headers to request
        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addGetHeaders();
        return mHttpWorker.doGet(url, request.getHeaders());
    }

    protected final Response doPost(Request request) {
        DefaultLogger.log().v(TAG, "doPost");
        UrlHelper urlHelper = new UrlHelper(request);
        String url = urlHelper.makeUrlForPostRequest();
        byte[] payload = urlHelper.getPayload();

        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addPostHeaders(payload);

        return mHttpWorker.doPost(url, request.getHeaders(), payload, request.getInstructionsObject().postQuery());
    }
}
