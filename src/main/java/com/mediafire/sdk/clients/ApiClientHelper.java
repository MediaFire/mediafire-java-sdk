package com.mediafire.sdk.clients;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.api_responses.user.GetActionTokenResponse;
import com.mediafire.sdk.api_responses.user.GetSessionTokenResponse;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.CredentialsInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.token.UploadActionToken;

import java.util.Map;

/**
 * Created by Chris Najar on 10/20/2014.
 */
public class ApiClientHelper extends AbstractApiClientHelper {
    private static final String TAG = ApiClientHelper.class.getCanonicalName();

    private SessionTokenManagerInterface mSessionTokenManager;
    private ActionTokenManagerInterface mActionTokenManager;
    private CredentialsInterface mUserCredentials;
    private final CredentialsInterface mDeveloperCredentials;

    public ApiClientHelper(SessionTokenManagerInterface sessionTokenManager, ActionTokenManagerInterface actionTokenManager, CredentialsInterface userCredentials, CredentialsInterface developerCredentials) {
        mSessionTokenManager = sessionTokenManager;
        mActionTokenManager = actionTokenManager;
        mUserCredentials = userCredentials;
        mDeveloperCredentials = developerCredentials;
    }

    @Override
    public void borrowToken() {
        DefaultLogger.log().v(TAG, "borrowToken - " + mRequest.getInstructionsObject().getBorrowTokenType());
        switch (mRequest.getInstructionsObject().getBorrowTokenType()) {
            case V2:
                SessionToken sessionToken = mSessionTokenManager.borrowSessionToken();
                mRequest.addToken(sessionToken);
                break;
            case UPLOAD:
                UploadActionToken uploadActionToken = mActionTokenManager.borrowUploadActionToken();
                mRequest.addToken(uploadActionToken);
                break;
            case IMAGE:
                ImageActionToken imageActionToken = mActionTokenManager.borrowImageActionToken();
                mRequest.addToken(imageActionToken);
                break;
            case NONE:
                break;
            default:
                break;
        }
    }

    @Override
    public void addTokenToRequestParameters() {
        if (mRequest.getToken() != null) {
            String tokenString = mRequest.getToken().getTokenString();

            DefaultLogger.log().v(TAG, "addTokenToRequestParameters - " + tokenString);
            mRequest.addQueryParameter("session_token", tokenString);
        }
    }

    @Override
    public void addSignatureToRequestParameters() {
        String signature = null;
        switch (mRequest.getInstructionsObject().getSignatureType()) {
            case NEW_SESSION_TOKEN_SIGNATURE:
                addRequiredParametersForNewSessionToken();
                signature = makeSignatureForNewSessionToken();
                break;
            case API_REQUEST:
                signature = makeSignatureForApiRequest();
                break;
            case NO_SIGNATURE_REQUIRED:
                break;
            default:
                break;
        }

        if (signature != null) {
            DefaultLogger.log().v(TAG, "addSignatureToRequestParameters - " + signature);
            mRequest.addQueryParameter("signature", signature);
        }
    }

    private String makeSignatureForNewSessionToken() {
        // email + password + app id + api key
        // fb access token + app id + api key
        // tw oauth token + tw oauth token secret + app id + api key

        String userInfoPortionOfHashTarget = mUserCredentials.getConcatenatedCredentials();

        // apiKey is not required, but may be passed into the MFConfiguration object
        // Note: If the app does not have the "Require Secret Key" option checked,
        // then the API key may be omitted from the signature.
        // However, this should only be done when sufficient domain and/or network restrictions are in place.
        String hashTarget = userInfoPortionOfHashTarget + mDeveloperCredentials.getConcatenatedCredentials();

        String signature = hashString(hashTarget, "SHA-1");
        DefaultLogger.log().v(TAG, "makeSignatureForNewSessionToken - " + signature);
        return signature;
    }

    private void addRequiredParametersForNewSessionToken() {
        Map<String, String> credentialsMap = mUserCredentials.getCredentials();
        for (String key : credentialsMap.keySet()) {
            mRequest.addQueryParameter(key, credentialsMap.get(key));
        }

        DefaultLogger.log().v(TAG, "addRequiredParametersForNewSessionToken");

        mRequest.addQueryParameter("application_id", mDeveloperCredentials.getCredentials().get("application_id"));
    }

    @Override
    public void returnToken() {
        ResponseHelper responseHelper = new ResponseHelper(mResponse);

        if (mResponse == null || responseHelper.getResponseObject(ApiResponse.class) == null) {
            DefaultLogger.log().v(TAG, "returnToken - not returning a token. response null or couldn't find api response");
            return;
        }

        DefaultLogger.log().v(TAG, "returnToken - " + mRequest.getInstructionsObject().getReturnTokenType());
        switch (mRequest.getInstructionsObject().getReturnTokenType()) {
            case NEW_V2:
                GetSessionTokenResponse newSessionTokenResponse = responseHelper.getResponseObject(GetSessionTokenResponse.class);
                SessionToken newSessionToken = createNewSessionToken(newSessionTokenResponse);
                if (newSessionToken != null) {
                    mSessionTokenManager.receiveSessionToken(newSessionToken);
                }
                break;
            case V2:
                ApiResponse apiResponse = responseHelper.getResponseObject(ApiResponse.class);
                if (apiResponse.hasError() && apiResponse.getError() == 105 || apiResponse.getError() == 127) {
                    // don't return the token
                } else {
                    if (apiResponse.needNewKey()) {
                        ((SessionToken) mRequest.getToken()).updateSessionToken();
                    }
                    mSessionTokenManager.receiveSessionToken(((SessionToken) mRequest.getToken()));
                }
                break;
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
            case NONE:
                // if a token is invalid then there needs to be a call made to TokenFarm to notify
                if (responseHelper.getResponseObject(ApiResponse.class).hasError()) {
                    mActionTokenManager.tokensFailed();
                }
                break;
        }
    }

    public String makeSignatureForApiRequest() {
        DefaultLogger.log().v(TAG, "makeSignatureForApiRequest");
        // session token secret key + time + uri (concatenated)
        SessionToken sessionToken = (SessionToken) mRequest.getToken();
        int secretKeyMod256 = Integer.valueOf(sessionToken.getSecretKey()) % 256;
        String time = sessionToken.getTime();

        UrlHelper urlHelper = new UrlHelper(mRequest);

        String nonUrlEncodedQueryString = urlHelper.getQueryString(false);

        String baseUri = urlHelper.getBaseUriString();
        String fullUri = baseUri + nonUrlEncodedQueryString;

        String nonUrlEncodedString = secretKeyMod256 + time + fullUri;

        return hashString(nonUrlEncodedString, "MD5");
    }

    private ActionToken createActionToken(Class<? extends ActionToken> clazz, GetActionTokenResponse getActionTokenResponse) {
        DefaultLogger.log().v(TAG, "createActionToken");
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

    public SessionToken createNewSessionToken(GetSessionTokenResponse getSessionTokenResponse) {
        DefaultLogger.log().v(TAG, "createNewSessionToken");
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
        SessionToken mfSessionToken = new SessionToken(tokenString, secretKey, time, pkey, ekey);
        return mfSessionToken;
    }
}
