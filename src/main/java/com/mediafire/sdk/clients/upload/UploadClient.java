package com.mediafire.sdk.clients.upload;

import com.mediafire.sdk.clients.ClientHelperNoToken;
import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ClientHelperActionToken;
import com.mediafire.sdk.clients.ApiRequestGenerator;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.ApiVersion;
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
    private static final String PARAM_MTIME = "mTime";
    private static final String PARAM_VERSION_CONTROL = "version_control";
    private static final String PARAM_PREVIOUS_HASH = "previous_hash";
    private static final String PARAM_KEY = "key";
    private static final String PARAM_SOURCE_HASH = "source_hash";
    private static final String PARAM_TARGET_HASH = "target_hash";
    private static final String PARAM_TARGET_SIZE = "target_size";

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ActionTokenManagerInterface mActionTokenManager;
    private final HttpWorkerInterface mHttpWorker;

    public UploadClient(HttpWorkerInterface httpWorkerInterface, ActionTokenManagerInterface actionTokenManagerInterface, String apiVersion) {
        mApiRequestGenerator = new ApiRequestGenerator(apiVersion);
        mActionTokenManager = actionTokenManagerInterface;
        mHttpWorker = httpWorkerInterface;
    }

    public UploadClient(HttpWorkerInterface httpWorkerInterface, ActionTokenManagerInterface actionTokenManagerInterface) {
        this(httpWorkerInterface, actionTokenManagerInterface, ApiVersion.VERSION_CURRENT);
    }

    public Result check(CheckParameters checkParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/check.php");

        ClientHelperActionToken clientHelper = new ClientHelperActionToken("upload", mActionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FILENAME, checkParameters.getFilename());
        request.addQueryParameter(PARAM_HASH, checkParameters.getHash());
        request.addQueryParameter(PARAM_SIZE, checkParameters.getSize());
        request.addQueryParameter(PARAM_FOLDER_KEY, checkParameters.getFolderKey());
        request.addQueryParameter(PARAM_FILEDROP_KEY, checkParameters.getFiledropKey());
        request.addQueryParameter(PARAM_PATH, checkParameters.getPath());
        request.addQueryParameter(PARAM_RESUMABLE, checkParameters.getResumable());

        return apiClient.doRequest(request);
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

        ClientHelperActionToken clientHelper = new ClientHelperActionToken("upload", mActionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }

    public Result pollUpload(String key) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/poll.php");

        request.addQueryParameter(PARAM_KEY, key);

        ClientHelperNoToken clientHelper = new ClientHelperNoToken();
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }

    public Result resumable(ResumableParameters resumableParameters, String encodedShortFileName, long fileSize, String fileHash, int chunkNumber, String chunkHash, int chunkSize, byte[] payload) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("upload/resumable.php");

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

        request.addPayload(payload);

        request.addHeader("x-filename", encodedShortFileName);
        request.addHeader("x-filesize", String.valueOf(fileSize));
        request.addHeader("x-filehash", fileHash);
        request.addHeader("x-unit-id", Integer.toString(chunkNumber));
        request.addHeader("x-unit-hash", chunkHash);
        request.addHeader("x-unit-size", Integer.toString(chunkSize));

        ClientHelperActionToken clientHelper = new ClientHelperActionToken("upload", mActionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }
}
