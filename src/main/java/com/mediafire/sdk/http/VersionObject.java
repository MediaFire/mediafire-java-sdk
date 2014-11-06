package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/18/2014.
 * VersionObject is an object to store the api version
 */
public class VersionObject {

    private final String mApiVersion;

    /**
     * VersionObject Constructor
     * @param apiVersion String for the api version
     */
    public VersionObject(String apiVersion) {
        mApiVersion = apiVersion;
    }

    /**
     * Gets the api version
     * @return String api version
     */
    public String getApiVersion() {
        return mApiVersion;
    }
}
