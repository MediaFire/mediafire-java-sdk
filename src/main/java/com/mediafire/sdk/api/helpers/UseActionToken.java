package com.mediafire.sdk.api.helpers;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.UploadActionToken;

/**
 * Created by Chris on 11/9/2014.
 * BaseClientHelper for API calls that use action tokens (no signature)
 */
public class UseActionToken extends Instructions {
    private String mTokenType;
    private TokenManager mActionTokenManagerInterface;

    public UseActionToken(String tokenType, TokenManager actionTokenManagerInterface) {
        super();
        this.mTokenType = tokenType;
        mActionTokenManagerInterface = actionTokenManagerInterface;
    }

    @Override
    public void borrowToken(Request request) {
        if (mTokenType == null) {
            return;
        }

        if ("image".equals(mTokenType)) {
            ImageActionToken imageActionToken = mActionTokenManagerInterface.take(ImageActionToken.class);
            request.addToken(imageActionToken);
        } else if ("upload".equals(mTokenType)) {
            UploadActionToken uploadActionToken = mActionTokenManagerInterface.take(UploadActionToken.class);
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
            mActionTokenManagerInterface.tokensBad();
            return;
        }

        if (!apiResponse.hasError()) {
            return;
        } else {
            mActionTokenManagerInterface.tokensBad();
        }
    }
}
