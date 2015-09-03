package com.mediafire.sdk.uploader;

/**
 * Created by christophernajar on 9/2/15.
 */
public interface MediaFireWebUpload extends MediaFireUpload {
    /**
     * The URL to the file to be saved
     * @return
     */
    String getUrl();

    /**
     * The file key of a preexisting file in the session user's account. This file will be updated with the content of the uploaded file.
     * @return
     */
    String getQuickKey();
}
