package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.LinkedHashMap;

public class SystemApi {

    private SystemApi() {
        // no instantiation, utility class
    }

    public static <T extends ApiResponse> T getInfo(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/system/get_info.php", requestParams, false);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
}
