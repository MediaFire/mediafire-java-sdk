package com.mediafire.sdk.client;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.api_responses.user.GetActionTokenResponse;
import com.mediafire.sdk.api_responses.user.GetSessionTokenResponse;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.ApiObject;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.token.Token;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class TokenHelper {
    private final Configuration mConfiguration;

    public TokenHelper(Configuration configuration) {
        mConfiguration = configuration;
    }

    public void updateToken(SessionToken sessionToken) {
        sessionToken.updateSessionToken();
    }

    public boolean determineIfTokenNeedsUpdating(ApiResponse apiResponse) {
        return apiResponse.needNewKey();
    }

    public void returnToken(ApiObject apiObject, Token token) {
        switch (apiObject.getTypeOfTokenToReturn()) {
            case VERSION_2:
                mConfiguration.getSessionTokenManagerInterface().receiveSessionToken((SessionToken) token);
                break;
            case NEW_UPLOAD:
                mConfiguration.getActionTokenManagerInterface().receiveUploadActionToken((ActionToken) token);
                break;
            case NEW_IMAGE:
                mConfiguration.getActionTokenManagerInterface().receiveImageActionToken((ActionToken) token);
                break;
            case NO_TOKEN_NEEDS_TO_BE_RETURNED:
                break;
        }
    }

    public Token borrowToken(ApiObject apiObject) {
        Token token = null;
        switch (apiObject.getTypeOfTokenToBorrow()) {
            case VERSION_2:
                token = mConfiguration.getSessionTokenManagerInterface().borrowSessionToken();
                break;
            case NEW_UPLOAD:
                token = mConfiguration.getActionTokenManagerInterface().borrowUploadActionToken();
                break;
            case NEW_IMAGE:
                token = mConfiguration.getActionTokenManagerInterface().borrowImageActionToken();
                break;
        }
        return token;
    }

    private ActionToken createActionToken(ActionToken.Type type, GetActionTokenResponse getActionTokenResponse, Request request) {
        if (getActionTokenResponse == null) {
            return null;
        }

        if (getActionTokenResponse.hasError()) {
            return null;
        }

        String tokenString = getActionTokenResponse.getActionToken();
        Long tokenExpiry;
        if (request.getQueryParameters().containsKey("lifespan")) {
            tokenExpiry = (Long) request.getQueryParameters().get("lifespan");
        } else {
            tokenExpiry = 0L;
        }
        ActionToken actionToken = new ActionToken(tokenString, type, tokenExpiry);
        return actionToken;
    }

    private SessionToken createNewSessionToken(GetSessionTokenResponse getSessionTokenResponse) {
        if (getSessionTokenResponse == null) {
            return null;
        }

        if (getSessionTokenResponse.hasError()) {
            return null;
        }

        String tokenString = getSessionTokenResponse.getSessionToken();
        String secretKey = getSessionTokenResponse.getSecretKey();
        String time = getSessionTokenResponse.getTime();
        String pkey = getSessionTokenResponse.getPkey();
        String ekey = getSessionTokenResponse.getEkey();
        String permanentToken = getSessionTokenResponse.getPermanentToken();
        SessionToken mfSessionToken = new SessionToken(tokenString, secretKey, time, pkey, ekey, permanentToken);

        return mfSessionToken;
    }
}
