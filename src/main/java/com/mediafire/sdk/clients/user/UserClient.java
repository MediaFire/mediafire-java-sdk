package com.mediafire.sdk.clients.user;

import com.mediafire.sdk.clients.ClientHelperNoToken;
import com.mediafire.sdk.clients.*;
import com.mediafire.sdk.config.*;
import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class UserClient {
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
    private static final String PARAM_COLLECT_META_DATA = "collect_metadata";

    private final CredentialsInterface mUserCredentials;
    private final CredentialsInterface mDeveloperCredentials;

    private final HttpWorkerInterface mHttpWorker;
    private final SessionTokenManagerInterface mSessionTokenManager;
    private final ActionTokenManagerInterface mActionTokenManager;
    private final String mApiVersion;
    private final ApiRequestGenerator mApiRequestGenerator;

    public UserClient(Configuration configuration, String apiVersion) {
        mApiVersion = apiVersion;
        mHttpWorker = configuration.getHttpWorker();
        mSessionTokenManager = configuration.getSessionTokenManager();
        mActionTokenManager = configuration.getActionTokenManager();
        mUserCredentials = configuration.getUserCredentials();
        mDeveloperCredentials = configuration.getDeveloperCredentials();
        mApiRequestGenerator = new ApiRequestGenerator(mApiVersion);
    }

    public UserClient(Configuration configuration) {
        this(configuration, ApiVersion.VERSION_CURRENT);
    }

    public Result getSessionTokenV2() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_session_token.php");
        // add application_id and relative parameters are added by ApiClientHelper
        request.addQueryParameter(PARAM_TOKEN_VERSION, 2);
        ClientHelperNewSessionToken clientHelper = new ClientHelperNewSessionToken(mUserCredentials, mDeveloperCredentials, mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }

    public Result getImageActionToken(int lifespanMinutes) {
        return getActionToken("image", lifespanMinutes);
    }

    public Result getUploadActionToken(int lifespanMinutes) {
        return getActionToken("upload", lifespanMinutes);
    }

    private Result getActionToken(String type, int lifespan) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_action_token.php");

        // add application_id and relative parameters are added by ApiClientHelper
        request.addQueryParameter(PARAM_TOKEN_VERSION, 2);
        request.addQueryParameter(PARAM_TOKEN_TYPE, type);
        request.addQueryParameter(PARAM_TOKEN_LIFESPAN, lifespan);

        // add application_id and relative parameters are added by ApiClientHelper
        request.addQueryParameter(PARAM_TOKEN_VERSION, 2);
        ClientHelperNewActionToken clientHelper = new ClientHelperNewActionToken(type, mActionTokenManager, mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }

    public Result getAvatar() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_avatar.php");
        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }

    public Result setAvatar(SetAvatarParameters requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/set_avatar.php");

        request.addQueryParameter(PARAM_SET_AVATAR_ACTION, requestParams.getAction());
        request.addQueryParameter(PARAM_QUICK_KEY, requestParams.getQuickKey());
        request.addQueryParameter(PARAM_URL, requestParams.getUrl());

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }

    public Result getInfo() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_info.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }

    public Result getSettings() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_settings.php");

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }

    public Result setSettings(SetSettingsParameters requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/set_settings.php");

        request.addQueryParameter(PARAM_PREVIOUS_FILE_VERSIONS, requestParams.getPreviousFileVersions());
        request.addQueryParameter(PARAM_DEFAULT_SHARE_LINK_STATUS, requestParams.getDefaultShareLinkStatus());
        request.addQueryParameter(PARAM_COLLECT_META_DATA, requestParams.getCollectMetaData());

        ClientHelperApi clientHelper = new ClientHelperApi(mSessionTokenManager);
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }

    public Result register(RegisterParameters requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/register.php");

        request.addQueryParameter(PARAM_APPLICATION_ID, requestParams.getApplicationId());
        request.addQueryParameter(PARAM_EMAIL, requestParams.getEmail());
        request.addQueryParameter(PARAM_PASSWORD, requestParams.getPassword());
        request.addQueryParameter(PARAM_FB_ACCESS_TOKEN, requestParams.getFacebookAccessToken());
        request.addQueryParameter(PARAM_FIRST_NAME, requestParams.getFirstName());
        request.addQueryParameter(PARAM_LAST_NAME, requestParams.getLastName());
        request.addQueryParameter(PARAM_DISPLAY_NAME, requestParams.getDisplayName());

        ClientHelperNoToken clientHelper = new ClientHelperNoToken();
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorker);
        return apiClient.doRequest(request);
    }
}
