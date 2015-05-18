package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.LinkedHashMap;

public class ContactApi {

    private ContactApi() {
        // no instantiation, utility class
    }

    public static <T extends ApiResponse> T add(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/add.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T delete(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/delete.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T fetch(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/fetch.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T getAvatar(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/get_avatar.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T getSources(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/get_sources.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T setAvatar(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/set_avatar.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T summary(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/summary.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
}
