package com.mediafire.sdk.clients.transcode;

import com.mediafire.sdk.clients.HeadersHelper;
import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.clients.UrlHelper;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.*;

/**
 * Created by jondh on 11/4/14.
 */
public class TranscodeClient {
    private static final String PARAM_CONTAINER = "container";
    private static final String PARAM_MEDIA_SIZE = "media_size";
    private static final String PARAM_EXISTS = "exists";

    private HttpWorkerInterface mHttpWorker;
    private final String mStreamingUrl;
    private final String mContainer;

    public TranscodeClient(HttpWorkerInterface httpWorker, String streamingUrl, String container) {
        mHttpWorker = httpWorker;
        mStreamingUrl = streamingUrl;
        mContainer = container;
    }

    public Result doRequest(Request request) {
        String url = new UrlHelper(request).makeConcatenatedUrlForGet();
        // add headers to request
        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addGetHeaders();
        Response response = mHttpWorker.doGet(url, request.getHeaders());

        return new Result(response, request);
    }

    public Result create(String media_size) {

        Request request = new Request(new HostObject(mStreamingUrl, null, null, null), null, null, null);

        request.addQueryParameter(PARAM_CONTAINER, mContainer);
        if(media_size != null) {
            request.addQueryParameter(PARAM_MEDIA_SIZE, media_size);
        }
        request.addQueryParameter(PARAM_EXISTS, "create");

        return doRequest(request);
    }

    public Result create() {
        return create(null);
    }

    public Result check(String media_size) {

        Request request = new Request(new HostObject(mStreamingUrl, null, null, null), null, null, null);

        request.addQueryParameter(PARAM_CONTAINER, mContainer);
        if(media_size != null) {
            request.addQueryParameter(PARAM_MEDIA_SIZE, media_size);
        }
        request.addQueryParameter(PARAM_EXISTS, "check");

        return doRequest(request);
    }

    public Result check() {
        return check(null);
    }

    public Result status(String media_size) {

        Request request = new Request(new HostObject(mStreamingUrl, null, null, null), null, null, null);

        request.addQueryParameter(PARAM_CONTAINER, mContainer);
        if(media_size != null) {
            request.addQueryParameter(PARAM_MEDIA_SIZE, media_size);
        }
        request.addQueryParameter(PARAM_EXISTS, "status");

        return doRequest(request);
    }

    public Result status() {
        return status(null);
    }

    public String getStreamingUrl() {

        return mStreamingUrl + "?container=" + mContainer;
    }

}
