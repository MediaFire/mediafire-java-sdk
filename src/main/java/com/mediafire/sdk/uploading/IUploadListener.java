package com.mediafire.sdk.uploading;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.upload.CheckResponse;
import com.mediafire.sdk.http.Result;

import java.util.Map;

/**
* Created by Chris on 12/22/2014.
*/
public interface IUploadListener {
    public void uploadCancelledException(long id, Map<String, Object> uploadInfo, Exception exception, State state);
    public void uploadCancelledResultInvalid(long id, Map<String, Object> uploadInfo, Result result, State state);
    public void uploadCancelledResponseObjectInvalid(long id, Map<String, Object> uploadInfo, Result result, State state);
    public void uploadCancelledStorageLimitExceeded(long id, Map<String, Object> uploadInfo, State state);
    public void uploadCancelledFileLargerThanStorageSpaceAvailable(long id, Map<String, Object> uploadInfo, State state);
    public void uploadCancelledPollAttempts(long id);
    public void uploadCancelledApiError(long id, Map<String, Object> uploadInfo, State state, ApiResponse response, Result result);
    public void uploadCancelledApiResponseMissingResumableUpload(long id, Map<String, Object> uploadInfo, CheckResponse apiResponse, Result result);
    public void uploadCancelledApiResponseMissingBitmap(long id, Map<String, Object> uploadInfo, CheckResponse apiResponse, Result result);

    public void uploadStarted(long id);
    public void uploadFinished(long id, Map<String, Object> uploadInfo, String quickKey);
    public void pollUpdate(long id, Map<String, Object> uploadInfo, int status);
    public void resumableUpdate(long id, Map<String, Object> uploadInfo, double percentFinished);
}
