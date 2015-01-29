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

    void exceptionDuringUpload(Upload upload, Exception exception) {
        if (mListener != null) {
            mListener.exceptionOccurred(upload, exception);
        }
    }

    void generalCancel(Upload upload, Result result) {
        if (mListener != null) {
            mListener.generalCancel(upload, result);
        }
    }

    void storageLimitExceeded(Upload upload) {
        if (mListener != null) {
            mListener.storageLimitExceeded(upload);
        }
    }

    void apiError(Upload upload, Result result) {
        if (mListener != null) {
            mListener.apiError(upload, result);
        }
    }

    void pollMaxAttemptsReached(Poll.PollUpload upload) {
        if (mListener != null) {
            mListener.maxPollsReached(upload);
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
                            mListener.uploadFinished(upload, checkResponse.getDuplicateQuickkey());
                        }
                    }
                    break;
                case DO_NOT_UPLOAD:
                default:
                    if (mListener != null) {
                        mListener.uploadFinished(upload, checkResponse.getDuplicateQuickkey());
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
            mListener.uploadFinished(upload, quickKey);
        }
    }

    void pollUpdate(Poll.PollUpload upload, int status) {
        if (mListener != null) {
            mListener.pollUpdate(upload, status);
        }
    }

    void instantFinished(Instant.InstantUpload upload, String quickKey) {
        if (mListener != null) {
            mListener.uploadFinished(upload, quickKey);
        }
    }

    void resumableProgress(Resumable.ResumableUpload upload, double percentFinished) {
        if (mListener != null) {
            mListener.uploadProgress(upload, percentFinished);
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
            mListener.uploadStarted(upload);
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
