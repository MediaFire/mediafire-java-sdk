package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.ApiResponse;

public interface MediaFireClient {
    /**
     * makes a request to MediaFire. the ApiResponse matches the class passed.
     * @param request
     * @param classOfT
     * @param <T>
     * @return
     */
    <T extends ApiResponse> T doNoAuthRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException;

    /**
     *
     * @param request
     * @return
     * @throws MediaFireException
     */
    MediaFireHttpResponse doConversionRequest(MediaFireApiRequest request) throws MediaFireException;

    /**
     *
     * @param request
     * @param payload
     * @return
     * @throws MediaFireException
     */
    <T extends ApiResponse> T doUploadRequest(MediaFireApiRequest request, byte[] payload) throws MediaFireException;

    /**
     *
     * @param request
     * @param classOfT
     * @param <T>
     * @return
     * @throws MediaFireException
     */
    <T extends ApiResponse> T doAuthenticatedRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException;

    /**
     *
     * @return
     * @throws MediaFireException
     * @param type
     */
    MediaFireActionToken getActionToken(int type) throws MediaFireException;
}
