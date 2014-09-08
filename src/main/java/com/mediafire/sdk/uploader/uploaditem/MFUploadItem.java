package com.mediafire.sdk.uploader.uploaditem;

import com.mediafire.sdk.config.MFConfiguration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MFUploadItem {
    private static final String TAG = MFUploadItem.class.getCanonicalName();
    private int uploadAttemptCount;
    private boolean cancelled;
    private String fileName;
    private MFUploadItemOptions mfUploadItemOptions;
    private final MFFileData fileData;
    private MFChunkData mfChunkData;
    private MFResumableBitmap bitmap;
    private String pollUploadKey;


    public MFUploadItem(String path, MFUploadItemOptions mfUploadItemOptions) {
        if (path == null) {
            throw new IllegalArgumentException("path must not be null");
        }

        if (mfUploadItemOptions == null) {
            this.mfUploadItemOptions = new MFUploadItemOptions.Builder().build();
        } else {
            this.mfUploadItemOptions = mfUploadItemOptions;
        }

        if (this.mfUploadItemOptions.getCustomFileName() == null || this.mfUploadItemOptions.getCustomFileName().isEmpty()) {
            setFileName(path);
        } else {
            fileName = this.mfUploadItemOptions.getCustomFileName();
        }

        //set Object fields so they won't be null
        fileData = new MFFileData(path);
        fileData.setFileSize();
        fileData.setFileHash();
        pollUploadKey = "";
        mfChunkData = new MFChunkData();
        bitmap = new MFResumableBitmap(0, new ArrayList<Integer>());
        uploadAttemptCount = 0;
    }

    public MFUploadItem(String path) {
        this(path, null);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancelUpload() {
        cancelled = true;
    }

    public int getUploadAttemptCount() {
        MFConfiguration.getStaticMFLogger().v(TAG, "getUploadAttemptCount(" + uploadAttemptCount + ")");
        uploadAttemptCount++;
        return uploadAttemptCount;
    }

    public String getFileName() {
        if (mfUploadItemOptions.getCustomFileName() != null && !mfUploadItemOptions.getCustomFileName().isEmpty()) {
            fileName = mfUploadItemOptions.getCustomFileName();
        }
        return fileName;
    }

    public MFFileData getFileData() {
        return fileData;
    }

    public String getPollUploadKey() {
        return pollUploadKey;
    }

    public MFUploadItemOptions getUploadOptions() {
        if (mfUploadItemOptions == null) {
            mfUploadItemOptions = new MFUploadItemOptions.Builder().build();
        }
        return mfUploadItemOptions;
    }

    public MFChunkData getChunkData() {
        if (mfChunkData == null) {
            mfChunkData = new MFChunkData();
        }
        return mfChunkData;
    }

    public MFResumableBitmap getBitmap() {
        if (bitmap == null) {
            MFConfiguration.getStaticMFLogger().v(TAG, "  resumable bitmap reference lost");
            bitmap = new MFResumableBitmap(0, new ArrayList<Integer>());
        }
        return bitmap;
    }

    public void setBitmap(MFResumableBitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setPollUploadKey(String pollUploadKey) {
        this.pollUploadKey = pollUploadKey;
    }

    private void setFileName(String path) {
        String[] splitName = path.split("/");
        //just throwing the UnsupportedEncodingException exception to whoever creates the upload item
        try {
            this.fileName = URLEncoder.encode(splitName[splitName.length - 1], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (this.getClass() != object.getClass()) {
            return false;
        }

        if (!fileData.getFilePath().equals(((MFUploadItem) object).fileData.getFilePath())) {
            return false;
        }

        if (fileData.getFileHash() != null && ((MFUploadItem) object).fileData.getFileHash() != null) {
            if (!fileData.getFileHash().equals(((MFUploadItem) object).fileData.getFileHash())) {
                return false;
            }
        }

        return true;
    }
}
