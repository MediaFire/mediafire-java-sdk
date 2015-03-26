package com.mediafire.sdk.uploading;

import com.mediafire.sdk.http.Result;

/**
* Created by Chris on 12/22/2014.
*/
public interface UploadProcessListener {
    public void exceptionOccurred(Upload upload, Exception exception);
    public void apiError(Upload upload, Result result);
    public void storageLimitExceeded(Upload upload);
    public void maxPollsReached(Upload upload);
    public void generalCancel(Upload upload, Result result);

    public void uploadStarted(Upload upload);
    public void uploadFinished(Upload upload, String quickKey);
    public void pollUpdate(Upload upload, int status);
    public void uploadProgress(Upload upload, double percentFinished);
}
