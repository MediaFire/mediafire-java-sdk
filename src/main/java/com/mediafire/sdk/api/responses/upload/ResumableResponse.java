package com.mediafire.sdk.api.responses.upload;

import com.mediafire.sdk.api.responses.ApiResponse;

public class ResumableResponse extends ApiResponse {
    private ResumableDoUpload doupload;
    private ResumableUpload resumable_upload;

    public ResumableDoUpload getDoUpload() {
        if (doupload == null) {
            return new ResumableDoUpload();
        }
        return doupload;
    }

    public ResumableUpload getResumableUpload() {
        if (resumable_upload == null) {
            return new ResumableUpload();
        }
        return resumable_upload;
    }

}

