package com.mediafire.sdk.clients;

import com.mediafire.sdk.api_responses.user.GetActionTokenResponse;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.UploadActionToken;

/**
 * Created by Chris on 11/9/2014.
 * BaseClientHelper used for fetching new action tokens, the only difference between NewActionTokenClientHelper
 * and ApiClientHelper is that returnToken() also needs to return an action token to the ActionTokenManagerInterface
 */
public class ClientHelperNewActionToken extends ClientHelperApi {

    private TokenType mTokenType;
    private ActionTokenManagerInterface mActionTokenManagerInterface;

    public ClientHelperNewActionToken(TokenType tokenType, ActionTokenManagerInterface actionTokenManagerInterface, SessionTokenManagerInterface sessionTokenManagerInterface) {
        super(sessionTokenManagerInterface);

        mTokenType = tokenType;
        mActionTokenManagerInterface = actionTokenManagerInterface;
    }

    @Override
    public void borrowToken(Request request) {
        super.borrowToken(request);
    }

    @Override
    public void addSignatureToRequestParameters(Request request) {
        super.borrowToken(request);
    }

    @Override
    public void returnToken(Response response, Request request) {
        super.returnToken(response, request);
        // in addition to calling super.returnToken(), the action token needs to be passed to
        // the ActionTokenManagerInterface

        GetActionTokenResponse getActionTokenResponse = getResponseObject(response, GetActionTokenResponse.class);
        boolean badRequest = false;
        if (getActionTokenResponse.hasError()) {
            badRequest = true;
        }

        if (getActionTokenResponse.getError() == 105) {
            badRequest = true;
        }

        if (getActionTokenResponse.getError() == 127) {
            badRequest = true;
        }

        if (badRequest) {
            mActionTokenManagerInterface.tokensFailed();
            return;
        }

        switch(mTokenType) {
            case UPLOAD:
                UploadActionToken uploadActionToken = (UploadActionToken) createActionToken(UploadActionToken.class, getActionTokenResponse, request);
                mActionTokenManagerInterface.receiveUploadActionToken(uploadActionToken);
                System.out.printf("%s - %s", TAG, "returnToken - returned new upload actiontoken to token manager");
                break;
            case IMAGE:
                ImageActionToken mfImageActionToken = (ImageActionToken) createActionToken(ImageActionToken.class, getActionTokenResponse, request);
                mActionTokenManagerInterface.receiveImageActionToken(mfImageActionToken);
                System.out.printf("%s - %s", TAG, "returnToken - returned new image actiontoken to token manager");
                break;
        }
    }

    private ActionToken createActionToken(Class<? extends ActionToken> clazz, GetActionTokenResponse getActionTokenResponse, Request request) {
        if (getActionTokenResponse == null) {
            System.out.printf("%s - %s", TAG, "createActionToken - no action token response, return null action token");
            return null;
        }

        if (getActionTokenResponse.hasError()) {
            System.out.printf("%s - %s", TAG, "createActionToken - action token response has error, return null action token");
            return null;
        }

        String tokenString = getActionTokenResponse.getActionToken();
        long tokenExpiry;
        if (request.getQueryParameters().containsKey("lifespan")) {
            Object lifeSpanParam = request.getQueryParameters().get("lifespan");
            String lifeSpanParamAsString = String.valueOf(lifeSpanParam);
            tokenExpiry = Long.valueOf(lifeSpanParamAsString);
        } else {
            tokenExpiry = 0;
        }

        System.out.printf("%s - %s", TAG, "createActionToken - creating token with expiry of " + tokenExpiry);

        if (clazz == ImageActionToken.class) {
            System.out.printf("%s - %s", TAG, "createActionToken - returning new image action token");
            return new ImageActionToken(tokenString, tokenExpiry);
        }

        if (clazz == UploadActionToken.class) {
            System.out.printf("%s - %s", TAG, "createActionToken - returning new upload action token");
            return new UploadActionToken(tokenString, tokenExpiry);
        }

        System.out.printf("%s - %s", TAG, "createActionToken - unknown token class passed, returning null");
        return null;
    }
}
