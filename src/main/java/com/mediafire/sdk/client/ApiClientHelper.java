package com.mediafire.sdk.client;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.api_responses.user.GetActionTokenResponse;
import com.mediafire.sdk.api_responses.user.GetSessionTokenResponse;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.token.UploadActionToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Chris Najar on 10/20/2014.
 */
public class ApiClientHelper {
    private Configuration mConfiguration;
    private Request mRequest;
    private Response mResponse;

    public ApiClientHelper(Configuration configuration) {
        mConfiguration = configuration;
    }

    public void setup(Request request) {
        mRequest = request;
        // borrow token, if necessary
        borrowToken();
        // add token, if necessary, to request parameters
        addTokenToRequestParameters();
        // add signature, if necessary, to request parameters
        addSignatureToRequestParameters();
    }

    private void borrowToken() {
        switch (mRequest.getInstructionsObject().getBorrowTokenType()) {
            case V2:
                SessionToken sessionToken = mConfiguration.getSessionTokenManagerInterface().borrowSessionToken();
                mRequest.addToken(sessionToken);
                break;
            case UPLOAD:
                UploadActionToken uploadActionToken = mConfiguration.getActionTokenManagerInterface().borrowUploadActionToken();
                mRequest.addToken(uploadActionToken);
                break;
            case IMAGE:
                ImageActionToken imageActionToken = mConfiguration.getActionTokenManagerInterface().borrowImageActionToken();
                mRequest.addToken(imageActionToken);
                break;
            default:
                // for type NONE, NEW there is no need to request a token.
                break;
        }
    }

    private void addTokenToRequestParameters() {
        if (mRequest.getToken() != null) {
            String tokenString = mRequest.getToken().getTokenString();
            mRequest.addQueryParameter("session_token", tokenString);
        }
    }

    private void addSignatureToRequestParameters() {
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
            mRequest.addQueryParameter("signature", signature);
        }
    }

    private String makeSignatureForNewSessionToken() {
        // email + password + app id + api key
        // fb access token + app id + api key
        // tw oauth token + tw oauth token secret + app id + api key

        String userInfoPortionOfHashTarget = mConfiguration.getUserCredentialsInterface().getConcatenatedCredentials();

        String appId = mConfiguration.getAppId();
        String apiKey = mConfiguration.getApiKey();

        // apiKey is not required, but may be passed into the MFConfiguration object
        // Note: If the app does not have the "Require Secret Key" option checked,
        // then the API key may be omitted from the signature.
        // However, this should only be done when sufficient domain and/or network restrictions are in place.
        String hashTarget;
        if (apiKey == null) {
            hashTarget = userInfoPortionOfHashTarget + appId;
        } else {
            hashTarget = userInfoPortionOfHashTarget + appId + apiKey;
        }

        return hashString(hashTarget, "SHA-1");
    }

    private void addRequiredParametersForNewSessionToken() {
        Map<String, String> credentialsMap = mConfiguration.getUserCredentialsInterface().getCredentials();
        for (String key : credentialsMap.keySet()) {
            mRequest.addQueryParameter(key, credentialsMap.get(key));
        }

        mRequest.addQueryParameter("application_id", mConfiguration.getAppId());
    }

    public void cleanup(Response response) {
        mResponse = response;
        returnToken();
    }

    private void returnToken() {
        ResponseHelper responseHelper = new ResponseHelper(mResponse);

        if (mResponse == null || responseHelper.getResponseObject(ApiResponse.class) == null) {
            return;
        }
        switch (mRequest.getInstructionsObject().getReturnTokenType()) {
            case NEW_V2:
                GetSessionTokenResponse newSessionTokenResponse = responseHelper.getResponseObject(GetSessionTokenResponse.class);
                SessionToken newSessionToken = createNewSessionToken(newSessionTokenResponse);
                if (newSessionToken != null) {
                    mConfiguration.getSessionTokenManagerInterface().receiveSessionToken(newSessionToken);
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
                    mConfiguration.getSessionTokenManagerInterface().receiveSessionToken(((SessionToken) mRequest.getToken()));
                }
                break;
            case NEW_UPLOAD:
                GetActionTokenResponse uploadActionTokenResponse = responseHelper.getResponseObject(GetActionTokenResponse.class);
                if (uploadActionTokenResponse.hasError() && uploadActionTokenResponse.getError() == 105 || uploadActionTokenResponse.getError() == 127) {
                    mConfiguration.getActionTokenManagerInterface().tokensFailed();
                } else {
                    UploadActionToken uploadActionToken = (UploadActionToken) createActionToken(UploadActionToken.class, uploadActionTokenResponse);
                    mConfiguration.getActionTokenManagerInterface().receiveUploadActionToken(uploadActionToken);
                }
                break;
            case NEW_IMAGE:
                GetActionTokenResponse imageActionTokenResponse = responseHelper.getResponseObject(GetActionTokenResponse.class);
                if (imageActionTokenResponse.hasError() && imageActionTokenResponse.getError() == 105 || imageActionTokenResponse.getError() == 127) {
                    mConfiguration.getActionTokenManagerInterface().tokensFailed();
                } else {
                    ImageActionToken mfImageActionToken = (ImageActionToken) createActionToken(ImageActionToken.class, imageActionTokenResponse);
                    mConfiguration.getActionTokenManagerInterface().receiveImageActionToken(mfImageActionToken);
                }
                break;
            case NONE:
                // for types NONE
                // there is no need to return a token

                // if a token is invalid then there needs to be a call made to TokenFarm to notify
                if (responseHelper.getResponseObject(ApiResponse.class).hasError()) {
                    mConfiguration.getActionTokenManagerInterface().tokensFailed();
                }
                break;
        }
    }

    public String makeSignatureForApiRequest() {
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
        SessionToken mfSessionToken = new SessionToken(tokenString, secretKey, time, pkey, ekey);
        return mfSessionToken;
    }

    private String hashString(String target, String hashAlgorithm) {
        String hash;
        try {
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);

            md.update(target.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            hash = target;
        }
        return hash;
    }
}
