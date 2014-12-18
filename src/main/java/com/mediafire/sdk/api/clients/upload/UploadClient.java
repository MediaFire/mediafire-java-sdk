package com.mediafire.sdk.api.clients.upload;

import com.mediafire.sdk.client_helpers.ClientHelperNoToken;
import com.mediafire.sdk.api.clients.ApiClient;
import com.mediafire.sdk.client_helpers.ClientHelperActionToken;
import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.HttpInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by jondh on 11/4/14.
 */
public class UploadClient {
    private static final String PARAM_FILENAME = "filename";
    private static final String PARAM_HASH = "hash";
    private static final String PARAM_SIZE = "size";
    private static final String PARAM_FOLDER_KEY = "folder_key";
    private static final String PARAM_FILEDROP_KEY = "filedrop_key";
    private static final String PARAM_PATH = "path";
    private static final String PARAM_RESUMABLE = "resumable";
    private static final String PARAM_QUICK_KEY = "quick_key";
    private static final String PARAM_ACTION_ON_DUPLICATE = "action_on_duplicate";
    private static final String PARAM_MTIME = "mtime";
    private static final String PARAM_VERSION_CONTROL = "version_control";
    private static final String PARAM_PREVIOUS_HASH = "previous_hash";
    private static final String PARAM_KEY = "key";
    private static final String PARAM_SOURCE_HASH = "source_hash";
    private static final String PARAM_TARGET_HASH = "target_hash";
    private static final String PARAM_TARGET_SIZE = "target_size";

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiUploadActionTokenClient;
    private final ApiClient apiNoTokenClient;

    public UploadClient(HttpInterface httpInterface, ActionTokenManagerInterface actionTokenManagerInterface) {
        mApiRequestGenerator = new ApiRequestGenerator();

        ClientHelperActionToken clientHelperUploadActionToken = new ClientHelperActionToken("upload", actionTokenManagerInterface);
        apiUploadActionTokenClient = new ApiClient(clientHelperUploadActionToken, httpInterface);

        ClientHelperNoToken clientHelper = new ClientHelperNoToken();
        apiNoTokenClient = new ApiClient(clientHelper, httpInterface);
    }

    public Result check(CheckParameters checkParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/check.php");

        request.addQueryParameter(PARAM_FILENAME, checkParameters.getFilename());
        request.addQueryParameter(PARAM_HASH, checkParameters.getHash());
        request.addQueryParameter(PARAM_SIZE, checkParameters.getSize());
        request.addQueryParameter(PARAM_FOLDER_KEY, checkParameters.getFolderKey());
        request.addQueryParameter(PARAM_FILEDROP_KEY, checkParameters.getFiledropKey());
        request.addQueryParameter(PARAM_PATH, checkParameters.getPath());
        request.addQueryParameter(PARAM_RESUMABLE, checkParameters.getResumable());

        return apiUploadActionTokenClient.doRequest(request);
    }

    public Result instant(InstantParameters instantParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/instant.php");

        request.addQueryParameter(PARAM_HASH, instantParameters.getHash());
        request.addQueryParameter(PARAM_SIZE, instantParameters.getSize());
        request.addQueryParameter(PARAM_FILENAME, instantParameters.getFilename());
        request.addQueryParameter(PARAM_QUICK_KEY, instantParameters.getQuickKey());
        request.addQueryParameter(PARAM_FOLDER_KEY, instantParameters.getFolderKey());
        request.addQueryParameter(PARAM_FILEDROP_KEY, instantParameters.getFiledropKey());
        request.addQueryParameter(PARAM_PATH, instantParameters.getPath());
        request.addQueryParameter(PARAM_ACTION_ON_DUPLICATE, instantParameters.getActionOnDuplicate());
        request.addQueryParameter(PARAM_MTIME, instantParameters.getMTime());
        request.addQueryParameter(PARAM_VERSION_CONTROL, instantParameters.getVersionControl());

        return apiUploadActionTokenClient.doRequest(request);
    }

    public Result pollUpload(String key) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/poll_upload.php");

        request.addQueryParameter(PARAM_KEY, key);

        return apiNoTokenClient.doRequest(request);
    }

    public Result resumable(ResumableParameters resumableParameters, HeaderParameters headerParameters, byte[] payload) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/resumable.php");

        if (resumableParameters != null) {
            request.addQueryParameter(PARAM_FILEDROP_KEY, resumableParameters.getFiledropKey());
            request.addQueryParameter(PARAM_SOURCE_HASH, resumableParameters.getSourceHash());
            request.addQueryParameter(PARAM_TARGET_HASH, resumableParameters.getTargetHash());
            request.addQueryParameter(PARAM_TARGET_SIZE, resumableParameters.getTargetSize());
            request.addQueryParameter(PARAM_QUICK_KEY, resumableParameters.getQuickKey());
            request.addQueryParameter(PARAM_FOLDER_KEY, resumableParameters.getFolderKey());
            request.addQueryParameter(PARAM_PATH, resumableParameters.getPath());
            request.addQueryParameter(PARAM_ACTION_ON_DUPLICATE, resumableParameters.getActionOnDuplicate());
            request.addQueryParameter(PARAM_MTIME, resumableParameters.getMTime());
            request.addQueryParameter(PARAM_VERSION_CONTROL, resumableParameters.getVersionControl());
            request.addQueryParameter(PARAM_PREVIOUS_HASH, resumableParameters.getPreviousHash());
        }

        request.addPayload(payload);

        request.addHeader("x-filesize", headerParameters.getFileSize());
        request.addHeader("x-filehash", headerParameters.getFileHash());
        request.addHeader("x-unit-id", headerParameters.getUnitId());
        request.addHeader("x-unit-hash", headerParameters.getUnitHash());
        request.addHeader("x-unit-size", headerParameters.getUnitSize());

        return apiUploadActionTokenClient.doRequest(request);
    }

    public Result resumable(HeaderParameters headerParameters, byte[] payload) {
        return resumable(null, headerParameters, payload);
    }
}
