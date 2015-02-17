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
            System.out.println("upload has started for " + mUpload.getFile());
        }
        Check check = new Check(mUpload, mHttp, mTokenManager, this);
        if (mDebug) {
            check.debug(true);
        }
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
        if (checkResponse.doesHashExistInAccount()) {
            if (mDebug) {
                System.out.println("the hash for " + mUpload.getFile() + " exists in the users account");
            }
            boolean inFolder = checkResponse.isInFolder();
            Upload.Options.ActionOnInAccount actionOnInAccount = upload.getOptions().getActionOnInAccount();
            switch (actionOnInAccount) {
                case UPLOAD_ALWAYS:
                    if (mDebug) {
                        System.out.println("the option UPLOAD_ALWAYS has been set for " + mUpload.getFile() + ", so upload/instant will start");
                    }
                    doInstantUpload(upload);
                    break;
                case UPLOAD_IF_NOT_IN_FOLDER:
                    if (!inFolder) {
                        if (mDebug) {
                            System.out.println("the option UPLOAD_IF_NOT_IN_FOLDER has been set for " + mUpload.getFile() + ", and the hash does not exist in the folder passed, so upload/instant will start");
                        }
                        doInstantUpload(upload);
                    } else {
                        if (mDebug) {
                            System.out.println("the option UPLOAD_IF_NOT_IN_FOLDER has been set for " + mUpload.getFile() + ", but the hash does exist in the folder passed, so the upload is finished");
                        }
                        if (mListener != null) {
                            mListener.uploadFinished(upload, checkResponse.getDuplicateQuickkey());
                        }
                    }
                    break;
                case DO_NOT_UPLOAD:
                default:
                    if (mDebug) {
                        System.out.println("the option DO_NOT_UPLOAD has been set for " + mUpload.getFile() + ", so the upload is finished");
                    }
                    if (mListener != null) {
                        mListener.uploadFinished(upload, checkResponse.getDuplicateQuickkey());
                    }
                    break;
            }
            return;
        }

        if (mDebug) {
            System.out.println("the hash for " + mUpload.getFile() + " does not exist in the users account");
        }

        if (checkResponse.doesHashExist()) {
            if (mDebug) {
                System.out.println("the hash for " + mUpload.getFile() + " exists on MediaFire, so upload/instant will start");
            }
            // hash exists
            doInstantUpload(upload);
        } else {
            if (mDebug) {
                System.out.println("the hash for " + mUpload.getFile() + " does not exist on MediaFire, so upload/resumable will start");
            }
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

        if (allUnitsReady) {
            if (mDebug) {
                System.out.println("upload/resumable finished and all units are ready for " + mUpload.getFile() + " and upload/poll_upload can start with upload key: " + responsePollKey);
            }
            doPollUpload(upload, responsePollKey);
        } else {
            if (mDebug) {
                System.out.println("upload/resumable finished and all units are not ready for " + mUpload.getFile() + " so upload/check can start");
            }
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
        if (mDebug) {
            check.debug(true);
        }
        check.run();
    }

    private void doPollUpload(Resumable.ResumableUpload upload, String responsePollKey) {
        Poll.PollUpload pollUpload = new Poll.PollUpload(upload, responsePollKey);
        Poll poll = new Poll(pollUpload, mHttp, mTokenManager, this, Poll.DEFAULT_SLEEP_TIME, Poll.DEFAULT_MAX_POLLS);
        if (mDebug) {
            poll.debug(true);
        }
        poll.run();
    }

    private void doInstantUpload(Instant.InstantUpload upload) {
        Instant instant = new Instant(upload, mHttp, mTokenManager, this);
        if (mDebug) {
            instant.debug(true);
        }
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
        if (mDebug) {
            resumable.debug(true);
        }
        resumable.run();
    }
}
