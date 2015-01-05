package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.NewActionToken;
import com.mediafire.sdk.api.helpers.NewSessionToken;
import com.mediafire.sdk.config.IDeveloperCredentials;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.config.IUserCredentials;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris on 1/5/2015.
 */
public class TokenClient {

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient mApiClient;
    private final Instructions mSessionTokenInstructions;
    private final Instructions mImageTokenInstructions;
    private final Instructions mUploadTokenInstructions;

    public TokenClient(IHttp httpInterface,
                       IUserCredentials userCredentials,
                       IDeveloperCredentials developerCredentials,
                       ITokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();
        mApiClient = new ApiClient(httpInterface);
        mSessionTokenInstructions = new NewSessionToken(userCredentials, developerCredentials, tokenManager);
        mImageTokenInstructions = new NewActionToken("image", tokenManager);
        mUploadTokenInstructions = new NewActionToken("upload", tokenManager);
    }

    public Result getSessionTokenV2() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_session_token.php");
        request.addQueryParameter("token_version", "2");
        return mApiClient.doRequest(mSessionTokenInstructions, request);
    }

    public Result getUploadActionToken(int lifespan) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_action_token.php");
        request.addQueryParameter("lifespan", lifespan);
        return mApiClient.doRequest(mUploadTokenInstructions, request);
    }

    public Result getImageActionToken(int lifespan) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_action_token.php");
        request.addQueryParameter("lifespan", lifespan);
        return mApiClient.doRequest(mImageTokenInstructions, request);
    }
}
