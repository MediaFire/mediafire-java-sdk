package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MFApiRequest;
import com.mediafire.sdk.MediaFireApiRequest;
import com.mediafire.sdk.MediaFireClient;
import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.api.responses.UploadInstantResponse;
import com.mediafire.sdk.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

class MFRunnableInstantUpload implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(MFRunnableInstantUpload.class);

    private static final String PARAM_FILENAME = "filename";
    private static final String PARAM_FOLDER_KEY = "folder_key";
    private static final String PARAM_FOLDER_PATH = "path";
    private static final String PARAM_SIZE = "size";
    private static final String PARAM_HASH = "hash";

    private final MediaFireClient mediaFire;
    private final MediaFireFileUpload upload;
    private final OnInstantUploadStatusListener callback;


    public MFRunnableInstantUpload(MediaFireClient mediaFire, MediaFireFileUpload upload, OnInstantUploadStatusListener callback) {

        this.mediaFire = mediaFire;
        this.upload = upload;
        this.callback = callback;
    }

    @Override
    public void run() {
        logger.info("upload thread started");

        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        if (this.upload.getFileSize() == 0) {
            params.put(PARAM_SIZE, this.upload.getFile().length());
        } else {
            params.put(PARAM_SIZE, this.upload.getFileSize());
        }

        if (!TextUtils.isEmpty(this.upload.getSha256Hash())) {
            params.put(PARAM_HASH, this.upload.getSha256Hash());
        } else {
            params.put(PARAM_HASH, mediaFire.getHasher().sha256(this.upload.getFile()));
        }

        params.put(PARAM_FILENAME, this.upload.getFileName());

        if (!TextUtils.isEmpty(this.upload.getFolderKey())) {
            params.put(PARAM_FOLDER_KEY, this.upload.getFolderKey());
        }

        if (!TextUtils.isEmpty(this.upload.getMediaFirePath())) {
            params.put(PARAM_FOLDER_PATH, this.upload.getMediaFirePath());
        }

        MediaFireApiRequest request = new MFApiRequest("/upload/instant.php", params, null, null);
        UploadInstantResponse response = null;
        try {
            response = mediaFire.sessionRequest(request, UploadInstantResponse.class);
        } catch (MediaFireException e) {
            if (this.callback != null) {
                this.callback.onInstantUploadMediaFireException(this.upload, e);
            }
            return;
        }

        if (this.callback != null) {
            if (response.hasError()) {
                this.callback.onInstantUploadApiError(this.upload, response);
            } else {
                String quickKey = response.getQuickKey();
                String fileName = response.getFileName();
                this.callback.onInstantUploadFinished(this.upload, quickKey, fileName);
            }

        }
    }

    public interface OnInstantUploadStatusListener {
        void onInstantUploadFinished(MediaFireFileUpload upload, String quickKey, String fileName);
        void onInstantUploadMediaFireException(MediaFireFileUpload upload, MediaFireException exception);
        void onInstantUploadApiError(MediaFireFileUpload upload, UploadInstantResponse response);
    }
}
