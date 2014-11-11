package com.mediafire.sdk.clients;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.UploadActionToken;

/**
 * Created by Chris on 11/9/2014.
 * BaseClientHelper for API calls that use action tokens (no signature)
 */
public class ClientHelperActionToken extends BaseClientHelper {
    private String mTokenType;
    private ActionTokenManagerInterface mActionTokenManagerInterface;

    public ClientHelperActionToken(String tokenType, ActionTokenManagerInterface actionTokenManagerInterface) {
        this.mTokenType = tokenType;
        mActionTokenManagerInterface = actionTokenManagerInterface;
    }

    @Override
    public void borrowToken(Request request) {
        if (mTokenType == null) {
            return;
        }

        if ("image".equals(mTokenType)) {

            ImageActionToken imageActionToken = mActionTokenManagerInterface.borrowImageActionToken();
            request.addToken(imageActionToken);
        }

        if ("upload".equals(mTokenType)) {
            UploadActionToken uploadActionToken = mActionTokenManagerInterface.borrowUploadActionToken();
            request.addToken(uploadActionToken);
        }
    }

    @Override
    public void addSignatureToRequestParameters(Request request) {
        // no signature is required when using action tokens
    }

    @Override
    public void returnToken(Response response, Request request) {
        // there is no need to return action tokens, but if the response code is 105 (invalid token) then notify
        // ActionTokenManagerInterface
        if (response == null) {
            return;
        }

        ApiResponse apiResponse = getResponseObject(response, ApiResponse.class);

        if (apiResponse == null) {
            mActionTokenManagerInterface.tokensFailed();
            return;
        }

        if (!apiResponse.hasError()) {
            return;
        }

        if (apiResponse.getError() == 105) {
            mActionTokenManagerInterface.tokensFailed();
        }
    }
}
