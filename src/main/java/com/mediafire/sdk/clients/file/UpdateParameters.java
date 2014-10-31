package com.mediafire.sdk.clients.file;

/**
 * Created by Chris Najar on 10/30/2014.
 */
public class UpdateParameters {
    public String mFileName;
    public String mDescription;
    public String mPrivacy;
    public String mTime;

    public UpdateParameters() { }

    public UpdateParameters fileName(String fileName) {
        if (fileName == null) {
            return this;
        }

        mFileName = fileName;
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

    public enum Privacy {
        PRIVATE, PUBLIC,
    }
}
