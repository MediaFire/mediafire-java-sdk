package com.mediafire.sdk.client;

import com.mediafire.sdk.http.ApiObject;
import com.mediafire.sdk.http.HostObject;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.VersionObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class UrlHelper {

    private final Request mRequest;

    public UrlHelper(Request request) {
        mRequest = request;
    }

    public String makeUrlForPostRequest() {
        HostObject hostObject = mRequest.getHostObject();

        String baseUrl;
        if (mRequest.getApiObject().isQueryPostable()) {
            baseUrl = makeUrlForGetRequest();
        } else {
            baseUrl = getBaseUrlString(hostObject);
        }

        return baseUrl;
    }

    public String makeUrlForGetRequest() {
        ApiObject apiObject = mRequest.getApiObject();
        HostObject hostObject = mRequest.getHostObject();
        Map<String, Object> queryParameters = mRequest.getQueryParameters();
        VersionObject versionObject = mRequest.getVersionObject();

        String baseUrl = getBaseUrlString(hostObject);

        String baseUri = getBaseUriString(apiObject, versionObject);

        String query = getQueryString(queryParameters, true);

        return baseUrl + baseUri + query;
    }

    public String getBaseUrlString(HostObject hostObject) {
        String transferProtocol = hostObject.getTransferProtocol();

        String subdomain = hostObject.getSubdomain();

        String domain = hostObject.getDomain();

        return transferProtocol + "://" + subdomain + "." + domain;
    }

    public String getBaseUriString(ApiObject apiObject, VersionObject versionObject) {
        String apiVersion = versionObject.getApiVersion();

        String path = apiObject.getPath();

        String file = apiObject.getFile();

        String baseUri = new String("/");
        if (apiVersion != null) {
            baseUri += "api/" + apiVersion + "/";
        }

        baseUri += path + "api/";

        baseUri += file;

        return baseUri;
    }

    public String getQueryString(Map<String, Object> queryParameters, boolean encoded) {
        if (queryParameters == null) {
            return "";
        }

        String queryString = new String();
        for (String key : queryParameters.keySet()) {
            String rawStringValue = String.valueOf(queryParameters.get(key));
            if (encoded) {
                String encodedValue = utf8Encode(rawStringValue);
                queryString += "&" + key + "=" + encodedValue;
            } else {
                queryString += "&" + key + "=" + rawStringValue;
            }
        }

        return queryString.replaceFirst("&", "?");
    }

    public byte[] getPayload() {
        byte[] payload;

        if (mRequest.getApiObject().isQueryPostable()) {
            payload = new String(getQueryString(mRequest.getQueryParameters(), true)).getBytes();
        } else {
            payload = mRequest.getPayload();
        }
        return payload;
    }

    private String utf8Encode(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }
    }
}
