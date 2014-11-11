package com.mediafire.sdk.clients.upload;

/**
 * Created by Chris on 11/11/2014.
 */
public enum ActionOnDuplicate {
    KEEP("keep"),
    SKIP("skip"),
    REPLACE("replace");

    private final String value;

    private ActionOnDuplicate(String value) {
        this.value = value;
    }

    /**
     * gets the value of the enum
     * @return String value
     */
    public String getValue() {
        return value;
    }
}
