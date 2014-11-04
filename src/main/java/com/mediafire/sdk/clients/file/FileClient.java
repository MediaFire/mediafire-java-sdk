package com.mediafire.sdk.clients.file;

import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.ApiObject;
import com.mediafire.sdk.http.BorrowTokenType;
import com.mediafire.sdk.http.HostObject;
import com.mediafire.sdk.http.InstructionsObject;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.http.ReturnTokenType;
import com.mediafire.sdk.http.SignatureType;

/**
 * Created by Chris Najar on 10/30/2014.
 */
public class FileClient extends PathSpecificApiClient {
    private static final String PARAM_QUICK_KEY = "quick_key";
    private static final String PARAM_FOLDER_KEY = "folder_key";
    private static final String PARAM_FILE_NAME = "filename";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_MTIME = "mtime";
    private static final String PARAM_PRIVACY = "privacy";
    private static final String PARAM_LINK_TYPE = "link_type";

    private final HostObject mHost;
    private final InstructionsObject mInstructions;

    public FileClient(Configuration configuration, String apiVersion) {
        super(configuration, apiVersion);
        // init host object
        mHost = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructions = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.V2, true);
    }

    public Result getInfo(String quickKey) {
        ApiObject apiObject = new ApiObject("file", "get_info.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);

        return doRequestJson(request);
    }

    public Result delete(String quickKey) {
        ApiObject apiObject = new ApiObject("file", "delete.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);

        return doRequestJson(request);
    }

    public Result copy(String quickKey, String destinationKey) {
        ApiObject apiObject = new ApiObject("file", "copy.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);
        request.addQueryParameter(PARAM_FOLDER_KEY, destinationKey);

        return doRequestJson(request);
    }

    public Result getVersion(String quickKey) {
        ApiObject apiObject = new ApiObject("file", "get_versions.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);

        return doRequestJson(request);
    }

    public Result move(String quickKey) {
        ApiObject apiObject = new ApiObject("file", "move.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);

        return doRequestJson(request);
    }

    public Result update(String quickKey, UpdateParameters params) {
        ApiObject apiObject = new ApiObject("file", "update.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);
        request.addQueryParameter(PARAM_FILE_NAME, params.mFileName);
        request.addQueryParameter(PARAM_DESCRIPTION, params.mDescription);
        request.addQueryParameter(PARAM_PRIVACY, params.mPrivacy);
        request.addQueryParameter(PARAM_MTIME, params.mTime);

        return doRequestJson(request);
    }

    public Result rename(String quickKey, String newName) {
        UpdateParameters params = new UpdateParameters();
        params.fileName(newName);
        return update(quickKey, params);
    }

    public Result makePublic(String quickKey) {
        UpdateParameters params = new UpdateParameters();
        params.privacy(UpdateParameters.Privacy.PUBLIC);
        return update(quickKey, params);
    }

    public Result makePrivate(String quickKey) {
        UpdateParameters params = new UpdateParameters();
        params.privacy(UpdateParameters.Privacy.PRIVATE);
        return update(quickKey, params);
    }

    public Result getLinks(String quickKey, LinkType linkType) {
        ApiObject apiObject = new ApiObject("file", "get_links.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_QUICK_KEY, quickKey);
        if (linkType != null) {
            request.addQueryParameter(PARAM_LINK_TYPE, linkType.getLinkType());
        }

        return doRequestJson(request);
    }
}
