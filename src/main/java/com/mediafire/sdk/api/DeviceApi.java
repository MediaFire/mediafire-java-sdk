package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class DeviceApi {
    
    private DeviceApi() {
        // no instantiation, utility class
    }

    public static DeviceGetChangesResponse getChanges(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_changes.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceGetChangesResponse.class);
    }

    public static DeviceGetStatusResponse getStatus(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_status.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceGetStatusResponse.class);
    }
    
    public static DeviceEmptyTrashResponse emptyTrash(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/empty_trash.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceEmptyTrashResponse.class);
    }

    public static DeviceFollowResourceResponse followResource(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/follow_resource.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceFollowResourceResponse.class);
    }
    
    public static DeviceGetForeignChangesResponse getForeignChanges(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_foreign_changes.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceGetForeignChangesResponse.class);
    }
    
    public static DeviceGetForeignResourcesResponse getForeignResources(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_foreign_resources.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceGetForeignResourcesResponse.class);
    }
    
    public static DeviceGetPatchResponse getPatch(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_patch.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceGetPatchResponse.class);
    }
    
    public static DeviceGetResourceSharesResponse getResourceShares(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_resource_shares.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceGetResourceSharesResponse.class);
    }
    
    public static DeviceGetTrashResponse getTrash(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_trash.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceGetTrashResponse.class);
    }
    
    public static DeviceGetUpdatesResponse getUpdates(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_updates.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceGetUpdatesResponse.class);
    }
    
    public static DeviceGetUserSharesResponse getUserShares(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/get_user_shares.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceGetUserSharesResponse.class);
    }
    
    public static DeviceRequestForeignResource requestForeignResource(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/request_foreign_resource.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceRequestForeignResource.class);
    }
    
    public static DeviceShareResourcesResponse shareResources(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/share_resources.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceShareResourcesResponse.class);
    }

    public static DeviceSetForeignResourceSync setForeignResourceSync(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/set_foreign_resource_sync.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceSetForeignResourceSync.class);
    }
    
    public static DeviceToggleForeignResourceSync toggleForeignResourceSync(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/toggle_foreign_resource_sync.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceToggleForeignResourceSync.class);
    }
    
    public static DeviceUnfollowResourceResponse unfollowResource(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/unfollow_resource.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceUnfollowResourceResponse.class);
    }
    
    
    public static DeviceUnshareResourcesResponse unshareResources(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/device/unshare_resources.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, DeviceUnshareResourcesResponse.class);
    }
}
