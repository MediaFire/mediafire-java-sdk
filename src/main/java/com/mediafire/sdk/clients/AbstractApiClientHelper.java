package com.mediafire.sdk.clients;

import com.mediafire.sdk.api_responses.user.GetSessionTokenResponse;
import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.SessionToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Chris Najar on 10/20/2014.
 */
public abstract class AbstractApiClientHelper {
    private static final String TAG = AbstractApiClientHelper.class.getCanonicalName();
    protected Request mRequest;
    protected Response mResponse;

    public final void setup(Request request) {
        DefaultLogger.log().v(TAG, "setup");
        mRequest = request;
        borrowToken();
        addTokenToRequestParameters();
        addSignatureToRequestParameters();
    }

    public final void cleanup(Response response) {
        DefaultLogger.log().v(TAG, "cleanup");
        mResponse = response;
        returnToken();
    }

    public abstract void borrowToken();

    public abstract void addTokenToRequestParameters();

    public abstract void addSignatureToRequestParameters();

    public abstract void returnToken();

    protected SessionToken createNewSessionToken(GetSessionTokenResponse getSessionTokenResponse) {
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

    protected final String makeSignatureForApiRequest() {
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

    protected final String hashString(String target, String hashAlgorithm) {
        DefaultLogger.log().v(TAG, "hashString");
        try {
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);

            md.update(target.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return target;
        }
    }
}
