package com.mediafire.sdk.uploader.uploaditem;

import java.util.ArrayList;

/**
 * UploadItem contains the information about an item to be uploaded
 */
public class UploadItem {
    private int uploadAttemptCount;
    private boolean cancelled;
    private String fileName;
    private UploadItemOptions uploadItemOptions;
    private final FileData fileData;
    private ChunkData chunkData;
    private ResumableBitmap bitmap;
    private String pollUploadKey;

    /**
     * UploadItem Constructor
     * @param path String path of the file to upload
     * @param uploadItemOptions UploadItemOptions options for the file to be uploaded
     */
    public UploadItem(String path, UploadItemOptions uploadItemOptions) {
        if (path == null) {
            throw new IllegalArgumentException("path must not be null");
        }

        if (uploadItemOptions == null) {
            this.uploadItemOptions = new UploadItemOptions.Builder().build();
        } else {
            this.uploadItemOptions = uploadItemOptions;
        }

        if (this.uploadItemOptions.getCustomFileName() == null || this.uploadItemOptions.getCustomFileName().isEmpty()) {
            setFileName(path);
        } else {
            fileName = this.uploadItemOptions.getCustomFileName();
        }

        //set Object fields so they won't be null
        fileData = new FileData(path);
        fileData.setFileSize();
        fileData.setFileHash();
        pollUploadKey = "";
        chunkData = new ChunkData();
        bitmap = new ResumableBitmap(0, new ArrayList<Integer>());
        uploadAttemptCount = 0;
    }

    /**
     * UploadItem Constructor
     * @param path String path of the file to upload
     */
    public UploadItem(String path) {
        this(path, null);
    }

    /**
     * Returns if the upload is cancelled
     * @return boolean if the upload is cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Cancels this upload
     */
    public void cancelUpload() {
        cancelled = true;
    }

    /**
     * Gets the number of upload attempts
     * @return int number of upload attempts
     */
    public int getUploadAttemptCount() {
        uploadAttemptCount++;
        return uploadAttemptCount;
    }

    /**
     * Gets the file name for the upload
     * @return String fileName
     */
    public String getFileName() {
        if (uploadItemOptions.getCustomFileName() != null && !uploadItemOptions.getCustomFileName().isEmpty()) {
            fileName = uploadItemOptions.getCustomFileName();
        }
        return fileName;
    }

    /**
     * Gets the file data for the upload
     * @return FileData fileData
     */
    public FileData getFileData() {
        return fileData;
    }

    /**
     * Gets the poll upload key for the upload
     * @return String pollUploadKey
     */
    public String getPollUploadKey() {
        return pollUploadKey;
    }

    /**
     * Gets the UploadItemOptions for the upload
     * @return UploadItemOptions uploadItemOptions
     */
    public UploadItemOptions getUploadOptions() {
        if (uploadItemOptions == null) {
            uploadItemOptions = new UploadItemOptions.Builder().build();
        }
        return uploadItemOptions;
    }

    /**
     * Gets the ChunkData for the upload
     * @return ChunkData chunkData
     */
    public ChunkData getChunkData() {
        if (chunkData == null) {
            chunkData = new ChunkData();
        }
        return chunkData;
    }

    /**
     * Gets the ResumableBitmap for the upload
     * @return ResumableBitmap bitmap
     */
    public ResumableBitmap getBitmap() {
        if (bitmap == null) {
            bitmap = new ResumableBitmap(0, new ArrayList<Integer>());
        }
        return bitmap;
    }

    /**
     * Sets the ResumableBitmap for the upload
     * @param bitmap ResumableBitmap for the upload
     */
    public void setBitmap(ResumableBitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Sets the poll upload key for the upload
     * @param pollUploadKey String pollUploadKey for the upload
     */
    public void setPollUploadKey(String pollUploadKey) {
        this.pollUploadKey = pollUploadKey;
    }

    private void setFileName(String path) {
        String[] splitName = path.split("/");
        this.fileName = splitName[splitName.length-1];
    }

    /**
     * Overridden equals method to check if two UploadItems are the same
     * @param object The object to check against
     * @return boolean if the objects are equal
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (this.getClass() != object.getClass()) {
            return false;
        }

        if (!fileData.getFilePath().equals(((UploadItem) object).fileData.getFilePath())) {
            return false;
        }

        if (fileData.getFileHash() != null && ((UploadItem) object).fileData.getFileHash() != null) {
            if (!fileData.getFileHash().equals(((UploadItem) object).fileData.getFileHash())) {
                return false;
            }
        }

        return true;
    }
}
