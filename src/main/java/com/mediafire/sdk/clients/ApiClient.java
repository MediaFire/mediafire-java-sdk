package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.*;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

public class ApiClient {
    private HttpWorkerInterface mHttpWorker;
    private SessionTokenManagerInterface mSessionTokenManager;
    private ActionTokenManagerInterface mActionTokenManager;
    private CredentialsInterface mCredentials;
    private String mAppId;
    private String mApiKey;

    public ApiClient(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManager, ActionTokenManagerInterface actionTokenManager, CredentialsInterface credentialsInterface, String appId, String apiKey) {
        this.mHttpWorker = httpWorkerInterface;
        mSessionTokenManager = sessionTokenManager;
        mActionTokenManager = actionTokenManager;
        mCredentials = credentialsInterface;
        mAppId = appId;
        mApiKey = apiKey;
    }

    public ApiClient(Configuration configuration) {
        this(configuration.getHttpWorkerInterface(), configuration.getSessionTokenManagerInterface(), configuration.getActionTokenManagerInterface(), configuration.getUserCredentialsInterface(), configuration.getAppId(), configuration.getApiKey());
    }

    public Result doRequest(Request request) {
        ApiClientHelper apiClientHelper = new ApiClientHelper(mSessionTokenManager, mActionTokenManager, mCredentials, mAppId, mApiKey);

        // setup should handle the following:
        // 1. getting an ActionToken or SessionToken (if required) as per InstructionsObject
        // 2. calling Request.addToken() to add the token to the request.
        // 3. adding the session_token parameter to the query parameters via Request.addQueryParameter()
        // 4. calculate a signature (if required) as per InstructionsObject
        // 5. add signature parameters to the query parameters via Request.addQueryParameter()
        // 6. return Token or notify Token manager interfaces if a Token is invalid.
        apiClientHelper.setup(request);

        String httpMethod = request.getHostObject().getHttpMethod();

        Response response = doRequest(request, httpMethod);

        apiClientHelper.cleanup(response);

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
        return mHttpWorker.doGet(url, request.getHeaders());
    }

    private Response doPost(Request request) {
        UrlHelper urlHelper = new UrlHelper(request);
        String url = urlHelper.makeUrlForPostRequest();
        byte[] payload = urlHelper.getPayload();

        HeadersHelper headersHelper = new HeadersHelper(request);
        headersHelper.addPostHeaders(payload);

        return mHttpWorker.doPost(url, request.getHeaders(), payload, request.getInstructionsObject().postQuery());
    }
}
