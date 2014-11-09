package com.mediafire.sdk.clients.system;

import com.mediafire.sdk.clients.ClientHelper;
import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.*;

/**
 * Created by jondh on 11/4/14.
 */
public class SystemClient extends PathSpecificApiClient {

    private final HostObject mHost;
    private final InstructionsObject mInstructions;

    public SystemClient(ClientHelper clientHelper, HttpWorkerInterface httpWorkerInterface, String apiVersion) {
        super(clientHelper, httpWorkerInterface, apiVersion);
        // init host object
        mHost = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructions = new InstructionsObject(BorrowTokenType.NONE, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, true);
    }

    public Result getInfo() {
        ApiObject apiObject = new ApiObject("system", "get_info.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        return doRequestJson(request);
    }
}
