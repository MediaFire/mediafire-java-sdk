package com.mediafire.sdk.uploading;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.upload.CheckResponse;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris on 12/22/2014.
 */
public class UploadManagerTestImpl extends UploadManager {

    boolean mResumableFinished;
    boolean mAddListener;
    boolean mRemoveListener;
    boolean mAddUpload;
    boolean mPurge;
    boolean mPause;
    boolean mResume;
    boolean mStartNextAvailableUpload;
    boolean mSortQueueByFileSize;
    boolean mMoveToFrontOfQueue;
    boolean mMoveToEndOfQueue;
    boolean mExceptionDuringUpload;
    boolean mResultInvalidDuringUpload;
    boolean mResponseObjectNull;
    boolean mStorageLimitExceeded;
    boolean mFileLargerThanStorageSpaceAvailable;
    boolean mResumableUploadPortionOfApiResponseMissing;
    boolean mBitmapPortionOfApiResponseMissing;
    boolean mCheckFinished;
    boolean mPollFinished;
    boolean mPollUpdate;
    boolean mPollMaxAttemptsReached;
    boolean mInstantFinished;
    boolean mApiError;
    boolean mResumableProgress;

    public UploadManagerTestImpl(int concurrentUploads, IHttp http, ITokenManager tokenManager) {
        super(concurrentUploads, http, tokenManager);
    }

    public final void resetTestFields() {
        mResumableFinished = false;
        mAddListener = false;
        mRemoveListener = false;
        mAddUpload = false;
        mPurge = false;
        mPause = false;
        mResume = false;
        mStartNextAvailableUpload = false;
        mSortQueueByFileSize = false;
        mMoveToFrontOfQueue = false;
        mMoveToEndOfQueue = false;
        mExceptionDuringUpload = false;
        mResultInvalidDuringUpload = false;
        mResponseObjectNull = false;
        mStorageLimitExceeded = false;
        mFileLargerThanStorageSpaceAvailable = false;
        mResumableUploadPortionOfApiResponseMissing = false;
        mBitmapPortionOfApiResponseMissing = false;
        mCheckFinished = false;
        mPollFinished = false;
        mPollUpdate = false;
        mPollMaxAttemptsReached = false;
        mInstantFinished = false;
        mApiError = false;
        mResumableProgress = false;
    }

    @Override
    void resumableFinished(Resumable.ResumableUpload upload, String responsePollKey, boolean allUnitsReady) {
        mResumableFinished = true;
    }

    @Override
    public void addListener(IUploadListener uploadListener) {
        mAddListener = true;
    }

    @Override
    public void removeListener(IUploadListener uploadListener) {
        mRemoveListener = true;
    }

    @Override
    public void addUpload(Upload upload) {
        mAddUpload = true;
    }

    @Override
    public void purge(boolean letCurrentUploadFinish) {
        mPurge = true;
    }

    @Override
    public void pause() {
        mPause = true;
    }

    @Override
    public void resume() {
        mResume = true;
    }

    @Override
    public void startNextAvailableUpload() {
        mStartNextAvailableUpload = true;
    }

    @Override
    public void sortQueueByFileSize(boolean ascending) {
        mSortQueueByFileSize = true;
    }

    @Override
    public void moveToFrontOfQueue(long id) {
        mMoveToFrontOfQueue = true;
    }

    @Override
    public void moveToEndOfQueue(long id) {
        mMoveToEndOfQueue = true;
    }

    @Override
    void exceptionDuringUpload(State state, Exception exception, Upload upload) {
        mExceptionDuringUpload = true;
    }

    @Override
    void resultInvalidDuringUpload(State state, Result result, Upload upload) {
        mResultInvalidDuringUpload = true;
    }

    @Override
    void responseObjectNull(State state, Result result, Upload upload) {
        mResponseObjectNull = true;
    }

    @Override
    void storageLimitExceeded(State state, Upload upload) {
        mStorageLimitExceeded = true;
    }

    @Override
    void fileLargerThanStorageSpaceAvailable(State state, Upload upload) {
        mFileLargerThanStorageSpaceAvailable = true;
    }

    @Override
    void resumableUploadPortionOfApiResponseMissing(Upload upload, CheckResponse apiResponse, Result result) {
        mResumableUploadPortionOfApiResponseMissing = true;
    }

    @Override
    void bitmapPortionOfApiResponseMissing(Upload upload, CheckResponse apiResponse, Result result) {
        mBitmapPortionOfApiResponseMissing = true;
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
    void apiError(State state, Upload upload, ApiResponse response, Result result) {
        mApiError = true;
    }

    @Override
    void resumableProgress(Resumable.ResumableUpload upload, double percentFinished) {
        mResumableProgress = true;
    }
}
