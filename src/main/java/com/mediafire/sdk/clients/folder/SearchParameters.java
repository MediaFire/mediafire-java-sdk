package com.mediafire.sdk.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class SearchParameters {
    public String mFolderKey;
    public String mFilter;
    public String mDeviceId;
    public String mSearchAll;
    public String mDetails;

    public SearchParameters() { }

    public SearchParameters folderKey(String folderKey) {
        if (folderKey == null) {
            return this;
        }

        mFolderKey = folderKey;
        return this;
    }

    public SearchParameters filter(String filter) {
        if (filter == null) {
            return this;
        }

        mFilter = filter;
        return this;
    }

    public SearchParameters deviceId(String deviceId) {
        if (deviceId == null) {
            return this;
        }

        mDeviceId = deviceId;
        return this;
    }

    public SearchParameters searchAll(boolean searchAll) {
        mSearchAll = searchAll ? "yes" : "no";
        return this;
    }

    public SearchParameters details(String details) {
        if (details == null) {
            return this;
        }

        mDetails = details;
        return this;
    }
}
