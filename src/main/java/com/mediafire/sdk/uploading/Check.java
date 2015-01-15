package com.mediafire.sdk.uploading;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mediafire.sdk.api.responses.upload.CheckResponse;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Result;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Chris on 12/22/2014.
 */
class Check extends UploadRunnable {

    private Upload mUpload;
    private UploadProcess mProcessMonitor;

    public Check(Upload upload, HttpHandler http, TokenManager tokenManager, UploadProcess processMonitor) {
        super(http, tokenManager);
        mUpload = upload;
        mProcessMonitor = processMonitor;
    }

    @Override
    public void run() {
        if (!(mUpload instanceof Resumable.ResumableUpload)) {
            mProcessMonitor.uploadStarted(mUpload);
        }

        Map<String, Object> requestParams;
        try {
            requestParams = makeQueryParams();
        } catch (IOException exception) {
            mProcessMonitor.exceptionDuringUpload(State.CHECK, exception, mUpload);
            return;
        } catch (NoSuchAlgorithmException exception) {
            mProcessMonitor.exceptionDuringUpload(State.CHECK, exception, mUpload);
            return;
        }

        Result result = getUploadClient().check(requestParams);

        if (!resultValid(result)) {
            mProcessMonitor.resultInvalidDuringUpload(State.CHECK, result, mUpload);
            return;
        }

        byte[] responseBytes = result.getResponse().getBytes();
        String fullResponse = new String(responseBytes);
        String response = getResponseStringForGson(fullResponse);

        CheckResponse apiResponse;
        try {
            apiResponse = new Gson().fromJson(response, CheckResponse.class);
        } catch (JsonSyntaxException exception) {
            mProcessMonitor.exceptionDuringUpload(State.CHECK, exception, mUpload);
            return;
        }

        if (apiResponse == null) {
            mProcessMonitor.responseObjectNull(State.CHECK, result, mUpload);
            return;
        }

        if (apiResponse.hasError()) {
            mProcessMonitor.apiError(State.CHECK, mUpload, apiResponse, result);
            return;
        }

        if (apiResponse.isStorageLimitExceeded()) {
            mProcessMonitor.storageLimitExceeded(State.CHECK, mUpload);
            return;
        }

        if (apiResponse.getStorageLimit() - apiResponse.getUsedStorageSize() < mUpload.getFile().length()
                && apiResponse.getStorageLimit() != 0) {
            mProcessMonitor.fileLargerThanStorageSpaceAvailable(State.CHECK, mUpload);
            return;
        }

        String hash = String.valueOf(requestParams.get("hash"));
        Instant.InstantUpload upload = new Instant.InstantUpload(mUpload, hash);
        // add check for all units ready and have poll upload key (do poll upload)
        mProcessMonitor.checkFinished(upload, apiResponse);
    }

    @Override
    protected Map<String, Object> makeQueryParams() throws IOException, NoSuchAlgorithmException {
        String customFileName = mUpload.getOptions().getCustomFileName();
        String hash = Hasher.getSHA256Hash(mUpload.getFile());
        long size = mUpload.getFile().length();
        String uploadFolderKey = mUpload.getOptions().getUploadFolderKey();
        String uploadPath = mUpload.getOptions().getUploadPath();
        String resumable = "yes";
        String responseFormat = "json";

        Map<String, Object> requestParams = new LinkedHashMap<String, Object>();

        if (customFileName != null) {
            requestParams.put("filename", customFileName);
        } else {
            requestParams.put("filename", mUpload.getFile().getName());
        }

        requestParams.put("hash", hash);
        requestParams.put("size", size);

        if (uploadFolderKey != null) {
            requestParams.put("folder_key", null);
        }

        if (uploadPath != null) {
            requestParams.put("path", null);
        }

        requestParams.put("resumable", resumable);
        requestParams.put("response_format", responseFormat);

        return requestParams;
    }
}
