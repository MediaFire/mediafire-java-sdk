package com.mediafire.sdk.uploader.calls;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 11/13/2014.
 */
public class CallHelper {

    private CallHelper() { }

    public static <ResponseClass extends ApiResponse> ResponseClass getResponseObject(byte[] responseBytes, Class<ResponseClass> responseClass) {
        if (responseBytes == null) {
            return null;
        }

        String responseString = new String(responseBytes);

        if (responseString == null) {
            return null;
        }
        return new Gson().fromJson(getResponseStringForGson(responseString), responseClass);
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

    public static boolean resultInvalid(Result result) {
        if (result == null) {
            return true;
        }

        if (result.getResponse() == null) {
            return true;
        }

        if (result.getRequest() == null) {
            return true;
        }

        if (result.getResponse().getBytes() == null) {
            return true;
        }

        if (result.getResponse().getBytes().length == 0) {
            return true;
        }

        if (result.getResponse().getHeaderFields() == null) {
            return true;
        }

        Response response = result.getResponse();

        Map<String, List<String>> responseHeaders = response.getHeaderFields();

        if (responseHeaders == null) {
            return true;
        }

        List<String> contentTypeList = responseHeaders.get("Content-Type");

        if (contentTypeList == null) {
            return true;
        }

        boolean contentTypeIsJson = contentTypeList.contains("application/json");

        if (!contentTypeIsJson) {
            return true;
        }

        return false;
    }
}
