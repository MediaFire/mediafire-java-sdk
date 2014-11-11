package com.mediafire.sdk.clients.file;

/**
 * Created by Chris Najar on 10/30/2014.
 */
public class UpdateParameters {
    private String mFileName;
    private String mDescription;
    private String mPrivacy;
    private String mTime;

    public UpdateParameters(Builder builder) {
        mFileName = builder.mFileName;
        mDescription = builder.mDescription;
        mPrivacy = builder.mPrivacy;
        mTime = builder.mTime;
    }

    public String getFileName() {
        return mFileName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPrivacy() {
        return mPrivacy;
    }

    public String getMtime() {
        return mTime;
    }
    
    public static class Builder {
        private String mFileName;
        private String mDescription;
        private String mPrivacy;
        private String mTime;
    
        public Builder fileName(String fileName) {
            if (fileName == null) {
                return this;
            }
    
            mFileName = fileName;
            return this;
        }
    
        public Builder description(String description) {
            if (description == null) {
                return this;
            }
    
            mDescription = description;
            return this;
        }
    
        public Builder privacy(Privacy privacy) {
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

        public Builder mtime(String mtime) {
            mTime = mtime;
            return this;
        }

        public UpdateParameters build() {
            return new UpdateParameters(this);
        }
    
        public enum Privacy {
            PRIVATE, PUBLIC,
        }
    }
}
