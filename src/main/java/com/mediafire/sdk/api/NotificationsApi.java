package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.LinkedHashMap;

/**
 * Created by Chris on 1/19/2015.
 */
public class NotificationsApi {

    private NotificationsApi() {
        // no instantiation, utility class only
    }

    public static <T extends ApiResponse> T  getCache(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/notifications/get_cache.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T  peekCache(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/notifications/peek_cache.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T  sendMessage(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/notifications/send_message.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T  sendNotification(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/notifications/send_notification.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
}
