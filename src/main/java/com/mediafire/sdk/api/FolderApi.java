package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class FolderApi {

    private FolderApi() {
        // no instantiation, utility class
    }

    public static FolderCopyResponse copy(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/folder/copy.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FolderCopyResponse.class);
    }

    public static FolderCreateResponse create(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/folder/create.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FolderCreateResponse.class);
    }

    public static FolderMoveResponse move(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/folder/move.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FolderMoveResponse.class);
    }

    public static FolderDeleteResponse delete(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/folder/delete.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FolderDeleteResponse.class);
    }

    public static FolderPurgeResponse purge(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/folder/purge.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FolderPurgeResponse.class);
    }

    public static FolderUpdateResponse update(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/folder/update.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FolderUpdateResponse.class);
    }

    public static FolderGetInfoResponse getInfo(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/folder/get_info.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FolderGetInfoResponse.class);
    }

    public static FolderGetContentsResponse getContent(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/folder/get_content.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FolderGetContentsResponse.class);
    }

    public static FolderGetRevisionResponse getRevision(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/folder/get_revision.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FolderGetRevisionResponse.class);
    }

    public static FolderSearchResponse search(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/folder/search.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FolderSearchResponse.class);
    }
}
