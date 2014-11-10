package com.mediafire.sdk.clients.folder;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ClientHelperApi;
import com.mediafire.sdk.clients.ApiRequestGenerator;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by jondh on 11/4/14.
 */
public class FolderClient {
    private static final String PARAM_FOLDER_KEY = "folder_key";
    private static final String PARAM_FOLDER_KEY_SRC = "folder_key_src";
    private static final String PARAM_FOLDER_KEY_DST = "folder_key_dst";
    private static final String PARAM_FOLDERNAME = "foldername";
    private static final String PARAM_PARENT_KEY = "parent_key";
    private static final String PARAM_ALLOW_DUPLICATE_NAME = "allow_duplicate_name";
    private static final String PARAM_MTIME = "mtime";
    private static final String PARAM_PRIVACY = "privacy";
    private static final String PARAM_PRIVACY_RECURSIVE = "privacy_recursive";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_DEVICE_ID = "device_id";
    private static final String PARAM_DETAILS = "details";
    private static final String PARAM_CONTENT_TYPE = "content_type";
    private static final String PARAM_FILTER = "filter";
    private static final String PARAM_ORDER_BY = "order_by";
    private static final String PARAM_ORDER_DIRECTION = "order_direction";
    private static final String PARAM_CHUNK = "chunk";
    private static final String PARAM_CHUNK_SIZE = "chunk_size";
    private static final String PARAM_RETURN_CHANGES = "return_changes";
    private static final String PARAM_SEARCH_ALL = "search_all";

    private final ApiRequestGenerator mApiRequestGenerator;
    private final HttpWorkerInterface mHttpWorker;
    private final SessionTokenManagerInterface mSessionTokenManager;

    public FolderClient(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManagerInterface, String apiVersion) {
        mHttpWorker = httpWorkerInterface;
        mSessionTokenManager = sessionTokenManagerInterface;
        mApiRequestGenerator = new ApiRequestGenerator(apiVersion);
    }

    public FolderClient(HttpWorkerInterface httpWorker, SessionTokenManagerInterface sessionTokenManager) {
        this(httpWorker, sessionTokenManager, ApiVersion.VERSION_CURRENT);
    }

    public Result copy(MovementParameters movementParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/copy.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FOLDER_KEY_SRC, movementParameters.mFolderKeySrc);
        request.addQueryParameter(PARAM_FOLDER_KEY_DST, movementParameters.mFolderKeyDst);

        return apiClient.doRequest(request);
    }

    public Result create(String folderName, CreateParameters createParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/create.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FOLDERNAME, folderName);
        request.addQueryParameter(PARAM_PARENT_KEY, createParameters.mParentKey);
        request.addQueryParameter(PARAM_ALLOW_DUPLICATE_NAME, createParameters.mAllowDuplicateName);
        request.addQueryParameter(PARAM_MTIME, createParameters.mMTime);

        return apiClient.doRequest(request);
    }

    public Result move(MovementParameters movementParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/move.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FOLDER_KEY_SRC, movementParameters.mFolderKeySrc);
        request.addQueryParameter(PARAM_FOLDER_KEY_DST, movementParameters.mFolderKeyDst);

        return apiClient.doRequest(request);
    }

    public Result delete(String folderKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/delete.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FOLDER_KEY, folderKey);

        return apiClient.doRequest(request);
    }

    public Result purge(String folderKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/purge.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FOLDER_KEY, folderKey);

        return apiClient.doRequest(request);
    }

    public Result update(String quickKey, UpdateParameters params) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/update.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FOLDER_KEY, quickKey);
        request.addQueryParameter(PARAM_FOLDERNAME, params.mFoldername);
        request.addQueryParameter(PARAM_DESCRIPTION, params.mDescription);
        request.addQueryParameter(PARAM_PRIVACY, params.mPrivacy);
        request.addQueryParameter(PARAM_PRIVACY_RECURSIVE, params.mPrivacyRecursive);
        request.addQueryParameter(PARAM_MTIME, params.mMTime);

        return apiClient.doRequest(request);
    }

    public Result getInfo(GetInfoParameters getInfoParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/get_info.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FOLDER_KEY, getInfoParameters.mFolderKey);
        request.addQueryParameter(PARAM_DEVICE_ID, getInfoParameters.mDeviceId);
        request.addQueryParameter(PARAM_DETAILS, getInfoParameters.mDetails);

        return apiClient.doRequest(request);
    }

    public Result getContent(GetContentParameters getContentParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/get_content.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FOLDER_KEY, getContentParameters.mFolderKey);
        request.addQueryParameter(PARAM_CONTENT_TYPE, getContentParameters.mContentType);
        request.addQueryParameter(PARAM_FILTER, getContentParameters.mFilter);
        request.addQueryParameter(PARAM_DEVICE_ID, getContentParameters.mDeviceId);
        request.addQueryParameter(PARAM_ORDER_BY, getContentParameters.mOrderBy);
        request.addQueryParameter(PARAM_ORDER_DIRECTION, getContentParameters.mOrderDirection);
        request.addQueryParameter(PARAM_CHUNK, getContentParameters.mChunk);
        request.addQueryParameter(PARAM_CHUNK_SIZE, getContentParameters.mChunkSize);
        request.addQueryParameter(PARAM_DETAILS, getContentParameters.mDetails);

        return apiClient.doRequest(request);
    }

    public Result getRevision(String folderKey, String returnChanges) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/get_revision.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FOLDER_KEY, folderKey);
        request.addQueryParameter(PARAM_RETURN_CHANGES, returnChanges);

        return apiClient.doRequest(request);
    }

    public Result search(SearchParameters searchParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/search.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);

        request.addQueryParameter(PARAM_FOLDER_KEY, searchParameters.mFolderKey);
        request.addQueryParameter(PARAM_FILTER, searchParameters.mFilter);
        request.addQueryParameter(PARAM_DEVICE_ID, searchParameters.mDeviceId);
        request.addQueryParameter(PARAM_SEARCH_ALL, searchParameters.mSearchAll);
        request.addQueryParameter(PARAM_DETAILS, searchParameters.mDetails);

        return apiClient.doRequest(request);
    }
}
