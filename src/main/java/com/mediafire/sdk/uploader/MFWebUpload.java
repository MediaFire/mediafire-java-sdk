package com.mediafire.sdk.uploader;

import java.net.URL;

public class MFWebUpload extends MFUpload implements MediaFireWebUpload {

    private final String url;
    private final String quickKey;
    private final String fileName;
    private final String folderKey;

    public MFWebUpload(String url, String quickKey, String fileName, String folderKey) {
        super(fileName, folderKey);
        this.url = url;
        this.quickKey = quickKey;
        this.fileName = fileName;
        this.folderKey = folderKey;
    }

    public MFWebUpload(String url, String quickKey, String fileName) {
        this(url, quickKey, fileName, null);
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public String getQuickKey() {
        return this.quickKey;
    }
}
