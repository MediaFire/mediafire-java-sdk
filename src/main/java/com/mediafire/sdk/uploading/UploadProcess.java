package com.mediafire.sdk.uploading;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.upload.CheckResponse;
import com.mediafire.sdk.api.responses.upload.ResumableUpload;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Result;

import java.util.List;

/**
 * Created by Chris on 1/15/2015.
 */
public class UploadProcess implements Runnable {

    private UploadProcessListener mListener;
    private final HttpHandler mHttp;
    private final TokenManager mTokenManager;
    private final Upload mUpload;


    public UploadProcess(HttpHandler http, TokenManager tokenManager, Upload upload) {
        mHttp = http;
        mTokenManager = tokenManager;
        mUpload = upload;
    }

    public void setUploadProcessListener(UploadProcessListener uploadListener) {
        mListener = uploadListener;
    }

    @Override
    public void run() {
        Check check = new Check(mUpload, mHttp, mTokenManager, this);
        check.run();
    }

    void exceptionDuringUpload(State state, Exception exception, Upload upload) { 
        if (mListener != null) {
            mListener.uploadCancelledException(upload.getId(), upload.getInfo(), exception, state);
        }
    }

    void resultInvalidDuringUpload(State state, Result result, Upload upload) {
        if (mListener != null) {
            mListener.uploadCancelledResultInvalid(upload.getId(), upload.getInfo(), result, state);
        }
    }

    void responseObjectNull(State state, Result result, Upload upload) {
        if (mListener != null) {
            mListener.uploadCancelledResponseObjectInvalid(upload.getId(), upload.getInfo(), result, state);
        }
    }

    void storageLimitExceeded(State state, Upload upload) {
        if (mListener != null) {
            mListener.uploadCancelledStorageLimitExceeded(upload.getId(), upload.getInfo(), state);
        }
    }

    void fileLargerThanStorageSpaceAvailable(State state, Upload upload) {
        if (mListener != null) {
            mListener.uploadCancelledFileLargerThanStorageSpaceAvailable(upload.getId(), upload.getInfo(), state);
        }
    }

    void resumableUploadPortionOfApiResponseMissing(Upload upload, CheckResponse apiResponse, Result result) {
        if (mListener != null) {
            mListener.uploadCancelledApiResponseMissingResumableUpload(upload.getId(), upload.getInfo(), apiResponse, result);
        }
    }

    void bitmapPortionOfApiResponseMissing(Upload upload, CheckResponse apiResponse, Result result) {
        if (mListener != null) {
            mListener.uploadCancelledApiResponseMissingBitmap(upload.getId(), upload.getInfo(), apiResponse, result);
        }
    }

    void checkFinished(Instant.InstantUpload upload, CheckResponse checkResponse) {
        // does the hash exist in the account?
        if (checkResponse.doesHashExistInAccount()) {
            boolean inFolder = checkResponse.isInFolder();
            Upload.Options.ActionOnInAccount actionOnInAccount = upload.getOptions().getActionOnInAccount();
            switch (actionOnInAccount) {
                case UPLOAD_ALWAYS:
                    doInstantUpload(upload);
                    break;
                case UPLOAD_IF_NOT_IN_FOLDER:
                    if (!inFolder) {
                        doInstantUpload(upload);
                    } else {
                        if (mListener != null) {
                            mListener.uploadFinished(upload.getId(), upload.getInfo(), checkResponse.getDuplicateQuickkey());
                        }
                    }
                    break;
                case DO_NOT_UPLOAD:
                default:
                    if (mListener != null) {
                        mListener.uploadFinished(upload.getId(), upload.getInfo(), checkResponse.getDuplicateQuickkey());
                    }
                    break;
            }
            return;
        }

        // does the hash exist at all? (not in user account)
        if (checkResponse.doesHashExist()) {
            // hash exists
            doInstantUpload(upload);
        } else {
            // hash doesn't exist
            doResumableUpload(upload, checkResponse);
        }
    }

    void pollFinished(Poll.PollUpload upload, String quickKey) {
        if (mListener != null) {
            mListener.uploadFinished(upload.getId(), upload.getInfo(), quickKey);
        }
    }

    void pollUpdate(Poll.PollUpload upload, int status) {
        if (mListener != null) {
            mListener.pollUpdate(upload.getId(), upload.getInfo(), status);
        }
    }

    void pollMaxAttemptsReached(Poll.PollUpload upload) {
        if (mListener != null) {
            mListener.uploadCancelledPollAttempts(upload.getId(), upload.getInfo());
        }
    }

    void instantFinished(Instant.InstantUpload upload, String quickKey) {
        if (mListener != null) {
            mListener.uploadFinished(upload.getId(), upload.getInfo(), quickKey);
        }
    }

    void apiError(State state, Upload upload, ApiResponse response, Result result) {
        if (mListener != null) {
            mListener.uploadCancelledApiError(upload.getId(), upload.getInfo(), state, response, result);
        }
    }

    void resumableProgress(Resumable.ResumableUpload upload, double percentFinished) {
        if (mListener != null) {
            mListener.resumableUpdate(upload.getId(), upload.getInfo(), percentFinished);
        }
    }

    void resumableFinished(Resumable.ResumableUpload upload, String responsePollKey, boolean allUnitsReady) {
        // poll if poll key exists and all units ready
        if (responsePollKey != null && allUnitsReady) {
            doPollUpload(upload, responsePollKey);
        } else {
            doCheckUpload(upload);
        }
    }

    void uploadStarted(Upload upload) {
        if (mListener != null) {
            mListener.uploadStarted(upload.getId(), upload.getInfo());
        }
    }

    private void doCheckUpload(Resumable.ResumableUpload upload) {
        Check check = new Check(upload, mHttp, mTokenManager, this);
        check.run();
    }

    private void doPollUpload(Resumable.ResumableUpload upload, String responsePollKey) {
        Poll.PollUpload pollUpload = new Poll.PollUpload(upload, responsePollKey);
        Poll poll = new Poll(pollUpload, mHttp, mTokenManager, this, Poll.DEFAULT_SLEEP_TIME, Poll.DEFAULT_MAX_POLLS);
        poll.run();
    }

    private void doInstantUpload(Instant.InstantUpload upload) {
        Instant instant = new Instant(upload, mHttp, mTokenManager, this);
        instant.run();
    }

    private void doResumableUpload(Instant.InstantUpload upload, CheckResponse checkResponse) {
        ResumableUpload resumableUploadObj = checkResponse.getResumableUpload();
        int numUnits = resumableUploadObj.getNumberOfUnits();
        int unitSize = resumableUploadObj.getUnitSize();
        int count = resumableUploadObj.getBitmap().getCount();
        List<Integer> words = resumableUploadObj.getBitmap().getWords();

        Resumable.ResumableUpload resumableUpload = new Resumable.ResumableUpload(upload, upload.getHash(), numUnits, unitSize, count, words);
        Resumable resumable = new Resumable(resumableUpload, mHttp, mTokenManager, this);
        resumable.run();
    }
}
