package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.helpers.Instructions;
import com.mediafire.sdk.api.helpers.UseSessionToken;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

import java.util.Map;

public class DeviceClient {
    
    private final ApiClient mApiClient;
    private final Instructions mInstructions;

    public DeviceClient(HttpHandler httpInterface, TokenManager tokenManager) {

        mInstructions = new UseSessionToken(tokenManager);
        mApiClient = new ApiClient(httpInterface);
    }

    public Result getChanges(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/get_changes.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result getChanges(Map<String, Object> requestParams) {
        return getChanges(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getStatus(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/get_status.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result getStatus(Map<String, Object> requestParams) {
        return getStatus(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result emptyTrash(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/empty_trash.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result emptyTrash(Map<String, Object> requestParams) {
        return emptyTrash(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result followResource(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/follow_resource.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result followResource(Map<String, Object> requestParams) {
        return followResource(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getForeignChanges(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/get_foreign_changes.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result getForeignChanges(Map<String, Object> requestParams) {
        return getForeignChanges(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getForeignResources(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/get_foreign_resources.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result getForeignResources(Map<String, Object> requestParams) {
        return getForeignResources(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getPatch(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/get_patch.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result getPatch(Map<String, Object> requestParams) {
        return getPatch(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getResourceShares(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/get_resource_shares.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result getResourceShares(Map<String, Object> requestParams) {
        return getResourceShares(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getTrash(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/get_trash.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result getTrash(Map<String, Object> requestParams) {
        return getTrash(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getUpdates(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/get_updates.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result getUpdates(Map<String, Object> requestParams) {
        return getUpdates(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result getUserShares(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/get_user_shares.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result getUserShares(Map<String, Object> requestParams) {
        return getUserShares(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result requestForeignResource(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/request_foreign_resource.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result requestForeignResource(Map<String, Object> requestParams) {
        return requestForeignResource(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result shareResources(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/share_resources.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result shareResources(Map<String, Object> requestParams) {
        return shareResources(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result setForeignResourceSync(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/set_foreign_resource_sync.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result setForeignResourceSync(Map<String, Object> requestParams) {
        return setForeignResourceSync(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result toggleForeignResourceSync(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/toggle_foreign_resource_sync.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result toggleForeignResourceSync(Map<String, Object> requestParams) {
        return toggleForeignResourceSync(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result unfollowResource(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/unfollow_resource.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result unfollowResource(Map<String, Object> requestParams) {
        return unfollowResource(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    public Result unshareResources(Map<String, Object> requestParams, String apiVersion) {
        Request request = ApiRequestGenerator.createRequestObjectFromPath("device/unshare_resources.php", apiVersion);

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }
    
    public Result unshareResources(Map<String, Object> requestParams) {
        return unshareResources(requestParams, ApiRequestGenerator.LATEST_STABLE_VERSION);
    }

    private void addParams(Request request, Map<String, Object> requestParams) {
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }
    }
}
