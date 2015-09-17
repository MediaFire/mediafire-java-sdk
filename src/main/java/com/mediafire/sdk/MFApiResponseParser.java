package com.mediafire.sdk;

import com.google.gson.*;
import com.mediafire.sdk.response_models.MediaFireApiResponse;
import com.mediafire.sdk.util.TextUtils;

public class MFApiResponseParser implements MediaFireApiResponseParser {

    public MFApiResponseParser() {

    }

    @Override
    public <T extends MediaFireApiResponse> T parseResponse(MediaFireHttpResponse response, Class<T> classOfT) throws MediaFireException {
        if (response == null) {
            throw new MediaFireException("MediaFireHttpResponse was null while trying to parse an ApiResponse", MediaFireException.RESPONSE_PARSER_RECEIVED_NULL_OR_EMPTY_RESPONSE);
        }
        byte[] responseBytes = response.getBody();

        if (responseBytes == null || responseBytes.length == 0) {
            throw new MediaFireException("MediaFireHttpResponse was null while trying to parse an ApiResponse", MediaFireException.RESPONSE_PARSER_RECEIVED_NULL_OR_EMPTY_RESPONSE);
        }

        try {
            String byteResponseAsString = new String(responseBytes);
            String responseString = getResponseString(byteResponseAsString);
            if (TextUtils.isEmpty(byteResponseAsString)) {
                throw new MediaFireException("response string was null or empty and could not be parsed", MediaFireException.RESPONSE_PARSER_RECEIVED_NULL_OR_EMPTY_RESPONSE);
            }
            return new Gson().fromJson(responseString, classOfT);
        } catch (JsonSyntaxException e) {
            throw new MediaFireException("Malformed Json response", MediaFireException.RESPONSE_PARSER_RECEIVED_MALFORMED_JSON, e);
        }
    }

    @Override
    public String getResponseFormat() {
        return "json";
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
