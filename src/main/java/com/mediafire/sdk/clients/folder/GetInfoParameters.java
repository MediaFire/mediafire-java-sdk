package com.mediafire.sdk.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class GetInfoParameters {
    private String mFolderKey;
    private String mDeviceId;
    private String mDetails;

    public GetInfoParameters(Builder builder) {
        mFolderKey = builder.mFolderKey;
        mDetails = builder.mDetails;
        mDeviceId = builder.mDeviceId;
    }

    public String getFolderKey() {
        return mFolderKey;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public String getDetails() {
        return mDetails;
    }
    
    public static class Builder {
        private String mFolderKey;
        private String mDeviceId;
        private String mDetails;
        
        public Builder() { }
        
        public Builder folderKey(String folderKey) {
            if (folderKey == null) {
                return this;
            }

            mFolderKey = folderKey;
            return this;
        }

        public Builder deviceId(String deviceId) {
            mDeviceId = deviceId;
            return this;
        }

        public Builder details(String details) {
            if (details == null) {
                return this;
            }

            mDetails = details;
            return this;
        }   
        
        public GetInfoParameters build() {
            return new GetInfoParameters(this);
        }
    }

}
