package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.CredentialsInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris Najar on 10/20/2014.
 */
public class ApiClientSessionTokenManager extends AbstractApiClient {

    private CredentialsInterface mUserCredentials;
    private CredentialsInterface mDeveloperCredentials;
    private SessionTokenManagerInterface mSessionTokenManager;

    public ApiClientSessionTokenManager(HttpWorkerInterface httpWorker, SessionTokenManagerInterface sessionTokenManager, CredentialsInterface userCredentials, CredentialsInterface developerCredentials) {
        super(httpWorker);
        this.mUserCredentials = userCredentials;
        this.mDeveloperCredentials = developerCredentials;
        mSessionTokenManager = sessionTokenManager;
    }

    @Override
    public Result doRequest(Request request) {
        ApiClientHelperSessionTokenManager apiClientHelper = new ApiClientHelperSessionTokenManager(mUserCredentials, mDeveloperCredentials, mSessionTokenManager);

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
