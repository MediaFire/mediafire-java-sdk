package com.mediafire.sdk.clients.upload;

import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.*;

/**
 * Created by jondh on 11/4/14.
 */
public class UploadClient extends PathSpecificApiClient {

    private final HostObject mHost;
    private final InstructionsObject mInstructions;

    public UploadClient(Configuration configuration, String apiVersion) {
        super(configuration, apiVersion);
        // init host object
        mHost = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructions = new InstructionsObject(BorrowTokenType.UPLOAD, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, true);
    }

    public Result check() {
        ApiObject apiObject = new ApiObject("system", "get_info.php");

        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        return doRequestJson(request);
    }

    public Result instant() {
        ApiObject apiObject = new ApiObject("system", "get_info.php");

        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        return doRequestJson(request);
    }

    public Result pollUpload() {
        ApiObject apiObject = new ApiObject("system", "get_info.php");
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.NONE, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, true);

        Request request = new Request(mHost, apiObject, instructionsObject, mVersionObject);

        return doRequestJson(request);
    }

    public Result resumable() {
        ApiObject apiObject = new ApiObject("system", "get_info.php");
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.UPLOAD, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, false);

        Request request = new Request(mHost, apiObject, instructionsObject, mVersionObject);

        return doRequestJson(request);
    }
}
