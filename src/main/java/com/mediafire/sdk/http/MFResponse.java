package com.mediafire.sdk.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mediafire.sdk.api_responses.ApiResponse;

import java.util.List;
import java.util.Map;

public final class MFResponse {
    private final int status;
    private final Map<String, List<String>> headers;
    private final byte[] bodyBytes;
    private final MFRequester mfRequester;

    public MFResponse(int status, Map<String, List<String>> headers, byte[] bodyBytes, MFRequester mfRequester) {
        this.status = status;
        this.headers = headers;
        this.bodyBytes = bodyBytes;
        this.mfRequester = mfRequester;
    }

    /**
     * gets the status code from the request
     * @return the status code from the request (i.e. 200, 404, etc.)
     */
    public int getStatus() {
        return status;
    }

    /**
     * gets the headers from the response
     * @return a Map of headers from the response.
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * gets the response bytes
     * @return byte array with the response
     */
    public byte[] getResponseAsBytes() {
        return bodyBytes;
    }

    /**
     * gets the response as a String
     * @return response as a String
     */
    public String getResponseAsString() {
        if (bodyBytes == null) {
            return null;
        }
        return new String(bodyBytes);
    }

    /**
     * gets a class populated with
     * @param responseClass the class which Gson will use to parse a json response via reflection.
     * @param <ResponseClass> .class which extends ApiResponse
     * @return an Object of type <ResponseClass> which extends ApiResponse
     */
    public <ResponseClass extends ApiResponse> ResponseClass getResponseObject(Class<ResponseClass> responseClass) {
        String responseString = getResponseAsString();
        if (responseString == null) {
            return null;
        }
        return new Gson().fromJson(getResponseStringForGson(responseString), responseClass);
    }

    /**
     * gets the original MFRequester.
     * @return MFRequester which was used to make the request. Also will contain any changes made during http process.
     */
    public MFRequester getOriginMFRequester() {
        return mfRequester;
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
}
