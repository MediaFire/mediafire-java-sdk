package com.mediafire.sdk.util;

import com.google.gson.*;
import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.api.responses.ApiResponse;

/**
 * Created by Chris on 5/15/2015.
 */
public class ResponseUtil {

    public static <T extends ApiResponse> T makeApiResponseFromHttpResponse(HttpApiResponse httpResponse, Class<T> classOfT) throws MediaFireException {
        if (httpResponse == null) {
            throw new MediaFireException("HttpApiResponse was null");
        }

        if (httpResponse.getBytes() == null || httpResponse.getBytes().length == 0) {
            throw new MediaFireException("HttpApiResponse.getBytes() was null or empty, nothing to parse");
        }

        try {
            byte[] responseBytes = httpResponse.getBytes();
            String responseString = new String(responseBytes);
            T apiResponse = new Gson().fromJson(getResponseStringForGson(responseString), classOfT);

            if (apiResponse.hasError()) {
                throw new MFApiException(apiResponse.getError(), apiResponse.getMessage());
            }

            return apiResponse;
        } catch (JsonSyntaxException e) {
            throw new MediaFireException("The json was malformed and could not be read", e);
        }
    }

    private static String getResponseStringForGson(String response) {
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
