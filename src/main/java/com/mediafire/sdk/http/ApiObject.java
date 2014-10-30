package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/17/2014.
 * ApiObject contains a path and a file (for a url)
 */
public class ApiObject {
    private final String mFile;
    private final String mPath;

    /**
     * ApiObject Constructor
     * @param path String path for api
     * @param file String file for api
     */
    public ApiObject(String path, String file) {
        mPath = path;
        mFile = file;
    }

    /**
     * Gets the path
     * @return String path
     */
    public String getPath() {
        return mPath;
    }

    /**
     * Gets the file
     * @return String file
     */
    public String getFile() {
        return mFile;
    }
}
