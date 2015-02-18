package com.mediafire.sdk.api.responses.upload;

import com.mediafire.sdk.api.responses.ApiResponse;

public class PollResponse extends ApiResponse {
    private PollDoUpload doupload;

    public PollDoUpload getDoUpload() {
        return doupload;
    }

}
