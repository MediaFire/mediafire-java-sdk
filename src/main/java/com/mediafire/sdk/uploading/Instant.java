package com.mediafire.sdk.uploading;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mediafire.sdk.api.responses.upload.InstantResponse;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Result;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Chris on 12/22/2014.
 */
class Instant extends UploadRunnable {

    private InstantUpload mUpload;
    private UploadProcess mProcessMonitor;

    public Instant(InstantUpload upload, HttpHandler http, TokenManager tokenManager, UploadProcess processMonitor) {
        super(http, tokenManager);
        mUpload = upload;
        mProcessMonitor = processMonitor;
    }

    @Override
    public void run() {
        Map<String, Object> requestParams = makeQueryParams();

        Result result = getUploadClient().instant(requestParams);

        if (!resultValid(result)) {
            mProcessMonitor.resultInvalidDuringUpload(State.INSTANT, result, mUpload);
            return;
        }

        byte[] responseBytes = result.getResponse().getBytes();
        String fullResponse = new String(responseBytes);
        String response = getResponseStringForGson(fullResponse);

        InstantResponse apiResponse;
        try {
            apiResponse = new Gson().fromJson(response, InstantResponse.class);
        } catch (JsonSyntaxException exception) {
            mProcessMonitor.exceptionDuringUpload(State.INSTANT, exception, mUpload);
            return;
        }

        if (apiResponse == null) {
            mProcessMonitor.responseObjectNull(State.INSTANT, result, mUpload);
            return;
        }

        if (apiResponse.hasError()) {
            mProcessMonitor.apiError(State.INSTANT, mUpload, apiResponse, result);
        }

        String quickKey = apiResponse.getQuickkey();

        mProcessMonitor.instantFinished(mUpload, quickKey);
    }

    @Override
    protected Map<String, Object> makeQueryParams() {
        String hash = mUpload.getHash();
        long size = mUpload.getFile().length();
        String customFileName = mUpload.getOptions().getCustomFileName();
        String uploadFolderKey = mUpload.getOptions().getUploadFolderKey();
        String uploadPath = mUpload.getOptions().getUploadPath();

        String actionOnDuplicate = "keep";

        String responseFormat = "json";

        Map<String, Object> requestParams = new LinkedHashMap<String, Object>();

        requestParams.put("hash", hash);
        requestParams.put("size", size);

        if (customFileName != null) {
            requestParams.put("filename", customFileName);
        } else {
            requestParams.put("filename", mUpload.getFile().getName());
        }


        if (uploadFolderKey != null) {
            requestParams.put("folder_key", null);
        }

        if (uploadPath != null) {
            requestParams.put("path", null);
        }

        requestParams.put("action_on_duplicate", actionOnDuplicate);
        requestParams.put("response_format", responseFormat);

        return requestParams;
    }

    static class InstantUpload extends Upload {

        private String mHash;

        public InstantUpload(Upload upload, String hash) {
            super(upload.getId(), upload.getFile(), upload.getOptions(), upload.getInfo());
            mHash = hash;
        }

        public String getHash() {
            return mHash;
        }
    }
}
