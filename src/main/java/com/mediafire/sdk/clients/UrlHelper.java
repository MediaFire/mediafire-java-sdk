package com.mediafire.sdk.clients;

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
        String baseUrl;
        if (mRequest.getInstructionsObject().postQuery()) {
            baseUrl = getBaseUrlString();
            baseUrl += getBaseUriString();
        } else {
            baseUrl = makeUrlForGetRequest();
        }

        return baseUrl;
    }

    public String makeUrlForGetRequest() {
        String baseUrl = getBaseUrlString();

        String baseUri = getBaseUriString();

        String query = getQueryString(true);

        return baseUrl + baseUri + query;
    }

    public String getBaseUrlString() {
        HostObject hostObject = mRequest.getHostObject();
        String transferProtocol = hostObject.getTransferProtocol();

        String subdomain = hostObject.getSubdomain();

        String domain = hostObject.getDomain();

        return transferProtocol + "://" + subdomain + "." + domain;
    }

    public String getBaseUriString() {
        ApiObject apiObject = mRequest.getApiObject();
        VersionObject versionObject = mRequest.getVersionObject();

        String apiVersion = versionObject.getApiVersion();

        String path = apiObject.getPath();

        String file = apiObject.getFile();

        String baseUri = new String("/");
        if (apiVersion != null) {
            baseUri += "api/" + apiVersion + "/";
        } else {
            baseUri += "api/";
        }

        baseUri += path + "/";

        baseUri += file;

        return baseUri;
    }

    public String getQueryString(boolean encoded, boolean rawKeyValue) {
        Map<String, Object> queryParameters = mRequest.getQueryParameters();
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

        if (rawKeyValue) {
            return queryString.replaceFirst("&", "");
        } else {
            return queryString.replaceFirst("&", "?");
        }
    }

    public String getQueryString(boolean encoded) {
        return getQueryString(encoded, false);
    }

    public byte[] getPayload() {
        byte[] payload;

        if (mRequest.getInstructionsObject().postQuery()) {
            String queryString = getQueryString(true, true);
            payload = new String(queryString).getBytes();
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
