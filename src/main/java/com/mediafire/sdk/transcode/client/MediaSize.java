package com.mediafire.sdk.transcode.client;

/**
 * Created by Chris on 11/12/2014.
 */
public enum MediaSize {
    _240P("240p"),
    _480P("480p"),
    _720P("720p"),
    _1080P("1080p"),
    DEFAULT("480p");

    private final String mValue;

    private MediaSize(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
