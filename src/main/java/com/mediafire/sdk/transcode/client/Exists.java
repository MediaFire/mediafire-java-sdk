package com.mediafire.sdk.transcode.client;

/**
 * Created by Chris on 11/12/2014.
 */
public enum Exists {
    CREATE("create"),
    STATUS("status"),
    CHECK("check");

    private final String mValue;

    private Exists(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
