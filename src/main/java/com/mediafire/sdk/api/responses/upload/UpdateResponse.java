package com.mediafire.sdk.api.responses.upload;

import com.mediafire.sdk.api.responses.ApiResponse;

/**
 * Created by Chris on 1/19/2015.
 */
public class UpdateResponse extends ApiResponse {
    private DoUpload doupload;

    public DoUpload getDoUpload() {
        return doupload;
    }
}
