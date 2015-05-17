package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.SystemGetInfoResponse;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class SystemApi {

    private SystemApi() {
        // no instantiation, utility class
    }

    public static SystemGetInfoResponse getInfo(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/system/get_info.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, SystemGetInfoResponse.class);
    }
}
