package com.mediafire.sdk.uploading;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.upload.CheckResponse;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Result;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris on 12/22/2014.
 */
public class UploadManager {

    private final int mConcurrentUploads;

    private final List<Upload> mUploads;
    private final List<IUploadListener> mListeners;
    private boolean mPaused;
    private final IHttp mHttp;
    private final ITokenManager mTokenManager;

    public UploadManager(int concurrentUploads, IHttp http, ITokenManager tokenManager) {
        mConcurrentUploads = concurrentUploads;
        mHttp = http;
        mTokenManager = tokenManager;
        mUploads = new LinkedList<Upload>();
        mListeners = new ArrayList<IUploadListener>();
        mPaused = true;
    }

    public void addListener(IUploadListener uploadListener) {
        mListeners.add(uploadListener);
    }

    public void removeListener(IUploadListener uploadListener) {
        mListeners.remove(uploadListener);
    }

    public void addUpload(Upload upload) {
        // TODO(cnajar) synchronization
        mUploads.add(upload);
    }

    public void purge(boolean letCurrentUploadFinish) {
        // TODO(cnajar) implement
    }

    public void pause() {
        // TODO(cnajar) implement
    }

    public void resume() {
        // TODO(cnajar) implement
    }

    public void startNextAvailableUpload() {
        // TODO(cnajar) implement
    }

    public void sortQueueByFileSize(boolean ascending) {
        // TODO(cnajar) implement
    }

    public void moveToFrontOfQueue(long id) {
        // TODO(cnajar) implement
    }

    public void moveToEndOfQueue(long id) {
        // TODO(cnajar) implement
    }

    void exceptionDuringUpload(State state, Exception exception, Upload upload) {
        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledException(upload.getId(), exception, state);
        }

        startNextAvailableUpload();
    }

    void resultInvalidDuringUpload(State state, Result result, Upload upload) {
        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledResultInvalid(upload.getId(), result, state);
        }

        startNextAvailableUpload();
    }

    void responseObjectNull(State state, Result result, Upload upload) {
        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledResponseObjectInvalid(upload.getId(), result, state);
        }

        startNextAvailableUpload();
    }

    void storageLimitExceeded(State state, Upload upload) {
        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledStorageLimitExceeded(upload.getId(), state);
        }

        startNextAvailableUpload();
    }

    void fileLargerThanStorageSpaceAvailable(State state, Upload upload) {
        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledFileLargerThanStorageSpaceAvailable(upload.getId(), state);
        }

        startNextAvailableUpload();
    }

    void resumableUploadPortionOfApiResponseMissing(Upload upload, CheckResponse apiResponse, Result result) {
        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledApiResponseMissingResumableUpload(upload.getId(), apiResponse, result);
        }

        startNextAvailableUpload();
    }

    void bitmapPortionOfApiResponseMissing(Upload upload, CheckResponse apiResponse, Result result) {
        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledApiResponseMissingBitmap(upload.getId(), apiResponse, result);
        }

        startNextAvailableUpload();
    }

    void checkFinished(Instant.InstantUpload upload, CheckResponse checkResponse) {
        // does the hash exist in the account?
        if (checkResponse.doesHashExistInAccount()) {
            boolean inFolder = checkResponse.isInFolder();
            switch (upload.getOptions().getActionOnInAccount()) {
                case UPLOAD_ALWAYS:
                    doInstantUpload(upload);
                    break;
                case UPLOAD_IF_NOT_IN_FOLDER:
                    if (!inFolder) {
                        doInstantUpload(upload);
                    } else {
                        for (IUploadListener listener : mListeners) {
                            listener.uploadFinished(upload.getId(), checkResponse.getDuplicateQuickkey());
                        }
                    }
                    break;
                case DO_NOT_UPLOAD:
                default:
                    for (IUploadListener listener : mListeners) {
                        listener.uploadFinished(upload.getId(), checkResponse.getDuplicateQuickkey());
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
        for (IUploadListener listener : mListeners) {
            listener.uploadFinished(upload.getId(), quickKey);
        }
        startNextAvailableUpload();
    }

    void pollUpdate(Poll.PollUpload upload, int status) {
        for (IUploadListener listener : mListeners) {
            listener.pollUpdate(upload.getId(), status);
        }
    }

    void pollMaxAttemptsReached(Poll.PollUpload upload) {
        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledPollAttempts(upload.getId());
        }
        startNextAvailableUpload();
    }

    void instantFinished(Instant.InstantUpload upload, String quickKey) {
        for (IUploadListener listener : mListeners) {
            listener.uploadFinished(upload.getId(), quickKey);
        }
        startNextAvailableUpload();
    }

    void apiError(State state, Upload upload, ApiResponse response, Result result) {
        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledApiError(upload.getId(), state, response, result);
        }
        startNextAvailableUpload();
    }

    void resumableProgress(Resumable.ResumableUpload upload, double percentFinished) {
        for (IUploadListener listener :mListeners) {
            listener.resumableUpdate(upload.getId(), percentFinished);
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

    private void doCheckUpload(Resumable.ResumableUpload upload) {
        Check check = new Check(upload, mHttp, mTokenManager, this);
    }

    private void doPollUpload(Resumable.ResumableUpload upload, String responsePollKey) {
        Poll.PollUpload pollUpload = new Poll.PollUpload(upload, responsePollKey);
        Poll poll = new Poll(pollUpload, mHttp, mTokenManager, this);
    }

    private void doInstantUpload(Instant.InstantUpload upload) {
        Instant instant = new Instant(upload, mHttp, mTokenManager, this);
    }

    private void doResumableUpload(Instant.InstantUpload upload, CheckResponse checkResponse) {
        CheckResponse.ResumableUpload resumableUploadObj = checkResponse.getResumableUpload();
        int numUnits = resumableUploadObj.getNumberOfUnits();
        int unitSize = resumableUploadObj.getUnitSize();
        int count = resumableUploadObj.getBitmap().getCount();
        List<Integer> words = resumableUploadObj.getBitmap().getWords();

        Resumable.ResumableUpload resumableUpload = new Resumable.ResumableUpload(upload, upload.getHash(), numUnits, unitSize, count, words);
        Resumable resumable = new Resumable(resumableUpload, mHttp, mTokenManager, this);
    }

    /**
    * Created by Chris on 12/22/2014.
    */
    public static interface IUploadListener {
        public void uploadCancelledException(long id, Exception exception, State state);
        public void uploadCancelledResultInvalid(long id, Result result, State state);
        public void uploadCancelledResponseObjectInvalid(long id, Result result, State state);
        public void uploadCancelledStorageLimitExceeded(long id, State state);
        public void uploadCancelledFileLargerThanStorageSpaceAvailable(long id, State state);
        public void uploadCancelledPollAttempts(long id);
        public void uploadCancelledApiError(long id, State state, ApiResponse response, Result result);
        public void uploadCancelledApiResponseMissingResumableUpload(long id, CheckResponse apiResponse, Result result);
        public void uploadCancelledApiResponseMissingBitmap(long id, CheckResponse apiResponse, Result result);

        public void uploadStarted(long id);
        public void uploadFinished(long id, String quickKey);
        public void pollUpdate(long id, int status);
        public void resumableUpdate(long id, double percentFinished);
    }
}
