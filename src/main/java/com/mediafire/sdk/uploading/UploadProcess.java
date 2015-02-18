package com.mediafire.sdk.uploading;

import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris on 1/15/2015.
 */
public class UploadProcess implements Runnable {
    private UploadProcessListener mListener;
    private final HttpHandler mHttp;
    private final TokenManager mTokenManager;
    private final Upload mUpload;
    private boolean mDebug;


    public UploadProcess(HttpHandler http, TokenManager tokenManager, Upload upload) {
        mHttp = http;
        mTokenManager = tokenManager;
        mUpload = upload;
    }

    public void setUploadProcessListener(UploadProcessListener uploadListener) {
        mListener = uploadListener;
    }

    public void debug(boolean debug) {
        mDebug = debug;
    }

    @Override
    public void run() {
        if (mDebug) {
            System.out.println("UploadProcess run() called for " + mUpload);
        }
        Check check = new Check(mUpload, mHttp, mTokenManager, this);
        if (mDebug) {
            check.debug(true);
        }
        check.run();
    }

    void exceptionDuringUpload(Upload upload, Exception exception) {
        if (mDebug) {
            System.out.println("exception during upload for " + mUpload + ". Exception: " + exception);
            exception.printStackTrace();
        }
        if (mListener != null) {
            mListener.exceptionOccurred(upload, exception);
        }
    }

    void generalCancel(Upload upload, Result result) {
        if (mDebug) {
            System.out.println("cancelled upload for " + mUpload);
        }
        if (mListener != null) {
            mListener.generalCancel(upload, result);
        }
    }

    void storageLimitExceeded(Upload upload) {
        if (mDebug) {
            System.out.println("storage limit exceeded during upload for " + mUpload);
        }
        if (mListener != null) {
            mListener.storageLimitExceeded(upload);
        }
    }

    void apiError(Upload upload, Result result) {
        if (mDebug) {
            System.out.println("api error during upload for " + mUpload);
        }
        if (mListener != null) {
            mListener.apiError(upload, result);
        }
    }

    void pollMaxAttemptsReached(Upload upload) {
        if (mDebug) {
            System.out.println("max poll attempts reached during upload for " + mUpload);
        }
        if (mListener != null) {
            mListener.maxPollsReached(upload);
        }
    }

    void checkFinished(Upload upload) {
        if (mDebug) {
            System.out.println("checkFinished() for " + mUpload);
        }
        if (upload.isHashInAccount()) {
            Upload.Options.ActionOnInAccount actionOnInAccount = upload.getOptions().getActionOnInAccount();
            switch (actionOnInAccount) {
                case UPLOAD_ALWAYS:
                    doInstantUpload(upload);
                    break;
                case UPLOAD_IF_NOT_IN_FOLDER:
                    if (upload.isHashInFolder()) {
                        doInstantUpload(upload);
                    } else {
                        if (mListener != null) {
                            mListener.uploadFinished(upload, upload.getDuplicateQuickKey());
                        }
                    }
                    break;
                case DO_NOT_UPLOAD:
                default:
                    if (mListener != null) {
                        mListener.uploadFinished(upload, upload.getDuplicateQuickKey());
                    }
                    break;
            }
            return;
        }

        if (upload.isHashInMediaFire()) {
            // hash exists
            doInstantUpload(upload);
        } else {
            // hash doesn't exist
            doResumableUpload(upload);
        }
    }

    void pollFinished(Upload upload, String quickKey) {
        if (mDebug) {
            System.out.println("poll finished for " + mUpload);
        }
        if (mListener != null) {
            mListener.uploadFinished(upload, quickKey);
        }
    }

    void pollUpdate(Upload upload, int status) {
        if (mDebug) {
            System.out.println("poll update for " + mUpload);
        }
        if (mListener != null) {
            mListener.pollUpdate(upload, status);
        }
    }

    void instantFinished(Upload upload) {
        if (mDebug) {
            System.out.println("instant finished for " + mUpload);
        }
        if (mListener != null) {
            mListener.uploadFinished(upload, upload.getNewQuickKey());
        }
    }

    void resumableProgress(Upload upload, double percentFinished) {
        if (mDebug) {
            System.out.println("resumable progress for " + mUpload);
        }
        if (mListener != null) {
            mListener.uploadProgress(upload, percentFinished);
        }
    }

    void resumableFinished(Upload upload) {
        if (mDebug) {
            System.out.println("resumable finished for " + mUpload);
        }
        if (upload.areAllUnitsReady()) {
            doPollUpload(upload, upload.getPollKey());
        } else {
            doCheckUpload(upload);
        }
    }

    void uploadStarted(Upload upload) {
        if (mDebug) {
            System.out.println("upload started for " + mUpload);
        }
        if (mListener != null) {
            mListener.uploadStarted(upload);
        }
    }

    private void doCheckUpload(Upload upload) {
        Check check = new Check(upload, mHttp, mTokenManager, this);
        if (mDebug) {
            check.debug(true);
        }
        check.run();
    }

    private void doPollUpload(Upload upload, String responsePollKey) {
        upload.setPollKey(responsePollKey);
        Poll poll = new Poll(upload, mHttp, mTokenManager, this, Poll.DEFAULT_SLEEP_TIME, Poll.DEFAULT_MAX_POLLS);
        if (mDebug) {
            poll.debug(true);
        }
        poll.run();
    }

    private void doInstantUpload(Upload upload) {
        Instant instant = new Instant(upload, mHttp, mTokenManager, this);
        if (mDebug) {
            instant.debug(true);
        }
        instant.run();
    }

    private void doResumableUpload(Upload upload) {
        Resumable resumable = new Resumable(upload, mHttp, mTokenManager, this);
        if (mDebug) {
            resumable.debug(true);
        }
        resumable.run();
    }
}
