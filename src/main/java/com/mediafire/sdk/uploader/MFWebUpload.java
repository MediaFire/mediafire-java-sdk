package com.mediafire.sdk.uploader;

import java.net.URL;

public class MFWebUpload extends MFUpload implements MediaFireWebUpload {

    private final String url;
    private final String fileName;
    private final String folderKey;

    public MFWebUpload(String url, String fileName, String folderKey) {
        super(fileName, folderKey);
        this.url = url;
        this.fileName = fileName;
        this.folderKey = folderKey;
    }

    public MFWebUpload(String url, String fileName) {
        this(url, fileName, null);
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public int getType() {
        return MediaFireUpload.TYPE_WEB_UPLOAD;
    }

    @Override
    public String toString() {
        return "MFWebUpload{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", folderKey='" + folderKey + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MFWebUpload webUpload = (MFWebUpload) o;

        if (getUrl() != null ? !getUrl().equals(webUpload.getUrl()) : webUpload.getUrl() != null) return false;
        if (getFileName() != null ? !getFileName().equals(webUpload.getFileName()) : webUpload.getFileName() != null)
            return false;
        if (getFolderKey() != null ? !getFolderKey().equals(webUpload.getFolderKey()) : webUpload.getFolderKey() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getUrl() != null ? getUrl().hashCode() : 0;
        result = 31 * result + (getFileName() != null ? getFileName().hashCode() : 0);
        result = 31 * result + (getFolderKey() != null ? getFolderKey().hashCode() : 0);
        return result;
    }
}
