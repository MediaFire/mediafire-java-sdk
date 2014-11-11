package com.mediafire.sdk.clients.upload;

import com.mediafire.sdk.uploader.uploaditem.UploadItemOptions;

/**
 * Created by jondh on 11/5/14.
 */
public class ResumableParameters {

    private final String mFiledropKey;
    private final String mSourceHash;
    private final String mTargetHash;
    private final String mTargetSize;
    private final String mQuickKey;
    private final String mFolderKey;
    private final String mPath;
    private final String mActionOnDuplicate;
    private final String mMTime;
    private final String mVersionControl;
    private final String mPreviousHash;

    public ResumableParameters(Builder builder) {
        mFiledropKey = builder.mFiledropKey;
        mSourceHash = builder.mSourceHash;
        mTargetHash = builder.mTargetHash;
        mTargetSize = builder.mTargetSize;
        mQuickKey = builder.mQuickKey;
        mFolderKey = builder.mFolderKey;
        mPath = builder.mPath;
        mActionOnDuplicate = builder.mActionOnDuplicate;
        mMTime = builder.mMTime;
        mVersionControl = builder.mVersionControl;
        mPreviousHash = builder.mPreviousHash;
    }

    public String getFiledropKey() {
        return mFiledropKey;
    }

    public String getSourceHash() {
        return mSourceHash;
    }

    public String getTargetHash() {
        return mTargetHash;
    }

    public String getTargetSize() {
        return mTargetSize;
    }

    public String getQuickKey() {
        return mQuickKey;
    }

    public String getFolderKey() {
        return mFolderKey;
    }

    public String getPath() {
        return mPath;
    }

    public String getActionOnDuplicate() {
        return mActionOnDuplicate;
    }

    public String getMTime() {
        return mMTime;
    }

    public String getVersionControl() {
        return mVersionControl;
    }

    public String getPreviousHash() {
        return mPreviousHash;
    }

    public static class Builder {

        private String mFiledropKey;
        private String mSourceHash;
        private String mTargetHash;
        private String mTargetSize;
        private String mQuickKey;
        private String mFolderKey;
        private String mPath;
        private String mActionOnDuplicate;
        private String mMTime;
        private String mVersionControl;
        private String mPreviousHash;

        public Builder() { }

        public Builder filedropKey(String filedropKey) {
            if(filedropKey == null) {
                return this;
            }

            mFiledropKey = filedropKey;
            return this;
        }

        public Builder sourceHash(String sourceHash) {
            if(sourceHash == null) {
                return this;
            }

            mSourceHash = sourceHash;
            return this;
        }

        public Builder targetHash(String targetHash) {
            if(targetHash == null) {
                return this;
            }

            mTargetHash = targetHash;
            return this;
        }

        public Builder targetSize(String targetSize) {
            if(targetSize == null) {
                return this;
            }

            mTargetSize = targetSize;
            return this;
        }

        public Builder quickKey(String quickKey) {
            if(quickKey == null) {
                return this;
            }

            mQuickKey = quickKey;
            return this;
        }

        public Builder folderKey(String folderKey) {
            if(folderKey == null) {
                return this;
            }

            mFolderKey = folderKey;
            return this;
        }

        public Builder path(String path) {
            if(path == null) {
                return this;
            }

            mPath = path;
            return this;
        }

        public Builder actionOnDuplicate(UploadItemOptions.ActionOnDuplicate actionOnDuplicate) {
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

        public Builder mTime(String mTime) {
            if(mTime == null) {
                return this;
            }

            mMTime = mTime;
            return this;
        }

        public Builder versionControl(UploadItemOptions.VersionControl versionControl) {
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

        public Builder previousHash(String previousHash) {
            if(previousHash == null) {
                return this;
            }

            mPreviousHash = previousHash;
            return this;
        }
        
        public ResumableParameters build() {
            return new ResumableParameters(this);
        }
    }
}
