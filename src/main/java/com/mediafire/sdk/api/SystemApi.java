package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.SystemGetInfoResponse;
import com.mediafire.sdk.requests.ApiRequest;

import java.util.Map;

public class SystemApi {

    private SystemApi() {
        // no instantiation, utility class
    }

    public static SystemGetInfoResponse getInfo(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/system/get_info.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, SystemGetInfoResponse.class);
    }
}
