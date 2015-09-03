package com.mediafire.sdk.uploader;

import java.io.File;

public class MFFileUpload extends MFUpload implements MediaFireFileUpload {

    private final String sha256Hash;
    private final long fileSize;
    private final String jsonFormattedUploadsArray;
    private final String deviceId;
    private final boolean preemptive;
    private final String fileDropKey;
    private final String mediaFirePath;
    private final boolean resumable;
    private final ActionOnInAccount actionOnInAccount;
    private final File file;

    protected MFFileUpload(Builder builder) {
        super(builder.fileName, builder.folderKey);
        this.sha256Hash = builder.sha256Hash;
        this.fileSize = builder.fileSize;
        this.jsonFormattedUploadsArray = builder.jsonFormattedUploadsArray;
        this.deviceId = builder.deviceId;
        this.preemptive = builder.preemptive;
        this.fileDropKey = builder.fileDropKey;
        this.mediaFirePath = builder.mediaFirePath;
        this.resumable = builder.resumable;
        this.actionOnInAccount = builder.actionOnInAccount;
        this.file = builder.file;
    }

    @Override
    public String getSha256Hash() {
        return this.sha256Hash;
    }

    @Override
    public long getFileSize() {
        return this.fileSize;
    }

    @Override
    public String getJsonFormattedUploadsArray() {
        return this.jsonFormattedUploadsArray;
    }

    @Override
    public String getDeviceId() {
        return this.deviceId;
    }

    @Override
    public boolean isPreemptive() {
        return this.preemptive;
    }

    @Override
    public String getFileDropKey() {
        return this.fileDropKey;
    }

    @Override
    public String getMediaFirePath() {
        return this.mediaFirePath;
    }

    @Override
    public boolean isResumable() {
        return this.resumable;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public ActionOnInAccount getActionOnInAccount() {
        return this.actionOnInAccount;
    }

    @Override
    public int getType() {
        return MediaFireUpload.TYPE_FILE_UPLOAD;
    }

    public static class Builder {
        private final File file;
        private final String fileName;
        private final String folderKey;

        private String sha256Hash;
        private long fileSize;
        private String jsonFormattedUploadsArray;
        private String deviceId;
        private boolean preemptive;
        private String fileDropKey;
        private String mediaFirePath;
        private boolean resumable;
        private ActionOnInAccount actionOnInAccount = ActionOnInAccount.UPLOAD_IF_NOT_IN_FOLDER;

        public Builder(File file, String fileName, String folderKey) {
            this.file = file;
            this.fileName = fileName;
            this.folderKey = folderKey;
        }

        public Builder(File file, String fileName) {
            this(file, fileName, null);
        }

        public Builder setSha256Hash(String sha256Hash) {
            this.sha256Hash = sha256Hash;
            return this;
        }

        public Builder setFileSize(long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public Builder setJsonFormattedUploadsArray(String jsonFormattedUploadsArray) {
            this.jsonFormattedUploadsArray = jsonFormattedUploadsArray;
            return this;
        }

        public Builder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder setPreemptive(boolean preemptive) {
            this.preemptive = preemptive;
            return this;
        }

        public Builder setFileDropKey(String fileDropKey) {
            this.fileDropKey = fileDropKey;
            return this;
        }

        public Builder setMediaFirePath(String mediaFirePath) {
            this.mediaFirePath = mediaFirePath;
            return this;
        }

        public Builder setResumable(boolean resumable) {
            this.resumable = resumable;
            return this;
        }

        public Builder setActionOnInAccount(ActionOnInAccount actionOnInAccount) {
            if (actionOnInAccount == null) {
                return this;
            }
            this.actionOnInAccount = actionOnInAccount;
            return this;
        }

        public MFFileUpload build() {
            return new MFFileUpload(this);
        }
    }
}
