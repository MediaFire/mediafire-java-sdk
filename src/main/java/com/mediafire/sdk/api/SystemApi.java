package com.mediafire.sdk.api;

import com.mediafire.sdk.MediaFireClient;
import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.LinkedHashMap;

/**
 * http://www.mediafire.com/developers/core_api/1.3/getting_started/
 * @see <a href="http://www.mediafire.com/developers/core_api/1.3/getting_started/">MediaFire Developer Portal</a>
 */
public class SystemApi {

    private SystemApi() {
        // no instantiation, utility class
    }

    /**
     * Returns all the configuration data about the MediaFire system.
     *
     * @param mediaFire an instance of MediaFire which has a session in progress
     * @param requestParams a LinkedHashMap of required and optional parameters
     * @param apiVersion version of the api to call e.g. 1.0, 1.1, 1.2
     * @param classOfT the .class file passed which will be used to parse the api JSON response using Gson (must extend ApiResponse)
     * @return an instance of {@param classOfT}
     * @throws com.mediafire.sdk.MediaFireException if an exception occurred
     * @throws  if there was an api error
     */
    public static <T extends ApiResponse> T getInfo(MediaFireClient mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MediaFireException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/system/get_info.php", requestParams, false);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
}
