package com.mediafire.sdk.transcode.client;

import com.mediafire.sdk.client_core.ApiClient;
import com.mediafire.sdk.client_core.UrlHelper;
import com.mediafire.sdk.client_helpers.BaseClientHelper;
import com.mediafire.sdk.client_helpers.ClientHelperNoToken;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

import javax.print.attribute.standard.Media;

/**
 * Created by jondh on 11/4/14.
 */
public class TranscodeClient {
    private static final String PARAM_CONTAINER = "container";
    private static final String PARAM_MEDIA_SIZE = "media_size";
    private static final String PARAM_EXISTS = "exists";
    private static final String HEADER_PARAM_ACCEPT_CHARSET = "Accept-Charset";

    private final String CHARSET = "UTF-8";
    private final ApiClient mApiClient;

    public TranscodeClient(HttpWorkerInterface httpWorker) {
        BaseClientHelper clientHelper = new ClientHelperNoToken();
        mApiClient = new ApiClient(clientHelper, httpWorker);
    }

    public Result create(String streamingUrl, String container, MediaSize mediaSize) {
        Request request = createRequestObjectFromPath(streamingUrl, container, mediaSize, Exists.CREATE);
        return mApiClient.doRequest(request);
    }

    public Result check(String streamingUrl, String container, MediaSize mediaSize) {
        Request request = createRequestObjectFromPath(streamingUrl, container, mediaSize, Exists.CHECK);
        return mApiClient.doRequest(request);
    }

    public Result status(String streamingUrl, String container, MediaSize mediaSize) {
        Request request = createRequestObjectFromPath(streamingUrl, container, mediaSize, Exists.STATUS);
        return mApiClient.doRequest(request);
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
