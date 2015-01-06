package com.mediafire.sdk.uploading;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.upload.CheckResponse;
import com.mediafire.sdk.api.responses.upload.ResumableUpload;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Result;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Chris on 12/22/2014.
 */
public class UploadManager implements IUploadManager<Upload> {

    private final ArrayList<IUploadListener> mListeners;
    private final PausableExecutor mExecutor;
    private final IHttp mHttp;
    private final ITokenManager mTokenManager;
    private final LinkedList<Upload> mUploadList;

    // debugging
    private boolean mDebug;

    // lock
    private Object mUploadListLock = new Object();

    public UploadManager(int numUploads, IHttp http, ITokenManager tokenManager) {
        mHttp = http;
        mTokenManager = tokenManager;
        mUploadList = new LinkedList<Upload>();
        mListeners = new ArrayList<IUploadListener>();
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        mExecutor = new PausableExecutor(1, numUploads, 1, TimeUnit.SECONDS, workQueue);
    }

    public void debug(boolean on) {
        mDebug = on;
    }

    public void addListener(IUploadListener uploadListener) {
        if (mDebug) {
            System.out.println(getClass() + " - addListener");
        }
        mListeners.add(uploadListener);
    }

    public void removeListener(IUploadListener uploadListener) {
        if (mDebug) {
            System.out.println(getClass() + " - removeListener");
        }
        mListeners.remove(uploadListener);
    }

    @Override
    public void addUpload(Upload upload) {
        if (mDebug) {
            System.out.println(getClass() + " - addUpload");
        }

        mUploadList.add(upload);
    }

    @Override
    public void purge(boolean shutdown) {
        if (mDebug) {
            System.out.println(getClass() + " - purge");
        }
        mUploadList.clear();
        mExecutor.getQueue().clear();

        if (shutdown) {
            mExecutor.shutdownNow();
        }
    }

    @Override
    public void pause() {
        if (mDebug) {
            System.out.println(getClass() + " - pause");
        }
        mExecutor.pause();
    }

    @Override
    public void resume() {
        if (mDebug) {
            System.out.println(getClass() + " - resume");
        }
        mExecutor.resume();
        startNextAvailableUpload();
    }

    @Override
    public boolean isPaused() {
        if (mDebug) {
            System.out.println(getClass() + " - isPaused");
        }
        return mExecutor.isPaused();
    }

    @Override
    public List<Upload> getQueuedUploads() {
        if (mDebug) {
            System.out.println(getClass() + " - getQueuedUploads");
        }
        return mUploadList;
    }

    public BlockingQueue<Runnable> getQueuedRunnables() {
        if (mDebug) {
            System.out.println(getClass() + " - getQueuedRunnables");
        }
        return mExecutor.getQueue();
    }

    @Override
    public List<UploadRunnable> getRunningUploads() {
        if (mDebug) {
            System.out.println(getClass() + " - getRunningUploads");
        }
        return mExecutor.getRunningTasks();
    }

    @Override
    public void startNextAvailableUpload() {
        if (mDebug) {
            System.out.println(getClass() + " - startNextAvailableUpload");
        }

        if (mExecutor.isPaused()) {
            if (mDebug) {
                System.out.println(getClass() + " - startNextAvailableUpload - executor paused, not starting an upload");
            }
            return;
        }

        synchronized (mUploadListLock) {
            if (mDebug) {
                System.out.println(getClass() + " - startNextAvailableUpload - begin start attempt");
            }
            while (!mUploadList.isEmpty() && mExecutor.getMaximumPoolSize() >= mExecutor.getRunningTasks().size()) {
                if (!mUploadList.isEmpty()) {
                    Upload upload = mUploadList.get(0);
                    mUploadList.remove(0);
                    Check check = new Check(upload, mHttp, mTokenManager, this);
                    mExecutor.execute(check);
                }
            }
        }
    }

    @Override
    public void sortQueueByFileSize(boolean ascending) {
        if (mDebug) {
            System.out.println(getClass() + " - sortQueueByFileSize");
        }
        if (!mExecutor.isPaused()) {
            return;
        }

        synchronized (mUploadListLock) {
            Upload upload;
            int slot;
            for (int currentSlot = 1; currentSlot < mUploadList.size(); currentSlot++) {
                upload = mUploadList.get(currentSlot);
                slot = currentSlot;

                if (ascending) {
                    sortAscending(currentSlot, upload);
                } else {
                    sortDescending(currentSlot, upload);
                }

                mUploadList.set(slot, upload);
            }
        }
    }

    private void sortDescending(int slot, Upload upload) {
        while (slot > 0 && mUploadList.get(slot - 1).getFile().length() < upload.getFile().length()) {
            mUploadList.set(slot, mUploadList.get(slot - 1));
            slot--;
        }
    }

    private void sortAscending(int slot, Upload upload) {
        while (slot > 0 && mUploadList.get(slot - 1).getFile().length() < upload.getFile().length()) {
            mUploadList.set(slot, mUploadList.get(slot - 1));
            slot--;
        }
    }

    @Override
    public void moveToFrontOfQueue(long id) {
        if (mDebug) {
            System.out.println(getClass() + " - moveToFrontOfQueue");
        }
        if (!mExecutor.isPaused()) {
            return;
        }
        synchronized (mUploadListLock) {
            boolean match = false;
            Upload upload = null;
            Iterator<Upload> iterator = mUploadList.iterator();

            while (iterator.hasNext() && !match) {
                upload = iterator.next();
                if (upload.getId() == id) {
                    match = true;
                    iterator.remove();
                } else {
                    upload = null;
                }
            }

            if (upload != null) {
                mUploadList.addFirst(upload);
            }
        }
        resume();
    }

    @Override
    public void moveToEndOfQueue(long id) {
        if (mDebug) {
            System.out.println(getClass() + " - moveToEndOfQueue");
        }
        if (!mExecutor.isPaused()) {
            return;
        }

        synchronized (mUploadListLock) {
            boolean match = false;
            Upload upload = null;
            Iterator<Upload> iterator = mUploadList.iterator();

            while (iterator.hasNext() && !match) {
                upload = iterator.next();
                if (upload.getId() == id) {
                    match = true;
                    iterator.remove();
                } else {
                    upload = null;
                }
            }

            if (upload != null) {
                mUploadList.addLast(upload);
            }
        }
    }

    void exceptionDuringUpload(State state, Exception exception, Upload upload) {
        if (mDebug) {
            System.out.println(getClass() + " - exceptionDuringUpload - exception: " + exception + ", upload id: " + upload.getId());
        }
        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledException(upload.getId(), exception, state);
        }

        startNextAvailableUpload();
    }

    void resultInvalidDuringUpload(State state, Result result, Upload upload) {
        if (mDebug) {
            System.out.println(getClass() + " - addListener - state: " + state + ", upload id: " + upload.getId());
        }

        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledResultInvalid(upload.getId(), result, state);
        }

        startNextAvailableUpload();
    }

    void responseObjectNull(State state, Result result, Upload upload) {
        if (mDebug) {
            System.out.println(getClass() + " - responseObjectNull - state: " + state + ", upload id: " + upload.getId());
        }

        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledResponseObjectInvalid(upload.getId(), result, state);
        }

        startNextAvailableUpload();
    }

    void storageLimitExceeded(State state, Upload upload) {
        if (mDebug) {
            System.out.println(getClass() + " - storageLimitExceeded - state: " + state + ", upload id: " + upload.getId());
        }

        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledStorageLimitExceeded(upload.getId(), state);
        }

        startNextAvailableUpload();
    }

    void fileLargerThanStorageSpaceAvailable(State state, Upload upload) {
        if (mDebug) {
            System.out.println(getClass() + " - fileLargerThanStorageSpaceAvailable - state: " + state + ", upload id: " + upload.getId());
        }

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
        if (mDebug) {
            System.out.println(getClass() + " - checkFinished - upload id: " + upload.getId());
        }
        // does the hash exist in the account?
        if (checkResponse.doesHashExistInAccount()) {
            boolean inFolder = checkResponse.isInFolder();
            Upload.Options.ActionOnInAccount actionOnInAccount = upload.getOptions().getActionOnInAccount();
            switch (actionOnInAccount) {
                case UPLOAD_ALWAYS:
                    if (mDebug) {
                        System.out.println(getClass() + " - checkFinished - ActionOnInAccount: " + actionOnInAccount + ", uploading (upload always)");
                    }
                    doInstantUpload(upload);
                    break;
                case UPLOAD_IF_NOT_IN_FOLDER:
                    if (!inFolder) {
                        if (mDebug) {
                            System.out.println(getClass() + " - checkFinished - ActionOnInAccount: " + actionOnInAccount + ", uploading (not in folder)");
                        }
                        doInstantUpload(upload);
                    } else {
                        if (mDebug) {
                            System.out.println(getClass() + " - checkFinished - ActionOnInAccount: " + actionOnInAccount + ", not uploading (already in folder)");
                        }
                        for (IUploadListener listener : mListeners) {
                            listener.uploadFinished(upload.getId(), checkResponse.getDuplicateQuickkey());
                        }
                    }
                    break;
                case DO_NOT_UPLOAD:
                default:
                    if (mDebug) {
                        System.out.println(getClass() + " - checkFinished - ActionOnInAccount: " + actionOnInAccount + ", not uploading (do not upload)");
                    }
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
        if (mDebug) {
            System.out.println(getClass() + " - pollFinished - upload id: " + upload.getId() + ", quickKey: " + quickKey);
        }

        for (IUploadListener listener : mListeners) {
            listener.uploadFinished(upload.getId(), quickKey);
        }
        startNextAvailableUpload();
    }

    void pollUpdate(Poll.PollUpload upload, int status) {
        if (mDebug) {
            System.out.println(getClass() + " - pollUpdate - upload id: " + upload.getId() + ", status #" + status);
        }

        for (IUploadListener listener : mListeners) {
            listener.pollUpdate(upload.getId(), status);
        }
    }

    void pollMaxAttemptsReached(Poll.PollUpload upload) {
        if (mDebug) {
            System.out.println(getClass() + " - pollMaxAttemptsReached - upload id: " + upload.getId());
        }

        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledPollAttempts(upload.getId());
        }
        startNextAvailableUpload();
    }

    void instantFinished(Instant.InstantUpload upload, String quickKey) {
        if (mDebug) {
            System.out.println(getClass() + " - instantFinished - upload id: " + upload.getId() + ", quickKey: " + quickKey);
        }

        for (IUploadListener listener : mListeners) {
            listener.uploadFinished(upload.getId(), quickKey);
        }
        startNextAvailableUpload();
    }

    void apiError(State state, Upload upload, ApiResponse response, Result result) {
        if (mDebug) {
            System.out.println(getClass() + " - apiError - state: " + state + ", upload id: " + upload.getId() +", api error message: " + response.getMessage());
        }

        for (IUploadListener listener : mListeners) {
            listener.uploadCancelledApiError(upload.getId(), state, response, result);
        }
        startNextAvailableUpload();
    }

    void resumableProgress(Resumable.ResumableUpload upload, double percentFinished) {
        if (mDebug) {
            System.out.println(getClass() + " - resumableProgress - upload id: " + upload.getId() + ", %completed: " + percentFinished);
        }

        for (IUploadListener listener : mListeners) {
            listener.resumableUpdate(upload.getId(), percentFinished);
        }
    }

    void resumableFinished(Resumable.ResumableUpload upload, String responsePollKey, boolean allUnitsReady) {
        if (mDebug) {
            System.out.println(getClass() + " - resumableFinished - upload id: " + upload.getId() + ", responsePollKey: " + responsePollKey);
        }

        // poll if poll key exists and all units ready
        if (responsePollKey != null && allUnitsReady) {
            doPollUpload(upload, responsePollKey);
        } else {
            doCheckUpload(upload);
        }
    }

    void uploadStarted(Upload upload) {
        if (mDebug) {
            System.out.println(getClass() + " - uploadStarted - upload id: " + upload.getId());
        }

        for (IUploadListener listener : mListeners) {
            listener.uploadStarted(upload.getId());
        }
    }

    private void doCheckUpload(Resumable.ResumableUpload upload) {
        if (mDebug) {
            System.out.println(getClass() + " - doCheckUpload - upload id: " + upload.getId());
        }

        Check check = new Check(upload, mHttp, mTokenManager, this);
        check.debug(mDebug);
        mExecutor.execute(check);
    }

    private void doPollUpload(Resumable.ResumableUpload upload, String responsePollKey) {
        if (mDebug) {
            System.out.println(getClass() + " - doPollUpload - upload id: " + upload.getId());
        }

        Poll.PollUpload pollUpload = new Poll.PollUpload(upload, responsePollKey);
        Poll poll = new Poll(pollUpload, mHttp, mTokenManager, this);
        poll.debug(mDebug);
        mExecutor.execute(poll);
    }

    private void doInstantUpload(Instant.InstantUpload upload) {
        if (mDebug) {
            System.out.println(getClass() + " - doInstantUpload - upload id: " + upload.getId());
        }

        Instant instant = new Instant(upload, mHttp, mTokenManager, this);
        instant.debug(mDebug);
        mExecutor.execute(instant);
    }

    private void doResumableUpload(Instant.InstantUpload upload, CheckResponse checkResponse) {
        if (mDebug) {
            System.out.println(getClass() + " - doResumableUpload - upload id: " + upload.getId());
        }

        ResumableUpload resumableUploadObj = checkResponse.getResumableUpload();
        int numUnits = resumableUploadObj.getNumberOfUnits();
        int unitSize = resumableUploadObj.getUnitSize();
        int count = resumableUploadObj.getBitmap().getCount();
        List<Integer> words = resumableUploadObj.getBitmap().getWords();

        Resumable.ResumableUpload resumableUpload = new Resumable.ResumableUpload(upload, upload.getHash(), numUnits, unitSize, count, words);
        Resumable resumable = new Resumable(resumableUpload, mHttp, mTokenManager, this);
        resumable.debug(mDebug);
        mExecutor.execute(resumable);
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

    private class PausableExecutor extends ThreadPoolExecutor {
        private boolean isPaused;
        private final ReentrantLock pauseLock = new ReentrantLock();
        private final Condition unpaused = pauseLock.newCondition();
        private final List<UploadRunnable> mRunning = Collections.synchronizedList(new ArrayList<UploadRunnable>());

        public PausableExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            pauseLock.lock();
            try {
                while (isPaused) {
                    unpaused.await();
                }
            } catch (InterruptedException ie) {
                t.interrupt();
            } finally {
                pauseLock.unlock();
            }

            mRunning.add((UploadRunnable) r);
        }

        @Override
        public void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (r instanceof UploadRunnable) {
                UploadRunnable runnable = (UploadRunnable) r;
                mRunning.remove(runnable);
            }
        }

        public void pause() {
            pauseLock.lock();
            try {
                isPaused = true;
                for (UploadRunnable runnable : mRunning) {
                    runnable.pause();
                }
            } finally {
                pauseLock.unlock();
            }
        }

        public void resume() {
            pauseLock.lock();
            try {
                isPaused = false;
                unpaused.signalAll();

                for (UploadRunnable runnable : mRunning) {
                    runnable.resume();
                }
            } finally {
                pauseLock.unlock();
            }
        }

        public List<UploadRunnable> getRunningTasks() {
            return mRunning;
        }

        public boolean isPaused() {
            return isPaused;
        }
    }
}
