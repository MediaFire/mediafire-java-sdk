package com.mediafire.sdk.uploader.call_manager;

import com.mediafire.sdk.api.clients.upload.UploadClient;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.ResponseCode;
import com.mediafire.sdk.api.responses.upload.CheckResponse;
import com.mediafire.sdk.api.responses.upload.InstantResponse;
import com.mediafire.sdk.api.responses.upload.PollResponse;
import com.mediafire.sdk.api.responses.upload.ResumableResponse;
import com.mediafire.sdk.uploader.calls.CallCheck;
import com.mediafire.sdk.uploader.calls.CallInstant;
import com.mediafire.sdk.uploader.calls.CallPoll;
import com.mediafire.sdk.uploader.calls.CallResumable;
import com.mediafire.sdk.uploader.upload_items.PollUpload;
import com.mediafire.sdk.uploader.upload_items.ResumableUpload;
import com.mediafire.sdk.uploader.upload_items.Upload;
import com.mediafire.sdk.uploader.uploaditem.ResumableBitmap;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Chris on 11/13/2014.
 */
public class UploadRunnable implements CallUploadObserver, Runnable {
    private final UploadClient mUploadClient;
    private final Upload mUpload;
    private final UploadUiInterface mUploadListener;

    public UploadRunnable(UploadClient uploadClient, Upload upload, UploadUiInterface uploadListener) {
        mUploadClient = uploadClient;
        mUpload = upload;
        mUploadListener = uploadListener;
    }

    @Override
    public void run() {
        CallCheck callCheck = new CallCheck(mUploadClient, mUpload);
        callCheck.addObserver(this);
        try {
            callCheck.check();
        } catch (IOException e) {
            e.printStackTrace();
            mUploadListener.onCancelled(mUpload, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            mUploadListener.onCancelled(mUpload, e.getMessage());
        }
    }

    @Override
    public void stateChange(Upload upload, ApiResponse apiResponse) {

        if (apiResponse == null) {
            mUploadListener.onCancelled(upload, "ApiResponse null");
            return;
        }

        if (apiResponse.hasError()) {
            mUploadListener.onCancelled(upload, "ApiResponse error: " + apiResponse.getMessage());
            return;
        }

        if (apiResponse.getErrorCode() != ResponseCode.NO_ERROR) {
            mUploadListener.onCancelled(upload, "ApiResponse error: " + apiResponse.getMessage());
            return;
        }


        Class clazz = apiResponse.getClass();

        if (clazz == CheckResponse.class) {
            checkUploadFinished(upload, (CheckResponse) apiResponse);
        } else if (clazz == InstantResponse.class) {
            instantUploadFinished(upload, (InstantResponse) apiResponse);
        } else if (clazz == ResumableResponse.class) {
            resumableUploadFinished(upload, (ResumableResponse) apiResponse);
        } else if (clazz == PollResponse.class) {
            pollUploadFinished(upload, (PollResponse) apiResponse);
        } else {

        }
    }

    private void checkUploadFinished(Upload upload, CheckResponse checkResponse) {
        int numUnits = checkResponse.getResumableUpload().getNumberOfUnits();
        int unitSize = checkResponse.getResumableUpload().getUnitSize();

        int count = checkResponse.getResumableUpload().getBitmap().getCount();
        List<Integer> words = checkResponse.getResumableUpload().getBitmap().getWords();
        ResumableBitmap bitmap = new ResumableBitmap(count, words);

        ResumableUpload resumableUpload;
        try {
            resumableUpload = new ResumableUpload(mUpload, numUnits, unitSize, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            mUploadListener.onCancelled(upload, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            mUploadListener.onCancelled(upload, e.getMessage());
        }

        //as a preventable infinite loop measure, an upload item cannot continue after upload/check.php if it has gone through the process 20x
        //20x is high, but it should never happen and will allow for more information gathering.
        if (checkResponse.getStorageLimitExceeded()) {
            mUploadListener.onCancelled(upload, "storage limit exceeded");
        } else if (checkResponse.getResumableUpload().areAllUnitsReady() && !uploadItem.getPollUploadKey().isEmpty()) {
            // all units are ready and we have the poll upload key. start polling.
            String pollKey = null;
            PollUpload pollUpload = new PollUpload(upload, pollKey);
            CallPoll callPoll = new CallPoll(mUploadClient, pollUpload);
            callPoll.poll();
        } else {
            if (checkResponse.doesHashExists()) { //hash does exist for the file
                if (!checkResponse.isInAccount()) { // hash which exists is not in the account
                    doInstantUpload(upload);
                } else { // hash exists and is in the account
                    boolean inFolder = checkResponse.isInFolder();
                    switch (upload.getOptions().getActionOnInAccount()) {
                        case UPLOAD_ALWAYS:
                            doInstantUpload(upload);
                            break;
                        case UPLOAD_IF_NOT_IN_FOLDER:
                            if (!inFolder) {
                                doInstantUpload(upload);
                            } else {
                                mUploadListener.onCompleted(upload, checkResponse.getDuplicateQuickkey());
                            }
                            break;
                        case DO_NOT_UPLOAD:
                        default:
                            mUploadListener.onCompleted(upload, checkResponse.getDuplicateQuickkey());
                            break;
                    }
                }
            } else {
                // hash does not exist. call resumable.
                if (checkResponse.getResumableUpload().getUnitSize() == 0) {
                    mUploadListener.onCancelled(upload, "resumable upload unit size 0");
                    return;
                }

                if (checkResponse.getResumableUpload().getNumberOfUnits() == 0) {
                    mUploadListener.onCancelled(upload, "resumable upload num units 0");
                    return;
                }

                if (checkResponse.getResumableUpload().areAllUnitsReady() && !mUploadItem.getPollUploadKey().isEmpty()) {
                    // all units are ready and we have the poll upload key. start polling.
                    PollUpload pollUpload = new PollUpload(upload, pollKey);
                    doPollUpload(pollUpload);
                } else {
                    // either we don't have the poll upload key or all units are not ready
                    doResumableUpload(null);
                }
            }
        }
    }

    private void instantUploadFinished(Upload upload, InstantResponse instantResponse) {

    }

    private void resumableUploadFinished(Upload upload, ResumableResponse resumableResponse) {

    }

    private void pollUploadFinished(Upload upload, PollResponse pollResponse) {

    }

    private void doInstantUpload(Upload upload) {
        CallInstant callInstant = new CallInstant(mUploadClient, upload);
        callInstant.instant();
    }

    private void doPollUpload(PollUpload upload) {
        CallPoll callPoll = new CallPoll(mUploadClient, upload);
        callPoll.poll();
    }

    private void doResumableUpload(ResumableUpload upload) {
        CallResumable callResumable = new CallResumable(mUploadClient, upload);
        try {
            callResumable.resumable();
        } catch (IOException e) {
            e.printStackTrace();
            mUploadListener.onCancelled(upload, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            mUploadListener.onCancelled(upload, e.getMessage());
        }
    }
}
