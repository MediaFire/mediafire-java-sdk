package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.*;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

public class ApiClient extends AbstractApiClient {
    private SessionTokenManagerInterface mSessionTokenManager;
    private ActionTokenManagerInterface mActionTokenManager;
    private CredentialsInterface mUserCredentials;
    private CredentialsInterface mDeveloperCredentials;

    public ApiClient(HttpWorkerInterface httpWorker, SessionTokenManagerInterface sessionTokenManager, ActionTokenManagerInterface actionTokenManager, CredentialsInterface credentialsInterface, CredentialsInterface developerCredentials) {
        super(httpWorker);
        mSessionTokenManager = sessionTokenManager;
        mActionTokenManager = actionTokenManager;
        mUserCredentials = credentialsInterface;
        mDeveloperCredentials = developerCredentials;
    }

    @Override
    public Result doRequest(Request request) {
        ApiClientHelper apiClientHelper = new ApiClientHelper(mSessionTokenManager, mActionTokenManager, mUserCredentials, mDeveloperCredentials);

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
}
