package com.mediafire.sdk.requests;

import com.mediafire.sdk.api.UserApi;
import com.mediafire.sdk.util.HashUtil;
import com.mediafire.sdk.util.RequestUtil;

import java.util.*;

/**
 * Request is an object used to perform an Api Request
 */
public class ApiRequest {
    private static final String DEFAULT_SCHEME = "https";
    private static final String DEFAULT_DOMAIN = "www.mediafire.com";

    private final String scheme;
    private final String domain;
    private final String path;
    private final Map<String, Object> query;

    public ApiRequest(String scheme, String domain, String path, Map<String, Object> query) {
        this.scheme = scheme;
        this.domain = domain;
        this.path = path;
        this.query = query;
    }

    public ApiRequest(String path, Map<String, Object> query) {
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

    public Map<String, Object> getQueryMap() {
        return query;
    }

    public static ApiRequest newSessionRequestWithEmail(String apiKey, String appId, String email, String password) {
        Map<String, Object> query = getBaseParamsForNewSessionRequest(appId);
        query.put("email", email);
        query.put("password", password);
        return getApiRequestFromUserCredentials(apiKey, appId, query, email + password);
    }

    public static ApiRequest newSessionRequestWithEkey(String apiKey, String appId, String ekey, String password) {
        Map<String, Object> query = getBaseParamsForNewSessionRequest(appId);
        query.put("ekey", ekey);
        query.put("password", password);
        return getApiRequestFromUserCredentials(apiKey, appId, query, ekey + password);
    }

    public static ApiRequest newSessionRequestWithFacebook(String apiKey, String appId, String facebookAccessToken) {
        Map<String, Object> query = getBaseParamsForNewSessionRequest(appId);
        query.put("fb_access_token", facebookAccessToken);
        return getApiRequestFromUserCredentials(apiKey, appId, query, facebookAccessToken);
    }

    private static Map<String, Object> getBaseParamsForNewSessionRequest(String appId) {
        Map<String, Object> query = new LinkedHashMap<String, Object>();
        query.put("application_id", appId);
        query.put("token_version", 2);
        query.put("response_format", "json");
        return query;
    }

    private static ApiRequest getApiRequestFromUserCredentials(String apiKey, String appId, Map<String, Object> query, String userPortion) {
        if (apiKey != null) {
            query.put("signature", HashUtil.sha1(userPortion + appId + apiKey));
        } else {
            query.put("signature", HashUtil.sha1(userPortion + appId));
        }

        return new ApiRequest("/api/1.4/user/get_session_token.php", query);
    }
}
