package com.mediafire.sdk.api.helpers;

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
public class UseActionToken extends Instructions {
    private String mTokenType;
    private ITokenManager mActionITokenManagerInterface;

    public UseActionToken(String tokenType, ITokenManager actionITokenManagerInterface) {
        super();
        this.mTokenType = tokenType;
        mActionITokenManagerInterface = actionITokenManagerInterface;
    }

    @Override
    public void borrowToken(Request request) {
        if (mTokenType == null) {
            if (debugging()) {
                System.out.println(getClass() + " - borrowToken, token type null, not borrowing token");
            }

            return;
        }

        if ("image".equals(mTokenType)) {
            if (debugging()) {
                System.out.println(getClass() + " - borrowToken, borrowing image token from ITokenManager");
            }

            ImageActionToken imageActionToken = mActionITokenManagerInterface.take(ImageActionToken.class);
            request.addToken(imageActionToken);
        } else if ("upload".equals(mTokenType)) {
            if (debugging()) {
                System.out.println(getClass() + " - borrowToken, borrowing upload token from ITokenManager");
            }

            UploadActionToken uploadActionToken = mActionITokenManagerInterface.take(UploadActionToken.class);
            request.addToken(uploadActionToken);
        } else {
            if (debugging()) {
                System.out.println(getClass() + " - borrowToken, not borrowing token, token type unknown: " + mTokenType);
            }
        }
    }

    @Override
    public void addSignatureToRequestParameters(Request request) {
        // no signature is required when using action tokens
        if (debugging()) {
            System.out.println(getClass() + " - addSignatureToRequestParameters, no signature required");
        }
    }

    @Override
    public void returnToken(Response response, Request request) {
        // there is no need to return action tokens, but if the response code is 105 (invalid token) then notify
        // ActionTokenManagerInterface
        if (response == null) {
            if (debugging()) {
                System.out.println(getClass() + " - returnToken, Response null, not returning token");
            }
            return;
        }

        ApiResponse apiResponse = getResponseObject(response, ApiResponse.class);

        if (apiResponse == null) {
            if (debugging()) {
                System.out.println(getClass() + " - returnToken, ApiResponse null, not returning token, notifying token " +
                        "manager that tokens are bad");
            }
            mActionITokenManagerInterface.tokensBad();
            return;
        }

        if (!apiResponse.hasError()) {
            if (debugging()) {
                System.out.println(getClass() + " - returnToken, ApiResponse has no error, no need to return action token");
            }
            return;
        } else {
            if (debugging()) {
                System.out.println(getClass() + " - returnToken, ApiResponse error, not returning token, notifying token " +
                        "manager that tokens are bad, error #" + apiResponse.getError());
            }
            mActionITokenManagerInterface.tokensBad();
        }
    }
}
