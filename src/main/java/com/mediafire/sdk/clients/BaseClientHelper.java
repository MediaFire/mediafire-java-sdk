package com.mediafire.sdk.clients;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.SessionToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Chris on 11/6/2014.
 */
public abstract class BaseClientHelper {
    public final String TAG = getClass().getCanonicalName();

    public BaseClientHelper() { }

    public final void setup(Request request) {
        System.out.println(TAG + " - " + "setup");
        borrowToken(request);
        addTokenToRequestParameters(request);
        addSignatureToRequestParameters(request);
    }

    /**
     * Cleans up a response by returning appropriate tokens according to the requests InstructionsObject
     * Note: setup must be called first as the request parameters' InstructionsObject is used
     * @param response the response the get the new token from
     */
    public final void cleanup(Response response, Request request) {
        System.out.println(TAG + " - " + "cleanup");
        returnToken(response, request);
    }

    /**
     * subclasses should implement this method to borrow a token from a SessionTokenManagerInterface or
     * ActionTokenManagerInterface if a token is required. Request.addToken() is used to add a token to a Request.
     * @param request
     */
    public abstract void borrowToken(Request request);

    /**
     * Certain API calls required a signature to be appended to the query parameters. If a signature should be
     * calculated, implement this method.
     * @param request
     */
    public abstract void addSignatureToRequestParameters(Request request);

    /**
     * subclasses should implement this method if a Token needs to be returned to a SessionTokenManagerInterface
     * and/or an ActionTokenManagerInterface.
     * @param response
     * @param request
     */
    public abstract void returnToken(Response response, Request request);

    /**
     * Adds a session_token to a Requests' parameters by getting the token attached to the same Request
     * Note: setup must first be called as its request param is used
     */
    private final void addTokenToRequestParameters(Request request) {
        if (request.getToken() != null) {
            String tokenString = request.getToken().getTokenString();

            System.out.println(TAG + " - " + "addTokenToRequestParameters - " + tokenString);
            request.addQueryParameter("session_token", tokenString);
        } else {
            System.out.println(TAG + " - " + "addTokenToRequestParameters - no token to add for this request");
        }
    }

    /**
     * Hashes a string according to an algorithm
     * @param target the String to hash
     * @param hashAlgorithm the hashing algorithm to perform
     * @return the hashed version of the passed String
     */
    public final String hashString(String target, String hashAlgorithm) {
        System.out.println(TAG + " - " + "hashString - target: " + target);
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
        System.out.println(TAG + " - " + "hashString - hashed: " + result);
        return result;
    }

    /**
     * Gets an Object of type ApiResponse from the response
     * @param responseClass the class object extended from ApiResponse to be returned
     * @param <ResponseClass> the class object extended from ApiResponse to be returned
     * @return the new ApiResponse object of class ResponseClass
     */
    public <ResponseClass extends ApiResponse> ResponseClass getResponseObject(Response response, Class<ResponseClass> responseClass) {
        String responseString = getResponseAsString(response);
        String gsonResponseString = getResponseStringForGson(responseString);
        return new Gson().fromJson(gsonResponseString, responseClass);
    }

    /**
     * Returns the response from the Response object as a String
     * @return a new String of the response
     */
    public String getResponseAsString(Response response) {
        if (response == null) {
            return null;
        }

        if (response.getBytes() == null) {
            return null;
        }

        return new String(response.getBytes());
    }

    private String getResponseStringForGson(String response) {
        if (response == null || response.isEmpty()) {
            return null;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response);
        if (element.isJsonObject()) {
            JsonObject jsonResponse = element.getAsJsonObject().get("response").getAsJsonObject();
            return jsonResponse.toString();
        } else {
            return null;
        }
    }

    /**
     * Makes a signature based on an api Request Object
     * Note: setup must first be called as its request param is used
     * @return a String that is the new signature created
     */
    public final String makeSignatureForApiRequest(Request request) {

        // session token secret key + time + uri (concatenated)
        SessionToken sessionToken = (SessionToken) request.getToken();

        if (sessionToken == null) {
            System.out.printf("%s - %s", TAG, "makeSignatureForApiRequest - request had no token, returning null for signature");
            return null;
        }

        int secretKeyMod256 = Integer.valueOf(sessionToken.getSecretKey()) % 256;
        String time = sessionToken.getTime();

        UrlHelper urlHelper = new UrlHelper(request);

        String nonUrlEncodedQueryString = urlHelper.getQueryString(false);

        String baseUri = urlHelper.getBaseUriString();
        String fullUri = baseUri + nonUrlEncodedQueryString;

        String nonUrlEncodedString = secretKeyMod256 + time + fullUri;

        System.out.printf("%s - %s", TAG, "makeSignatureForApiRequest - hash target: " + nonUrlEncodedQueryString);
        String signature = hashString(nonUrlEncodedString, "MD5");
        System.out.printf("%s - %s", TAG, "makeSignatureForApiRequest - hashed: " + signature);
        return signature;
    }
}
