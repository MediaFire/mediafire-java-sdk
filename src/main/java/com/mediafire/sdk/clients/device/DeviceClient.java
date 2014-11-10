package com.mediafire.sdk.clients.device;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ClientHelperApi;
import com.mediafire.sdk.clients.ApiRequestGenerator;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by jondh on 11/4/14.
 */
public class DeviceClient {
    private static final String PARAM_REVISION = "revision";
    private static final String PARAM_DEVICE_ID = "device_id";
    private static final String PARAM_SIMPLE_REPORT = "simple_report";
    
    private final ApiRequestGenerator mApiRequestGenerator;
    private final HttpWorkerInterface mHttpWorker;
    private final SessionTokenManagerInterface mSessionTokenManager;

    public DeviceClient(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManagerInterface, String apiVersion) {
        mHttpWorker = httpWorkerInterface;
        mSessionTokenManager = sessionTokenManagerInterface;
        mApiRequestGenerator = new ApiRequestGenerator(apiVersion);
    }

    public DeviceClient(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManagerInterface) {
        this(httpWorkerInterface, sessionTokenManagerInterface, ApiVersion.VERSION_CURRENT);
    }

    public Result getChanges(String revision) {
        return getChanges(revision, null);
    }

    public Result getChanges(String revision, String deviceId) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_changes.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_REVISION, revision);
        request.addQueryParameter(PARAM_DEVICE_ID, deviceId);

        return apiClient.doRequest(request);
    }

    public Result getStatus(){
        return getStatus(null);
    }

    public Result getStatus(GetStatusParameters getStatusParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("device/get_status.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        if(getStatusParameters != null) {
            request.addQueryParameter(PARAM_SIMPLE_REPORT, getStatusParameters.mSimpleReport);
            request.addQueryParameter(PARAM_DEVICE_ID, getStatusParameters.mDeviceId);
        }

        return apiClient.doRequest(request);
    }
}
