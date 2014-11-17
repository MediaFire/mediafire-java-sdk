package com.mediafire.sdk.api.clients.upload;

/**
 * Created by Chris on 11/11/2014.
 */
public enum ActionOnDuplicate {
    KEEP("keep"),
    SKIP("skip"),
    REPLACE("replace");

    private final String mValue;

    private ActionOnDuplicate(String value) {
        this.mValue = value;
    }

    /**
     * gets the mValue of the enum
     * @return String mValue
     */
    public String getValue() {
        return mValue;
    }
}
