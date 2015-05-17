package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class FileApi {

    private FileApi() {
        // no instantiation, utility class
    }

    public static FileGetInfoResponse getInfo(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/get_info.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FileGetInfoResponse.class);
    }

    public static FileDeleteResponse delete(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/delete.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FileDeleteResponse.class);
    }

    public static FileCopyResponse copy(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/copy.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FileCopyResponse.class);
    }

    public static FileGetVersionResponse getVersion(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/get_version.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FileGetVersionResponse.class);
    }

    public static FileMoveResponse move(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/move.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FileMoveResponse.class);
    }

    public static FileUpdateResponse update(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/update.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FileUpdateResponse.class);
    }

    public static FileGetLinksResponse getLinks(MediaFire mediaFire, LinkedHashMap<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/file/get_links.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, FileGetLinksResponse.class);
    }
}
