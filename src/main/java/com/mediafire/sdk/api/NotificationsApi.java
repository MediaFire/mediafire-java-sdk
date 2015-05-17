package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.NotificationsGetCacheResponse;
import com.mediafire.sdk.api.responses.NotificationsPeekCacheResponse;
import com.mediafire.sdk.api.responses.NotificationsSendMessageResponse;
import com.mediafire.sdk.api.responses.NotificationsSendNotificationResponse;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.Map;

/**
 * Created by Chris on 1/19/2015.
 */
public class NotificationsApi {

    private NotificationsApi() {
        // no instantiation, utility class only
    }

    public static NotificationsGetCacheResponse getCache(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/notifications/get_cache.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, NotificationsGetCacheResponse.class);
    }

    public static NotificationsPeekCacheResponse peekCache(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/notifications/peek_cache.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, NotificationsPeekCacheResponse.class);
    }

    public static NotificationsSendMessageResponse sendMessage(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/notifications/send_message.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, NotificationsSendMessageResponse.class);
    }

    public static NotificationsSendNotificationResponse sendNotification(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/notifications/send_notification.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, NotificationsSendNotificationResponse.class);
    }
}
