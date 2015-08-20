package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.ApiResponse;

public class MFRequestCoordinator implements MediaFireRequestCoordinator {

    private static final String UTF8 = "UTF-8";

    private final MediaFireHttpRequester requester;
    private final MediaFireSessionStore sessionStore;
    private final MediaFireCredentialsStore credentialsStore;
    private final MediaFireHasher hasher;
    private final MediaFireApiResponseParser parser;

    public MFRequestCoordinator(MediaFireHttpRequester requester,
                                MediaFireSessionStore sessionStore,
                                MediaFireCredentialsStore credentialsStore,
                                MediaFireHasher hasher,
                                MediaFireApiResponseParser parser) {

        this.requester = requester;
        this.sessionStore = sessionStore;
        this.credentialsStore = credentialsStore;
        this.hasher = hasher;
        this.parser = parser;
    }

    @Override
    public <T extends ApiResponse> T noAuthRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException {
        MediaFireHttpRequest mediaFireHttpRequest = new MFHttpRequest(requestUrl, requestPayload, requestHeaders);
        MediaFireHttpResponse mediaFireHttpResponse = new MFHttpResponse(responseStatusCode, responseBody, responseHeaders);
        return parser.parseResponse(mediaFireHttpResponse, classOfT);
    }

    @Override
    public MediaFireHttpResponse conversionServerRequest(MediaFireApiRequest request) throws MediaFireException {
        MediaFireHttpRequest mediaFireHttpRequest = new MFHttpRequest(requestUrl, requestPayload, requestHeaders);
        MediaFireHttpResponse mediaFireHttpResponse = new MFHttpResponse(responseStatusCode, responseBody, responseHeaders);
        return mediaFireHttpResponse;
    }

    @Override
    public <T extends ApiResponse> T uploadRequest(MediaFireApiRequest request, byte[] payload, Class<T> classOfT) throws MediaFireException {
        MediaFireHttpRequest mediaFireHttpRequest = new MFHttpRequest(requestUrl, requestPayload, requestHeaders);
        MediaFireHttpResponse mediaFireHttpResponse = new MFHttpResponse(responseStatusCode, responseBody, responseHeaders);
        return parser.parseResponse(mediaFireHttpResponse, classOfT);
    }

    @Override
    public <T extends ApiResponse> T sessionRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException {
        MediaFireHttpRequest mediaFireHttpRequest = new MFHttpRequest(requestUrl, requestPayload, requestHeaders);
        MediaFireHttpResponse mediaFireHttpResponse = new MFHttpResponse(responseStatusCode, responseBody, responseHeaders);
        return parser.parseResponse(mediaFireHttpResponse, classOfT);
    }

    @Override
    public MediaFireActionToken getActionToken(int type) {
        return null;
    }
}
