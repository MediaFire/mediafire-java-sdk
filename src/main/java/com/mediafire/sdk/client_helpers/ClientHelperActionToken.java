package com.mediafire.sdk.client_helpers;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.config.ITokenManager;
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
    private ITokenManager mActionITokenManagerInterface;

    public ClientHelperActionToken(String tokenType, ITokenManager actionITokenManagerInterface) {
        super();
        this.mTokenType = tokenType;
        mActionITokenManagerInterface = actionITokenManagerInterface;
    }

    @Override
    public void borrowToken(Request request) {
        if (mTokenType == null) {
            return;
        }

        if ("image".equals(mTokenType)) {

            ImageActionToken imageActionToken = mActionITokenManagerInterface.take(ImageActionToken.class);
            request.addToken(imageActionToken);
        }

        if ("upload".equals(mTokenType)) {
            UploadActionToken uploadActionToken = mActionITokenManagerInterface.take(UploadActionToken.class);
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
            mActionITokenManagerInterface.tokensBad();
            return;
        }

        if (!apiResponse.hasError()) {
            return;
        }

        if (apiResponse.getError() == 105) {
            mActionITokenManagerInterface.tokensBad();
        }
    }
}
