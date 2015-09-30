package com.mediafire.sdk;

import com.mediafire.sdk.response_models.MediaFireApiResponse;

import java.util.Map;

public interface MediaFireClient {

    /**
     * request that will not append a signature or session token to the request
     * @param request
     * @param classOfT
     * @param <T>
     * @return
     * @throws MediaFireException
     */
    <T extends MediaFireApiResponse> T noAuthRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException;

    /**
     * request to the mediafire conversion server
     * @param hash
     * @param requestParameters
     * @return
     * @throws MediaFireException
     */
    MediaFireHttpResponse conversionServerRequest(String hash, Map<String, Object> requestParameters) throws MediaFireException;

    /**
     * request to the mediafire conversion server using an Action Token passed
     * @param hash
     * @param requestParameters
     * @param token
     * @return
     * @throws MediaFireException
     */
    MediaFireHttpResponse conversionServerRequest(String hash, Map<String, Object> requestParameters, MediaFireActionToken token) throws MediaFireException;

    /**
     * upload request which uses an Action Token
     * @param request
     * @param classOfT
     * @param <T>
     * @return
     * @throws MediaFireException
     */
    <T extends MediaFireApiResponse> T uploadRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException;

    /**
     * upload request which uses an Action Token passed
     * @param request
     * @param classOfT
     * @param <T>
     * @param token
     * @return
     * @throws MediaFireException
     */
    <T extends MediaFireApiResponse> T uploadRequest(MediaFireApiRequest request, Class<T> classOfT, MediaFireActionToken token) throws MediaFireException;

    /**
     * request that will append a session token
     * @param request
     * @param classOfT
     * @param <T>
     * @return
     * @throws MediaFireException
     */
    <T extends MediaFireApiResponse> T sessionRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException;

    /**
     * request that will append a session token using the token passed (will not be returned when finished with the request)
     * @param request
     * @param classOfT
     * @param <T>
     * @param token
     * @return
     * @throws MediaFireException
     */
    <T extends MediaFireApiResponse> T sessionRequest(MediaFireApiRequest request, Class<T> classOfT, MediaFireSessionToken token) throws MediaFireException;

    /**
     * request that will append a session token using the token passed (will not be returned when finished with the request). Should only be used with V1 tokens.
     * @param request
     * @param classOfT
     * @param <T>
     * @param token
     * @return
     * @throws MediaFireException
     */
    <T extends MediaFireApiResponse> T sessionRequest(MediaFireApiRequest request, Class<T> classOfT, String token) throws MediaFireException;

    /**
     * request for a new session token
     * @param <T>
     * @return
     * @throws MediaFireException
     */
    <T extends MediaFireApiResponse> T authenticationRequest(Class<T> classOfT) throws MediaFireException;

    /**
     * the http requester
     * @return
     */
    MediaFireHttpRequester getHttpRequester();

    /**
     * the session store
     * @return
     */
    MediaFireSessionStore getSessionStore();

    /**
     * the credentials store
     * @return
     */
    MediaFireCredentialsStore getCredentialStore();

    /**
     * the hasher
     * @return
     */
    MediaFireHasher getHasher();

    /**
     * the response parser
     * @return
     */
    MediaFireApiResponseParser getResponseParser();

    /**
     * the application id of the client
     * @return
     */
    String getApplicationId();

    /**
     * the api key of the client
     * @return
     */
    String getApiKey();

    /**
     * api version that will always be used (unless null or empty)
     * @return
     */
    String getApiVersion();

    /**
     * creates a signature for a request with a session token.
     * @param token
     * @param uri
     * @param query
     * @return
     * @throws MediaFireException
     */
    String getSessionSignature(MediaFireSessionToken token, String uri, Map<String, Object> query) throws MediaFireException;
}
