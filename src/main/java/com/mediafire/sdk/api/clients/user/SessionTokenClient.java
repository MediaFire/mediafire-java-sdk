package com.mediafire.sdk.api.clients.user;

import com.mediafire.sdk.api.clients.ApiClient;
import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.client_helpers.ClientHelperNewSessionToken;
import com.mediafire.sdk.config.CredentialsInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris on 11/11/2014.
 */
public class SessionTokenClient {
    private static final String PARAM_TOKEN_VERSION = "token_version";

    private final ApiRequestGenerator mApiRequestGenerator;
    private final HttpWorkerInterface mHttpWorker;
    private final ApiClient apiClient;

    public SessionTokenClient(HttpWorkerInterface httpWorkerInterface, CredentialsInterface userCredentials, CredentialsInterface developerCredentials, SessionTokenManagerInterface sessionTokenManager) {
        mHttpWorker = httpWorkerInterface;
        mApiRequestGenerator = new ApiRequestGenerator();

        ClientHelperNewSessionToken clientHelper = new ClientHelperNewSessionToken(userCredentials, developerCredentials, sessionTokenManager);
        apiClient = new ApiClient(clientHelper, mHttpWorker);
    }

    public Result getSessionTokenV2() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_session_token.php");
        // add application_id and relative parameters are added by ApiClientHelper
        request.addQueryParameter(PARAM_TOKEN_VERSION, 2);

        return apiClient.doRequest(request);
    }
}
