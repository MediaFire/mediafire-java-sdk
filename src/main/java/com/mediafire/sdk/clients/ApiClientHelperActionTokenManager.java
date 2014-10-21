package com.mediafire.sdk.clients;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.api_responses.user.GetActionTokenResponse;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.token.UploadActionToken;

/**
 * Created by Chris Najar on 10/20/2014.
 */
public class ApiClientHelperActionTokenManager extends AbstractApiClientHelper {

    private SessionTokenManagerInterface mSessionTokenManager;
    private ActionTokenManagerInterface mActionTokenManager;

    public ApiClientHelperActionTokenManager(SessionTokenManagerInterface sessionTokenManager, ActionTokenManagerInterface actionTokenManager) {
        mSessionTokenManager = sessionTokenManager;
        mActionTokenManager = actionTokenManager;
    }

    @Override
    public void borrowToken() {
        // borrow session token from session manager
        SessionToken sessionToken = mSessionTokenManager.borrowSessionToken();
        mRequest.addToken(sessionToken);
    }

    @Override
    public void addTokenToRequestParameters() {
        // add token to request parameter
        if (mRequest.getToken() != null) {
            String tokenString = mRequest.getToken().getTokenString();
            mRequest.addQueryParameter("session_token", tokenString);
        }
    }

    @Override
    public void addSignatureToRequestParameters() {
        // add signature to request parameter
        String signature = makeSignatureForApiRequest();

        if (signature != null) {
            mRequest.addQueryParameter("signature", signature);
        }
    }

    @Override
    public void returnToken() {
        ResponseHelper responseHelper = new ResponseHelper(mResponse);

        if (mResponse == null || responseHelper.getResponseObject(ApiResponse.class) == null) {
            return;
        }

        if (responseHelper.getApiResponse().hasError()) {
            mActionTokenManager.tokensFailed();
            return;
        }

        // return token to session manager
        ApiResponse apiResponse = responseHelper.getResponseObject(ApiResponse.class);
        if (apiResponse.hasError() && apiResponse.getError() == 105 || apiResponse.getError() == 127) {
            // don't return the token
        } else {
            if (apiResponse.needNewKey()) {
                ((SessionToken) mRequest.getToken()).updateSessionToken();
            }
            mSessionTokenManager.receiveSessionToken(((SessionToken) mRequest.getToken()));
        }

        // return new token to action token manager

        switch (mRequest.getInstructionsObject().getReturnTokenType()) {
            case NEW_UPLOAD:
                GetActionTokenResponse uploadActionTokenResponse = responseHelper.getResponseObject(GetActionTokenResponse.class);
                if (uploadActionTokenResponse.hasError() && uploadActionTokenResponse.getError() == 105 || uploadActionTokenResponse.getError() == 127) {
                    mActionTokenManager.tokensFailed();
                } else {
                    UploadActionToken uploadActionToken = (UploadActionToken) createActionToken(UploadActionToken.class, uploadActionTokenResponse);
                    mActionTokenManager.receiveUploadActionToken(uploadActionToken);
                }
                break;
            case NEW_IMAGE:
                GetActionTokenResponse imageActionTokenResponse = responseHelper.getResponseObject(GetActionTokenResponse.class);
                if (imageActionTokenResponse.hasError() && imageActionTokenResponse.getError() == 105 || imageActionTokenResponse.getError() == 127) {
                    mActionTokenManager.tokensFailed();
                } else {
                    ImageActionToken mfImageActionToken = (ImageActionToken) createActionToken(ImageActionToken.class, imageActionTokenResponse);
                    mActionTokenManager.receiveImageActionToken(mfImageActionToken);
                }
                break;
        }
    }

    private ActionToken createActionToken(Class<? extends ActionToken> clazz, GetActionTokenResponse getActionTokenResponse) {
        if (getActionTokenResponse == null) {
            return null;
        }

        if (getActionTokenResponse.hasError()) {
            return null;
        }

        String tokenString = getActionTokenResponse.getActionToken();
        long tokenExpiry;
        if (mRequest.getQueryParameters().containsKey("lifespan")) {
            tokenExpiry = (Long) mRequest.getQueryParameters().get("lifespan");
        } else {
            tokenExpiry = 0;
        }

        if (clazz == ImageActionToken.class) {
            return new ImageActionToken(tokenString, tokenExpiry);
        } else if (clazz == UploadActionToken.class) {
            return new UploadActionToken(tokenString, tokenExpiry);
        } else {
            return null;
        }
    }
}
