package com.mediafire.sdk.clients.folder;

import com.mediafire.sdk.clients.ApiClientHelper;
import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.*;

/**
 * Created by jondh on 11/4/14.
 */
public class FolderClient extends PathSpecificApiClient {
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

    private final HostObject mHost;
    private final InstructionsObject mInstructions;

    public FolderClient(ApiClientHelper apiClientHelper, HttpWorkerInterface httpWorkerInterface, String apiVersion) {
        super(apiClientHelper, httpWorkerInterface, apiVersion);
        // init host object
        mHost = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructions = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.V2, true);
    }

    public Result copy(MovementParameters movementParameters) {
        ApiObject apiObject = new ApiObject("folder", "copy.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FOLDER_KEY_SRC, movementParameters.mFolderKeySrc);
        request.addQueryParameter(PARAM_FOLDER_KEY_DST, movementParameters.mFolderKeyDst);

        return doRequestJson(request);
    }

    public Result create(String folderName, CreateParameters createParameters) {
        ApiObject apiObject = new ApiObject("folder", "create.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FOLDERNAME, folderName);
        request.addQueryParameter(PARAM_PARENT_KEY, createParameters.mParentKey);
        request.addQueryParameter(PARAM_ALLOW_DUPLICATE_NAME, createParameters.mAllowDuplicateName);
        request.addQueryParameter(PARAM_MTIME, createParameters.mMTime);

        return doRequestJson(request);
    }

    public Result move(MovementParameters movementParameters) {
        ApiObject apiObject = new ApiObject("folder", "move.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FOLDER_KEY_SRC, movementParameters.mFolderKeySrc);
        request.addQueryParameter(PARAM_FOLDER_KEY_DST, movementParameters.mFolderKeyDst);

        return doRequestJson(request);
    }

    public Result delete(String folderKey) {
        ApiObject apiObject = new ApiObject("folder", "delete.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FOLDER_KEY, folderKey);

        return doRequestJson(request);
    }

    public Result purge(String folderKey) {
        ApiObject apiObject = new ApiObject("folder", "purge.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FOLDER_KEY, folderKey);

        return doRequestJson(request);
    }

    public Result update(String quickKey, UpdateParameters params) {
        ApiObject apiObject = new ApiObject("folder", "update.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FOLDER_KEY, quickKey);
        request.addQueryParameter(PARAM_FOLDERNAME, params.mFoldername);
        request.addQueryParameter(PARAM_DESCRIPTION, params.mDescription);
        request.addQueryParameter(PARAM_PRIVACY, params.mPrivacy);
        request.addQueryParameter(PARAM_PRIVACY_RECURSIVE, params.mPrivacyRecursive);
        request.addQueryParameter(PARAM_MTIME, params.mMTime);

        return doRequestJson(request);
    }

    public Result getInfo(GetInfoParameters getInfoParameters) {
        ApiObject apiObject = new ApiObject("folder", "get_info.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FOLDER_KEY, getInfoParameters.mFolderKey);
        request.addQueryParameter(PARAM_DEVICE_ID, getInfoParameters.mDeviceId);
        request.addQueryParameter(PARAM_DETAILS, getInfoParameters.mDetails);

        return doRequestJson(request);
    }

    public Result getContent(GetContentParameters getContentParameters) {
        ApiObject apiObject = new ApiObject("folder", "get_content.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FOLDER_KEY, getContentParameters.mFolderKey);
        request.addQueryParameter(PARAM_CONTENT_TYPE, getContentParameters.mContentType);
        request.addQueryParameter(PARAM_FILTER, getContentParameters.mFilter);
        request.addQueryParameter(PARAM_DEVICE_ID, getContentParameters.mDeviceId);
        request.addQueryParameter(PARAM_ORDER_BY, getContentParameters.mOrderBy);
        request.addQueryParameter(PARAM_ORDER_DIRECTION, getContentParameters.mOrderDirection);
        request.addQueryParameter(PARAM_CHUNK, getContentParameters.mChunk);
        request.addQueryParameter(PARAM_CHUNK_SIZE, getContentParameters.mChunkSize);
        request.addQueryParameter(PARAM_DETAILS, getContentParameters.mDetails);

        return doRequestJson(request);
    }

    public Result getRevision(String folderKey, String returnChanges) {
        ApiObject apiObject = new ApiObject("folder", "get_revision.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FOLDER_KEY, folderKey);
        request.addQueryParameter(PARAM_RETURN_CHANGES, returnChanges);

        return doRequestJson(request);
    }

    public Result search(SearchParameters searchParameters) {
        ApiObject apiObject = new ApiObject("folder", "search.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_FOLDER_KEY, searchParameters.mFolderKey);
        request.addQueryParameter(PARAM_FILTER, searchParameters.mFilter);
        request.addQueryParameter(PARAM_DEVICE_ID, searchParameters.mDeviceId);
        request.addQueryParameter(PARAM_SEARCH_ALL, searchParameters.mSearchAll);
        request.addQueryParameter(PARAM_DETAILS, searchParameters.mDetails);

        return doRequestJson(request);
    }
}
