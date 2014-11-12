package com.mediafire.sdk.api.responses.file;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.LinkedList;
import java.util.List;

public class CopyResponse extends ApiResponse {

    private int skipped_count;
    private int other_count;
    private int device_revision;
    private List<String> new_quickkeys;


    public int getSkippedCount() {
        return skipped_count;
    }

    public int getOtherCount() {
        return other_count;
    }

    public int getDeviceRevision() {
        return device_revision;
    }

    public List<String> getNewQuickKeys() {
        if (new_quickkeys == null) {
            new_quickkeys = new LinkedList<String>();
        }
        return new_quickkeys;
    }
}
