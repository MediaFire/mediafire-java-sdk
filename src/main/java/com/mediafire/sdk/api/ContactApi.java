package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.Map;

public class ContactApi {

    private ContactApi() {
        // no instantiation, utility class
    }

    public static ContactAddResponse add(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/add.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, ContactAddResponse.class);
    }

    public static ContactDeleteResponse delete(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/delete.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, ContactDeleteResponse.class);
    }

    public static ContactFetchResponse fetch(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/fetch.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, ContactFetchResponse.class);
    }

    public static ContactGetAvatarResponse getAvatar(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/get_avatar.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, ContactGetAvatarResponse.class);
    }

    public static ContactGetSourcesResponse getSources(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/get_sources.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, ContactGetSourcesResponse.class);
    }

    public UserSetAvatarResponse setAvatar(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/set_avatar.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, UserSetAvatarResponse.class);
    }

    public static ContactSummaryResponse summary(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/contact/summary.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, ContactSummaryResponse.class);
    }
}
