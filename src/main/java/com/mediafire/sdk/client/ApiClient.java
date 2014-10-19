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
        // create TokenHelper
        TokenHelper tokenHelper = new TokenHelper(mConfiguration);
        // borrow token, if it's required
        Token token = tokenHelper.borrowToken(request.getInstructionsObject());
        // add token to request parameters, if borrowed token
        if (token != null) {
            request.addQueryParameter("session_token", token.getTokenString());
        }
        // Create SignatureHelper
        SignatureHelper signatureHelper = new SignatureHelper(request, mConfiguration);
        // calculate signature, if needed
        String signature = signatureHelper.calculateSignature();
        // add signature to request parameters, if needed
        if (signature != null) {
            request.addQueryParameter("signature", signature);
        }

        // get http method
        String httpMethod = request.getHostObject().getHttpMethod();

        // use HttpWorkerInterface to do a GET or POST and get a response
        Response response = doRequest(request, httpMethod);

        // create ResponseHelper
        ResponseHelper responseHelper = new ResponseHelper();

        // determine if token needs updating
        boolean tokenNeedsUpdating = tokenHelper.determineIfTokenNeedsUpdating(responseHelper.getApiResponse());

        // update token, if required
        if (tokenNeedsUpdating) {
            tokenHelper.updateToken((SessionToken) token);
        }
        // return token, if required and there is no error
        tokenHelper.returnToken(request.getInstructionsObject(), token);
        return new Result(response, request);
    }

    public Response doRequest(Request request, String method) {
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
        return mConfiguration.getHttpWorkerInterface().doGet(url);
    }

    private Response doPost(Request request) {
        UrlHelper urlHelper = new UrlHelper(request);
        String url = urlHelper.makeUrlForPostRequest();
        Map<String, String> headers = request.getHeaders();
        byte[] payload = urlHelper.getPayload();
        return mConfiguration.getHttpWorkerInterface().doPost(url, headers, payload);
    }
}
