package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/17/2014.
 */
public class ApiObject {
    private final String mFile;
    private final String mPath;

    public ApiObject(String path, String file) {
        mPath = path;
        mFile = file;
    }

    public String getPath() {
        return mPath;
    }

    public String getFile() {
        return mFile;
    }
}
