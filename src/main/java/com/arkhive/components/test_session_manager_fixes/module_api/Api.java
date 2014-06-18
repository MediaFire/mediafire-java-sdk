package com.arkhive.components.test_session_manager_fixes.module_api;

import com.arkhive.components.test_session_manager_fixes.MediaFire;
import com.arkhive.components.test_session_manager_fixes.module_api_descriptor.ApiRequestHttpPostProcessor;
import com.arkhive.components.test_session_manager_fixes.module_api_descriptor.ApiRequestHttpPreProcessor;
import com.arkhive.components.test_session_manager_fixes.module_api_descriptor.ApiRequestObject;
import com.arkhive.components.test_session_manager_fixes.module_api_descriptor.interfaces.ApiRequestRunnableCallback;
import com.arkhive.components.test_session_manager_fixes.module_api_descriptor.requests.BlockingApiGetRequest;
import com.arkhive.components.test_session_manager_fixes.module_api_descriptor.requests.RunnableApiGetRequest;
import com.arkhive.components.test_session_manager_fixes.module_http_processor.HttpPeriProcessor;
import com.arkhive.components.test_session_manager_fixes.module_token_farm.TokenFarm;
import com.arkhive.components.test_session_manager_fixes.module_token_farm.interfaces.TokenFarmDistributor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

/**
 * Created by  on 6/17/2014.
 */
public class Api {
    private static final String FAIL_RES = "{\"response\":{\"message\":\"Unknown API error\",\"result\":\"Error\"}}";
    private static HttpPeriProcessor httpPeriProcessor;
    private static TokenFarmDistributor tokenFarm;
    public File file;
    public Folder folder;
    public User user;
    public System system;
    public Device device;
    public Upload upload;

    public Api(TokenFarm tokenFarm, HttpPeriProcessor httpPeriProcessor) {
        this.tokenFarm = tokenFarm;
        this.httpPeriProcessor = httpPeriProcessor;
        file = new File();
        folder = new Folder();
        user = new User();
        system = new System();
        device = new Device();
        upload = new Upload();
    }

    static BlockingApiGetRequest createBlockingApiGetRequest(ApiRequestObject apiRequestObject) {
        return new BlockingApiGetRequest(new ApiRequestHttpPreProcessor(), new ApiRequestHttpPostProcessor(), tokenFarm, httpPeriProcessor, apiRequestObject);
    }

    static RunnableApiGetRequest createApiGetRequestRunnable(ApiRequestRunnableCallback callback, ApiRequestObject apiRequestObject) {
        return new RunnableApiGetRequest(callback, new ApiRequestHttpPreProcessor(), new ApiRequestHttpPostProcessor(), tokenFarm, httpPeriProcessor, apiRequestObject);
    }



    /**
     * Transform a string into a JsonElement.
     * <p/>
     * All response strings returned from the web api are wrapped in response json element.
     * This method strips the wrapper element, and converts the remaining element into a JsonElement via GSON.
     *
     * @param response A response string from a web API call.
     * @return The JsonElement created from the response string.
     */
    static JsonElement getResponseElement(String response) {
        if (response.length() == 0 || response.isEmpty() || response == null) {
            response = FAIL_RES;
        }
        JsonElement returnJson = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(response);
        if (rootElement.isJsonObject()) {
            JsonElement jsonResult = rootElement.getAsJsonObject().get("response");
            if (jsonResult.isJsonObject()) {
                returnJson = jsonResult.getAsJsonObject();
            }
        }
        return returnJson;
    }

    /**
     * converts a String received from JSON format into a response String.
     *
     * @param response - the response received in JSON format
     * @return the response received which can then be parsed into a specific format as per Gson.fromJson()
     */
    static String getResponseString(String response) {
        if (response.length() == 0 || response.isEmpty() || response == null) {
            response = FAIL_RES;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response);
        if (element.isJsonObject()) {
            JsonObject jsonResponse = element.getAsJsonObject().get("response").getAsJsonObject();
            return jsonResponse.toString();
        } else {
            return FAIL_RES;
        }
    }
}
