package com.mediafire.sdk.http;

import com.mediafire.sdk.config.MFConfiguration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public abstract class MFHttp {
    private static final String TAG = MFHttp.class.getCanonicalName();

    public MFHttp () {}

    protected final String makeQueryString(Map<String, String> requestParameters, boolean urlEncode) throws UnsupportedEncodingException {
        MFConfiguration.getStaticMFLogger().d(TAG, "makeQueryString(encode=" + urlEncode + ")");
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : requestParameters.keySet()) {
            stringBuilder.append("&");
            stringBuilder.append(key);
            stringBuilder.append("=");
            if (urlEncode) {
                stringBuilder.append(urlEncodedQueryValue(requestParameters.get(key)));
            } else {
                stringBuilder.append(requestParameters.get(key));
            }

        }
        String queryString = stringBuilder.toString().substring(1);
        return queryString;
    }

    protected final String urlEncodedQueryValue(String value) throws UnsupportedEncodingException {
        if (value == null) {
            return null;
        }
        return URLEncoder.encode(value, "UTF-8");
    }

    protected final String makeUrlAttachableQueryString(String queryString) {
        MFConfiguration.getStaticMFLogger().d(TAG, "makeUrlAttachableQueryString()");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?");
        stringBuilder.append(queryString);
        return stringBuilder.toString();
    }

    protected final String makeBaseUrl(MFRequester mfRequester) {
        MFConfiguration.getStaticMFLogger().d(TAG, "makeBaseUrl()");
        String scheme = mfRequester.getProtocol().getScheme();
        String host = mfRequester.getHost().getSubDomainAndHostName();
        String uri = mfRequester.getUri();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(scheme);
        stringBuilder.append(host);
        stringBuilder.append(uri);
        return stringBuilder.toString();
    }
}
