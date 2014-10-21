package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris Najar on 10/20/2014.
 */
public class ApiClientActionTokenManager extends AbstractApiClient {
    private static final String TAG = ApiClientActionTokenManager.class.getCanonicalName();
    private SessionTokenManagerInterface mSessionTokenManager;
    private ActionTokenManagerInterface mActionTokenManager;

    public ApiClientActionTokenManager(HttpWorkerInterface httpWorker, SessionTokenManagerInterface sessionTokenManager, ActionTokenManagerInterface actionTokenManager) {
        super(httpWorker);
        mSessionTokenManager = sessionTokenManager;
        mActionTokenManager = actionTokenManager;
    }

    @Override
    public Result doRequest(Request request) {
        DefaultLogger.log().v(TAG, "doRequest");
        ApiClientHelperActionTokenManager apiClientHelper = new ApiClientHelperActionTokenManager(mSessionTokenManager, mActionTokenManager);

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
