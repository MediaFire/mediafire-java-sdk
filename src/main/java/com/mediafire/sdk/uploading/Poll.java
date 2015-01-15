package com.mediafire.sdk.uploading;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mediafire.sdk.api.responses.upload.PollResponse;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Result;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Chris on 12/22/2014.
 */
class Poll extends UploadRunnable {
    public static final int DEFAULT_SLEEP_TIME = 2000;
    public static final int DEFAULT_MAX_POLLS = 60;

    private final PollUpload mUpload;
    private UploadProcess mProcessMonitor;
    private long mWaitTimeBetweenPollsMillis = DEFAULT_SLEEP_TIME;
    private int mMaxPolls = DEFAULT_MAX_POLLS;

    public Poll(PollUpload upload, HttpHandler mHttp, TokenManager mTokenManager, UploadProcess processMonitor,
                long waitTimeBetweenPollsMillis, int maxPolls) {
        super(mHttp, mTokenManager);
        mUpload = upload;
        mProcessMonitor = processMonitor;
        if (waitTimeBetweenPollsMillis > 0 && waitTimeBetweenPollsMillis <= 10000) {
            mWaitTimeBetweenPollsMillis = waitTimeBetweenPollsMillis;
        }

        if (maxPolls > 0 && maxPolls <= 60) {
            mMaxPolls = maxPolls;
        }
    }

    @Override
    public void run() {
        Map<String, Object> requestParameters = makeQueryParams();
        int pollCount = 0;

        while (pollCount < mMaxPolls) {
            pollCount++;

            Result result = getUploadClient().pollUpload(requestParameters);

            if (!resultValid(result)) {
                mProcessMonitor.resultInvalidDuringUpload(State.POLL, result, mUpload);
                return;
            }

            byte[] responseBytes = result.getResponse().getBytes();
            String fullResponse = new String(responseBytes);
            String response = getResponseStringForGson(fullResponse);

            PollResponse apiResponse;
            try {
                apiResponse = new Gson().fromJson(response, PollResponse.class);
            } catch (JsonSyntaxException exception) {
                mProcessMonitor.exceptionDuringUpload(State.POLL, exception, mUpload);
                return;
            }

            if (apiResponse == null) {
                mProcessMonitor.responseObjectNull(State.POLL, result, mUpload);
                return;
            }

            if (apiResponse.hasError()) {
                mProcessMonitor.apiError(State.POLL, mUpload, apiResponse, result);
                return;
            }

            PollResponse.DoUpload doUpload = apiResponse.getDoUpload();

            if (doUpload.getFileErrorCode() != 0) {
                mProcessMonitor.apiError(State.POLL, mUpload, apiResponse, result);
                return;
            }

            if (doUpload.getQuickKey() != null && !doUpload.getQuickKey().isEmpty()) {
                mProcessMonitor.pollFinished(mUpload, doUpload.getQuickKey());
                return;
            }

            int status = doUpload.getStatusCode();

            mProcessMonitor.pollUpdate(mUpload, status);

            try {
                Thread.sleep(mWaitTimeBetweenPollsMillis);
            } catch (InterruptedException exception) {
                mProcessMonitor.exceptionDuringUpload(State.POLL, exception, mUpload);
                return;
            }
        }

        mProcessMonitor.pollMaxAttemptsReached(mUpload);
    }

    @Override
    protected Map<String, Object> makeQueryParams() {
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
            super(upload.getId(), upload.getFile(), upload.getOptions(), upload.getInfo());
            mPollingKey = pollingKey;
        }

        public String getPollingKey() {
            return mPollingKey;
        }
    }
}
