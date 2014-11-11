package com.mediafire.sdk.clients.user;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class SetSettingsParameters {
    private int mPreviousFileVersions;
    private String mDefaultShareLinkStatus;
    private String mCollectMetaData;

    private SetSettingsParameters(Builder builder) {
        mPreviousFileVersions = builder.mPreviousFileVersions;
        mDefaultShareLinkStatus = builder.mDefaultShareLinkStatus;
        mCollectMetaData = builder.mCollectMetaData;
    }

    public int getPreviousFileVersions() {
        return mPreviousFileVersions;
    }

    public String getDefaultShareLinkStatus() {
        return mDefaultShareLinkStatus;
    }

    public String getCollectMetaData() {
        return mCollectMetaData;
    }

    public static class Builder {
        private static final int DEFAULT_PREVIOUS_FILE_VERSIONS = 10;
        private static final String DEFAULT_SHARE_LINK_STATUS = "inherit";
        private static final String DEFAULT_COLLECT_META_DATA = "no";

        private int mPreviousFileVersions = DEFAULT_PREVIOUS_FILE_VERSIONS;
        private String mDefaultShareLinkStatus = DEFAULT_SHARE_LINK_STATUS;
        private String mCollectMetaData = DEFAULT_COLLECT_META_DATA;

        public Builder() { }

        public Builder previousFileVersionsToKeep(int numOfOldVersionsToKeep) {
            if (numOfOldVersionsToKeep > 10 || numOfOldVersionsToKeep < 0) {
                return this;
            }

            mPreviousFileVersions = numOfOldVersionsToKeep;
            return this;
        }

        public Builder defaultShareLinkStatus(DefaultShareLinkStatus defaultShareLinkStatus) {
            if (defaultShareLinkStatus == null) {
                return this;
            }

            switch (defaultShareLinkStatus) {
                case ENABLED:
                    mDefaultShareLinkStatus = "enabled";
                    break;
                case DISABLED:
                    mDefaultShareLinkStatus = "disabled";
                    break;
                case INHERIT:
                    break;
            }

            return this;
        }

        public Builder collectMetaData(boolean collectMetaData) {
            if (collectMetaData) {
                mCollectMetaData = "yes";
            } else {
                mCollectMetaData = "no";
            }
            return this;
        }

        public SetSettingsParameters build() {
            return new SetSettingsParameters(this);
        }

        public enum DefaultShareLinkStatus {
            ENABLED, DISABLED, INHERIT
        }
    }
}
