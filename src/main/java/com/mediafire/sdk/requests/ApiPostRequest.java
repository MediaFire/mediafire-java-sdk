package com.mediafire.sdk.requests;

import com.mediafire.sdk.util.HashUtil;

import java.util.*;

/**
 * Request is an object used to perform an Api Request
 */
public class ApiPostRequest {
    private static final String DEFAULT_SCHEME = "https";
    private static final String DEFAULT_DOMAIN = "www.mediafire.com";

    private final String scheme;
    private final String domain;
    private final String path;
    private final LinkedHashMap<String, Object> query;

    public ApiPostRequest(String scheme, String domain, String path, LinkedHashMap<String, Object> query) {
        this.scheme = scheme;
        this.domain = domain;
        this.path = path;
        this.query = query;
    }

    public ApiPostRequest(String path, LinkedHashMap<String, Object> query) {
        this(DEFAULT_SCHEME, DEFAULT_DOMAIN, path, query);
    }

    public String getPath() {
        return path;
    }

    public String getDomain() {
        return domain;
    }

    public String getScheme() {
        return scheme;
    }

    public LinkedHashMap<String, Object> getQueryMap() {
        return query;
    }

    public static ApiPostRequest newSessionRequestWithEmail(String apiKey, String appId, String email, String password) {
        LinkedHashMap<String, Object> query = getBaseParamsForNewSessionRequest(appId);
        query.put("email", email);
        query.put("password", password);
        return getApiRequestFromUserCredentials(apiKey, appId, query, email + password);
    }

    public static ApiPostRequest newSessionRequestWithEkey(String apiKey, String appId, String ekey, String password) {
        LinkedHashMap<String, Object> query = getBaseParamsForNewSessionRequest(appId);
        query.put("ekey", ekey);
        query.put("password", password);
        return getApiRequestFromUserCredentials(apiKey, appId, query, ekey + password);
    }

    public static ApiPostRequest newSessionRequestWithFacebook(String apiKey, String appId, String facebookAccessToken) {
        LinkedHashMap<String, Object> query = getBaseParamsForNewSessionRequest(appId);
        query.put("fb_access_token", facebookAccessToken);
        return getApiRequestFromUserCredentials(apiKey, appId, query, facebookAccessToken);
    }

    private static LinkedHashMap<String, Object> getBaseParamsForNewSessionRequest(String appId) {
        LinkedHashMap<String, Object> query = new LinkedHashMap<String, Object>();
        query.put("application_id", appId);
        query.put("token_version", 2);
        query.put("response_format", "json");
        return query;
    }

    private static ApiPostRequest getApiRequestFromUserCredentials(String apiKey, String appId, LinkedHashMap<String, Object> query, String userPortion) {
        if (apiKey != null) {
            query.put("signature", HashUtil.sha1(userPortion + appId + apiKey));
        } else {
            query.put("signature", HashUtil.sha1(userPortion + appId));
        }

        return new ApiPostRequest("/api/1.4/user/get_session_token.php", query);
    }
}
