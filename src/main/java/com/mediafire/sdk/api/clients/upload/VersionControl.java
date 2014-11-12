package com.mediafire.sdk.api.clients.upload;

/**
 * Created by Chris on 11/11/2014.
 */
public enum VersionControl {
    CREATE_PATCHES("create_patches"),
    KEEP_REVISION("keep_revision"),
    NONE("none");

    private final String value;

    private VersionControl(String value) {
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
