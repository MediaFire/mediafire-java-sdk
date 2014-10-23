package com.mediafire.sdk.uploader.uploaditem;

import java.util.ArrayList;

public class UploadItem {
    private static final String TAG = UploadItem.class.getCanonicalName();
    private int uploadAttemptCount;
    private boolean cancelled;
    private String fileName;
    private UploadItemOptions uploadItemOptions;
    private final FileData fileData;
    private ChunkData chunkData;
    private ResumableBitmap bitmap;
    private String pollUploadKey;


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

    public UploadItem(String path) {
        this(path, null);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancelUpload() {
        cancelled = true;
    }

    public int getUploadAttemptCount() {
        uploadAttemptCount++;
        return uploadAttemptCount;
    }

    public String getFileName() {
        if (uploadItemOptions.getCustomFileName() != null && !uploadItemOptions.getCustomFileName().isEmpty()) {
            fileName = uploadItemOptions.getCustomFileName();
        }
        return fileName;
    }

    public FileData getFileData() {
        return fileData;
    }

    public String getPollUploadKey() {
        return pollUploadKey;
    }

    public UploadItemOptions getUploadOptions() {
        if (uploadItemOptions == null) {
            uploadItemOptions = new UploadItemOptions.Builder().build();
        }
        return uploadItemOptions;
    }

    public ChunkData getChunkData() {
        if (chunkData == null) {
            chunkData = new ChunkData();
        }
        return chunkData;
    }

    public ResumableBitmap getBitmap() {
        if (bitmap == null) {
            bitmap = new ResumableBitmap(0, new ArrayList<Integer>());
        }
        return bitmap;
    }

    public void setBitmap(ResumableBitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setPollUploadKey(String pollUploadKey) {
        this.pollUploadKey = pollUploadKey;
    }

    private void setFileName(String path) {
        String[] splitName = path.split("/");
        this.fileName = splitName[splitName.length-1];
    }

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
