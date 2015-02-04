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
    
    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient mApiClient;
    private final Instructions mInstructions;

    public DeviceClient(HttpHandler httpInterface, TokenManager tokenManager) {
        mApiRequestGenerator = new ApiRequestGenerator();

        mInstructions = new UseSessionToken(tokenManager);
        mApiClient = new ApiClient(httpInterface);
    }

    public Result getChanges(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_changes.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getStatus(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_status.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result emptyTrash(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/empty_trash.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result followResource(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/follow_resource.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getForeignChanges(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_foreign_changes.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getForeignResources(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_foreign_resources.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getPatch(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_patch.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getResourceShares(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_resource_shares.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getTrash(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_trash.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getUpdates(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_updates.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result getUserShares(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_user_shares.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result requestForeignResource(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/request_foreign_resource.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result shareResources(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/share_resources.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result setForeignResourceSync(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/set_foreign_resource_sync.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result toggleForeignResourceSync(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/toggle_foreign_resource_sync.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result unfollowResource(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/unfollow_resource.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    public Result unshareResources(Map<String, Object> requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/unshare_resources.php");

        if (requestParams != null) {
            addParams(request, requestParams);
        }

        return mApiClient.doRequest(mInstructions, request);
    }

    private void addParams(Request request, Map<String, Object> requestParams) {
        for (String key : requestParams.keySet()) {
            Object value = requestParams.get(key);
            request.addQueryParameter(key, value);
        }
    }
}
