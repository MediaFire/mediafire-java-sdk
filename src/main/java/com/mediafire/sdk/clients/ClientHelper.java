package com.mediafire.sdk.clients;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.api_responses.user.GetActionTokenResponse;
import com.mediafire.sdk.api_responses.user.GetSessionTokenResponse;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.ResponseApiClientError;
import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.token.UploadActionToken;

import java.util.Map;

/**
 * Created by Chris Najar on 10/20/2014.
 * ApiClientHelper is a class used to setup and cleanup a request
 */
public class ClientHelper extends BaseClientHelper {
    private static final String TAG = ClientHelper.class.getCanonicalName();
    private final Configuration mConfiguration;

    /**
     * ApiClientHelper Constructor
     * @param configuration a Configuration object
     */
    public ClientHelper(Configuration configuration) {
        mConfiguration = configuration;
    }

    /**
     * Borrows a token (type according to the requests InstructionsObject)
     * Note: setup must first be called as its request param is used
     */
    @Override
    public void borrowToken(Request request) {
        mConfiguration.getLogger().v(TAG, "borrowToken - added " + request.getBorrowTokenType() + " token");
        switch (request.getBorrowTokenType()) {
            case V2:
                SessionToken sessionToken = mConfiguration.getSessionTokenManager().borrowSessionToken();
                request.addToken(sessionToken);
                break;
            case UPLOAD:
                UploadActionToken uploadActionToken = mConfiguration.getActionTokenManager().borrowUploadActionToken();
                request.addToken(uploadActionToken);
                break;
            case IMAGE:
                ImageActionToken imageActionToken = mConfiguration.getActionTokenManager().borrowImageActionToken();
                request.addToken(imageActionToken);
                break;
            case NONE:
                break;
            default:
                break;
        }
    }

    @Override
    public void addSignatureToRequestParameters(Request request) {
        String signature = null;
        switch (request.getSignatureType()) {
            case NEW_SESSION_TOKEN_SIGNATURE:
                addRequiredParametersForNewSessionToken(request);
                signature = makeSignatureForNewSessionToken();
                break;
            case API_REQUEST:
                signature = makeSignatureForApiRequest(request);
                break;
            case NO_SIGNATURE_REQUIRED:
                break;
            default:
                break;
        }

        if (signature != null) {
            mConfiguration.getLogger().v(TAG, "addSignatureToRequestParameters - " + signature);
            request.addQueryParameter("signature", signature);
        } else {
            mConfiguration.getLogger().v(TAG, "addSignatureToRequestParameters - no signature to add for this request");
        }
    }

    private String makeSignatureForNewSessionToken() {
        // email + password + app id + api key
        // fb access token + app id + api key
        // tw oauth token + tw oauth token secret + app id + api key
        String userInfoPortionOfHashTarget = mConfiguration.getUserCredentials().getConcatenatedCredentials();

        // apiKey is not required, but may be passed into the MFConfiguration object
        // Note: If the app does not have the "Require Secret Key" option checked,
        // then the API key may be omitted from the signature.
        // However, this should only be done when sufficient domain and/or network restrictions are in place.
        String hashTarget = userInfoPortionOfHashTarget + mConfiguration.getDeveloperCredentials().getConcatenatedCredentials();

        mConfiguration.getLogger().v(TAG, "makeSignatureForNewSessionToken - pre-hash: " + hashTarget);
        String signature = hashString(hashTarget, "SHA-1");
        mConfiguration.getLogger().v(TAG, "makeSignatureForNewSessionToken - hashed: " + signature);
        return signature;
    }

    private void addRequiredParametersForNewSessionToken(Request request) {
        mConfiguration.getLogger().v(TAG, "addRequiredParametersForNewSessionToken");
        Map<String, String> credentialsMap = mConfiguration.getUserCredentials().getCredentials();
        for (String key : credentialsMap.keySet()) {
            request.addQueryParameter(key, credentialsMap.get(key));
        }

        request.addQueryParameter("application_id", mConfiguration.getDeveloperCredentials().getCredentials().get("application_id"));

        mConfiguration.getLogger().v(TAG, "Request parameters: " + request.getQueryParameters());
    }

    @Override
    public void returnToken(Response response, Request request) {
        if (response instanceof ResponseApiClientError) {
            mConfiguration.getLogger().v(TAG, "returnToken - not returning a token. Response is ResponseApiClientError");
            return;
        }

        ResponseHelper responseHelper = new ResponseHelper(response);

        if (response == null || responseHelper.getResponseObject(ApiResponse.class) == null) {
            mConfiguration.getLogger().v(TAG, "returnToken - not returning a token. response null or couldn't find api response");
            return;
        }

        mConfiguration.getLogger().v(TAG, "returnToken - " + request.getReturnTokenType());
        switch (request.getReturnTokenType()) {
            case NEW_V2:
                GetSessionTokenResponse newSessionTokenResponse = responseHelper.getResponseObject(GetSessionTokenResponse.class);
                SessionToken newSessionToken = createNewSessionToken(newSessionTokenResponse);
                if (newSessionToken != null) {
                    mConfiguration.getSessionTokenManager().receiveSessionToken(newSessionToken);
                }
                mConfiguration.getLogger().v(TAG, "returnToken - returned new v2 to token manager");
                break;
            case V2:
                ApiResponse apiResponse = responseHelper.getResponseObject(ApiResponse.class);
                if (!apiResponse.hasError() || (apiResponse.getError() != 105 && apiResponse.getError() != 127)) {
                    if (apiResponse.needNewKey()) {
                        ((SessionToken) request.getToken()).updateSessionToken();
                    }
                    mConfiguration.getSessionTokenManager().receiveSessionToken(((SessionToken) request.getToken()));
                }
                mConfiguration.getLogger().v(TAG, "returnToken - returned old v2 to token manager");
                break;
            case NEW_UPLOAD:
                GetActionTokenResponse uploadActionTokenResponse = responseHelper.getResponseObject(GetActionTokenResponse.class);
                if (uploadActionTokenResponse.hasError() && uploadActionTokenResponse.getError() == 105 || uploadActionTokenResponse.getError() == 127) {
                    mConfiguration.getActionTokenManager().tokensFailed();
                } else {
                    UploadActionToken uploadActionToken = (UploadActionToken) createActionToken(UploadActionToken.class, uploadActionTokenResponse, request);
                    mConfiguration.getActionTokenManager().receiveUploadActionToken(uploadActionToken);
                }
                mConfiguration.getLogger().v(TAG, "returnToken - returned new upload action token to token manager");
                break;
            case NEW_IMAGE:
                GetActionTokenResponse imageActionTokenResponse = responseHelper.getResponseObject(GetActionTokenResponse.class);
                if (imageActionTokenResponse.hasError() && imageActionTokenResponse.getError() == 105 || imageActionTokenResponse.getError() == 127) {
                    mConfiguration.getActionTokenManager().tokensFailed();
                } else {
                    ImageActionToken mfImageActionToken = (ImageActionToken) createActionToken(ImageActionToken.class, imageActionTokenResponse, request);
                    mConfiguration.getActionTokenManager().receiveImageActionToken(mfImageActionToken);
                }
                mConfiguration.getLogger().v(TAG, "returnToken - returned new image actiontoken to token manager");
                break;
            case NONE:
                // if a token is invalid then there needs to be a call made to TokenFarm to notify
                if (responseHelper.getResponseObject(ApiResponse.class).hasError()) {
                    mConfiguration.getActionTokenManager().tokensFailed();
                    mConfiguration.getLogger().v(TAG, "returnToken - notified token manager about failed action token");
                } else {
                    mConfiguration.getLogger().v(TAG, "returnToken - no tokens returned");
                }
                break;
        }
    }

    private ActionToken createActionToken(Class<? extends ActionToken> clazz, GetActionTokenResponse getActionTokenResponse, Request request) {
        if (getActionTokenResponse == null) {
            mConfiguration.getLogger().v(TAG, "createActionToken - no action token response, return null action token");
            return null;
        }

        if (getActionTokenResponse.hasError()) {
            mConfiguration.getLogger().v(TAG, "createActionToken - action token response has error, return null action token");
            return null;
        }

        String tokenString = getActionTokenResponse.getActionToken();
        long tokenExpiry;
        if (request.getQueryParameters().containsKey("lifespan")) {
            tokenExpiry = Long.valueOf( String.valueOf(request.getQueryParameters().get("lifespan")) );
        } else {
            tokenExpiry = 0;
        }

        mConfiguration.getLogger().v(TAG, "createActionToken - creating token with expiry of " + tokenExpiry);

        if (clazz == ImageActionToken.class) {
            mConfiguration.getLogger().v(TAG, "createActionToken - returning new image action token");
            return new ImageActionToken(tokenString, tokenExpiry);
        } else if (clazz == UploadActionToken.class) {
            mConfiguration.getLogger().v(TAG, "createActionToken - returning new upload action token");
            return new UploadActionToken(tokenString, tokenExpiry);
        } else {
            mConfiguration.getLogger().v(TAG, "createActionToken - unknown token class passed, returning null");
            return null;
        }
    }

    /**
     * Creates a SessionToken Object from a GetSessionTokenResponse
     * @param getSessionTokenResponse the response to create a SessionToken from
     * @return a new SessionToken Object
     */
    private SessionToken createNewSessionToken(GetSessionTokenResponse getSessionTokenResponse) {

        if (getSessionTokenResponse == null) {
            mConfiguration.getLogger().v(TAG, "createNewSessionToken - response null, returning null");
            return null;
        }

        if (getSessionTokenResponse.hasError()) {
            mConfiguration.getLogger().v(TAG, "createNewSessionToken - response has error, returning null");
            return null;
        }

        String tokenString = getSessionTokenResponse.getSessionToken();
        String secretKey = getSessionTokenResponse.getSecretKey();
        String time = getSessionTokenResponse.getTime();
        String pkey = getSessionTokenResponse.getPkey();
        String ekey = getSessionTokenResponse.getEkey();

        return new SessionToken(tokenString, secretKey, time, pkey, ekey);
    }
}
