package com.mediafire.sdk.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.requests.HttpApiResponse;

/**
 * Created by Chris on 5/15/2015.
 */
public class ResponseUtil {
    public static void validateHttpResponse(HttpApiResponse httpResponse) throws MFException {
        if (httpResponse.getBytes() == null || httpResponse.getBytes().length == 0) {
            throw new MFException("Server gave back a null response");
        }

        if (httpResponse.getHeaderFields() == null || httpResponse.getHeaderFields().isEmpty()) {
            throw new MFException("Server gave back null response headers");
        }

        if (httpResponse.getStatus() == 0) {
            throw new MFException("Server gave back no response status");
        }
    }

    public static <T extends ApiResponse> T makeApiResponseFromHttpResponse(HttpApiResponse httpResponse, Class<T> classOfT) {
        byte[] responseBytes = httpResponse.getBytes();
        String responseString = new String(responseBytes);
        return new Gson().fromJson(getResponseStringForGson(responseString), classOfT);
    }

    public static String getResponseStringForGson(String response) {
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
