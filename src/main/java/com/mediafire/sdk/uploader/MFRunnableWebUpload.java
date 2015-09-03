package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MFApiRequest;
import com.mediafire.sdk.MediaFireApiRequest;
import com.mediafire.sdk.MediaFireClient;
import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.api.responses.UploadAddWebUploadResponse;
import com.mediafire.sdk.util.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;

class MFRunnableWebUpload implements Runnable {

    private static final String PARAM_URL = "url";
    private static final String PARAM_FILENAME = "filename";
    private static final String PARAM_FOLDER_KEY = "folder_key";

    private final MediaFireClient mediaFire;
    private final MediaFireWebUpload upload;
    private final OnWebUploadStatusListener callback;

    public MFRunnableWebUpload(MediaFireClient mediaFire, MediaFireWebUpload upload, OnWebUploadStatusListener callback) {

        this.mediaFire = mediaFire;
        this.upload = upload;
        this.callback = callback;
    }

    @Override
    public void run() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(PARAM_URL, this.upload.getUrl());
        params.put(PARAM_FILENAME, this.upload.getFileName());

        if (!TextUtils.isEmpty(this.upload.getFolderKey())) {
            params.put(PARAM_FOLDER_KEY, this.upload.getFolderKey());
        }

        MediaFireApiRequest request = new MFApiRequest("upload/add_web_upload.php", params, null, null);
        UploadAddWebUploadResponse response = null;
        try {
            response = mediaFire.sessionRequest(request, UploadAddWebUploadResponse.class);
        } catch (MediaFireException e) {
            if (this.callback != null) {
                this.callback.onWebUploadException(this.upload, e);
            }
            return;
        }
        String uploadKey = response.getUploadKey();

        if (this.callback != null) {
            this.callback.onWebUploadFinish(this.upload, uploadKey);
        }
    }

    public interface OnWebUploadStatusListener {
        void onWebUploadException(MediaFireWebUpload upload, MediaFireException e);
        void onWebUploadFinish(MediaFireWebUpload upload, String uploadKey);
    }
}
