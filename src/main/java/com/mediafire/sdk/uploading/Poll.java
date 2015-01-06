package com.mediafire.sdk.uploading;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mediafire.sdk.api.responses.upload.PollResponse;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Result;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Chris on 12/22/2014.
 */
class Poll extends UploadRunnable {
    private static final int DEFAULT_SLEEP_TIME = 2000;
    private static final int DEFAULT_MAX_POLLS = 60;

    private final PollUpload mUpload;
    private UploadManager mManager;
    private long mWaitTimeBetweenPollsMillis = DEFAULT_SLEEP_TIME;
    private int mMaxPolls = DEFAULT_MAX_POLLS;

    public Poll(PollUpload upload,
                IHttp mHttp,
                ITokenManager mTokenManager,
                UploadManager manager,
                long waitTimeBetweenPollsMillis,
                int maxPolls) {
        super(mHttp, mTokenManager);
        mUpload = upload;
        mManager = manager;
        if (waitTimeBetweenPollsMillis > 0 && waitTimeBetweenPollsMillis <= 10000) {
            mWaitTimeBetweenPollsMillis = waitTimeBetweenPollsMillis;
        }

        if (maxPolls > 0 && maxPolls <= 60) {
            mMaxPolls = maxPolls;
        }
    }

    public Poll(PollUpload upload, IHttp http, ITokenManager tokenManager, UploadManager manager) {
        this(upload, http, tokenManager, manager, DEFAULT_SLEEP_TIME, DEFAULT_MAX_POLLS);
    }

    @Override
    public void run() {
        if (isDebugging()) {
            System.out.println(getClass() + " - run");
        }
        Map<String, Object> requestParameters = makeQueryParams();
        int pollCount = 0;

        while (pollCount < mMaxPolls) {
            pollCount++;

            try {
                yieldIfPaused();
            } catch (InterruptedException exception) {
                mManager.exceptionDuringUpload(State.POLL, exception, mUpload);
                return;
            }
            Result result = getUploadClient().pollUpload(requestParameters);

            if (!resultValid(result)) {
                mManager.resultInvalidDuringUpload(State.POLL, result, mUpload);
                return;
            }

            byte[] responseBytes = result.getResponse().getBytes();
            String fullResponse = new String(responseBytes);
            String response = getResponseStringForGson(fullResponse);

            PollResponse apiResponse;
            try {
                apiResponse = new Gson().fromJson(response, PollResponse.class);
            } catch (JsonSyntaxException exception) {
                mManager.exceptionDuringUpload(State.POLL, exception, mUpload);
                return;
            }

            if (apiResponse == null) {
                mManager.responseObjectNull(State.POLL, result, mUpload);
                return;
            }

            if (apiResponse.hasError()) {
                mManager.apiError(State.POLL, mUpload, apiResponse, result);
                return;
            }

            PollResponse.DoUpload doUpload = apiResponse.getDoUpload();

            if (doUpload.getFileErrorCode() != 0) {
                mManager.apiError(State.POLL, mUpload, apiResponse, result);
                return;
            }

            if (doUpload.getQuickKey() != null && !doUpload.getQuickKey().isEmpty()) {
                mManager.pollFinished(mUpload, doUpload.getQuickKey());
                return;
            }

            int status = doUpload.getStatusCode();

            mManager.pollUpdate(mUpload, status);

            try {
                Thread.sleep(mWaitTimeBetweenPollsMillis);
            } catch (InterruptedException exception) {
                mManager.exceptionDuringUpload(State.POLL, exception, mUpload);
                return;
            }
        }

        mManager.pollMaxAttemptsReached(mUpload);
    }

    @Override
    protected Map<String, Object> makeQueryParams() {
        if (isDebugging()) {
            System.out.println(getClass() + " - makeQueryParams");
        }

        String key = mUpload.getPollingKey();

        String responseFormat = "json";

        Map<String, Object> requestParams = new LinkedHashMap<String, Object>();

        requestParams.put("key", key);
        requestParams.put("response_format", responseFormat);

        return requestParams;
    }

    static class PollUpload extends Upload {

        private String mPollingKey;

        public PollUpload(Upload upload, String pollingKey) {
            super(upload.getId(), upload.getFile(), upload.getOptions());
            mPollingKey = pollingKey;
        }

        public String getPollingKey() {
            return mPollingKey;
        }
    }
}
