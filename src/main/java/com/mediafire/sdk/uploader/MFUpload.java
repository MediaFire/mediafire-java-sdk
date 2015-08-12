package com.mediafire.sdk.uploader;

abstract class MFUpload implements MediaFireUpload {
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

    @Override
    public String toString() {
        return "MFUpload{" +
                "fileName='" + fileName + '\'' +
                ", folderKey='" + folderKey + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MFUpload mfUpload = (MFUpload) o;

        if (getId() != mfUpload.getId()) return false;
        if (getFileName() != null ? !getFileName().equals(mfUpload.getFileName()) : mfUpload.getFileName() != null)
            return false;
        if (getFolderKey() != null ? !getFolderKey().equals(mfUpload.getFolderKey()) : mfUpload.getFolderKey() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getFileName() != null ? getFileName().hashCode() : 0;
        result = 31 * result + (getFolderKey() != null ? getFolderKey().hashCode() : 0);
        result = 31 * result + (int) (getId() ^ (getId() >>> 32));
        return result;
    }
}
