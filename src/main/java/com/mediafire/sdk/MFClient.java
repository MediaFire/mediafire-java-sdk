package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.MediaFireApiResponse;
import com.mediafire.sdk.api.responses.UserGetActionTokenResponse;
import com.mediafire.sdk.api.responses.UserGetSessionTokenResponse;
import com.mediafire.sdk.util.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MFClient implements MediaFireClient {

    private static final String UTF8 = "UTF-8";

    private final String overrideVersion;
    private final MediaFireHttpRequester requester;
    private final MediaFireSessionStore sessionStore;
    private final MediaFireCredentialsStore credentialsStore;
    private final MediaFireHasher hasher;
    private final MediaFireApiResponseParser parser;
    private final String applicationId;
    private final String apiKey;

    public MFClient(String applicationId,
                    String apiKey,
                    String overrideVersion,
                    MediaFireHttpRequester requester,
                    MediaFireSessionStore sessionStore,
                    MediaFireCredentialsStore credentialsStore,
                    MediaFireHasher hasher,
                    MediaFireApiResponseParser parser) {
        this.applicationId = applicationId;
        this.overrideVersion = overrideVersion;
        this.requester = requester;
        this.sessionStore = sessionStore;
        this.credentialsStore = credentialsStore;
        this.hasher = hasher;
        this.parser = parser;
        this.apiKey = apiKey;
    }

    @Override
    public <T extends MediaFireApiResponse> T noAuthRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException {

        Map<String, Object> query = new LinkedHashMap<String, Object>();
        query.put("response_format", getResponseParser().getResponseFormat());
        query.putAll(request.getQueryParameters());

        byte[] payload = makeQueryStringFromMap(query, true).getBytes();
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + UTF8);
        headers.put("Content-Length", payload.length);
        headers.put("Accept-Charset", "UTF-8");

        String baseUrl = "https://www.mediafire.com";

        StringBuilder url = new StringBuilder();
        url.append(baseUrl).append("/api");

        if (!TextUtils.isEmpty(getOverrideVersion())) {
            url.append("/").append(getOverrideVersion());
        } if (!TextUtils.isEmpty(request.getVersion())) {
            url.append("/").append(request.getVersion());
        }
        url.append(request.getPath());

        MediaFireHttpRequest mediaFireHttpRequest = new MFHttpRequest(url.toString(), payload, headers);
        MediaFireHttpResponse mediaFireHttpResponse = requester.post(mediaFireHttpRequest);
        return getResponseParser().parseResponse(mediaFireHttpResponse, classOfT);
    }

    @Override
    public MediaFireHttpResponse conversionServerRequest(String hash, Map<String, Object> requestParameters) throws MediaFireException {
        String baseUrl = "https://www.mediafire.com";

        StringBuilder url = new StringBuilder();
        url.append(baseUrl);
        url.append("/conversion_server.php?");
        if (hash == null || hash.length() < 4) {
            throw new MediaFireException("invalid hash passed in conversion request");
        }

        url.append(hash.substring(0, 4));

        MediaFireActionToken mediaFireActionToken;

        synchronized (getSessionStore()) {
            if (!getSessionStore().isActionTokenAvailable(MediaFireActionToken.TYPE_IMAGE)) {
                mediaFireActionToken = requestNewActionToken(MediaFireActionToken.TYPE_IMAGE);
                if (mediaFireActionToken == null) {
                    throw new MediaFireException("could not request action token type " + MediaFireActionToken.TYPE_IMAGE);
                }
                getSessionStore().put(mediaFireActionToken);
            } else {
                mediaFireActionToken = getSessionStore().getActionToken(MediaFireActionToken.TYPE_IMAGE);
            }
        }

        if (mediaFireActionToken == null) {
            throw new MediaFireException("could not get action token type " + MediaFireActionToken.TYPE_IMAGE + " from store");
        }

        requestParameters.put("session_token", mediaFireActionToken.getSessionToken());

        String encodedQuery = makeQueryStringFromMap(requestParameters, true);

        url.append(encodedQuery);

        MediaFireHttpRequest mediaFireHttpRequest = new MFHttpRequest(url.toString(), null, null);
        return getHttpRequester().get(mediaFireHttpRequest);
    }

    @Override
    public <T extends MediaFireApiResponse> T uploadRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException {

        Map<String, Object> query = new LinkedHashMap<String, Object>();
        query.put("response_format", getResponseParser().getResponseFormat());
        query.putAll(request.getQueryParameters());

        MediaFireActionToken mediaFireActionToken;

        synchronized (getSessionStore()) {
            if (!getSessionStore().isActionTokenAvailable(MediaFireActionToken.TYPE_UPLOAD)) {
                mediaFireActionToken = requestNewActionToken(MediaFireActionToken.TYPE_UPLOAD);
                if (mediaFireActionToken == null) {
                    throw new MediaFireException("could not request action token type " + MediaFireActionToken.TYPE_IMAGE);
                }
                getSessionStore().put(mediaFireActionToken);
            } else {
                mediaFireActionToken = getSessionStore().getActionToken(MediaFireActionToken.TYPE_UPLOAD);
            }
        }

        if (mediaFireActionToken == null) {
            throw new MediaFireException("could not get action token type " + MediaFireActionToken.TYPE_UPLOAD + " from store");
        }

        query.put("session_token", mediaFireActionToken.getSessionToken());

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/octet-stream");
        headers.put("Content-Length", request.getPayload().length);
        headers.put("Accept-Charset", "UTF-8");

        String baseUrl = "https://www.mediafire.com";
        StringBuilder url = new StringBuilder();
        url.append(baseUrl).append("/api");
        if (!TextUtils.isEmpty(getOverrideVersion())) {
            url.append("/").append(getOverrideVersion());
        } if (!TextUtils.isEmpty(request.getVersion())) {
            url.append("/").append(request.getVersion());
        }
        url.append(request.getPath()).append("?");

        String encodedQuery = makeQueryStringFromMap(query, true);

        url.append(encodedQuery);

        MediaFireHttpRequest mediaFireHttpRequest = new MFHttpRequest(url.toString(), request.getPayload(), headers);
        MediaFireHttpResponse mediaFireHttpResponse = getHttpRequester().post(mediaFireHttpRequest);
        return getResponseParser().parseResponse(mediaFireHttpResponse, classOfT);
    }

    @Override
    public <T extends MediaFireApiResponse> T sessionRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException {
        Map<String, Object> query = new LinkedHashMap<String, Object>();
        query.put("response_format", getResponseParser().getResponseFormat());

        String baseUrl = "https://www.mediafire.com";
        StringBuilder url = new StringBuilder();
        url.append(baseUrl);
        StringBuilder uri = new StringBuilder();
        uri.append("/api");
        if (!TextUtils.isEmpty(getOverrideVersion())) {
            uri.append("/").append(getOverrideVersion());
        } if (!TextUtils.isEmpty(request.getVersion())) {
            uri.append("/").append(request.getVersion());
        }
        uri.append(request.getPath());

        MediaFireSessionToken mediaFireSessionToken;

        synchronized (getSessionStore()) {
            if (!getSessionStore().isSessionTokenV2Available()) {
                mediaFireSessionToken = requestNewSessionToken();
            } else {
                mediaFireSessionToken = getSessionStore().getSessionTokenV2();
            }
        }

        if (mediaFireSessionToken == null) {
            throw new MediaFireException("could not get action token type " + MediaFireActionToken.TYPE_UPLOAD + " from store");
        }

        query.put("session_token", mediaFireSessionToken.getSessionToken());

        String signature = createSignatureForAuthenticatedRequest(mediaFireSessionToken.getSecretKey(), mediaFireSessionToken.getTime(), uri.toString(), query);

        query.put("signature", signature);

        String encodedQuery = makeQueryStringFromMap(query, true);

        Map<String, Object> headers = createHeadersUsingQueryAsPostBody(encodedQuery);

        url.append(uri.toString());

        MediaFireHttpRequest mediaFireHttpRequest = new MFHttpRequest(url.toString(), encodedQuery.getBytes(), headers);
        MediaFireHttpResponse mediaFireHttpResponse = getHttpRequester().post(mediaFireHttpRequest);

        T response = getResponseParser().parseResponse(mediaFireHttpResponse, classOfT);

        if (!response.hasError()) {
            getSessionStore().put(mediaFireSessionToken);
        }

        return response;
    }

    @Override
    public UserGetSessionTokenResponse authenticationRequest(String apiVersion, int tokenVersion) throws MediaFireException {

        int credentialType = getCredentialStore().getTypeStored();

        Map<String, Object> query = new LinkedHashMap<String, Object>();
        query.put("response_format", getResponseParser().getResponseFormat());
        query.put("token_version", tokenVersion);
        query.put("application_id", getApplicationId());

        StringBuilder unhashedSignature = new StringBuilder();
        switch (credentialType) {
            case MediaFireCredentialsStore.TYPE_EMAIL:
                MediaFireCredentialsStore.EmailCredentials emailCredentials = getCredentialStore().getEmailCredentials();
                query.put("email", emailCredentials.getEmail());
                query.put("password", emailCredentials.getPassword());
                unhashedSignature.append(emailCredentials.getEmail()).append(emailCredentials.getPassword());
                break;
            case MediaFireCredentialsStore.TYPE_EKEY:
                MediaFireCredentialsStore.EkeyCredentials ekeyCredentials = getCredentialStore().getEkeyCredentials();
                query.put("ekey", ekeyCredentials.getEkey());
                query.put("password", ekeyCredentials.getPassword());
                unhashedSignature.append(ekeyCredentials.getEkey()).append(ekeyCredentials.getPassword());
                break;
            case MediaFireCredentialsStore.TYPE_FACEBOOK:
                MediaFireCredentialsStore.FacebookCredentials facebookCredentials = getCredentialStore().getFacebookCredentials();
                query.put("fb_access_token", facebookCredentials.getFacebookAccessToken());
                unhashedSignature.append(facebookCredentials.getFacebookAccessToken());
                break;
            case MediaFireCredentialsStore.TYPE_TWITTER:
                MediaFireCredentialsStore.TwitterCredentials twitterCredentials = getCredentialStore().getTwitterCredentials();
                query.put("tw_oauth_token", twitterCredentials.getTwitterOauthToken());
                query.put("tw_oauth_token_secret", twitterCredentials.getTwitterOauthTokenSecret());
                unhashedSignature.append(twitterCredentials.getTwitterOauthToken()).append(twitterCredentials.getTwitterOauthTokenSecret());
                break;
            case MediaFireCredentialsStore.TYPE_NONE:
            default:
                throw new MediaFireException("no credentials stored, cannot authenticate");
        }

        if (getApiKey() != null) {
            unhashedSignature.append(getApiKey());
        }

        String hashedSignature = getHasher().sha1(unhashedSignature.toString());

        query.put("signature", hashedSignature);

        byte[] payload = makeQueryStringFromMap(query, true).getBytes();

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + UTF8);
        headers.put("Content-Length", payload.length);
        headers.put("Accept-Charset", "UTF-8");

        String baseUrl = "https://www.mediafire.com";
        StringBuilder url = new StringBuilder();
        url.append(baseUrl).append("/api");
        if (!TextUtils.isEmpty(getOverrideVersion())) {
            url.append("/").append(getOverrideVersion());
        } if (!TextUtils.isEmpty(apiVersion)) {
            url.append("/").append(apiVersion);
        }
        url.append("/user/get_session_token.php");

        MediaFireHttpRequest mediaFireHttpRequest = new MFHttpRequest(url.toString(), payload, headers);
        MediaFireHttpResponse mediaFireHttpResponse = getHttpRequester().post(mediaFireHttpRequest);
        return getResponseParser().parseResponse(mediaFireHttpResponse, UserGetSessionTokenResponse.class);
    }

    @Override
    public String getApplicationId() {
        return applicationId;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getOverrideVersion() {
        return overrideVersion;
    }

    @Override
    public MediaFireHttpRequester getHttpRequester() {
        return requester;
    }

    @Override
    public MediaFireSessionStore getSessionStore() {
        return sessionStore;
    }

    @Override
    public MediaFireCredentialsStore getCredentialStore() {
        return credentialsStore;
    }

    @Override
    public MediaFireHasher getHasher() {
        return hasher;
    }

    @Override
    public MediaFireApiResponseParser getResponseParser() {
        return parser;
    }

    public MediaFireActionToken requestNewActionToken(int type) throws MediaFireException {

        Map<String, Object> query = new LinkedHashMap<String, Object>();
        query.put("response_format", getResponseParser().getResponseFormat());

        int lifespan;
        switch (type) {
            case MediaFireActionToken.TYPE_IMAGE:
                lifespan = 60;
                query.put("type", "image");
                break;
            case MediaFireActionToken.TYPE_UPLOAD:
                lifespan = 360;
                query.put("type", "upload");
                break;
            default:
                throw new MediaFireException("invalid action token type passed: " + type);
        }

        query.put("lifespan", lifespan);

        MediaFireApiRequest request = new MFApiRequest("/user/get_action_token.php", query, getOverrideVersion(), null, null);
        UserGetActionTokenResponse response = sessionRequest(request, UserGetActionTokenResponse.class);
        if (response.hasError()) {
            return null;
        }
        String sessionToken = response.getActionToken();
        return new MFActionToken(sessionToken, type, System.currentTimeMillis(), lifespan);
    }

    private MediaFireSessionToken requestNewSessionToken() throws MediaFireException {
        UserGetSessionTokenResponse response = authenticationRequest(getOverrideVersion(), 2);
        if (response.hasError()) {
            return null;
        }

        String sessionToken = response.getSessionToken();
        String time = response.getTime();
        long secretKey = response.getSecretKey();
        String pkey = response.getPkey();
        String ekey = response.getEkey();
        return new MFSessionToken(sessionToken, time, secretKey, pkey, ekey);
    }

    public Map<String, Object> createHeadersUsingQueryAsPostBody(String encodedQuery) throws MediaFireException {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Accept-Charset", "UTF-8");
        headers.put("Content-Length", encodedQuery.length());
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + UTF8);
        return headers;
    }

    public String makeQueryStringFromMap(Map<String, Object> query, boolean encoded) throws MediaFireException {
        StringBuilder sb = new StringBuilder();
        for (String key : query.keySet()) {
            sb.append(constructQueryKVPair(key, query.get(key), encoded));
        }
        return sb.toString().replaceFirst("&", "");
    }

    public String constructQueryKVPair(String key, Object value, boolean encoded) throws MediaFireException {
        if (encoded) {
            try {
                return "&" + key + "=" + URLEncoder.encode(String.valueOf(value), UTF8);
            } catch (UnsupportedEncodingException e) {
                throw new MediaFireException("could not encode string using " + UTF8, e);
            }
        } else {
            return "&" + key + "=" + value;
        }
    }

    public String createSignatureForAuthenticatedRequest(long secretKey, String time, String path, Map<String, Object> query) throws MediaFireException {
        long secretKeyMod256 = secretKey % 256;
        String queryMap = makeQueryStringFromMap(query, false);
        String hashTarget = secretKeyMod256 + time + path + "?" + queryMap;
        return getHasher().md5(hashTarget);
    }
}
