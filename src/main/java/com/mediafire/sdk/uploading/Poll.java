package com.mediafire.sdk.uploading;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mediafire.sdk.api.responses.upload.PollDoUpload;
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
    public static final int DEFAULT_FREQUENCY = 2000;
    public static final int DEFAULT_MAX_POLLS = 60;
    public static final int DEFAULT_POLL_STATUS = 99;

    private final Upload mUpload;
    private UploadProcess mProcessMonitor;
    private long mPollFrequency = DEFAULT_FREQUENCY;
    private int mMaxPolls = DEFAULT_MAX_POLLS;
    private int mPollStatusToFinish = DEFAULT_POLL_STATUS;

    public Poll(Upload upload, HttpHandler mHttp, TokenManager mTokenManager, UploadProcess processMonitor) {
        super(mHttp, mTokenManager);
        mUpload = upload;
        mProcessMonitor = processMonitor;
    }

    public void setPollFrequency(long waitTimeBetweenPollsMillis) {
        if (waitTimeBetweenPollsMillis > 0 && waitTimeBetweenPollsMillis <= 10000) {
            mPollFrequency = waitTimeBetweenPollsMillis;
        }
    }

    public void setMaxPolls(int maxPolls) {
        if (maxPolls > 0 && maxPolls <= 60) {
            mMaxPolls = maxPolls;
        }
    }

    public void setPollStatusToFinish(int status) {
        mPollStatusToFinish = status;
    }

    @Override
    public void run() {
        if (isDebugging()) {
            System.out.println("starting upload/poll_upload for " + mUpload);
        }
        Map<String, Object> requestParameters = makeQueryParams();
        int pollCount = 0;

        while (pollCount < mMaxPolls) {
            pollCount++;

            Result result = getUploadClient().pollUpload(requestParameters);

            if (!resultValid(result)) {
                if (isDebugging()) {
                    System.out.println("cancelling upload/poll_upload for " + mUpload + " due to invalid result object");
                }
                mProcessMonitor.generalCancel(mUpload, result);
                return;
            }

            if (isDebugging()) {
                System.out.println("upload/poll request: " + result.getRequest());
                System.out.println("upload/poll response: " + result.getResponse());
            }

            byte[] responseBytes = result.getResponse().getBytes();
            String fullResponse = new String(responseBytes);
            String response = getResponseStringForGson(fullResponse);

            PollResponse apiResponse;
            try {
                apiResponse = new Gson().fromJson(response, PollResponse.class);
            } catch (JsonSyntaxException exception) {
                if (isDebugging()) {
                    System.out.println("cancelling upload/poll_upload for " + mUpload + " due to an exception: " + exception);
                }
                mProcessMonitor.exceptionDuringUpload(mUpload, exception);
                return;
            }

            if (apiResponse == null) {
                if (isDebugging()) {
                    System.out.println("cancelling upload/poll_upload for " + mUpload + " due to a null ApiResponse object");
                }
                mProcessMonitor.generalCancel(mUpload, result);
                return;
            }

            if (apiResponse.hasError()) {
                if (isDebugging()) {
                    System.out.println("cancelling upload/poll_upload for " + mUpload + " due to an ApiResponse error (" + apiResponse.getMessage() + ", error " + apiResponse.getError() + ")");
                }
                mProcessMonitor.apiError(mUpload, result);
                return;
            }

            PollDoUpload doUpload = apiResponse.getDoUpload();

            if (doUpload == null) {
                if (isDebugging()) {
                    System.out.println("cancelling upload/poll_upload for " + mUpload + " due to a null PollDoUpload object");
                }
                mProcessMonitor.generalCancel(mUpload, result);
                return;
            }

            String fileErrorCodeString = doUpload.getFileErrorCode();
            int fileErrorCode;
            if (fileErrorCodeString == null) {
                fileErrorCode = 0;
            } else if (fileErrorCodeString.isEmpty()) {
                fileErrorCode = 0;
            } else {
                fileErrorCode = Integer.parseInt(fileErrorCodeString);
            }

            if (fileErrorCode != 0) {
                if (isDebugging()) {
                    System.out.println("cancelling upload/poll_upload for " + mUpload + " due to a file error code (" + doUpload.getFileErrorCode() + ") ApiResponse error (" + apiResponse.getMessage() + ", error " + apiResponse.getError() + ")");
                }
                mProcessMonitor.apiError(mUpload, result);
                return;
            }

            if (doUpload.getQuickKey() != null && !doUpload.getQuickKey().isEmpty()) {
                if (isDebugging()) {
                    System.out.println("upload/poll_upload for " + mUpload + " has finished and received quick key " + doUpload.getQuickKey());
                }
                mProcessMonitor.pollFinished(mUpload, doUpload.getQuickKey());
                return;
            }

            String statusCodeString = doUpload.getStatusCode();
            int statusCode;
            if (statusCodeString == null) {
                statusCode = 0;
            } else if (statusCodeString.isEmpty()) {
                statusCode = 0;
            } else {
                statusCode = Integer.parseInt(statusCodeString);
            }

            if (statusCode >= mPollStatusToFinish) {
                mProcessMonitor.pollFinished(mUpload, doUpload.getQuickKey());
                return;
            }

            mProcessMonitor.pollUpdate(mUpload, statusCode);

            try {
                if (isDebugging()) {
                    System.out.println("pausing upload/poll_upload for " + mUpload + " before next poll for " + mPollFrequency + "ms");
                }
                Thread.sleep(mPollFrequency);
            } catch (InterruptedException exception) {
                if (isDebugging()) {
                    System.out.println("cancelling upload/poll_upload for " + mUpload + " due to an exception: " + exception);
                }
                mProcessMonitor.exceptionDuringUpload(mUpload, exception);
                return;
            }
        }

        if (isDebugging()) {
            System.out.println("max poll attempts (" + mMaxPolls + ") have been reached with upload/poll_upload for " + mUpload);
        }
        mProcessMonitor.pollMaxAttemptsReached(mUpload);
    }

    @Override
    protected Map<String, Object> makeQueryParams() {
        String key = mUpload.getPollKey();

        String responseFormat = "json";

        Map<String, Object> requestParams = new LinkedHashMap<String, Object>();

        requestParams.put("key", key);
        requestParams.put("response_format", responseFormat);

        return requestParams;
    }

}
