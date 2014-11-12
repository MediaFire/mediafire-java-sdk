package com.mediafire.sdk.api.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class UpdateParameters {
    private String mFoldername;
    private String mDescription;
    private String mPrivacy;
    private String mPrivacyRecursive;
    private String mMTime;

    public UpdateParameters(Builder builder) {
        mFoldername = builder.mFoldername;
        mDescription = builder.mDescription;
        mPrivacy = builder.mPrivacy;
        mPrivacyRecursive = builder.mPrivacyRecursive;
        mMTime = builder.mMTime;
    }

    public String getFoldername() {
        return mFoldername;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPrivacy() {
        return mPrivacy;
    }

    public String getPrivacyRecursive() {
        return mPrivacyRecursive;
    }

    public String getMTime() {
        return mMTime;
    }

    public static class Builder {
        private String mFoldername;
        private String mDescription;
        private String mPrivacy;
        private String mPrivacyRecursive;
        private String mMTime;
        
        public Builder() { }

        public Builder foldername(String foldername) {
            if (foldername == null) {
                return this;
            }

            mFoldername = foldername;
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

        public Builder privacyRecursive(boolean privacyRecursive) {
            mPrivacyRecursive = privacyRecursive ? "yes" : "no";
            return this;
        }

        public Builder mTime(String mTime) {
            if (mTime == null) {
                return this;
            }

            mMTime = mTime;
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
