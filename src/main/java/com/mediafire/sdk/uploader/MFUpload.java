package com.mediafire.sdk.uploader;

public class MFUpload implements MediaFireUpload {
    private final String fileName;
    private final String folderKey;
    private long id = -1;

    public MFUpload(String fileName, String folderKey) {
        this.fileName = fileName;
        this.folderKey = folderKey;
    }

    public MFUpload(String fileName) {
        this(fileName, null);
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getFolderKey() {
        return this.folderKey;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        if (id <= 0) {
            return;
        }
        this.id = id;
    }
}
