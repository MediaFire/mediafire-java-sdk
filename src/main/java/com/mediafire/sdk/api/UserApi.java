package com.mediafire.sdk.api;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.responses.*;
import com.mediafire.sdk.requests.ApiPostRequest;

import java.util.Map;

public class UserApi {

    public UserApi() {
        // no instantiation, utility class
    }

    public static UserGetAvatarResponse getAvatar(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/user/get_avatar.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, UserGetAvatarResponse.class);
    }
    
    public static UserSetAvatarResponse setAvatar(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/user/set_avatar.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, UserSetAvatarResponse.class);
    }

    public static UserGetInfoResponse getInfo(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/user/get_info.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, UserGetInfoResponse.class);
    }

    public static UserGetSettingsResponse getSettings(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/user/get_settings.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, UserGetSettingsResponse.class);
    }
    
    public static UserSetSettingsResponse setSettings(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/user/set_settings.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, UserSetSettingsResponse.class);
    }

    public static UserDestroyActionTokenResponse destroyActionToken(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/user/destroy_action_token.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, UserDestroyActionTokenResponse.class);
    }

    public static UserGetSessionTokenResponse getSessionToken(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/user/get_session_token.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, UserGetSessionTokenResponse.class);
    }

    public static UserGetActionTokenResponse getUploadActionToken(MediaFire mediaFire, Map<String, Object> requestParams, String apiVersion) throws MFException, MFApiException {
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/" + apiVersion + "/user/get_action_token.php", requestParams);
        return mediaFire.doApiRequest(apiPostRequest, UserGetActionTokenResponse.class);
    }
}
