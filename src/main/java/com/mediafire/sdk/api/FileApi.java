package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.LinkedHashMap;

public class FileApi {

    private FileApi() {
        // no instantiation, utility class
    }

    public static <T extends ApiResponse> T getInfo(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/get_info.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T delete(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/delete.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T copy(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/copy.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T getVersion(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/get_version.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T move(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/move.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T update(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/update.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T getLinks(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/get_links.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
}
