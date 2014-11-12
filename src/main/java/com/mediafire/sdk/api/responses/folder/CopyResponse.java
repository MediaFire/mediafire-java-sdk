package com.mediafire.sdk.api.responses.folder;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.LinkedList;
import java.util.List;

public class CopyResponse extends ApiResponse {
    private String asynchronous;

    private List<String> new_folderkeys;
    private String device_revision;

    public boolean isAsynchronous() {
        if (this.asynchronous == null) {
            this.asynchronous = "no";
        }

        return !"no".equalsIgnoreCase(this.asynchronous);
    }

    public List<String> getNewFolderKeys() {
        if (this.new_folderkeys == null) {
            this.new_folderkeys = new LinkedList<String>();
        }
        return this.new_folderkeys;
    }

    public int getDeviceRevision() {
        if (this.device_revision == null) {
            this.device_revision = "0";
        }
        return Integer.valueOf(this.device_revision);
    }
}
