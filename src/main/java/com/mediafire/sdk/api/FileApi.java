package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiRequest;

import java.util.Map;

public class FileApi {

    private FileApi() {
        // no instantiation, utility class
    }

    public static FileGetInfoResponse getInfo(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/file/get_info.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FileGetInfoResponse.class);
    }

    public static FileDeleteResponse delete(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/file/delete.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FileDeleteResponse.class);
    }

    public static FileCopyResponse copy(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/file/copy.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FileCopyResponse.class);
    }

    public static FileGetVersionResponse getVersion(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/file/get_version.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FileGetVersionResponse.class);
    }

    public static FileMoveResponse move(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/file/move.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FileMoveResponse.class);
    }

    public static FileUpdateResponse update(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/file/update.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FileUpdateResponse.class);
    }

    public static FileGetLinksResponse getLinks(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiRequest apiRequest = new ApiRequest("/api/" + apiVersion + "/file/get_links.php", requestParams);
        return mediaFire.doApiRequest(apiRequest, FileGetLinksResponse.class);
    }
}
