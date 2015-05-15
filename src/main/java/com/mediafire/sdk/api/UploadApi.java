package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiRequest;

import java.util.Map;

public class UploadApi {

    private UploadApi() {
        // no instantiation, utility class
    }

    public static UploadCheckResponse check(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/upload/check.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, UploadCheckResponse.class);
    }

    public static UploadInstantResponse instant(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/upload/instant.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, UploadInstantResponse.class);
    }
    public static UploadPollUploadResponse pollUpload(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/upload/poll_upload.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, UploadPollUploadResponse.class);
    }

    public static UploadResumableResponse resumable(MediaFire mediaFire, Map<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/upload/resumable.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, UploadResumableResponse.class);
    }

    public static UploadUpdateResponse update(MediaFire mediaFire, Map<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/upload/update.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, UploadUpdateResponse.class);
    }

    public static UploadUpdateResponse simple(MediaFire mediaFire, Map<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/upload/simple.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, UploadUpdateResponse.class);
    }
}
