package com.mediafire.sdk.uploader;

public interface MediaFireWebUpload extends MediaFireUpload {
    /**
     * The URL to the file to be saved
     * @return
     */
    String getUrl();
}
