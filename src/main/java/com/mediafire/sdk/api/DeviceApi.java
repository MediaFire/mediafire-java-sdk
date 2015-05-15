package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiRequest;

import java.util.Map;

public class DeviceApi {
    
    private DeviceApi() {
        // no instantiation, utility class
    }

    public static DeviceGetChangesResponse getChanges(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/get_changes.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceGetChangesResponse.class);
    }

    public static DeviceGetStatusResponse getStatus(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/get_status.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceGetStatusResponse.class);
    }
    
    public static DeviceEmptyTrashResponse emptyTrash(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/empty_trash.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceEmptyTrashResponse.class);
    }

    public static DeviceFollowResourceResponse followResource(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/follow_resource.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceFollowResourceResponse.class);
    }
    
    public static DeviceGetForeignChangesResponse getForeignChanges(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/get_foreign_changes.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceGetForeignChangesResponse.class);
    }
    
    public static DeviceGetForeignResourcesResponse getForeignResources(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/get_foreign_resources.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceGetForeignResourcesResponse.class);
    }
    
    public static DeviceGetPatchResponse getPatch(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/get_patch.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceGetPatchResponse.class);
    }
    
    public static DeviceGetResourceSharesResponse getResourceShares(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/get_resource_shares.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceGetResourceSharesResponse.class);
    }
    
    public static DeviceGetTrashResponse getTrash(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/get_trash.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceGetTrashResponse.class);
    }
    
    public static DeviceGetUpdatesResponse getUpdates(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/get_updates.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceGetUpdatesResponse.class);
    }
    
    public static DeviceGetUserSharesResponse getUserShares(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/get_user_shares.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceGetUserSharesResponse.class);
    }
    
    public static DeviceRequestForeignResource requestForeignResource(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/request_foreign_resource.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceRequestForeignResource.class);
    }
    
    public static DeviceShareResourcesResponse shareResources(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/share_resources.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceShareResourcesResponse.class);
    }

    public static DeviceSetForeignResourceSync setForeignResourceSync(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/set_foreign_resource_sync.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceSetForeignResourceSync.class);
    }
    
    public static DeviceToggleForeignResourceSync toggleForeignResourceSync(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/toggle_foreign_resource_sync.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceToggleForeignResourceSync.class);
    }
    
    public static DeviceUnfollowResourceResponse unfollowResource(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/unfollow_resource.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceUnfollowResourceResponse.class);
    }
    
    
    public static DeviceUnshareResourcesResponse unshareResources(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/device/unshare_resources.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, DeviceUnshareResourcesResponse.class);
    }
}
