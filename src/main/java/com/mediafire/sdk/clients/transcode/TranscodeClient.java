package com.mediafire.sdk.clients.transcode;

import com.mediafire.sdk.clients.UrlHelper;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

/**
 * Created by jondh on 11/4/14.
 */
public class TranscodeClient {
    private static final String PARAM_CONTAINER = "container";
    private static final String PARAM_MEDIA_SIZE = "media_size";
    private static final String PARAM_EXISTS = "exists";

    private final HttpWorkerInterface mHttpWorker;
    private final String mContainer;
    private final String CHARSET = "UTF-8";

    public TranscodeClient(HttpWorkerInterface httpWorker, String container) {
        mHttpWorker = httpWorker;
        mContainer = container;
    }

    public Result doRequest(Request request) {
        String url = new UrlHelper(request).getUrlForRequest();
        // add headers to request
        request.addHeader("Accept-Charset", CHARSET);
        Response response = mHttpWorker.doGet(url, request.getHeaders());

        return new Result(response, request);
    }

    public Result create(String url, String media_size) {

        Request request = new Request(url);

        request.addQueryParameter(PARAM_CONTAINER, mContainer);

        if(media_size != null) {
            request.addQueryParameter(PARAM_MEDIA_SIZE, media_size);
        }
        request.addQueryParameter(PARAM_EXISTS, "create");

        return doRequest(request);
    }

    public Result create(String url) {
        return create(url, null);
    }

    public Result check(String url, String media_size) {

        Request request = new Request(url);

        request.addQueryParameter(PARAM_CONTAINER, mContainer);
        if(media_size != null) {
            request.addQueryParameter(PARAM_MEDIA_SIZE, media_size);
        }
        request.addQueryParameter(PARAM_EXISTS, "check");

        return doRequest(request);
    }

    public Result check(String url) {
        return check(url, null);
    }

    public Result status(String url, String media_size) {

        Request request = new Request(url);

        request.addQueryParameter(PARAM_CONTAINER, mContainer);
        if(media_size != null) {
            request.addQueryParameter(PARAM_MEDIA_SIZE, media_size);
        }
        request.addQueryParameter(PARAM_EXISTS, "status");

        return doRequest(request);
    }

    public Result status(String url) {
        return status(url, null);
    }

    public String getStreamingUrl(String url) {
        return url + "?container=" + mContainer;
    }

}
