package com.mediafire.sdk.http;

import com.mediafire.sdk.config.MFConfiguration;
import com.mediafire.sdk.config.MFCredentials;
import com.mediafire.sdk.config.MFDefaultCredentials;
import com.mediafire.sdk.token.MFActionToken;
import com.mediafire.sdk.token.MFSessionToken;
import com.mediafire.sdk.token.MFTokenFarmCallback;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public final class MFHttpClientSetup extends MFHttp {
    private static final String TAG = MFHttpClientSetup.class.getCanonicalName();
    private static final String SHA1 = "SHA-1";
    private static final String MD5 = "MD5";

    private final MFTokenFarmCallback mfTokenFarmCallback;
    private final MFCredentials mfCredentials;
    private final MFNetworkConnectivityMonitor mfNetworkConnectivityMonitor;

    /**
     * Constructor to help set up an http request to mediafire.
     * @param mfTokenFarmCallback - callback used to borrow Tokens
     * @param mfConfiguration - the configuration used by the MFTokenFarm.
     */
    public MFHttpClientSetup(MFTokenFarmCallback mfTokenFarmCallback, MFConfiguration mfConfiguration) {
        super(mfConfiguration);
        this.mfTokenFarmCallback = mfTokenFarmCallback;
        this.mfCredentials = mfConfiguration.getMFCredentials();
        this.mfNetworkConnectivityMonitor = mfConfiguration.getMfNetworkConnectivityMonitor();
    }

    /**
     * prepares an MFRequester for making a request by:
     * borrowing a Token if needed,
     * adding the Token to the request if needed,
     * and calculating a signature if needed.
     * @param mfRequester - the MFRequester.
     * @throws UnsupportedEncodingException  - if UTF-8 encoding is not available.
     * @throws MFHttpException - if credentials are not set or if no network connection
     */
    public void prepareMFRequestForHttpClient(MFRequester mfRequester) throws UnsupportedEncodingException, MFHttpException {
        MFConfiguration.getStaticMFLogger().d(TAG, "prepareMFRequestForHttpClient()");
        // if no network connection as per network connectivity monitor, return failed mf response (null values and -1 status)
        if (!mfNetworkConnectivityMonitor.haveNetworkConnection()) {
            throw new MFHttpException("no network connection");
        }
        // borrow token, if necessary
        borrowToken(mfRequester);
        // add token, if necessary, to request parameters
        addTokenToRequestParameters(mfRequester);
        // add signature, if necessary, to request parameters
        addSignatureToRequestParameters(mfRequester);
    }

    private void addRequestParametersForNewSessionToken(MFRequester mfRequest) {
        MFConfiguration.getStaticMFLogger().d(TAG, "addRequestParametersForNewSessionToken()");
        Map<String, String> credentialsMap = mfCredentials.getCredentials();
        mfRequest.getRequestParameters().putAll(credentialsMap);
        mfRequest.getRequestParameters().put("application_id", mfConfiguration.getAppId());
    }

    private void addSignatureToRequestParameters(MFRequester mfRequester) throws UnsupportedEncodingException, MFHttpException {
        MFConfiguration.getStaticMFLogger().d(TAG, "addSignatureToRequestParameters(type: " + mfRequester.getTypeOfSignatureToAdd()+ ")");
        switch (mfRequester.getTypeOfSignatureToAdd()) {
            case V2:
                mfRequester.getRequestParameters().remove("signature");
                MFConfiguration.getStaticMFLogger().v(TAG, "request parameters before signature: " + mfRequester.getRequestParameters().toString());
                String recycledSessionTokenSignature = calculateSignatureForApiRequest(mfRequester);
                MFConfiguration.getStaticMFLogger().v(TAG, "signature calculated: " + recycledSessionTokenSignature);
                mfRequester.getRequestParameters().put("signature", recycledSessionTokenSignature);
                MFConfiguration.getStaticMFLogger().v(TAG, "request parameters  after signature: " + mfRequester.getRequestParameters().toString());
                break;
            case NEW:
                // add additional request parameters required for this signature
                addRequestParametersForNewSessionToken(mfRequester);
                String newSessionTokenSignature = calculateSignatureForNewSessionToken(mfConfiguration, mfCredentials);
                mfRequester.getRequestParameters().put("signature", newSessionTokenSignature);
                break;
            default:
                // for types NONE, UPLOAD, IMAGE
                // there is no need to attach a signature to the request parameters
                break;
        }
    }

    private String calculateSignatureForNewSessionToken(MFConfiguration mfConfiguration, MFCredentials credentials) throws MFHttpException {
        MFConfiguration.getStaticMFLogger().d(TAG, "calculateSignatureForNewSessionToken()");
        // email + password + app id + api key
        // fb access token + app id + api key
        // tw oauth token + tw oauth token secret + app id + api key

        String userInfoPortionOfHashTarget = null;
        switch (credentials.getUserCredentialsType()) {
            case FACEBOOK:
                String fb_token_key = MFDefaultCredentials.FACEBOOK_PARAMETER_FB_ACCESS_TOKEN;
                userInfoPortionOfHashTarget = credentials.getCredentials().get(fb_token_key);
                break;
            case TWITTER:
                String tw_oauth_token = MFDefaultCredentials.TWITTER_PARAMETER_TW_OAUTH_TOKEN;
                String tw_oauth_token_secret = MFDefaultCredentials.TWITTER_PARAMETER_TW_OAUTH_TOKEN_SECRET;
                userInfoPortionOfHashTarget = credentials.getCredentials().get(tw_oauth_token) + credentials.getCredentials().get(tw_oauth_token_secret);
                break;
            case MEDIAFIRE:
                String mf_email = MFDefaultCredentials.MEDIAFIRE_PARAMETER_EMAIL;
                String mf_pass = MFDefaultCredentials.MEDIAFIRE_PARAMETER_PASSWORD;
                userInfoPortionOfHashTarget = credentials.getCredentials().get(mf_email) + credentials.getCredentials().get(mf_pass);
                break;
            case UNSET:
                throw new MFHttpException("credentials must be set to call /api/user/get_session_token");
        }

        String appId = mfConfiguration.getAppId();
        String apiKey = mfConfiguration.getApiKey();

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

        return hashString(hashTarget, SHA1);
    }

    private String calculateSignatureForApiRequest(MFRequester mfRequester) throws UnsupportedEncodingException {
        MFConfiguration.getStaticMFLogger().d(TAG, "calculateSignatureForApiRequest()");
        // session token secret key + time + uri (concatenated)
        MFSessionToken sessionToken = (MFSessionToken) mfRequester.getToken();
        int secretKeyMod256 = Integer.valueOf(sessionToken.getSecretKey()) % 256;
        String time = sessionToken.getTime();
        String nonUrlEncodedQueryString = makeQueryString(mfRequester.getRequestParameters(), false);
        String urlAttachableQueryString = makeUrlAttachableQueryString(nonUrlEncodedQueryString);
        String baseUri = mfRequester.getUri();
        String fullUri = baseUri + urlAttachableQueryString;

        MFConfiguration.getStaticMFLogger().v(TAG, "going to calculate signature for secretKeyMod256: " + secretKeyMod256 + ", time: " + time + ", fullUri: " + fullUri);
        String nonUrlEncodedString = secretKeyMod256 + time + fullUri;

        MFConfiguration.getStaticMFLogger().v(TAG, "going to calculate signature for nonUrlEncodedString: " + nonUrlEncodedString);
        return hashString(nonUrlEncodedString, MD5);
    }

    private void addTokenToRequestParameters(MFRequester mfRequester) {
        MFConfiguration.getStaticMFLogger().d(TAG, "addTokenToRequestParameters()");
        if (mfRequester.isTokenRequired() && mfRequester.getToken() != null) {
            String tokenString = mfRequester.getToken().getTokenString();
            mfRequester.getRequestParameters().put("session_token", tokenString);
        } else {
            // token not required
        }
    }

    private void borrowToken(MFRequester mfRequester) {
        MFConfiguration.getStaticMFLogger().d(TAG, "borrowToken(type: " + mfRequester.getTypeOfTokenToBorrow() + ")");
        switch (mfRequester.getTypeOfTokenToBorrow()) {
            case V2:
                MFSessionToken sessionToken = mfTokenFarmCallback.borrowMFSessionToken();
                mfRequester.setToken(sessionToken);
                break;
            case UPLOAD:
                MFActionToken uploadActionToken = mfTokenFarmCallback.borrowMFUploadActionToken();
                mfRequester.setToken(uploadActionToken);
                break;
            case IMAGE:
                MFActionToken imageActionToken = mfTokenFarmCallback.borrowMFImageActionToken();
                mfRequester.setToken(imageActionToken);
                break;
            default:
                // for type NONE, NEW there is no need to request a token.
                break;
        }
    }

    private String hashString(String target, String hashAlgorithm) {
        MFConfiguration.getStaticMFLogger().d(TAG, "hashString()");
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
        MFConfiguration.getStaticMFLogger().v(TAG, hashAlgorithm + " hashed " + target + " to " + hash + ")");
        return hash;
    }
}
