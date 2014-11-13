package com.mediafire.sdk.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class CreateParameters {
    private String mParentKey;
    private String mAllowDuplicateName;
    private String mMTime;

    private CreateParameters(Builder builder) {
        mParentKey = builder.mParentKey;
        mAllowDuplicateName = builder.mAllowDuplicateName;
        mMTime = builder.mMTime;
    }

    public String getParentKey() {
        return mParentKey;
    }

    public String getAllowDuplicateName() {
        return mAllowDuplicateName;
    }

    public String getMTime() {
        return mMTime;
    }

    public static class Builder {
        private String mParentKey;
        private String mAllowDuplicateName;
        private String mMTime;
        
        public Builder() { }
    
        public Builder parentKey(String parentKey) {
            if (parentKey == null) {
                return this;
            }
    
            mParentKey = parentKey;
            return this;
        }
    
        public Builder allowDuplicateName(boolean allowDuplicateName) {
    
            mAllowDuplicateName = allowDuplicateName ? "yes" : "no";
            return this;
        }
    
        public Builder mTime(String mTime) {
            if (mTime == null) {
                return this;
            }
    
            mMTime = mTime;
            return this;
        }

        public CreateParameters build() {
            return new CreateParameters(this);
        }
    }
}
