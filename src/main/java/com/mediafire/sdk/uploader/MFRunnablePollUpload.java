package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MFApiRequest;
import com.mediafire.sdk.MediaFireApiRequest;
import com.mediafire.sdk.MediaFireClient;
import com.mediafire.sdk.MediaFireException;
import com.mediafire.sdk.response_models.upload.UploadPollUploadResponse;
import com.mediafire.sdk.response_models.data_models.DoUploadPollModel;
import com.mediafire.sdk.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

class MFRunnablePollUpload implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(MFRunnablePollUpload.class);

    private static final int TIME_BETWEEN_POLLS_MILLIS = 1000 * 5;
    private static final int MAX_POLLS = 24;

    private static final String PARAM_KEY = "key";

    private final MediaFireClient mediaFire;
    private final MediaFireFileUpload upload;
    private final String uploadKey;
    private final OnPollUploadStatusListener callback;
    private final int statusToFinish;

    public MFRunnablePollUpload(MediaFireClient mediaFire, MediaFireFileUpload upload, String uploadKey, OnPollUploadStatusListener callback, int statusToFinish) {

        this.mediaFire = mediaFire;
        this.upload = upload;
        this.uploadKey = uploadKey;
        this.callback = callback;
        this.statusToFinish = statusToFinish;
    }

    @Override
    public void run() {
        logger.info("upload thread started");

        final LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(PARAM_KEY, uploadKey);
        long pollCount = 0;
        do {
            MediaFireApiRequest request = new MFApiRequest("/upload/poll_upload.php", params, null, null);
            UploadPollUploadResponse response = null;
            try {
                response = mediaFire.sessionRequest(request, UploadPollUploadResponse.class);
            } catch (MediaFireException e) {
                if (this.callback != null) {
                    this.callback.onPollUploadMediaFireException(this.upload, e);
                }
                return;
            }

            if (callback != null && response.hasError()) {
                callback.onPollUploadApiError(this.upload, response);
                return;
            }

            DoUploadPollModel doUpload = response.getDoUpload();

            int fileErrorCode = doUpload.getFileErrorCode();
            int resultCode = doUpload.getResultCode();
            int statusCode = doUpload.getStatusCode();

            String description = doUpload.getDescription();

            String quickKey = doUpload.getQuickKey();
            String filename = doUpload.getFilename();

            if (TextUtils.isEmpty(quickKey)) {
                if (this.callback != null) {
                    this.callback.onPollUploadFinished(this.upload, quickKey, filename);
                }
                return;
            }

            if (statusCode >= statusToFinish) {
                if (this.callback != null) {
                    this.callback.onPollUploadFinished(this.upload, quickKey, filename);
                }
                return;
            }

            if (fileErrorCode != 0) {
                if (this.callback != null) {
                    this.callback.onPollUploadError(this.upload, fileErrorCode, resultCode, statusCode, description);
                }
                return;
            }

            if (resultCode != 0) {
                if (this.callback != null) {
                    this.callback.onPollUploadError(this.upload, fileErrorCode, resultCode, statusCode, description);
                }
                return;
            }

            if (this.callback != null) {
                this.callback.onPollUploadProgress(this.upload, statusCode, description);
            }

            try {
                Thread.sleep(TIME_BETWEEN_POLLS_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                if (this.callback != null) {
                    this.callback.onPollUploadThreadInterrupted(this.upload);
                }
                return;
            }
            pollCount++;
        } while (pollCount <= MAX_POLLS);

        if (this.callback != null) {
            this.callback.onPollUploadReachedMaxPollsWithoutConfirmationOfCompletion(this.upload);
        }
    }

    public interface OnPollUploadStatusListener {
        void onPollUploadFinished(MediaFireFileUpload upload, String quickKey, String fileName);
        void onPollUploadProgress(MediaFireFileUpload upload, int statusCode, String description);
        void onPollUploadError(MediaFireFileUpload upload, int fileErrorCode, int resultCode, int statusCode, String description);
        void onPollUploadMediaFireException(MediaFireFileUpload upload, MediaFireException e);
        void onPollUploadThreadInterrupted(MediaFireFileUpload upload);
        void onPollUploadApiError(MediaFireFileUpload upload, UploadPollUploadResponse response);
        void onPollUploadReachedMaxPollsWithoutConfirmationOfCompletion(MediaFireFileUpload upload);
    }
}
