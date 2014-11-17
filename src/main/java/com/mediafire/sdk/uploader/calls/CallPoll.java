package com.mediafire.sdk.uploader.calls;

import com.mediafire.sdk.api.clients.upload.UploadClient;
import com.mediafire.sdk.api.responses.ResponseCode;
import com.mediafire.sdk.api.responses.upload.PollFileError;
import com.mediafire.sdk.api.responses.upload.PollResponse;
import com.mediafire.sdk.api.responses.upload.PollResult;
import com.mediafire.sdk.api.responses.upload.PollStatus;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.uploader.upload_items.PollUpload;

/**
 * Created by Chris on 11/13/2014.
 */
public class CallPoll extends BaseCall {
    private static final int MAX_POLLS = 30;
    private static final int SECONDS_BETWEEN_POLLS = 10;

    private final UploadClient mUploadClient;
    private final PollUpload mUpload;

    private int currentPollCount = 0;

    public CallPoll(UploadClient uploadClient, PollUpload upload) {
        mUploadClient = uploadClient;
        mUpload = upload;
    }

    public void poll() {
        while (currentPollCount < MAX_POLLS) {
            currentPollCount++;

            Result result = mUploadClient.pollUpload(mUpload.getPollKey());

            if (CallHelper.resultInvalid(result)) {
                // TODO handle
                return;
            }

            Response response = result.getResponse();

            byte[] responseBytes = response.getBytes();

            PollResponse pollResponse = CallHelper.getResponseObject(responseBytes, PollResponse.class);

            ResponseCode responseCode = pollResponse.getErrorCode();

            if (responseCode != ResponseCode.NO_ERROR) {
                // TODO handle
                return;
            }

            PollResult pollResult = pollResponse.getDoUpload().getResultCode();

            if (pollResult != PollResult.SUCCESS) {
                // TODO handle
                return;
            }

            PollFileError pollFileError = pollResponse.getDoUpload().getFileErrorCode();

            if (pollFileError != PollFileError.NO_ERROR) {
                // TODO handle
                return;
            }

            PollStatus pollStatusCode = pollResponse.getDoUpload().getStatusCode();

            if (pollStatusCode == PollStatus.NO_MORE_REQUESTS_FOR_THIS_KEY) {
                // TODO handle
                return;
            }

            //wait before next api call
            try {
                Thread.sleep(SECONDS_BETWEEN_POLLS * 1000);
            } catch (InterruptedException e) {
                // TODO handle
            }
        }

        // TODO handle
    }
}
