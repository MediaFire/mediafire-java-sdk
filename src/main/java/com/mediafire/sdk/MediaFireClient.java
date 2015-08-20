package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.MediaFireApiResponse;

import java.util.Map;

public interface MediaFireClient {

    <T extends MediaFireApiResponse> T noAuthRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException;

    MediaFireHttpResponse conversionServerRequest(String hash, Map<String, Object> requestParameters) throws MediaFireException;

    <T extends MediaFireApiResponse> T uploadRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException;

    <T extends MediaFireApiResponse> T sessionRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException;

    <T extends MediaFireApiResponse> T authenticationRequest(String apiVersion, int tokenVersion) throws MediaFireException;

    MediaFireHttpRequester getHttpRequester();

    MediaFireSessionStore getSessionStore();

    MediaFireCredentialsStore getCredentialStore();

    MediaFireHasher getHasher();

    MediaFireApiResponseParser getResponseParser();

    String getApplicationId();

    String getApiKey();

    String getOverrideVersion();
}
