package com.mediafire.sdk.client_helpers;

import com.mediafire.sdk.api.responses.user.GetActionTokenResponse;
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

    private String mTokenType;
    private ActionTokenManagerInterface mActionTokenManagerInterface;

    public ClientHelperNewActionToken(String tokenType, ActionTokenManagerInterface actionTokenManagerInterface, SessionTokenManagerInterface sessionTokenManagerInterface) {
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
        super.addSignatureToRequestParameters(request);
    }

    @Override
    public void returnToken(Response response, Request request) {
        super.returnToken(response, request);
        // in addition to calling super.returnToken(), the action token needs to be passed to
        // the ActionTokenManagerInterface

        GetActionTokenResponse getActionTokenResponse = getResponseObject(response, GetActionTokenResponse.class);

        if (getActionTokenResponse == null) {
            mActionTokenManagerInterface.tokensFailed();
            return;
        }

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

        if ("image".equals(mTokenType)) {
            ImageActionToken mfImageActionToken = (ImageActionToken) createActionToken(ImageActionToken.class, getActionTokenResponse, request);
            mActionTokenManagerInterface.receiveImageActionToken(mfImageActionToken);
        }

        if ("upload".equals(mTokenType)) {
            UploadActionToken uploadActionToken = (UploadActionToken) createActionToken(UploadActionToken.class, getActionTokenResponse, request);
            mActionTokenManagerInterface.receiveUploadActionToken(uploadActionToken);
        }
    }

    private ActionToken createActionToken(Class<? extends ActionToken> clazz, GetActionTokenResponse getActionTokenResponse, Request request) {
        if (getActionTokenResponse == null) {
            return null;
        }

        if (getActionTokenResponse.hasError()) {
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

        if (clazz == ImageActionToken.class) {
            return new ImageActionToken(tokenString, tokenExpiry);
        }

        if (clazz == UploadActionToken.class) {
            return new UploadActionToken(tokenString, tokenExpiry);
        }

        return null;
    }
}
