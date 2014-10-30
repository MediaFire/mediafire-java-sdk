package com.mediafire.sdk.clients;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.http.Response;

/**
 * Created by Chris Najar on 10/18/2014.
 * ResponseHelper parses the json of a response and returns the response
 */
public class ResponseHelper {
    private static final String TAG = ResponseHelper.class.getCanonicalName();
    private Response mResponse;

    /**
     * ResponseHelper Constructor
     * @param response The response that contains the response bytes
     */
    public ResponseHelper(Response response) {
        mResponse = response;
    }

    /**
     * Gets an ApiResponse object from the response
     * @return a new ApiResponse
     */
    public ApiResponse getApiResponse() {
        return getResponseObject(ApiResponse.class);
    }

    /**
     * Gets an Object of type ApiResponse from the response
     * @param responseClass the class object extended from ApiResponse to be returned
     * @param <ResponseClass> the class object extended from ApiResponse to be returned
     * @return the new ApiResponse object of class ResponseClass
     */
    public <ResponseClass extends ApiResponse> ResponseClass getResponseObject(Class<ResponseClass> responseClass) {
        String responseString = getResponseAsString();
        if (responseString == null) {
            return null;
        }
        return new Gson().fromJson(getResponseStringForGson(responseString), responseClass);
    }

    /**
     * Returns the response from the Response object as a String
     * @return a new String of the response
     */
    public String getResponseAsString() {
        if (mResponse == null) {
            return null;
        }

        if (mResponse.getBytes() == null) {
            return null;
        }

        String responseString = new String(mResponse.getBytes());
        return responseString;
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
