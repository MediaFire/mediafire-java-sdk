package com.mediafire.sdk.client;

import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.*;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.token.Token;

import java.util.Map;

public class ApiClient {
    private final Configuration mConfiguration;

    public ApiClient(Configuration configuration) {
        mConfiguration = configuration;
    }

    public Result doRequest(Request request) {
        TokenHelper tokenHelper = new TokenHelper(mConfiguration);

        Token token = tokenHelper.borrowToken(request.getInstructionsObject());

        if (token != null) {
            request.addQueryParameter("session_token", token.getTokenString());
        }

        SignatureHelper signatureHelper = new SignatureHelper(request, mConfiguration);

        String signature = signatureHelper.calculateSignature();

        if (signature != null) {
            request.addQueryParameter("signature", signature);
        }

        String httpMethod = request.getHostObject().getHttpMethod();

        Response response = doRequest(request, httpMethod);

        ResponseHelper responseHelper = new ResponseHelper(response);

        boolean tokenNeedsUpdating = tokenHelper.determineIfTokenNeedsUpdating(responseHelper.getApiResponse());

        if (tokenNeedsUpdating) {
            tokenHelper.updateToken((SessionToken) token);
        }

        tokenHelper.returnToken(request.getInstructionsObject(), token);

        return new Result(response, request);
    }

    private Response doRequest(Request request, String method) {
        if (method.equalsIgnoreCase("get")) {
            return doGet(request);
        } else if (method.equalsIgnoreCase("post")) {
            return doPost(request);
        } else {
            throw new IllegalArgumentException("request method '" + method + "' not supported");
        }
    }

    private Response doGet(Request request) {
        String url = new UrlHelper(request).makeUrlForGetRequest();
        // add headers to request
        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addGetHeaders();

        return mConfiguration.getHttpWorkerInterface().doGet(url, null);
    }

    private Response doPost(Request request) {
        UrlHelper urlHelper = new UrlHelper(request);
        String url = urlHelper.makeUrlForPostRequest();
        byte[] payload = urlHelper.getPayload();

        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addPostHeaders(payload);
        Map<String, String> headers = request.getHeaders();

        return mConfiguration.getHttpWorkerInterface().doPost(url, headers, payload);
    }
}
