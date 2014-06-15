package com.arkhive.components.test_session_manager_fixes.layer_http;

import com.arkhive.components.test_session_manager_fixes.module_api_descriptor.ApiGetRequestObject;
import com.arkhive.components.test_session_manager_fixes.module_api_descriptor.ApiPostRequestObject;
import com.arkhive.components.test_session_manager_fixes.module_api_descriptor.ApiRequestObject;
import com.arkhive.components.test_session_manager_fixes.module_session_token.Token;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Chris Najar on 6/15/2014.
 */
public final class HttpPreProcessor {
    private final ApiRequestObject apiRequestObject;

    public HttpPreProcessor(ApiRequestObject apiRequestObject) {
        super();
        this.apiRequestObject = apiRequestObject;
    }

    public final void processApiRequestObject() {
        URL constructedUrl;
        if (ApiPostRequestObject.class.isInstance(apiRequestObject)) {
            System.out.println("API POST REQUEST API POST REQUEST");
            constructedUrl = createUrl((ApiPostRequestObject) apiRequestObject);
        } else {
            System.out.println("API GET REQUEST API GET REQUEST");
            constructedUrl = createUrl((ApiGetRequestObject) apiRequestObject);
        }

        apiRequestObject.setConstructedUrl(constructedUrl);
    }

    private URL createUrl(ApiGetRequestObject apiGetRequestObject) {
        String domain = apiGetRequestObject.getDomain();
        String uri = apiGetRequestObject.getUri();
        HashMap<String, String> requiredParameters = apiGetRequestObject.getRequiredParameters();
        HashMap<String, String> optionalParameters = apiGetRequestObject.getOptionalParameters();
        Token token = apiGetRequestObject.getToken();

        StringBuilder stringBuilder = new StringBuilder();
        if (domain != null) {
            stringBuilder.append(domain);
        }

        if (uri != null) {
            stringBuilder.append(uri);
        }

        if (token != null) {
            stringBuilder.append(constructParametersForUrl(token));
        }

        if (requiredParameters != null) {
            stringBuilder.append(constructParametersForUrl(requiredParameters));
        }

        if (optionalParameters != null) {
            stringBuilder.append(constructParametersForUrl(optionalParameters));
        }

        String urlString = stringBuilder.toString();
        urlString = cleanupUrlString(urlString);

        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            apiGetRequestObject.addExceptionDuringRequest(e);
            return null;
        }
    }

    private URL createUrl(ApiPostRequestObject apiPostRequestObject) {
        String domain = apiPostRequestObject.getDomain();
        String uri = apiPostRequestObject.getUri();
        HashMap<String, String> requiredParameters = apiPostRequestObject.getRequiredParameters();
        HashMap<String, String> optionalParameters = apiPostRequestObject.getOptionalParameters();
        Token token = apiPostRequestObject.getToken();

        StringBuilder stringBuilder = new StringBuilder();
        if (domain != null) {
            stringBuilder.append(domain);
        }

        if (uri != null) {
            stringBuilder.append(uri);
        }

        if (token != null) {
            stringBuilder.append(constructParametersForUrl(token));
        }

        if (requiredParameters != null) {
            stringBuilder.append(constructParametersForUrl(requiredParameters));
        }

        if (optionalParameters != null) {
            stringBuilder.append(constructParametersForUrl(optionalParameters));
        }

        String urlString = stringBuilder.toString();
        urlString = cleanupUrlString(urlString);

        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            apiPostRequestObject.addExceptionDuringRequest(e);
            return null;
        }
    }

    protected final String constructParametersForUrl(HashMap<String, String> parameters) {
        StringBuilder stringBuilder = new StringBuilder();
        if (parameters != null && parameters.size() > 0) {
            for (String key : parameters.keySet()) {
                stringBuilder.append("&").append(key).append("=").append(parameters.get(key));
            }
        } else {
            stringBuilder.append("");
        }
        return stringBuilder.toString();
    }

    protected final String constructParametersForUrl(Token token) {
        StringBuilder stringBuilder = new StringBuilder();

        if (token.getTokenString() != null) {
            stringBuilder.append("&session_token=");
            stringBuilder.append(token.getTokenString());
        }

        if (token.getTokenSignature() != null) {
            stringBuilder.append("&signature=");
            stringBuilder.append(token.getTokenSignature());
        }

        return stringBuilder.toString();
    }

    protected final String cleanupUrlString(String urlString) {
        String cleanedUrlString;
        if (urlString.contains("&")) {
            cleanedUrlString = urlString.replaceFirst("&", "?");
        } else {
            cleanedUrlString = urlString;
        }

        return cleanedUrlString;
    }
}
