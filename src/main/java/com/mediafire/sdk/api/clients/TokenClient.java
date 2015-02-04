package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.NewActionToken;
import com.mediafire.sdk.api.helpers.NewSessionToken;
import com.mediafire.sdk.config.DeveloperCredentials;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.config.UserCredentials;
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

    public TokenClient(HttpHandler httpInterface,
                       UserCredentials userCredentials,
                       DeveloperCredentials developerCredentials,
                       TokenManager tokenManager) {
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
        request.addQueryParameter("type", "upload");

        return mApiClient.doRequest(mUploadTokenInstructions, request);
    }

    public Result getImageActionToken(int lifespan) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_action_token.php");
        request.addQueryParameter("lifespan", lifespan);
        request.addQueryParameter("type", "image");

        return mApiClient.doRequest(mImageTokenInstructions, request);
    }
}
