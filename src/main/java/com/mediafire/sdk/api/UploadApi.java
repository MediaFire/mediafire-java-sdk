package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class UploadApi {

    private UploadApi() {
        // no instantiation, utility class
    }

    public static <T extends ApiResponse> T check(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/upload/check.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T instant(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/upload/instant.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    public static <T extends ApiResponse> T pollUpload(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/upload/poll_upload.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T resumable(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/upload/resumable.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T update(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/upload/update.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T simple(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, Map<String, Object> headerParameters, byte[] payload, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/upload/simple.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
}
