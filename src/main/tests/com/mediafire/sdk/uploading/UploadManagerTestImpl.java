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
    
    public final void printTestFields() {
        System.out.println("mResumableFinished                          " + mResumableFinished);
        System.out.println("mAddListener                                " + mAddListener);
        System.out.println("mRemoveListener                             " + mRemoveListener);
        System.out.println("mAddUpload                                  " + mAddUpload);
        System.out.println("mPurge                                      " + mPurge);
        System.out.println("mPause                                      " + mPause);
        System.out.println("mResume                                     " + mResume);
        System.out.println("mStartNextAvailableUpload                   " + mStartNextAvailableUpload);
        System.out.println("mSortQueueByFileSize                        " + mSortQueueByFileSize);
        System.out.println("mMoveToFrontOfQueue                         " + mMoveToFrontOfQueue);
        System.out.println("mMoveToEndOfQueue                           " + mMoveToEndOfQueue);
        System.out.println("mExceptionDuringUpload                      " + mExceptionDuringUpload);
        System.out.println("mResultInvalidDuringUpload                  " + mResultInvalidDuringUpload);
        System.out.println("mResponseObjectNull                         " + mResponseObjectNull);
        System.out.println("mStorageLimitExceeded                       " + mStorageLimitExceeded);
        System.out.println("mFileLargerThanStorageSpaceAvailable        " + mFileLargerThanStorageSpaceAvailable);
        System.out.println("mResumableUploadPortionOfApiResponseMissing " + mResumableUploadPortionOfApiResponseMissing);
        System.out.println("mBitmapPortionOfApiResponseMissing          " + mBitmapPortionOfApiResponseMissing);
        System.out.println("mCheckFinished                              " + mCheckFinished);
        System.out.println("mPollFinished                               " + mPollFinished);
        System.out.println("mPollUpdate                                 " + mPollUpdate);
        System.out.println("mPollMaxAttemptsReached                     " + mPollMaxAttemptsReached);
        System.out.println("mInstantFinished                            " + mInstantFinished);
        System.out.println("mApiError                                   " + mApiError);
        System.out.println("mResumableProgress                          " + mResumableProgress);
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
    public void purge(boolean shutdown) {
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
