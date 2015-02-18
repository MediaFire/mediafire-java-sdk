package com.mediafire.sdk.api.responses.upload;

import com.mediafire.sdk.api.responses.ApiResponse;

public class InstantResponse extends ApiResponse {
    private String quickkey;
    private String filename;
    private long device_revision;
    private Revision newrevision;
    private Revision newfolderrevision;

    public class Revision {
        private long revision;
        private long epoch;

        public long getRevision() {
            return revision;
        }

        public long getEpoch() {
            return epoch;
        }
    }

    public String getQuickkey() {
        return quickkey;
    }

    public long getDeviceRevision() {
        return device_revision;
    }

    public Revision getNewRevision() {
        if (newrevision == null) {
            return new Revision();
        }
        return newrevision;
    }

    public Revision getNewFolderRevision() {
        if (newfolderrevision == null) {
            return new Revision();
        }
        return newfolderrevision;
    }

    public String getFileName() {
        return filename;
    }
}

