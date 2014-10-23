package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class VersionObject {

    private String mApiVersion;

    public VersionObject(String apiVersion) {
        mApiVersion = apiVersion;
    }

    public String getApiVersion() {
        return mApiVersion;
    }
}
