package com.mediafire.sdk.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class CreateParameters {
    public String mParentKey;
    public String mAllowDuplicateName;
    public String mMTime;

    public CreateParameters() { }

    public CreateParameters parentKey(String parentKey) {
        if (parentKey == null) {
            return this;
        }

        mParentKey = parentKey;
        return this;
    }

    public CreateParameters allowDuplicateName(boolean allowDuplicateName) {

        mAllowDuplicateName = allowDuplicateName ? "yes" : "no";
        return this;
    }

    public CreateParameters mTime(String mTime) {
        if (mTime == null) {
            return this;
        }

        mMTime = mTime;
        return this;
    }
}
