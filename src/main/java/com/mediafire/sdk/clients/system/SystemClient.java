package com.mediafire.sdk.clients.system;

import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.*;

/**
 * Created by jondh on 11/4/14.
 */
public class SystemClient extends PathSpecificApiClient {

    private final HostObject mHost;
    private final InstructionsObject mInstructions;

    public SystemClient(Configuration configuration, String apiVersion) {
        super(configuration, apiVersion);
        // init host object
        mHost = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructions = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.V2, true);
    }

    public Result getInfo() {
        ApiObject apiObject = new ApiObject("system", "get_info.php");
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.NONE, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, true);

        Request request = new Request(mHost, apiObject, instructionsObject, mVersionObject);

        return doRequestJson(request);
    }
}
