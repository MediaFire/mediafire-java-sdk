package com.mediafire.sdk.api.responses.upload;

/**
* Created by Chris on 2/17/2015.
*/
public class PollDoUpload extends DoUpload {
    private String status;
    private String description;
    private String fileerror;
    private String quickkey;
    private String size;
    private String revision;
    private String created;
    private String filename;
    private String hash;

    public String getStatusCode() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getFileErrorCode() {
        return fileerror;
    }

    public String getQuickKey() {
        return quickkey;
    }

    public String getSize() {
        return size;
    }

    public String getRevision() {
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
}
