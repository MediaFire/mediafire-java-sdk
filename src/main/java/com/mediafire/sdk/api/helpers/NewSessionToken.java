package com.mediafire.sdk.api.helpers;

import com.mediafire.sdk.api.responses.user.GetSessionTokenResponse;
import com.mediafire.sdk.config.DeveloperCredentials;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.config.UserCredentials;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris on 11/9/2014.
 * BaseClientHelper used with ApiClient to get new session tokens.
 */
public class NewSessionToken extends Instructions {
    private UserCredentials mUserCredentials;
    private DeveloperCredentials mDeveloperCredentials;
    private TokenManager mSessionTokenManagerInterface;

    public NewSessionToken(UserCredentials userCredentials, DeveloperCredentials developerCredentials, TokenManager sessionTokenManagerInterface) {
        super();
        mUserCredentials = userCredentials;
        mDeveloperCredentials = developerCredentials;
        mSessionTokenManagerInterface = sessionTokenManagerInterface;
    }
    
    @Override
    public void borrowToken(Request request) {
        // no token needs to be borrowed for new session tokens
    }

    @Override
    public void addSignatureToRequestParameters(Request request) {
        addRequiredParametersForNewSessionToken(request);
        String signature = makeSignatureForNewSessionToken();
        request.addSignature(signature);
    }

    @Override
    public void returnToken(Response response, Request request) {
        GetSessionTokenResponse newSessionTokenResponse = getResponseObject(response, GetSessionTokenResponse.class);
        SessionToken newSessionToken = createNewSessionToken(newSessionTokenResponse);
        if (newSessionToken != null) {
            mSessionTokenManagerInterface.give(newSessionToken);
        }
    }

    private String makeSignatureForNewSessionToken() {
        // email + password + app id + api key
        // fb access token + app id + api key
        // tw oauth token + tw oauth token secret + app id + api key
        String userInfoPortionOfHashTarget = mUserCredentials.getCredentialsString();

        // apiKey is not required, but may be passed into the MFConfiguration object
        // Note: If the app does not have the "Require Secret Key" option checked,
        // then the API key may be omitted from the signature.
        // However, this should only be done when sufficient domain and/or network restrictions are in place.
        String devInfoPortionOfHashTarget = mDeveloperCredentials.getApplicationId();
        if (mDeveloperCredentials.requiresSecretKey()) {
            devInfoPortionOfHashTarget += mDeveloperCredentials.getApiKey();
        }

        String hashTarget = userInfoPortionOfHashTarget + devInfoPortionOfHashTarget;

        String signature = hashString(hashTarget, "SHA-1");
        return signature;
    }

    private void addRequiredParametersForNewSessionToken(Request request) {
        UserCredentials.Credentials credentials = mUserCredentials.getCredentials();

        if (credentials == null) {
            return;
        } else if (credentials instanceof UserCredentials.Ekey) {
            request.addQueryParameter("ekey", ((UserCredentials.Ekey) credentials).getEkey());
            request.addQueryParameter("password", ((UserCredentials.Ekey) credentials).getPassword());
        } else if (credentials instanceof UserCredentials.Email) {
            request.addQueryParameter("email", ((UserCredentials.Email) credentials).getEmail());
            request.addQueryParameter("password", ((UserCredentials.Email) credentials).getPassword());
        } else if (credentials instanceof UserCredentials.Facebook) {
            request.addQueryParameter("fb_access_token", ((UserCredentials.Facebook) credentials).getFacebookAccessToken());
        } else if (credentials instanceof UserCredentials.Twitter) {
            request.addQueryParameter("tw_oauth_token", ((UserCredentials.Twitter) credentials).getOauthToken());
            request.addQueryParameter("tw_oauth_token_secret", ((UserCredentials.Twitter) credentials).getTokenSecret());
        }

        request.addQueryParameter("application_id", mDeveloperCredentials.getApplicationId());
    }

    /**
     * Creates a SessionToken Object from a GetSessionTokenResponse
     * @param getSessionTokenResponse the response to create a SessionToken from
     * @return a new SessionToken Object
     */
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

        SessionToken.Builder builder = new SessionToken.Builder(tokenString);
        builder.secretKey(secretKey).time(time).pkey(pkey).ekey(ekey);

        return builder.build();
    }
}
