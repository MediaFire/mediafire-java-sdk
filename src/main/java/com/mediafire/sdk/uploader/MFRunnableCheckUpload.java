package com.mediafire.sdk.uploader;

import com.mediafire.sdk.*;
import com.mediafire.sdk.response_models.MediaFireApiResponse;
import com.mediafire.sdk.response_models.upload.UploadCheckResponse;
import com.mediafire.sdk.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

class MFRunnableCheckUpload implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(MFRunnableCheckUpload.class);

    private static final String PARAM_FILENAME = "filename";
    private static final String PARAM_FOLDER_KEY = "folder_key";
    private static final String PARAM_FOLDER_PATH = "path";
    private static final String PARAM_RESUMABLE = "resumable";
    private static final String PARAM_SIZE = "size";
    private static final String PARAM_HASH = "hash";

    private final MediaFireFileUpload upload;
    private final MediaFireClient mediaFire;
    private final OnCheckUploadStatusListener callback;

    public MFRunnableCheckUpload(MediaFireClient mediaFire, MediaFireFileUpload upload, OnCheckUploadStatusListener callback) {
        this.upload = upload;
        this.mediaFire = mediaFire;
        this.callback = callback;
    }

    @Override
    public void run() {
        this.logger.info("upload thread started");
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put(PARAM_RESUMABLE, this.upload.isResumable() ? "yes" : "no");

        if (this.upload.getFileSize() == 0) {
            params.put(PARAM_SIZE, this.upload.getFile().length());
        } else {
            params.put(PARAM_SIZE, this.upload.getFileSize());
        }

        if (!TextUtils.isEmpty(this.upload.getSha256Hash())) {
            params.put(PARAM_HASH, this.upload.getSha256Hash());
        } else {
            params.put(PARAM_HASH, this.mediaFire.getHasher().sha256(this.upload.getFile()));
        }

        params.put(PARAM_FILENAME, this.upload.getFileName());

        if (!TextUtils.isEmpty(this.upload.getFolderKey())) {
            params.put(PARAM_FOLDER_KEY, this.upload.getFolderKey());
        }

        if (!TextUtils.isEmpty(this.upload.getMediaFirePath())) {
            params.put(PARAM_FOLDER_PATH, this.upload.getMediaFirePath());
        }

        MediaFireApiRequest request = new MFApiRequest("/upload/check.php", params, null, null);

        try {
            UploadCheckResponse response = this.mediaFire.sessionRequest(request, UploadCheckResponse.class);

            if (this.callback != null) {
                if (response.hasError()) {
                    this.callback.onCheckUploadApiError(this.upload, response);
                } else {
                    this.callback.onCheckUploadFinished(this.upload, response);
                }
            }
        } catch (MediaFireException e) {
            if (this.callback != null) {
                this.callback.onCheckUploadSdkException(this.upload, e);
            }
        }
    }

    public interface OnCheckUploadStatusListener {
        void onCheckUploadFinished(MediaFireFileUpload upload, UploadCheckResponse response);
        void onCheckUploadSdkException(MediaFireFileUpload upload, MediaFireException e);
        void onCheckUploadApiError(MediaFireFileUpload upload, MediaFireApiResponse response);
    }
}
