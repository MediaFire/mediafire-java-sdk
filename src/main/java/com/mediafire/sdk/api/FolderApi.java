package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiRequest;

import java.util.Map;

public class FolderApi {

    private FolderApi() {
        // no instantiation, utility class
    }

    public static FolderCopyResponse copy(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/folder/copy.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FolderCopyResponse.class);
    }

    public static FolderCreateResponse create(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/folder/create.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FolderCreateResponse.class);
    }

    public static FolderMoveResponse move(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/folder/move.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FolderMoveResponse.class);
    }

    public static FolderDeleteResponse delete(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/folder/delete.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FolderDeleteResponse.class);
    }

    public static FolderPurgeResponse purge(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/folder/purge.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FolderPurgeResponse.class);
    }

    public static FolderUpdateResponse update(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/folder/update.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FolderUpdateResponse.class);
    }

    public static FolderGetInfoResponse getInfo(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/folder/get_info.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FolderGetInfoResponse.class);
    }

    public static FolderGetContentsResponse getContent(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/folder/get_content.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FolderGetContentsResponse.class);
    }

    public static FolderGetRevisionResponse getRevision(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/folder/get_revision.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FolderGetRevisionResponse.class);
    }

    public static FolderSearchResponse search(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/folder/search.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FolderSearchResponse.class);
    }
}
