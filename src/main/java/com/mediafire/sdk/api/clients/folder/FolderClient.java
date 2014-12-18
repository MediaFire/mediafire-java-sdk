package com.mediafire.sdk.api.clients.folder;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.clients.ApiClient;
import com.mediafire.sdk.client_helpers.ClientHelperApi;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
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
    private static final String PARAM_SEARCH_TEXT = "search_text";

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;

    public FolderClient(IHttp httpInterface, ITokenManager sessionITokenManagerInterface) {
        mApiRequestGenerator = new ApiRequestGenerator();

        ClientHelperApi clientHelper = new ClientHelperApi(sessionITokenManagerInterface);
        apiClient = new ApiClient(clientHelper, httpInterface);
    }

    public Result copy(String sourceFolderKey, String destinationFolderKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/copy.php");

        request.addQueryParameter(PARAM_FOLDER_KEY_SRC, sourceFolderKey);
        request.addQueryParameter(PARAM_FOLDER_KEY_DST, destinationFolderKey);

        return apiClient.doRequest(request);
    }

    public Result copy(String sourceFolderKey) {
        return copy(sourceFolderKey, null);
    }

    public Result create(String folderName, CreateParameters createParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/create.php");

        request.addQueryParameter(PARAM_FOLDERNAME, folderName);
        request.addQueryParameter(PARAM_PARENT_KEY, createParameters.getParentKey());
        request.addQueryParameter(PARAM_ALLOW_DUPLICATE_NAME, createParameters.getAllowDuplicateName());
        request.addQueryParameter(PARAM_MTIME, createParameters.getMTime());

        return apiClient.doRequest(request);
    }

    public Result create(String folderName, String destinationFolderKey) {
        CreateParameters.Builder builder = new CreateParameters.Builder();
        builder.allowDuplicateName(true);
        builder.parentKey(destinationFolderKey);
        CreateParameters createParameters = builder.build();
        return create(folderName, createParameters);
    }

    public Result move(String sourceFolderKey, String destinationFolderKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/move.php");

        request.addQueryParameter(PARAM_FOLDER_KEY_SRC, sourceFolderKey);
        request.addQueryParameter(PARAM_FOLDER_KEY_DST, destinationFolderKey);

        return apiClient.doRequest(request);
    }

    public Result move(String sourceFolderKey) {
        return move(sourceFolderKey, null);
    }

    public Result delete(String folderKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/delete.php");

        request.addQueryParameter(PARAM_FOLDER_KEY, folderKey);

        return apiClient.doRequest(request);
    }

    public Result purge(String folderKey) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/purge.php");

        request.addQueryParameter(PARAM_FOLDER_KEY, folderKey);

        return apiClient.doRequest(request);
    }

    public Result update(String quickKey, UpdateParameters params) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/update.php");

        request.addQueryParameter(PARAM_FOLDER_KEY, quickKey);
        request.addQueryParameter(PARAM_FOLDERNAME, params.getFoldername());
        request.addQueryParameter(PARAM_DESCRIPTION, params.getDescription());
        request.addQueryParameter(PARAM_PRIVACY, params.getPrivacy());
        request.addQueryParameter(PARAM_PRIVACY_RECURSIVE, params.getPrivacyRecursive());
        request.addQueryParameter(PARAM_MTIME, params.getMTime());

        return apiClient.doRequest(request);
    }

    public Result getInfo(GetInfoParameters getInfoParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/get_info.php");

        request.addQueryParameter(PARAM_FOLDER_KEY, getInfoParameters.getFolderKey());
        request.addQueryParameter(PARAM_DEVICE_ID, getInfoParameters.getDeviceId());
        request.addQueryParameter(PARAM_DETAILS, getInfoParameters.getDetails());

        return apiClient.doRequest(request);
    }

    public Result getContent(GetContentParameters getContentParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/get_content.php");

        request.addQueryParameter(PARAM_FOLDER_KEY, getContentParameters.getFolderKey());
        request.addQueryParameter(PARAM_CONTENT_TYPE, getContentParameters.getContentType());
        request.addQueryParameter(PARAM_FILTER, getContentParameters.getFilter());
        request.addQueryParameter(PARAM_DEVICE_ID, getContentParameters.getDeviceId());
        request.addQueryParameter(PARAM_ORDER_BY, getContentParameters.getOrderBy());
        request.addQueryParameter(PARAM_ORDER_DIRECTION, getContentParameters.getOrderDirection());
        request.addQueryParameter(PARAM_CHUNK, getContentParameters.getChunk());
        request.addQueryParameter(PARAM_CHUNK_SIZE, getContentParameters.getChunkSize());
        request.addQueryParameter(PARAM_DETAILS, getContentParameters.getDetails());

        return apiClient.doRequest(request);
    }

    public Result getRevision(String folderKey, boolean returnChanges) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/get_revision.php");

        request.addQueryParameter(PARAM_FOLDER_KEY, folderKey);
        request.addQueryParameter(PARAM_RETURN_CHANGES, returnChanges ? "yes" : "no");

        return apiClient.doRequest(request);
    }

    public Result getRevision(String folderKey) {
        return getRevision(folderKey, false);
    }

    public Result search(SearchParameters searchParameters) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("folder/search.php");

        request.addQueryParameter(PARAM_FOLDER_KEY, searchParameters.getFolderKey());
        request.addQueryParameter(PARAM_FILTER, searchParameters.getFilter());
        request.addQueryParameter(PARAM_DEVICE_ID, searchParameters.getDeviceId());
        request.addQueryParameter(PARAM_SEARCH_ALL, searchParameters.getSearchAll());
        request.addQueryParameter(PARAM_DETAILS, searchParameters.getDetails());
        request.addQueryParameter(PARAM_SEARCH_TEXT, searchParameters.getSearchText());

        return apiClient.doRequest(request);
    }
}
