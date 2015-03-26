package com.mediafire.sdk.api.responses.upload;

import com.mediafire.sdk.api.responses.ApiResponse;

public class InstantResponse extends ApiResponse {
    private String quickkey;
    private String filename;
    private String device_revision;
    private Revision newrevision;
    private Revision newfolderrevision;

    public String getQuickkey() {
        return quickkey;
    }

    public String getDeviceRevision() {
        return device_revision;
    }

    public Revision getNewRevision() {
        return newrevision;
    }

    public Revision getNewFolderRevision() {
        return newfolderrevision;
    }

    public String getFileName() {
        return filename;
    }
}

