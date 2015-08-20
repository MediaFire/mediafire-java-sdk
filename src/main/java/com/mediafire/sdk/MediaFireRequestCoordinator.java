package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.ApiResponse;

public interface MediaFireRequestCoordinator {

    <T extends ApiResponse> T noAuthRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException;

    MediaFireHttpResponse conversionServerRequest(MediaFireApiRequest request) throws MediaFireException;

    <T extends ApiResponse> T uploadRequest(MediaFireApiRequest request, byte[] payload, Class<T> classOfT) throws MediaFireException;

    <T extends ApiResponse> T sessionRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException;

    MediaFireActionToken getActionToken(int type);
}
