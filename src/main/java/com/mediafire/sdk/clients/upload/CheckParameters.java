package com.mediafire.sdk.clients.upload;

/**
 * Created by jondh on 11/5/14.
 */
public class CheckParameters {
    private final String mFilename;
    private final String mHash;
    private final String mSize;
    private final String mFolderKey;
    private final String mFiledropKey;
    private final String mPath;
    private final String mResumable;
    
    public CheckParameters(Builder builder) {
        mFilename = builder.mFilename;
        mHash = builder.mHash;
        mSize = builder.mSize;
        mFolderKey = builder.mFolderKey;
        mFiledropKey = builder.mFiledropKey;
        mPath = builder.mPath;
        mResumable = builder.mResumable;
    }

    public String getFilename() {
        return mFilename;
    }

    public String getHash() {
        return mHash;
    }

    public String getSize() {
        return mSize;
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

    public String getResumable() {
        return mResumable;
    }

    public static class Builder {
        private static final String DEFAULT_RESUMABLE = "yes";
        
        private final String mFilename;
        private String mHash;
        private String mSize;
        private String mFolderKey;
        private String mFiledropKey;
        private String mPath;
        private String mResumable = DEFAULT_RESUMABLE;

        public Builder(String filename) {
            mFilename = filename;
        }

        public Builder hash(String hash) {
            if(hash == null) {
                return this;
            }

            mHash = hash;
            return this;
        }

        public Builder size(String size) {
            if(size == null) {
                return this;
            }

            mSize = size;
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
        public Builder resumable(boolean resumable) {
            mResumable = resumable ? "yes" : "no";
            return this;
        }
        
        public CheckParameters build() {
            return new CheckParameters(this);
        }
    }
}
