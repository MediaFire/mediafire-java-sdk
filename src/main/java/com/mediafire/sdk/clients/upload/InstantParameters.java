package com.mediafire.sdk.clients.upload;

import com.mediafire.sdk.uploader.uploaditem.UploadItemOptions;

/**
 * Created by jondh on 11/5/14.
 */
public class InstantParameters {
    public final String mHash;
    public final String mSize;
    public String mFilename;
    public String mQuickKey;
    public String mFolderKey;
    public String mFiledropKey;
    public String mPath;
    public String mActionOnDuplicate;
    public String mMTime;
    public String mVersionControl;
    public String mPreviousHash;

    public InstantParameters(String hash, String size) {
        mHash = hash;
        mSize = size;
    }

    public InstantParameters filename(String filename) {
        if(filename == null) {
            return this;
        }

        mFilename = filename;
        return this;
    }

    public InstantParameters quickKey(String quickKey) {
        if(quickKey == null) {
            return this;
        }

        mQuickKey = quickKey;
        return this;
    }

    public InstantParameters folderKey(String folderKey) {
        if(folderKey == null) {
            return this;
        }

        mFolderKey = folderKey;
        return this;
    }

    public InstantParameters filedropKey(String filedropKey) {
        if(filedropKey == null) {
            return this;
        }

        mFiledropKey = filedropKey;
        return this;
    }

    public InstantParameters path(String path) {
        if(path == null) {
            return this;
        }

        mPath = path;
        return this;
    }

    public InstantParameters actionOnDuplicate(UploadItemOptions.ActionOnDuplicate actionOnDuplicate) {
        if(actionOnDuplicate == null) {
            return this;
        }

        switch (actionOnDuplicate) {
            case SKIP:
                mActionOnDuplicate = "skip";
                break;
            case KEEP:
                mActionOnDuplicate = "keep";
                break;
            case REPLACE:
                mActionOnDuplicate = "replace";
                break;
        }

        return this;
    }

    public InstantParameters mTime(String mTime) {
        if(mTime == null) {
            return this;
        }

        mMTime = mTime;
        return this;
    }

    public InstantParameters versionControl(UploadItemOptions.VersionControl versionControl) {
        if(versionControl == null) {
            return this;
        }

        switch (versionControl) {
            case CREATE_PATCHES:
                mVersionControl = "create_patches";
                break;
            case KEEP_REVISION:
                mVersionControl = "keep_revision";
                break;
            case NONE:
                mVersionControl = "none";
                break;
        }

        return this;
    }

    public InstantParameters previousHash(String previousHash) {
        if(previousHash == null) {
            return this;
        }

        mPreviousHash = previousHash;
        return this;
    }
}
