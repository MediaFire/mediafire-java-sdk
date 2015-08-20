package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MediaFireException;

/**
 * Created by Chris on 5/18/2015.
 */
public interface MediaFireUploadHandler {

    /**
     * called when an upload has failed with an MediaFireException. the upload may be retried, but likely will continue to fail
     * @param id id of upload
     * @param e MediaFireException
     */
    void uploadFailed(long id, MediaFireException e);

    /**
     * called when a chunk of an upload has finished and the total progress has changed
     * @param id id of upload
     * @param percentFinished the approximate progress of the upload
     */
    void uploadProgress(long id, double percentFinished);

    /**
     * called when an upload is finished
     * @param id id of upload
     * @param quickKey the quick key of the finished upload (can be null)
     * @param fileName the file name of the finished upload (can be null)
     */
    void uploadFinished(long id, String quickKey, String fileName);

    /**
     * called when an upload is polling
     * @param id id of upload
     * @param statusCode the status code
     * @param description the description from the server
     */
    void uploadPolling(long id, int statusCode, String description);
}
