package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.LinkedHashMap;

public class DeviceApi {
    
    private DeviceApi() {
        // no instantiation, utility class
    }

    public static <T extends ApiResponse> T getChanges(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_changes.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T getStatus(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_status.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T emptyTrash(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/empty_trash.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T followResource(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/follow_resource.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T getForeignChanges(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_foreign_changes.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T getForeignResources(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_foreign_resources.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T getPatch(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_patch.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T getResourceShares(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_resource_shares.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T getTrash(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_trash.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T getUpdates(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_updates.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T getUserShares(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_user_shares.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T requestForeignResource(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/request_foreign_resource.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T shareResources(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/share_resources.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }

    public static <T extends ApiResponse> T setForeignResourceSync(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/set_foreign_resource_sync.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T toggleForeignResourceSync(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/toggle_foreign_resource_sync.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    public static <T extends ApiResponse> T unfollowResource(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/unfollow_resource.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
    
    
    public static <T extends ApiResponse> T unshareResources(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion, Class<T> classOfT) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/unshare_resources.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, classOfT);
    }
}
