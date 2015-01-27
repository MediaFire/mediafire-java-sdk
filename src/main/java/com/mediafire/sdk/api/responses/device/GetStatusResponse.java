package com.mediafire.sdk.api.responses.device;

import com.mediafire.sdk.api.responses.ApiResponse;

public class GetStatusResponse extends ApiResponse {
    private String async_jobs_in_progress;
    private String device_revision;

    public long getRevision() {
        if (device_revision == null || device_revision.isEmpty()) {
            device_revision = "0";
        }
        return Long.valueOf(device_revision);
    }

    public boolean isAsyncJobInProgress() {
        if (async_jobs_in_progress == null || async_jobs_in_progress.isEmpty()) {
            async_jobs_in_progress = "no";
        }

        return "yes".equalsIgnoreCase(async_jobs_in_progress);
    }
}
