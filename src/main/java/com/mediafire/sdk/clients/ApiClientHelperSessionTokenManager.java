package com.mediafire.sdk.clients;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.api_responses.user.GetSessionTokenResponse;
import com.mediafire.sdk.config.CredentialsInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.token.SessionToken;

import java.util.Map;

/**
 * Created by Chris Najar on 10/20/2014.
 */
public class ApiClientHelperSessionTokenManager extends AbstractApiClientHelper {
    private static final String TAG = ApiClientHelperSessionTokenManager.class.getCanonicalName();

    private CredentialsInterface mUserCredentials;
    private CredentialsInterface mDeveloperCredentials;
    private SessionTokenManagerInterface mSessionTokenManager;

    public ApiClientHelperSessionTokenManager(CredentialsInterface userCredentials,
                                              CredentialsInterface developerCredentials,
                                              SessionTokenManagerInterface sessionTokenManager) {
        mUserCredentials = userCredentials;
        mDeveloperCredentials = developerCredentials;
        mSessionTokenManager = sessionTokenManager;
    }

    @Override
    public void borrowToken() {
        // no token borrowed
    }

    @Override
    public void addTokenToRequestParameters() {
        // no token to add
    }

    @Override
    public void addSignatureToRequestParameters() {
        DefaultLogger.log().v(TAG, "addSignatureToRequestParameters");
        addRequiredParametersForNewSessionToken();
        String signature = makeSignatureForNewSessionToken();

        if (signature != null) {
            mRequest.addQueryParameter("signature", signature);
        }
    }

    private void addRequiredParametersForNewSessionToken() {
        DefaultLogger.log().v(TAG, "addRequiredParametersForNewSessionToken");
        Map<String, String> credentialsMap = mUserCredentials.getCredentials();
        for (String key : credentialsMap.keySet()) {
            mRequest.addQueryParameter(key, credentialsMap.get(key));
        }

        mRequest.addQueryParameter("application_id", mDeveloperCredentials.getCredentials().get("application_id"));
    }

    private String makeSignatureForNewSessionToken() {
        DefaultLogger.log().v(TAG, "makeSignatureForNewSessionToken");
        // email + password + app id + api key
        // fb access token + app id + api key
        // tw oauth token + tw oauth token secret + app id + api key

        String userInfoPortionOfHashTarget = mUserCredentials.getConcatenatedCredentials();
        String devInfoPortionOfHashTarget = mDeveloperCredentials.getConcatenatedCredentials();

        // apiKey is not required, but may be passed into the MFConfiguration object
        // Note: If the app does not have the "Require Secret Key" option checked,
        // then the API key may be omitted from the signature.
        // However, this should only be done when sufficient domain and/or network restrictions are in place.
        String hashTarget = userInfoPortionOfHashTarget + devInfoPortionOfHashTarget;

        return hashString(hashTarget, "SHA-1");
    }

    @Override
    public void returnToken() {
        DefaultLogger.log().v(TAG, "returnToken");
        ResponseHelper responseHelper = new ResponseHelper(mResponse);

        if (mResponse == null || responseHelper.getResponseObject(ApiResponse.class) == null) {
            mSessionTokenManager.getNewSessionTokenFailed(mResponse);
            return;
        }

        GetSessionTokenResponse newSessionTokenResponse = responseHelper.getResponseObject(GetSessionTokenResponse.class);
        SessionToken newSessionToken = createNewSessionToken(newSessionTokenResponse);
        DefaultLogger.log().v(TAG, "token null: " + (newSessionToken == null));
        DefaultLogger.log().v(TAG, "token secret key: " + newSessionToken.getSecretKey());
        DefaultLogger.log().v(TAG, "token time: " + newSessionToken.getTime());
        DefaultLogger.log().v(TAG, "token ekey: " + newSessionToken.getEkey());
        DefaultLogger.log().v(TAG, "token pkey: " + newSessionToken.getPkey());
        DefaultLogger.log().v(TAG, "token permtoken: " + newSessionToken.getPermanentToken());
        if (newSessionToken != null) {
            mSessionTokenManager.receiveSessionToken(newSessionToken);
        }
    }
}