package com.mediafire.sdk.client;

import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.token.SessionToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class SignatureHelper {

    private final Request mRequest;
    private Configuration mConfiguration;

    public SignatureHelper(Request request, Configuration configuration) {
        mRequest = request;
        mConfiguration = configuration;
    }

    public String calculateSignature() {
        String signature = null;
        switch (mRequest.getApiObject().getTypeOfSignatureToAdd()) {
            case NEW_SESSION_TOKEN_SIGNATURE:
                calculateSignatureForNewSessionToken();
                break;
            case USING_SESSION_TOKEN_SIGNATURE:
                calculateSignatureWithSessionToken();
                break;
            case NO_SIGNATURE_REQUIRED:
                break;
        }
        return signature;
    }

    private String calculateSignatureForNewSessionToken() {
        String userInfoPortionOfHashTarget = mConfiguration.getUserCredentialsInterface().getConcatenatedCredentials();
        String appId = mConfiguration.getAppId();
        String apiKey = mConfiguration.getApiKey();

        // Note: If the app does not have the "Require Secret Key" option checked, then the API key may be omitted from
        // the signature. However, this should only be done when sufficient domain/network restrictions are in place.
        String hashTarget;
        if (apiKey == null) {
            hashTarget = userInfoPortionOfHashTarget + appId;
        } else {
            hashTarget = userInfoPortionOfHashTarget + appId + apiKey;
        }

        return hashString(hashTarget, "SHA-1");
    }

    private String calculateSignatureWithSessionToken() {
        // session token secret key + time + uri (concatenated)
        SessionToken sessionToken = (SessionToken) mRequest.getToken();
        int secretKeyMod256 = Integer.valueOf(sessionToken.getSecretKey()) % 256;
        String time = sessionToken.getTime();

        UrlHelper urlHelper = new UrlHelper(mRequest);

        String queryString = urlHelper.getQueryString(mRequest.getQueryParameters(), false);
        String baseUri = urlHelper.getBaseUriString(mRequest.getApiObject(), mRequest.getVersionObject());
        String fullUri = baseUri + queryString;

        String nonUrlEncodedString = secretKeyMod256 + time + fullUri;

        return hashString(nonUrlEncodedString, "MD5");
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
