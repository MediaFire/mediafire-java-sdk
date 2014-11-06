package com.mediafire.sdk.clients.upload;

/**
 * Created by jondh on 11/5/14.
 */
public class CheckParameters {
    public final String mFilename;
    public String mHash;
    public String mSize;
    public String mFolderKey;
    public String mFiledropKey;
    public String mPath;
    public String mResumable;

    public CheckParameters(String filename) {
        mFilename = filename;
    }

    public CheckParameters hash(String hash) {
        if(hash == null) {
            return this;
        }

        mHash = hash;
        return this;
    }

    public CheckParameters size(String size) {
        if(size == null) {
            return this;
        }

        mSize = size;
        return this;
    }
    public CheckParameters folderKey(String folderKey) {
        if(folderKey == null) {
            return this;
        }

        mFolderKey = folderKey;
        return this;
    }
    public CheckParameters filedropKey(String filedropKey) {
        if(filedropKey == null) {
            return this;
        }

        mFiledropKey = filedropKey;
        return this;
    }
    public CheckParameters path(String path) {
        if(path == null) {
            return this;
        }

        mPath = path;
        return this;
    }
    public CheckParameters resumable(boolean resumable) {
        mResumable = resumable ? "yes" : "no";
        return this;
    }

}
