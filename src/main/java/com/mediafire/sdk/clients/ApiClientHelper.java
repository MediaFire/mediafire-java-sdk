package com.mediafire.sdk.clients;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.api_responses.user.GetActionTokenResponse;
import com.mediafire.sdk.api_responses.user.GetSessionTokenResponse;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.ResponseApiClientError;
import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.token.UploadActionToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Chris Najar on 10/20/2014.
 * ApiClientHelper is a class used to setup and cleanup a request
 */
public class ApiClientHelper {
    private static final String TAG = ApiClientHelper.class.getCanonicalName();
    private final Configuration mConfiguration;
    private Request mRequest;
    private Response mResponse;

    /**
     * ApiClientHelper Constructor
     * @param configuration a Configuration object
     */
    ApiClientHelper(Configuration configuration) {
        mConfiguration = configuration;
    }

    /**
     * Sets up a request for the API based off of the requests InstructionsObject
     * @param request the request to be setup with the InstructionsObject
     */
    final void setup(Request request) {
        mConfiguration.getLogger().v(TAG, "setup");
        mRequest = request;
        borrowToken();
        addTokenToRequestParameters();
        addSignatureToRequestParameters();
    }

    /**
     * Cleans up a response by returning appropriate tokens according to the requests InstructionsObject
     * Note: setup must be called first as the request parameters' InstructionsObject is used
     * @param response the response the get the new token from
     */
    final void cleanup(Response response) {
        mConfiguration.getLogger().v(TAG, "cleanup");
        mResponse = response;
        returnToken();
    }

    /**
     * Borrows a token (type according to the requests InstructionsObject)
     * Note: setup must first be called as its request param is used
     */
    public void borrowToken() {
        mConfiguration.getLogger().v(TAG, "borrowToken - added " + mRequest.getInstructionsObject().getBorrowTokenType() + " token");
        switch (mRequest.getInstructionsObject().getBorrowTokenType()) {
            case V2:
                SessionToken sessionToken = mConfiguration.getSessionTokenManager().borrowSessionToken();
                mRequest.addToken(sessionToken);
                break;
            case UPLOAD:
                UploadActionToken uploadActionToken = mConfiguration.getActionTokenManager().borrowUploadActionToken();
                mRequest.addToken(uploadActionToken);
                break;
            case IMAGE:
                ImageActionToken imageActionToken = mConfiguration.getActionTokenManager().borrowImageActionToken();
                mRequest.addToken(imageActionToken);
                break;
            case NONE:
                break;
            default:
                break;
        }
    }

    /**
     * Adds a session_token to a Requests' parameters by getting the token attached to the same Request
     * Note: setup must first be called as its request param is used
     */
    public void addTokenToRequestParameters() {
        if (mRequest.getToken() != null) {
            String tokenString = mRequest.getToken().getTokenString();

            mConfiguration.getLogger().v(TAG, "addTokenToRequestParameters - " + tokenString);
            mRequest.addQueryParameter("session_token", tokenString);
        } else {
            mConfiguration.getLogger().v(TAG, "addTokenToRequestParameters - no token to add for this request");
        }
    }

    /**
     * Adds a signature to a Requests' parameters according to the same Requests' InstructionsObject
     * Note: setup must first be called as its request param is used
     */
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
            mConfiguration.getLogger().v(TAG, "addSignatureToRequestParameters - " + signature);
            mRequest.addQueryParameter("signature", signature);
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

    private void addRequiredParametersForNewSessionToken() {
        mConfiguration.getLogger().v(TAG, "addRequiredParametersForNewSessionToken");
        Map<String, String> credentialsMap = mConfiguration.getUserCredentials().getCredentials();
        for (String key : credentialsMap.keySet()) {
            mRequest.addQueryParameter(key, credentialsMap.get(key));
        }

        mRequest.addQueryParameter("application_id", mConfiguration.getDeveloperCredentials().getCredentials().get("application_id"));

        mConfiguration.getLogger().v(TAG, "Request parameters: " + mRequest.getQueryParameters());
    }

    /**
     * Returns a token according to a Requests' InstructionsObject by calling the Configurations' token manager interface
     * Note: setup must first be called as its request param is used
     * Note: cleanup must first be called as its response param is used
     */
    public void returnToken() {
        if (mResponse instanceof ResponseApiClientError) {
            mConfiguration.getLogger().v(TAG, "returnToken - not returning a token. Response is ResponseApiClientError");
            return;
        }

        ResponseHelper responseHelper = new ResponseHelper(mResponse);

        if (mResponse == null || responseHelper.getResponseObject(ApiResponse.class) == null) {
            mConfiguration.getLogger().v(TAG, "returnToken - not returning a token. response null or couldn't find api response");
            return;
        }

        mConfiguration.getLogger().v(TAG, "returnToken - " + mRequest.getInstructionsObject().getReturnTokenType());
        switch (mRequest.getInstructionsObject().getReturnTokenType()) {
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
                if (apiResponse.hasError() && apiResponse.getError() == 105 || apiResponse.getError() == 127) {
                    // don't return the token
                } else {
                    if (apiResponse.needNewKey()) {
                        ((SessionToken) mRequest.getToken()).updateSessionToken();
                    }
                    mConfiguration.getSessionTokenManager().receiveSessionToken(((SessionToken) mRequest.getToken()));
                }
                mConfiguration.getLogger().v(TAG, "returnToken - returned old v2 to token manager");
                break;
            case NEW_UPLOAD:
                GetActionTokenResponse uploadActionTokenResponse = responseHelper.getResponseObject(GetActionTokenResponse.class);
                if (uploadActionTokenResponse.hasError() && uploadActionTokenResponse.getError() == 105 || uploadActionTokenResponse.getError() == 127) {
                    mConfiguration.getActionTokenManager().tokensFailed();
                } else {
                    UploadActionToken uploadActionToken = (UploadActionToken) createActionToken(UploadActionToken.class, uploadActionTokenResponse);
                    mConfiguration.getActionTokenManager().receiveUploadActionToken(uploadActionToken);
                }
                mConfiguration.getLogger().v(TAG, "returnToken - returned new upload action token to token manager");
                break;
            case NEW_IMAGE:
                GetActionTokenResponse imageActionTokenResponse = responseHelper.getResponseObject(GetActionTokenResponse.class);
                if (imageActionTokenResponse.hasError() && imageActionTokenResponse.getError() == 105 || imageActionTokenResponse.getError() == 127) {
                    mConfiguration.getActionTokenManager().tokensFailed();
                } else {
                    ImageActionToken mfImageActionToken = (ImageActionToken) createActionToken(ImageActionToken.class, imageActionTokenResponse);
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

    private ActionToken createActionToken(Class<? extends ActionToken> clazz, GetActionTokenResponse getActionTokenResponse) {
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
        if (mRequest.getQueryParameters().containsKey("lifespan")) {
            tokenExpiry = Long.valueOf((Integer) mRequest.getQueryParameters().get("lifespan"));
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
    protected SessionToken createNewSessionToken(GetSessionTokenResponse getSessionTokenResponse) {

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
        SessionToken mfSessionToken = new SessionToken(tokenString, secretKey, time, pkey, ekey);
        return mfSessionToken;
    }

    /**
     * Makes a signature based on an api Request Object
     * Note: setup must first be called as its request param is used
     * @return a String that is the new signature created
     */
    protected final String makeSignatureForApiRequest() {

        // session token secret key + time + uri (concatenated)
        SessionToken sessionToken = (SessionToken) mRequest.getToken();

        if (sessionToken == null) {
            mConfiguration.getLogger().v(TAG, "makeSignatureForApiRequest - request had no token, returning null for signature");
            return null;
        }

        int secretKeyMod256 = Integer.valueOf(sessionToken.getSecretKey()) % 256;
        String time = sessionToken.getTime();

        UrlHelper urlHelper = new UrlHelper(mRequest);

        String nonUrlEncodedQueryString = urlHelper.getQueryString(false);

        String baseUri = urlHelper.getBaseUriString();
        String fullUri = baseUri + nonUrlEncodedQueryString;

        String nonUrlEncodedString = secretKeyMod256 + time + fullUri;

        mConfiguration.getLogger().v(TAG, "makeSignatureForApiRequest - hash target: " + nonUrlEncodedQueryString);
        String signature = hashString(nonUrlEncodedString, "MD5");
        mConfiguration.getLogger().v(TAG, "makeSignatureForApiRequest - hashed: " + signature);
        return signature;
    }

    /**
     * Hashes a string according to an algorithm
     * @param target the String to hash
     * @param hashAlgorithm the hashing algorithm to perform
     * @return the hashed version of the passed String
     */
    protected final String hashString(String target, String hashAlgorithm) {
        mConfiguration.getLogger().v(TAG, "hashString - target: " + target);
        String result;
        try {
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);

            md.update(target.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            result = target;
        }
        mConfiguration.getLogger().v(TAG, "hashString - hashed: " + result);
        return result;
    }
}
