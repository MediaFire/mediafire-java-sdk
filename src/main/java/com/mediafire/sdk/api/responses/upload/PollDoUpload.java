package com.mediafire.sdk.api.responses.upload;

/**
* Created by Chris on 2/17/2015.
*/
public class PollDoUpload extends DoUpload {
    private int status;
    private String description;
    private int fileerror;
    private String quickkey;
    private long size;
    private long revision;
    private String created;
    private String filename;
    private String hash;
    private ResumableUpload resumable_upload;

    public int getStatusCode() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public int getFileErrorCode() {
        return fileerror;
    }

    public String getQuickKey() {
        return quickkey;
    }

    public long getSize() {
        return size;
    }

    public long getRevision() {
        return revision;
    }

    public String getCreated() {
        return created;
    }

    public String getFilename() {
        return filename;
    }

    public String getHash() {
        return hash;
    }

    public ResumableUpload getResumableUpload() {
        return resumable_upload;
    }
}
