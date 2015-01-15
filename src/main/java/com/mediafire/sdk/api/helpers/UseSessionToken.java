package com.mediafire.sdk.api.helpers;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris on 11/9/2014.
 * ClientHelper used for the majority of calls to the MediaFire API.
 * This BaseClientHelper implementation should not be used for calls to get new session tokens or calls that use
 * action tokens
 */
public class UseSessionToken extends Instructions {

    private TokenManager mSessionTokenManagerInterface;

    public UseSessionToken(TokenManager sessionTokenManagerInterface) {
        super();
        mSessionTokenManagerInterface = sessionTokenManagerInterface;
    }

    @Override
    public void borrowToken(Request request) {
        if (debugging()) {
            System.out.println(getClass() + " - borrowToken, borrowing token from ITokenManager");
        }

        SessionToken sessionToken = mSessionTokenManagerInterface.take(SessionToken.class);
        request.addToken(sessionToken);
    }

    @Override
    public void addSignatureToRequestParameters(Request request) {
        if (debugging()) {
            System.out.println(getClass() + " - addSignature, making signature for ApiRequest");
        }

        String signature = makeSignatureForApiRequest(request);
        request.addSignature(signature);
    }

    @Override
    public void returnToken(Response response, Request request) {
        ApiResponse apiResponse = getResponseObject(response, ApiResponse.class);
        if (apiResponse == null) {
            if (debugging()) {
                System.out.println(getClass() + " - returnToken, ApiResponse null, not returning token");
            }

            return;
        }

        if (!apiResponse.hasError() || (apiResponse.getError() != 105 && apiResponse.getError() != 127)) {
            if (apiResponse.needNewKey()) {
                if (debugging()) {
                    System.out.println(getClass() + " - returnToken, updating secret key");
                }

                ((SessionToken) request.getToken()).updateSessionToken();
            }


            if (debugging()) {
                System.out.println(getClass() + " - returnToken, returning token to ITokenManager");
            }


            mSessionTokenManagerInterface.give(request.getToken());
        } else {
            if (debugging()) {
                System.out.println(getClass() + " - returnToken, error #" + apiResponse.getError() +
                        ", message: " + apiResponse.getMessage());
            }

        }
    }
}
