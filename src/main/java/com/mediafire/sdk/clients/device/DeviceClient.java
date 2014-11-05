package com.mediafire.sdk.clients.device;

import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.*;

/**
 * Created by jondh on 11/4/14.
 */
public class DeviceClient extends PathSpecificApiClient{
    private static final String PARAM_REVISION = "revision";
    private static final String PARAM_DEVICE_ID = "device_id";
    private static final String PARAM_SIMPLE_REPORT = "simple_report";

    private final HostObject mHost;
    private final InstructionsObject mInstructions;

    public DeviceClient(Configuration configuration, String apiVersion) {
        super(configuration, apiVersion);
        // init host object
        mHost = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructions = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.V2, true);
    }

    public Result getChanges(String revision) {
        return getChanges(revision, null);
    }

    public Result getChanges(String revision, String deviceId) {
        ApiObject apiObject = new ApiObject("device", "get_changes.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_REVISION, revision);
        request.addQueryParameter(PARAM_DEVICE_ID, deviceId);

        return doRequestJson(request);
    }

    public Result getStatus(){
        return getStatus(null);
    }

    public Result getStatus(GetStatusParameters getStatusParameters) {
        ApiObject apiObject = new ApiObject("device", "get_status.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        if(getStatusParameters != null) {
            request.addQueryParameter(PARAM_SIMPLE_REPORT, getStatusParameters.mSimpleReport);
            request.addQueryParameter(PARAM_DEVICE_ID, getStatusParameters.mDeviceId);
        }

        return doRequestJson(request);
    }
}
