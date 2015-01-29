package com.mediafire.sdk.uploading;

import com.mediafire.sdk.api.responses.upload.CheckResponse;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris on 12/22/2014.
 */
public class UploadProcessTestImpl extends UploadProcess {

    boolean mResumableFinished;
    boolean mStorageLimitExceeded;
    boolean mCheckFinished;
    boolean mPollFinished;
    boolean mPollUpdate;
    boolean mPollMaxAttemptsReached;
    boolean mInstantFinished;
    boolean mApiError;
    boolean mResumableProgress;
    boolean mGeneralError;
    boolean mExceptionOccurred;
    boolean mUploadStarted;

    public UploadProcessTestImpl(HttpHandler http, TokenManager tokenManager, Upload upload) {
        super(http, tokenManager, upload);
    }

    @Override
    void resumableFinished(Resumable.ResumableUpload upload, String responsePollKey, boolean allUnitsReady) {
        mResumableFinished = true;
    }

    @Override
    void storageLimitExceeded(Upload upload) {
        mStorageLimitExceeded = true;
    }

    @Override
    void checkFinished(Instant.InstantUpload upload, CheckResponse checkResponse) {
        mCheckFinished = true;
    }

    @Override
    void pollFinished(Poll.PollUpload upload, String quickKey) {
        mPollFinished = true;
    }

    @Override
    void pollUpdate(Poll.PollUpload upload, int status) {
        mPollUpdate = true;
    }

    @Override
    void pollMaxAttemptsReached(Poll.PollUpload upload) {
        mPollMaxAttemptsReached = true;
    }

    @Override
    void instantFinished(Instant.InstantUpload upload, String quickKey) {
        mInstantFinished = true;
    }

    @Override
    void resumableProgress(Resumable.ResumableUpload upload, double percentFinished) {
        mResumableProgress = true;
    }

    @Override
    void apiError(Upload upload, Result result) {
        super.apiError(upload, result);
        mApiError = true;
    }

    @Override
    void generalCancel(Upload upload, Result result) {
        super.generalCancel(upload, result);
        mGeneralError = true;
    }

    @Override
    void exceptionDuringUpload(Upload upload, Exception exception) {
        super.exceptionDuringUpload(upload, exception);
        mExceptionOccurred = true;
    }

    @Override
    void uploadStarted(Upload upload) {
        super.uploadStarted(upload);
        mUploadStarted = true;
    }


}
