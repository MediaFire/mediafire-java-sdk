package com.mediafire.sdk.api.clients.upload;

/**
 * Created by jondh on 11/5/14.
 */
public class InstantParameters {
    private final String mHash;
    private final long mSize;
    private final String mFilename;
    private final String mQuickKey;
    private final String mFolderKey;
    private final String mFiledropKey;
    private final String mPath;
    private final String mActionOnDuplicate;
    private final String mMTime;
    private final String mVersionControl;
    private final String mPreviousHash;

    private InstantParameters(Builder builder) {
        mHash = builder.mHash;
        mSize = builder.mSize;
        mFiledropKey = builder.mFiledropKey;
        mFilename = builder.mFilename;
        mQuickKey = builder.mQuickKey;
        mFolderKey = builder.mFolderKey;
        mPath = builder.mPath;
        mActionOnDuplicate = builder.mActionOnDuplicate;
        mMTime = builder.mMTime;
        mVersionControl = builder.mVersionControl;
        mPreviousHash = builder.mPreviousHash;
    }

    public String getHash() {
        return mHash;
    }

    public long getSize() {
        return mSize;
    }

    public String getFilename() {
        return mFilename;
    }

    public String getQuickKey() {
        return mQuickKey;
    }

    public String getFolderKey() {
        return mFolderKey;
    }

    public String getFiledropKey() {
        return mFiledropKey;
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
        private final String mHash;
        private final long mSize;
        private String mFilename;
        private String mQuickKey;
        private String mFolderKey;
        private String mFiledropKey;
        private String mPath;
        private String mActionOnDuplicate;
        private String mMTime;
        private String mVersionControl;
        private String mPreviousHash;

        public Builder(String hash, long size) {
            mHash = hash;
            mSize = size;
        }

        public Builder filename(String filename) {
            if(filename == null) {
                return this;
            }

            mFilename = filename;
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

        public Builder filedropKey(String filedropKey) {
            if(filedropKey == null) {
                return this;
            }

            mFiledropKey = filedropKey;
            return this;
        }

        public Builder path(String path) {
            if(path == null) {
                return this;
            }

            mPath = path;
            return this;
        }

        public Builder actionOnDuplicate(ActionOnDuplicate actionOnDuplicate) {
            if(actionOnDuplicate == null) {
                return this;
            }

            mActionOnDuplicate = actionOnDuplicate.getValue();

            return this;
        }

        public Builder mTime(String mTime) {
            if(mTime == null) {
                return this;
            }

            mMTime = mTime;
            return this;
        }

        public Builder versionControl(VersionControl versionControl) {
            if(versionControl == null) {
                return this;
            }

            switch (versionControl) {
                case CREATE_PATCHES:
                    mVersionControl = versionControl.getValue();
                    break;
                case KEEP_REVISION:
                    mVersionControl = versionControl.getValue();
                    break;
                case NONE:
                    mVersionControl = versionControl.getValue();
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
        
        public InstantParameters build() {
            return new InstantParameters(this);
        }
    }
}
