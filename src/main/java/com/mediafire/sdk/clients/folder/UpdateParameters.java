package com.mediafire.sdk.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class UpdateParameters {
    public String mFoldername;
    public String mDescription;
    public String mPrivacy;
    public String mPrivacyRecursive;
    public String mMTime;

    public UpdateParameters() { }

    public UpdateParameters foldername(String foldername) {
        if (foldername == null) {
            return this;
        }

        mFoldername = foldername;
        return this;
    }

    public UpdateParameters description(String description) {
        if (description == null) {
            return this;
        }

        mDescription = description;
        return this;
    }

    public UpdateParameters privacy(Privacy privacy) {
        if (privacy == null) {
            return this;
        }

        switch(privacy) {
            case PRIVATE:
                mPrivacy = "private";
                break;
            case PUBLIC:
                mPrivacy = "public";
                break;
        }

        return this;
    }

    public UpdateParameters privacyRecursive(boolean privacyRecursive) {
        mPrivacyRecursive = privacyRecursive ? "yes" : "no";
        return this;
    }

    public UpdateParameters mTime(String mTime) {
        if (mTime == null) {
            return this;
        }

        mMTime = mTime;
        return this;
    }

    public enum Privacy {
        PRIVATE, PUBLIC,
    }
}
