package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MFApiRequest;
import com.mediafire.sdk.MediaFireApiRequest;
import com.mediafire.sdk.MediaFireClient;
import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.response_models.upload.UploadGetWebUploadsResponse;
import com.mediafire.sdk.response_models.data_models.WebUploadsModel;
import com.mediafire.sdk.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

class MFRunnableGetWebUpload implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(MFRunnableGetWebUpload.class);

    private static final int TIME_BETWEEN_POLLS_MILLIS = 1000 * 5;
    private static final int MAX_POLLS = 24;

    private static final String PARAM_RESPONSE_FORMAT = "response_format";
    private static final String PARAM_UPLOAD_KEY = "upload_key";
    private static final String PARAM_ALL_WEB_UPLOADS = "all_web_uploads";

    private final MediaFireClient mediaFire;
    private final MediaFireWebUpload upload;
    private final String uploadKey;
    private final OnGetWebUploadStatusListener callback;
    private final int statusToFinish;

    public MFRunnableGetWebUpload(MediaFireClient mediaFire, MediaFireWebUpload upload, String uploadKey, OnGetWebUploadStatusListener callback, int statusToFinish) {

        this.mediaFire = mediaFire;
        this.upload = upload;
        this.uploadKey = uploadKey;
        this.callback = callback;
        this.statusToFinish = statusToFinish;
    }

    @Override
    public void run() {
        logger.info("upload thread started");

        final LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put(PARAM_UPLOAD_KEY, uploadKey);
        params.put(PARAM_ALL_WEB_UPLOADS, "yes");
        long pollCount = 0;
        do {
            MediaFireApiRequest request = new MFApiRequest("/upload/get_web_uploads.php", params, null, null);
            UploadGetWebUploadsResponse response;
            try {
                response = mediaFire.sessionRequest(request, UploadGetWebUploadsResponse.class);
            } catch (MediaFireException e) {
                if (callback != null) {
                    callback.onGetWebUploadsMediaFireException(upload, e);
                }
                return;
            }
            WebUploadsModel[] webUploadsArray = response.getWebUploads();

            if (webUploadsArray == null || webUploadsArray.length == 0) {
                pollCount++;
                continue;
            }

            WebUploadsModel webUpload = webUploadsArray[0];

            int statusCode = webUpload.getStatusCode();
            int errorStatus = webUpload.getErrorStatus();
            String description = webUpload.getStatus();
            String quickKey = webUpload.getQuickKey();
            String filename = webUpload.getFilename();

            if (!TextUtils.isEmpty(quickKey)) {
                if (callback != null) {
                    callback.onGetWebUploadsFinished(upload, quickKey, filename);
                }
                return;
            }

            if (statusCode >= statusToFinish) {
                if (callback != null) {
                    callback.onGetWebUploadsFinished(upload, quickKey, filename);
                }
                return;
            }

            if (errorStatus != 0) {
                if (callback != null) {
                    callback.onGetWebUploadsError(upload, statusCode, errorStatus, description);
                }
            }

            if (callback != null) {
                callback.onGetWebUploadsProgress(upload, statusCode, description);
            }

            try {
                Thread.sleep(TIME_BETWEEN_POLLS_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                if (callback != null) {
                    callback.onGetWebUploadsInterrupted(upload, e);
                }
                return;
            }

            pollCount++;
        } while (pollCount <= MAX_POLLS);

        if (callback != null) {
            callback.onGetWebUploadsFinishedWithoutVerificationOfCompletedUpload(upload);
        }
    }

    public interface OnGetWebUploadStatusListener {
        void onGetWebUploadsProgress(MediaFireWebUpload upload, int statusCode, String description);
        void onGetWebUploadsFinished(MediaFireWebUpload upload, String quickKey, String filename);
        void onGetWebUploadsError(MediaFireWebUpload upload, int statusCode, int errorStatus, String description);
        void onGetWebUploadsMediaFireException(MediaFireWebUpload upload, MediaFireException e);
        void onGetWebUploadsInterrupted(MediaFireWebUpload upload, InterruptedException e);
        void onGetWebUploadsFinishedWithoutVerificationOfCompletedUpload(MediaFireWebUpload upload);
    }
}
