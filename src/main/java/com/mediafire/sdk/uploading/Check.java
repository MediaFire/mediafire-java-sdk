package com.mediafire.sdk.uploading;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mediafire.sdk.api.responses.upload.CheckResponse;
import com.mediafire.sdk.api.responses.upload.ResumableBitmap;
import com.mediafire.sdk.api.responses.upload.ResumableUpload;
import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Result;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
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
        if (mUpload.getNumberOfUnits() == 0 && mUpload.getUnitSize() == 0) {
            if (isDebugging()) {
                System.out.println("upload/check has started for " + mUpload);
            }
            mProcessMonitor.uploadStarted(mUpload);
        }

        Map<String, Object> requestParams;
        try {
            requestParams = makeQueryParams();
        } catch (IOException exception) {
            if (isDebugging()) {
                System.out.println("cancelling upload/check for " + mUpload + " due to an exception: " + exception);
            }
            mProcessMonitor.exceptionDuringUpload(mUpload, exception);
            return;
        } catch (NoSuchAlgorithmException exception) {
            if (isDebugging()) {
                System.out.println("cancelling upload/check for " + mUpload + " due to an exception: " + exception);
            }
            mProcessMonitor.exceptionDuringUpload(mUpload, exception);
            return;
        }

        Result result = getUploadClient().check(requestParams);

        if (!resultValid(result)) {
            if (isDebugging()) {
                System.out.println("cancelling upload/check for " + mUpload + " due to invalid result object");
            }
            mProcessMonitor.generalCancel(mUpload, result);
            return;
        }

        if (isDebugging()) {
            System.out.println("upload/check request: " + result.getRequest());
            System.out.println("upload/check response: " + result.getResponse());
        }

        byte[] responseBytes = result.getResponse().getBytes();
        String fullResponse = new String(responseBytes);
        String response = getResponseStringForGson(fullResponse);

        CheckResponse apiResponse;
        try {
            apiResponse = new Gson().fromJson(response, CheckResponse.class);
        } catch (JsonSyntaxException exception) {
            if (isDebugging()) {
                System.out.println("cancelling upload/check for " + mUpload + " due to an exception: " + exception);
            }
            mProcessMonitor.exceptionDuringUpload(mUpload, exception);
            return;
        }

        if (apiResponse == null) {
            if (isDebugging()) {
                System.out.println("cancelling upload/check for " + mUpload + " due to a null ApiResponse object");
            }
            mProcessMonitor.generalCancel(mUpload, result);
            return;
        }

        if (apiResponse.hasError()) {
            if (isDebugging()) {
                System.out.println("cancelling upload/check for " + mUpload + " due to an ApiResponse error (" + apiResponse.getMessage() + ", error " + apiResponse.getError() + ")");
            }
            mProcessMonitor.apiError(mUpload, result);
            return;
        }

        if ("yes".equals(apiResponse.getStorageLimitExceeded())) {
            if (isDebugging()) {
                System.out.println("storage limit exceeded while processing upload/check for " + mUpload);
            }
            mProcessMonitor.storageLimitExceeded(mUpload);
            return;
        }

        if (apiResponse.getStorageLimit() - apiResponse.getUsedStorageSize() < mUpload.getFile().length()
                && apiResponse.getStorageLimit() != 0) {
            if (isDebugging()) {
                System.out.println("storage limit exceeded while processing upload/check for " + mUpload);
            }
            mProcessMonitor.storageLimitExceeded(mUpload);
            return;
        }

        String hash = String.valueOf(requestParams.get("hash"));
        mUpload.setHash(hash);

        String hashExists = apiResponse.getHashExists();
        String hashExistsInAccount = apiResponse.getInAccount();
        String hashExistsInFolder = apiResponse.getInFolder();
        String fileExistsInFolder = apiResponse.getFileExists();
        String fileExistsInFolderWithDifferentHash = apiResponse.getDifferentHash();
        String duplicateQuickKey = apiResponse.getDuplicateQuickkey();

        mUpload.setHashInMediaFire(hashExists != null && "yes".equals(hashExists));
        mUpload.setHashInAccount(hashExistsInAccount != null && "yes".equals(hashExistsInAccount));
        mUpload.setHashInFolder(hashExistsInFolder != null && "yes".equals(hashExistsInFolder));
        mUpload.setFileNameInFolder(fileExistsInFolder != null && "yes".equals(fileExistsInFolder));
        mUpload.setFileNameInFolderWithDifferentHash(fileExistsInFolderWithDifferentHash != null && "yes".equals(fileExistsInFolderWithDifferentHash));
        mUpload.setDuplicateQuickKey(duplicateQuickKey);

        ResumableUpload resumableUpload = apiResponse.getResumableUpload();
        if (resumableUpload != null) {
            if (isDebugging()) {
                System.out.println("resumable upload was available from upload/check, upload info: " + mUpload);
            }
            String allUnitsReady = resumableUpload.getAllUnitsReady();
            int numUnits = resumableUpload.getNumberOfUnits();
            int unitSize = resumableUpload.getUnitSize();

            mUpload.setAllUnitsReady(allUnitsReady != null && "yes".equals(allUnitsReady));
            mUpload.setNumberOfUnits(numUnits);
            mUpload.setUnitSize(unitSize);

            ResumableBitmap bitmap = resumableUpload.getBitmap();
            if (bitmap != null) {
                if (isDebugging()) {
                    System.out.println("resumable bitmap was available from upload/check, upload info: " + mUpload);
                }
                int count = bitmap.getCount();
                List<Integer> words = bitmap.getWords();

                mUpload.updateUploadBitmap(count, words);
            } else {
                if (isDebugging()) {
                    System.out.println("resumable bitmap was not available from upload/check, upload info: " + mUpload);
                }
            }
        } else {
            if (isDebugging()) {
                System.out.println("resumable upload was not available from upload/check, upload info: " + mUpload);
            }
        }

        // add check for all units ready and have poll upload key (do poll upload)
        if (isDebugging()) {
            System.out.println("upload/check for " + mUpload + " has finished");
        }
        mProcessMonitor.checkFinished(mUpload);
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
            requestParams.put("folder_key", uploadFolderKey);
        }

        if (uploadPath != null) {
            requestParams.put("path", uploadPath);
        }

        requestParams.put("resumable", resumable);
        requestParams.put("response_format", responseFormat);

        return requestParams;
    }
}
