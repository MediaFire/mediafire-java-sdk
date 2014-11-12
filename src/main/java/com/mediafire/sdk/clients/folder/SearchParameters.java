package com.mediafire.sdk.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class SearchParameters {
    private final String mSearchText;
    private final String mFolderKey;
    private final String mFilter;
    private final String mDeviceId;
    private final String mSearchAll;
    private final String mDetails;

    private SearchParameters(Builder builder) {
        mSearchText = builder.mSearchText;
        mFolderKey = builder.mFolderKey;
        mFilter = builder.mFilter;
        mDeviceId = builder.mDeviceId;
        mSearchAll = builder.mSearchAll;
        mDetails = builder.mDetails;
    }

    public String getSearchText() {
        return mSearchText;
    }

    public String getFolderKey() {
        return mFolderKey;
    }

    public String getFilter() {
        return mFilter;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public String getSearchAll() {
        return mSearchAll;
    }

    public String getDetails() {
        return mDetails;
    }

    public static class Builder {
        private final String mSearchText;
        private String mFolderKey;
        private String mFilter;
        private String mDeviceId;
        private String mSearchAll;
        private String mDetails;

        public Builder(String searchText) { 
            mSearchText = searchText;
        }

        public Builder folderKey(String folderKey) {
            if (folderKey == null) {
                return this;
            }

            mFolderKey = folderKey;
            return this;
        }

        public Builder filter(String filter) {
            if (filter == null) {
                return this;
            }

            mFilter = filter;
            return this;
        }

        public Builder deviceId(String deviceId) {
            if (deviceId == null) {
                return this;
            }

            mDeviceId = deviceId;
            return this;
        }

        public Builder searchAll(boolean searchAll) {
            mSearchAll = searchAll ? "yes" : "no";
            return this;
        }

        public Builder details(String details) {
            if (details == null) {
                return this;
            }

            mDetails = details;
            return this;
        }
        
        public SearchParameters build() {
            return new SearchParameters(this);
        }
    }
}
