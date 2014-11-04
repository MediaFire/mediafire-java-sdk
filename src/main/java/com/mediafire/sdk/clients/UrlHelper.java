package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.http.ApiObject;
import com.mediafire.sdk.http.HostObject;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.VersionObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Chris Najar on 10/18/2014.
 * UrlHelper creates different parts of a url from a Request object
 */
public class UrlHelper {
    private static final String TAG = UrlHelper.class.getCanonicalName();

    private final Request mRequest;

    /**
     * UrlHelper Constructor
     * @param request the Request to for the url (parts) from
     */
    public UrlHelper(Request request) {
        mRequest = request;
    }

    public String makeConcatenatedUrlForGet() {
        HostObject hostObject = mRequest.getHostObject();
        String transferProtocol = (hostObject.getTransferProtocol() == null) ? "" : hostObject.getTransferProtocol();
        String domain = (hostObject.getDomain() == null) ? "" : hostObject.getDomain();
        String subdomain = (hostObject.getSubdomain() == null) ? "" : hostObject.getSubdomain();

        String query = getQueryString(true);

        return transferProtocol + domain + subdomain + query;
    }

    /**
     * Makes a url for post if the Requests' InstructionsObject says is postable, otherwise makes a url for get request
     * @return a String containing the full url
     */
    public String makeUrlForPostRequest() {
        DefaultLogger.log().v(TAG, "makeUrlForPostRequest");
        String baseUrl;
        if (mRequest.getInstructionsObject().postQuery()) {
            baseUrl = getBaseUrlString();
            baseUrl += getBaseUriString();
        } else {
            baseUrl = makeUrlForGetRequest();
        }

        return baseUrl;
    }

    /**
     * Makes a full url for a get request (including query params)
     * @return a String containing the full url
     */
    public String makeUrlForGetRequest() {
        DefaultLogger.log().v(TAG, "makeUrlForGetRequest");
        String baseUrl = getBaseUrlString();

        String baseUri = getBaseUriString();

        String query = getQueryString(true);

        return baseUrl + baseUri + query;
    }

    /**
     * Makes a base url from the request
     * @return a String containing the base url
     */
    public String getBaseUrlString() {
        DefaultLogger.log().v(TAG, "getBaseUrlString");
        HostObject hostObject = mRequest.getHostObject();
        String transferProtocol = hostObject.getTransferProtocol();

        String subdomain = hostObject.getSubdomain();

        String domain = hostObject.getDomain();

        return transferProtocol + "://" + subdomain + "." + domain;
    }

    /**
     * Makes a base uri from the request
     * @return a String containing the base uri
     */
    public String getBaseUriString() {
        DefaultLogger.log().v(TAG, "getBaseUriString");
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

    /**
     * Makes a query string from the request
     * @param encoded boolean of whether to uft8 encode the query parameters or not
     * @param rawKeyValue boolean of whether to have a query string, or a query string with a ? at the front
     * @return a String containing the query parameters
     */
    public String getQueryString(boolean encoded, boolean rawKeyValue) {
        DefaultLogger.log().v(TAG, "getQueryString");
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

    /**
     * Convenience method for getQueryString with rawKeyValue = false
     * @param encoded boolean of whether to uft8 encode the query parameters or not
     * @return a String containing the query parameters
     */
    public String getQueryString(boolean encoded) {
        return getQueryString(encoded, false);
    }

    /**
     * Returns the payload of the request, if is post query, set the query string as payload
     * @return a byte array of the payload
     */
    public byte[] getPayload() {
        DefaultLogger.log().v(TAG, "getPayload");
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
