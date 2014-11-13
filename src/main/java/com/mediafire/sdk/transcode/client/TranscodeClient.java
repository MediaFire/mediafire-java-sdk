package com.mediafire.sdk.transcode.client;

import com.mediafire.sdk.client_core.BaseClient;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

/**
 * Created by jondh on 11/4/14.
 */
public class TranscodeClient extends BaseClient {
    private static final String PARAM_CONTAINER = "container";
    private static final String PARAM_MEDIA_SIZE = "media_size";
    private static final String PARAM_EXISTS = "exists";
    private static final String HEADER_PARAM_ACCEPT_CHARSET = "Accept-Charset";

    private final String CHARSET = "UTF-8";

    public TranscodeClient(HttpWorkerInterface httpWorker) {
        super(httpWorker);
    }

    @Override
    public Result doRequest(Request request) {
        Response response = doGet(request);
        return new Result(response, request);
    }

    public Result create(String streamingUrl, String container, MediaSize mediaSize) {
        Request request = createRequestObjectFromPath(streamingUrl, container, mediaSize, Exists.CREATE);
        return doRequest(request);
    }

    public Result create(String streamingUrl, String container) {
        return create(streamingUrl, container, MediaSize.DEFAULT);
    }

    public Result check(String streamingUrl, String container, MediaSize mediaSize) {
        Request request = createRequestObjectFromPath(streamingUrl, container, mediaSize, Exists.CHECK);
        return doRequest(request);
    }

    public Result check(String streamingUrl, String container) {
        return check(streamingUrl, container, MediaSize.DEFAULT);
    }

    public Result status(String streamingUrl, String container, MediaSize mediaSize) {
        Request request = createRequestObjectFromPath(streamingUrl, container, mediaSize, Exists.STATUS);
        return doRequest(request);
    }

    public Result status(String streamingUrl, String container) {
        return status(streamingUrl, container, MediaSize.DEFAULT);
    }

    private Request createRequestObjectFromPath(String streamingUrl, String container, MediaSize mediaSize, Exists exists) {
        if (mediaSize == null) {
            mediaSize = MediaSize.DEFAULT;
        }

        if (exists == null) {
            exists = Exists.CHECK;
        }

        Request request = new Request(streamingUrl);
        request.addQueryParameter(PARAM_CONTAINER, container);
        request.addQueryParameter(PARAM_MEDIA_SIZE, mediaSize.getValue());
        request.addQueryParameter(PARAM_EXISTS, exists.getValue());
        request.addQueryParameter("response_format", "json");

        request.addHeader(HEADER_PARAM_ACCEPT_CHARSET, CHARSET);
        return request;
    }
}
