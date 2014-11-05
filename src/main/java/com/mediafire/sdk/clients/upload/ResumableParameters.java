package com.mediafire.sdk.clients.upload;

import com.mediafire.sdk.uploader.uploaditem.UploadItemOptions;

/**
 * Created by jondh on 11/5/14.
 */
public class ResumableParameters {
    public String mFiledropKey;
    public String mSourceHash;
    public String mTargetHash;
    public String mTargetSize;
    public String mQuickKey;
    public String mFolderKey;
    public String mPath;
    public String mActionOnDuplicate;
    public String mMTime;
    public String mVersionControl;
    public String mPreviousHash;

    public ResumableParameters filedropKey(String filedropKey) {
        if(filedropKey == null) {
            return this;
        }

        mFiledropKey = filedropKey;
        return this;
    }

    public ResumableParameters sourceHash(String sourceHash) {
        if(sourceHash == null) {
            return this;
        }

        mSourceHash = sourceHash;
        return this;
    }

    public ResumableParameters targetHash(String targetHash) {
        if(targetHash == null) {
            return this;
        }

        mTargetHash = targetHash;
        return this;
    }

    public ResumableParameters targetSize(String targetSize) {
        if(targetSize == null) {
            return this;
        }

        mTargetSize = targetSize;
        return this;
    }

    public ResumableParameters quickKey(String quickKey) {
        if(quickKey == null) {
            return this;
        }

        mQuickKey = quickKey;
        return this;
    }

    public ResumableParameters folderKey(String folderKey) {
        if(folderKey == null) {
            return this;
        }

        mFolderKey = folderKey;
        return this;
    }



    public ResumableParameters path(String path) {
        if(path == null) {
            return this;
        }

        mPath = path;
        return this;
    }

    public ResumableParameters actionOnDuplicate(UploadItemOptions.ActionOnDuplicate actionOnDuplicate) {
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

    public ResumableParameters mTime(String mTime) {
        if(mTime == null) {
            return this;
        }

        mMTime = mTime;
        return this;
    }

    public ResumableParameters versionControl(UploadItemOptions.VersionControl versionControl) {
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

    public ResumableParameters previousHash(String previousHash) {
        if(previousHash == null) {
            return this;
        }

        mPreviousHash = previousHash;
        return this;
    }

}
