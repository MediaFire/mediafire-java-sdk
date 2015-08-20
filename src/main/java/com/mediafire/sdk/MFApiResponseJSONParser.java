package com.mediafire.sdk;

import com.google.gson.*;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.util.TextUtils;

public class MFApiResponseJSONParser implements MediaFireApiResponseParser {

    public MFApiResponseJSONParser() {

    }

    @Override
    public <T extends ApiResponse> T parseResponse(byte[] responseBytes, Class<T> classOfT) throws MediaFireException {
        if (responseBytes == null || responseBytes.length == 0) {
            return null;
        }

        try {
            String responseString = new String(responseBytes);
            return new Gson().fromJson(getResponseString(responseString), classOfT);
        } catch (JsonSyntaxException e) {
            throw new MediaFireException("Malformed Json response", e);
        }
    }

    private String getResponseString(String response) {
        if (TextUtils.isEmpty(response)) {
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
