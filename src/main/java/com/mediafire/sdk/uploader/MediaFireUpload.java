package com.mediafire.sdk.uploader;

public interface MediaFireUpload {
    /**
     * The UTF-8 encoded name, plus extension, of the file to be created(between 3 and 255 characters in length).
     * @return
     */
    String getFileName();

    /**
     * The destination folder key. If not passed the API will use the cloud root folder. Alternatively, you may pass myfiles as a folderkey moniker for the root folder.
     * @return
     */
    String getFolderKey();

    /**
     * The unique id for this upload.
     * @return < 1 if an id hasn't been set
     */
    long getId();

    /**
     * sets the unique id for this upload
     * @param id
     */
    void setId(long id);
}
