package com.mediafire.sdk.clients.user;

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
 * Created by Chris Najar on 10/29/2014.
 */
public class UserClient extends PathSpecificApiClient {
    private static final String PARAM_APPLICATION_ID = "application_id";
    private static final String PARAM_TOKEN_TYPE = "type";
    private static final String PARAM_TOKEN_LIFESPAN = "lifespan";
    private static final String PARAM_SET_AVATAR_ACTION = "action";
    private static final String PARAM_QUICK_KEY = "quick_key";
    private static final String PARAM_URL = "url";
    private static final String PARAM_PREVIOUS_FILE_VERSIONS = "previous_file_versions";
    private static final String PARAM_DEFAULT_SHARE_LINK_STATUS = "default_share_link_status";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_FB_ACCESS_TOKEN = "fb_access_token";
    private static final String PARAM_FIRST_NAME = "first_name";
    private static final String PARAM_LAST_NAME = "last_name";
    private static final String PARAM_DISPLAY_NAME = "display_name";
    private static final String PARAM_TOKEN_VERSION = "token_version";

    private HostObject mHost;
    private InstructionsObject mInstructions;

    public UserClient(Configuration configuration, String apiVersion) {
        super(configuration, apiVersion);

        // init host object
        mHost = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructions = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.V2, false);
    }

    public UserClient(Configuration configuration) {
        this(configuration, null);
    }

    public Result getSessionTokenV2() {
        ApiObject apiObject = new ApiObject("user", "get_session_token.php");
        InstructionsObject instructions = new InstructionsObject(BorrowTokenType.NONE, SignatureType.NEW_SESSION_TOKEN_SIGNATURE, ReturnTokenType.NEW_V2, true);
        Request request = new Request(mHost, apiObject, instructions, mVersionObject);
        // add application_id and relative parameters are added by ApiClientHelper
        request.addQueryParameter(PARAM_TOKEN_VERSION, 2);
        return doRequestJson(request);
    }

    public Result getImageActionToken(int lifespanMinutes) {
        return getActionToken("image", lifespanMinutes);
    }

    public Result getUploadActionToken(int lifespanMinutes) {
        return getActionToken("upload", lifespanMinutes);
    }

    private Result getActionToken(String type, int lifespan) {
        ApiObject apiObject = new ApiObject("user", "get_action_token.php");
        InstructionsObject instructions;
        if (type.equals("image")) {
            instructions = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.NEW_IMAGE, true);
        } else {
            instructions = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.NEW_UPLOAD, true);
        }

        Request request = new Request(mHost, apiObject, instructions, mVersionObject);

        request.addQueryParameter(PARAM_TOKEN_TYPE, type);
        request.addQueryParameter(PARAM_TOKEN_LIFESPAN, lifespan);

        return doRequestJson(request);
    }

    public Result getAvatar() {
        ApiObject apiObject = new ApiObject("user", "get_avatar.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);
        return doRequestJson(request);
    }

    public Result setAvatar(SetAvatarParameters requestParams) {
        ApiObject apiObject = new ApiObject("user", "set_avatar.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_SET_AVATAR_ACTION, requestParams.mAction);
        request.addQueryParameter(PARAM_QUICK_KEY, requestParams.mQuickKey);
        request.addQueryParameter(PARAM_URL, requestParams.mUrl);

        return doRequestJson(request);
    }

    public Result getInfo() {
        ApiObject apiObject = new ApiObject("user", "get_info.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);
        return doRequestJson(request);
    }

    public Result getSettings() {
        ApiObject apiObject = new ApiObject("user", "get_settings.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        return doRequestJson(request);
    }

    public Result setSettings(SetSettingsParameters requestParams) {
        ApiObject apiObject = new ApiObject("user", "set_settings.php");
        Request request = new Request(mHost, apiObject, mInstructions, mVersionObject);

        request.addQueryParameter(PARAM_PREVIOUS_FILE_VERSIONS, requestParams.mPreviousFileVersions);
        request.addQueryParameter(PARAM_DEFAULT_SHARE_LINK_STATUS, requestParams.mDefaultShareLinkStatus);

        return doRequestJson(request);
    }

    public Result register(RegisterParameters requestParams) {
        ApiObject apiObject = new ApiObject("user", "register.php");
        InstructionsObject instructions = new InstructionsObject(BorrowTokenType.NONE, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, true);
        Request request = new Request(mHost, apiObject, instructions, mVersionObject);

        request.addQueryParameter(PARAM_APPLICATION_ID, requestParams.mApplicationId);
        request.addQueryParameter(PARAM_EMAIL, requestParams.mEmail);
        request.addQueryParameter(PARAM_PASSWORD, requestParams.mPassword);
        request.addQueryParameter(PARAM_FB_ACCESS_TOKEN, requestParams.mFacebookAccessToken);
        request.addQueryParameter(PARAM_FIRST_NAME, requestParams.mFirstName);
        request.addQueryParameter(PARAM_LAST_NAME, requestParams.mLastName);
        request.addQueryParameter(PARAM_DISPLAY_NAME, requestParams.mDisplayName);

        return doRequestJson(request);
    }
}
