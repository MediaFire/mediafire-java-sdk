package com.mediafire.sdk.uploading;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mediafire.sdk.api.responses.upload.InstantResponse;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.token.Token;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Chris on 12/22/2014.
 */
class Instant extends UploadRunnable {

    private Upload mUpload;
    private UploadProcess mProcessMonitor;

    public Instant(Upload upload, HttpHandler http, TokenManager tokenManager, UploadProcess processMonitor) {
        super(http, tokenManager);
        mUpload = upload;
        mProcessMonitor = processMonitor;
    }

    @Override
    public void run() {
        if (isDebugging()) {
            System.out.println("starting upload/instant for " + mUpload);
        }
        Map<String, Object> requestParams = makeQueryParams();

        Result result = getUploadClient().instant(requestParams);

        if (!resultValid(result)) {
            if (isDebugging()) {
                System.out.println("cancelling upload/instant for " + mUpload + " due to invalid result object");
            }
            mProcessMonitor.generalCancel(mUpload, result);
            return;
        }

        if (isDebugging()) {
            System.out.println("upload/instant request: " + result.getRequest());
            System.out.println("upload/instant response: " + result.getResponse());
        }

        byte[] responseBytes = result.getResponse().getBytes();
        String fullResponse = new String(responseBytes);
        String response = getResponseStringForGson(fullResponse);

        InstantResponse apiResponse;
        try {
            apiResponse = new Gson().fromJson(response, InstantResponse.class);
        } catch (JsonSyntaxException exception) {
            if (isDebugging()) {
                System.out.println("cancelling upload/instant for " + mUpload + " due to an exception: " + exception);
            }
            mProcessMonitor.exceptionDuringUpload(mUpload, exception);
            return;
        }

        if (apiResponse == null) {
            if (isDebugging()) {
                System.out.println("cancelling upload/instant for " + mUpload + " due to a null ApiResponse object");
            }
            mProcessMonitor.generalCancel(mUpload, result);
            return;
        }

        if (apiResponse.hasError()) {
            if (isDebugging()) {
                System.out.println("cancelling upload/instant for " + mUpload + " due to an ApiResponse error (" + apiResponse.getMessage() + ", error " + apiResponse.getError() + ")");
            }
            mProcessMonitor.apiError(mUpload, result);
        }

        String quickKey = apiResponse.getQuickkey();
        mUpload.setNewQuickKey(quickKey);

        if (isDebugging()) {
            System.out.println("upload/instant for " + mUpload + " has finished");
        }
        mProcessMonitor.instantFinished(mUpload);
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
            requestParams.put("folder_key", uploadFolderKey);
        }

        if (uploadPath != null) {
            requestParams.put("path", uploadPath);
        }

        requestParams.put("action_on_duplicate", actionOnDuplicate);
        requestParams.put("response_format", responseFormat);

        return requestParams;
    }

}
