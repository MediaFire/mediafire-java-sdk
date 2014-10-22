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
 */
public class ResponseHelper {
    private static final String TAG = ResponseHelper.class.getCanonicalName();
    private Response mResponse;

    public ResponseHelper(Response response) {
        mResponse = response;
    }

    public ApiResponse getApiResponse() {
        return getResponseObject(ApiResponse.class);
    }

    public <ResponseClass extends ApiResponse> ResponseClass getResponseObject(Class<ResponseClass> responseClass) {
        String responseString = getResponseAsString();
        if (responseString == null) {
            return null;
        }
        return new Gson().fromJson(getResponseStringForGson(responseString), responseClass);
    }

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
