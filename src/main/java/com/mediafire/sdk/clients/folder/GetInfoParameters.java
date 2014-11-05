package com.mediafire.sdk.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class GetInfoParameters {
    public String mFolderKey;
    public int mDeviceId;
    public String mDetails;

    public GetInfoParameters() { }

    public GetInfoParameters folderKey(String folderKey) {
        if (folderKey == null) {
            return this;
        }

        mFolderKey = folderKey;
        return this;
    }

    public GetInfoParameters deviceId(int deviceId) {
        mDeviceId = deviceId;
        return this;
    }

    public GetInfoParameters details(String details) {
        if (details == null) {
            return this;
        }

        mDetails = details;
        return this;
    }
}
