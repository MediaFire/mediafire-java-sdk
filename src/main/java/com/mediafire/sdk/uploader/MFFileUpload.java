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
        private static final boolean DEFAULT_PREEMPTIVE = false;
        private static final boolean DEFAULT_RESUMABLE = true;

        private final File file;
        private final String fileName;
        private final String folderKey;

        private String sha256Hash;
        private long fileSize;
        private String jsonFormattedUploadsArray;
        private String deviceId;
        private boolean preemptive = DEFAULT_PREEMPTIVE;
        private String fileDropKey;
        private String mediaFirePath;
        private boolean resumable = DEFAULT_RESUMABLE;
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

    @Override
    public String toString() {
        return "MFFileUpload{" +
                "sha256Hash='" + sha256Hash + '\'' +
                ", fileSize=" + fileSize +
                ", jsonFormattedUploadsArray='" + jsonFormattedUploadsArray + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", preemptive=" + preemptive +
                ", fileDropKey='" + fileDropKey + '\'' +
                ", mediaFirePath='" + mediaFirePath + '\'' +
                ", resumable=" + resumable +
                ", actionOnInAccount=" + actionOnInAccount +
                ", file=" + file +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MFFileUpload that = (MFFileUpload) o;

        if (getFileSize() != that.getFileSize()) return false;
        if (isPreemptive() != that.isPreemptive()) return false;
        if (isResumable() != that.isResumable()) return false;
        if (getSha256Hash() != null ? !getSha256Hash().equals(that.getSha256Hash()) : that.getSha256Hash() != null)
            return false;
        if (getJsonFormattedUploadsArray() != null ? !getJsonFormattedUploadsArray().equals(that.getJsonFormattedUploadsArray()) : that.getJsonFormattedUploadsArray() != null)
            return false;
        if (getDeviceId() != null ? !getDeviceId().equals(that.getDeviceId()) : that.getDeviceId() != null)
            return false;
        if (getFileDropKey() != null ? !getFileDropKey().equals(that.getFileDropKey()) : that.getFileDropKey() != null)
            return false;
        if (getMediaFirePath() != null ? !getMediaFirePath().equals(that.getMediaFirePath()) : that.getMediaFirePath() != null)
            return false;
        if (getActionOnInAccount() != that.getActionOnInAccount()) return false;
        if (getFile() != null ? !getFile().equals(that.getFile()) : that.getFile() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getSha256Hash() != null ? getSha256Hash().hashCode() : 0;
        result = 31 * result + (int) (getFileSize() ^ (getFileSize() >>> 32));
        result = 31 * result + (getJsonFormattedUploadsArray() != null ? getJsonFormattedUploadsArray().hashCode() : 0);
        result = 31 * result + (getDeviceId() != null ? getDeviceId().hashCode() : 0);
        result = 31 * result + (isPreemptive() ? 1 : 0);
        result = 31 * result + (getFileDropKey() != null ? getFileDropKey().hashCode() : 0);
        result = 31 * result + (getMediaFirePath() != null ? getMediaFirePath().hashCode() : 0);
        result = 31 * result + (isResumable() ? 1 : 0);
        result = 31 * result + (getActionOnInAccount() != null ? getActionOnInAccount().hashCode() : 0);
        result = 31 * result + (getFile() != null ? getFile().hashCode() : 0);
        return result;
    }
}
