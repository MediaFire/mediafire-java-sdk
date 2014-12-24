package com.mediafire.sdk.api.responses.upload;

/**
* Created by Chris on 12/23/2014.
*/
public class DoUpload {
    private String result;
    private String key;

    public int getResultCode() {
        if (result == null || result.isEmpty()) {
            result = "0";
        }
        return Integer.parseInt(result);
    }

    public int getResult() {
        if (result == null || result.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(result);
    }

    public String getPollUploadKey() {
        if (key == null) {
            return "";
        }
        return key;
    }
}
