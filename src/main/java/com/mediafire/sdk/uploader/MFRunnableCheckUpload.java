package com.mediafire.sdk.uploader;

import com.mediafire.sdk.*;
import com.mediafire.sdk.api.responses.UploadCheckResponse;
import com.mediafire.sdk.util.TextUtils;

import java.util.LinkedHashMap;

class MFRunnableCheckUpload implements Runnable {

    private static final String PARAM_RESPONSE_FORMAT = "response_format";
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
        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(PARAM_RESUMABLE, this.upload.isResumable() ? "yes" : "no");
        params.put(PARAM_SIZE, this.upload.getFileSize());
        params.put(PARAM_HASH, this.upload.getSha256Hash());
        params.put(PARAM_FILENAME, this.upload.getFileName());
        if (!TextUtils.isEmpty(this.upload.getFolderKey())) {
            params.put(PARAM_FOLDER_KEY, this.upload.getFolderKey());
        }

        if (!TextUtils.isEmpty(this.upload.getMediaFirePath())) {
            params.put(PARAM_FOLDER_PATH, this.upload.getMediaFirePath());
        }

        MediaFireApiRequest request = new MFApiRequest("upload/check.php", params, null, null);

        try {
            UploadCheckResponse response = mediaFire.sessionRequest(request, UploadCheckResponse.class);

            if (this.callback != null) {
                this.callback.onCheckUploadFinished(this.upload, response);
            }
        } catch (MediaFireException e) {
            if (this.callback != null) {
                this.callback.onCheckUploadMediaFireException(this.upload, e);
            }
        }
    }

    public interface OnCheckUploadStatusListener {
        void onCheckUploadFinished(MediaFireFileUpload mediaFireUpload, UploadCheckResponse response);
        void onCheckUploadMediaFireException(MediaFireFileUpload mediaFireUpload, MediaFireException e);
    }
}
