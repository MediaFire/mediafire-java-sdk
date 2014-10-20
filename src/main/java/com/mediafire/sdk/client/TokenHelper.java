package com.mediafire.sdk.client;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.api_responses.user.GetActionTokenResponse;
import com.mediafire.sdk.api_responses.user.GetSessionTokenResponse;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.InstructionsObject;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.token.*;

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

    public void returnToken(InstructionsObject instructionsObject, Token token) {
        switch (instructionsObject.getReturnTokenType()) {
            case VERSION_2:
                mConfiguration.getSessionTokenManagerInterface().receiveSessionToken((SessionToken) token);
                break;
            case NEW_UPLOAD:
                mConfiguration.getActionTokenManagerInterface().receiveUploadActionToken((ActionToken) token);
                break;
            case NEW_IMAGE:
                mConfiguration.getActionTokenManagerInterface().receiveImageActionToken((ActionToken) token);
                break;
            case NONE:
                break;
        }
    }

    public Token borrowToken(InstructionsObject instructionsObject) {
        Token token = null;
        switch (instructionsObject.getBorrowTokenType()) {
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

    private ActionToken createActionToken(GetActionTokenResponse getActionTokenResponse, Request request) {
        if (request == null) {
            return null;
        }

        if (getActionTokenResponse == null) {
            return null;
        }

        if (getActionTokenResponse.hasError()) {
            return null;
        }

        String type = String.valueOf(request.getQueryParameters().get("type"));

        if (type == null) {
            return null;
        }

        String tokenString = getActionTokenResponse.getActionToken();
        Long tokenExpiry;
        if (request.getQueryParameters().containsKey("lifespan")) {
            tokenExpiry = (Long) request.getQueryParameters().get("lifespan");
        } else {
            tokenExpiry = 0L;
        }

        ActionToken actionToken;
        if (type.equals("image")) {
            actionToken = new ImageActionToken(tokenString, tokenExpiry);
        } else if (type.equals("upload")) {
            actionToken = new UploadActionToken(tokenString, tokenExpiry);
        } else {
            actionToken = null;
        }

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
