package com.mediafire.sdk.util;

import com.mediafire.sdk.MFRuntimeException;
import com.mediafire.sdk.requests.ApiPostRequest;
import com.mediafire.sdk.requests.UploadPostRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

    private static final String UTF8 = "UTF-8";

    private RequestUtil() {
        // no instantiation, utility class
    }

    public static Map<String, String> makeHeadersFromApiRequest(ApiPostRequest apiPostRequest) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept-Charset", "UTF-8");
        headers.put("Content-Length", String.valueOf(makeQueryStringFromMap(apiPostRequest.getQueryMap(), true).getBytes().length));
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + UTF8);
        return headers;
    }

    public static byte[] makeQueryPayloadFromApiRequest(ApiPostRequest apiPostRequest) {
        return makeQueryStringFromMap(apiPostRequest.getQueryMap(), true).getBytes();
    }

    public static String makeQueryStringFromMap(Map<String, Object> query, boolean encoded) {
        try {
            StringBuilder sb = new StringBuilder();
            for (String key : query.keySet()) {
                sb.append(constructQueryKVPair(key, query.get(key), encoded));
            }
            return sb.toString().replaceFirst("&", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new MFRuntimeException("SDK error while encoding using charset " + UTF8, e);
        }
    }

    public static String makeUrlFromApiRequest(ApiPostRequest apiPostRequest) {
        return apiPostRequest.getScheme() + "://" + apiPostRequest.getDomain() + apiPostRequest.getPath();
    }

    public static String makeUrlFromUploadRequest(UploadPostRequest uploadRequest) {
        return makeUrlFromApiRequest(uploadRequest) + "?" + makeQueryStringFromMap(uploadRequest.getQueryMap(), true);
    }

    private static String constructQueryKVPair(String key, Object value, boolean encoded) throws UnsupportedEncodingException {
        if (encoded) {
            return "&" + key + "=" + URLEncoder.encode(String.valueOf(value), UTF8);
        } else {
            return "&" + key + "=" + value;
        }
    }

    public static String makeSignatureForApiRequest(ApiPostRequest apiPostRequest) {
        return null;
    }
}
