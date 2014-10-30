package com.mediafire.sdk.clients.user;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class SetSettingsParameters {
    int DEFAULT_PREVIOUS_FILE_VERSIONS = 10;
    String DEFAULT_SHARE_LINK_STATUS = "inherit";

    public int mPreviousFileVersions = DEFAULT_PREVIOUS_FILE_VERSIONS;
    public String mDefaultShareLinkStatus = DEFAULT_SHARE_LINK_STATUS;

    public SetSettingsParameters() { }

    public SetSettingsParameters previousFileVersionsToKeep(int numOfOldVersionsToKeep) {
        if (numOfOldVersionsToKeep > 10 || numOfOldVersionsToKeep < 0) {
            return this;
        }

        mPreviousFileVersions = numOfOldVersionsToKeep;
        return this;
    }

    public SetSettingsParameters defaultShareLinkStatus(DefaultShareLinkStatus status) {
        if (status == null) {
            return this;
        }

        switch (status) {
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

    public enum DefaultShareLinkStatus {
        ENABLED, DISABLED, INHERIT
    }
}
