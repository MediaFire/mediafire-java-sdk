package com.mediafire.sdk.api.clients.folder;

/**
 * Created by Chris on 11/11/2014.
 */
public enum Details {
    YES("yes"),
    NO("no"),
    SHALLOW("shallow");

    private final String mValue;

    private Details(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
