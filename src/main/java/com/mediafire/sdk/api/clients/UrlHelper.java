package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.http.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Chris Najar on 10/18/2014.
 * UrlHelper creates different parts of a url from a Request object
 */
public class UrlHelper {
    private final Request mRequest;

    /**
     * UrlHelper Constructor
     * @param request the Request to for the url (parts) from
     */
    public UrlHelper(Request request) {
        mRequest = request;
    }

    public String getUrlForRequest() {
        if (mRequest.getHttpMethod().equalsIgnoreCase("get")) {
            return getUrlForGETRequest();
        }

        return getUrlForPOSTRequest();
    }

    /**
     * Makes a base uri from the request
     * @return a String containing the base uri
     */
    public String getPathString() {
        if (mRequest.getPath() == null) {
            return "/";
        }

        return "/" + mRequest.getPath();
    }

    /**
     * Makes a query string from the request
     * @param encoded boolean of whether to uft8 encode the query parameters or not
     * @param rawKeyValue boolean of whether to have a query string, or a query string with a ? at the front
     * @return a String containing the query parameters
     */
    public String getQueryString(boolean encoded, boolean rawKeyValue) {
        Map<String, Object> queryParameters = mRequest.getQueryParameters();
        if (queryParameters == null) {
            return "";
        }

        String queryString = "";
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
     * Makes a url for post if the Requests' InstructionsObject says is postable, otherwise makes a url for get request
     * @return a String containing the full url
     */
    private String getUrlForPOSTRequest() {

        if (mRequest.postQuery()) {
            return getBaseUrlString() + getPathString();
        }

        return getUrlForGETRequest();
    }

    /**
     * Makes a full url for a get request (including query params)
     * @return a String containing the full url
     */
    private String getUrlForGETRequest() {
        return getBaseUrlString() + getPathString() + getQueryString(true);
    }

    /**
     * Makes a base url from the request
     * @return a String containing the base url
     */
    private String getBaseUrlString() {
        return mRequest.getScheme() + "://" + mRequest.getFullDomain();
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
