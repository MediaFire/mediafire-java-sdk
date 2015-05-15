package com.mediafire.sdk.util;

import com.mediafire.sdk.MFRuntimeException;
import com.mediafire.sdk.requests.ImageRequest;
import com.mediafire.sdk.requests.ApiRequest;
import com.mediafire.sdk.requests.UploadRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

    private static final String UTF8 = "UTF-8";

    private RequestUtil() {
        // no instantiation, utility class
    }

    public static Map<String, String> makeHeadersFromApiRequest(ApiRequest apiRequest) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept-Charset", "UTF-8");
        headers.put("Content-Length", String.valueOf(makeQueryStringFromMap(apiRequest.getQueryMap(), true).getBytes().length));
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + UTF8);
        return headers;
    }

    public static Map<String, String> makeHeadersFromUploadRequest(UploadRequest uploadRequest) {
        return null;
    }

    public static byte[] makeQueryPayloadFromApiRequest(ApiRequest apiRequest) {
        return new byte[0];
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

    public static String makeUrlFromApiRequest(ApiRequest apiRequest) {
        return apiRequest.getScheme() + "://" + apiRequest.getDomain() + apiRequest.getPath();
    }

    public static String makeUrlFromUploadRequest(UploadRequest uploadRequest) {
        return makeUrlFromApiRequest(uploadRequest) + "?" + makeQueryStringFromMap(uploadRequest.getQueryMap(), true);
    }

    public static String makeUrlFromImageRequest(ImageRequest imageRequest) {
        return null;
    }

    private static String constructQueryKVPair(String key, Object value, boolean encoded) throws UnsupportedEncodingException {
        if (encoded) {
            return "&" + key + "=" + URLEncoder.encode(String.valueOf(value), UTF8);
        } else {
            return "&" + key + "=" + value;
        }
    }
}
