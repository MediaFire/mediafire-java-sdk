package com.mediafire.sdk.clients.user;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class SetSettingsParameters {
    private static final int DEFAULT_PREVIOUS_FILE_VERSIONS = 10;
    private static final String DEFAULT_SHARE_LINK_STATUS = "inherit";
    private static final String DEFAULT_COLLECT_META_DATA = "no";

    public int mPreviousFileVersions = DEFAULT_PREVIOUS_FILE_VERSIONS;
    public String mDefaultShareLinkStatus = DEFAULT_SHARE_LINK_STATUS;
    public String mCollectMetaData = DEFAULT_COLLECT_META_DATA;

    public SetSettingsParameters(int numOfOldVersionsToKeep) {
        if (numOfOldVersionsToKeep > 0 && numOfOldVersionsToKeep < 10) {
            mPreviousFileVersions = numOfOldVersionsToKeep;
        }
    }

    public SetSettingsParameters(DefaultShareLinkStatus defaultShareLinkStatus) {
        if (defaultShareLinkStatus == null) {
            defaultShareLinkStatus = DefaultShareLinkStatus.INHERIT;
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
    }

    public SetSettingsParameters previousFileVersionsToKeep(int numOfOldVersionsToKeep) {
        if (numOfOldVersionsToKeep > 10 || numOfOldVersionsToKeep < 0) {
            return this;
        }

        mPreviousFileVersions = numOfOldVersionsToKeep;
        return this;
    }

    public SetSettingsParameters defaultShareLinkStatus(DefaultShareLinkStatus defaultShareLinkStatus) {
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

    public SetSettingsParameters collectMetaData(boolean collectMetaData) {
        if (collectMetaData) {
            mCollectMetaData = "yes";
        } else {
            mCollectMetaData = "no";
        }
        return this;
    }

    public enum DefaultShareLinkStatus {
        ENABLED, DISABLED, INHERIT
    }
}
