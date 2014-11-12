package com.mediafire.sdk.clients.user;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ApiRequestGenerator;
import com.mediafire.sdk.clients.ClientHelperApi;
import com.mediafire.sdk.clients.ClientHelperNewActionToken;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class UserClient {
    private static final String PARAM_SET_AVATAR_ACTION = "action";
    private static final String PARAM_QUICK_KEY = "quick_key";
    private static final String PARAM_URL = "url";
    private static final String PARAM_PREVIOUS_FILE_VERSIONS = "previous_file_versions";
    private static final String PARAM_DEFAULT_SHARE_LINK_STATUS = "default_share_link_status";
    private static final String PARAM_COLLECT_META_DATA = "collect_metadata";

    private final HttpWorkerInterface mHttpWorker;
    private final SessionTokenManagerInterface mSessionTokenManager;
    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;

    public UserClient(Configuration configuration) {
        mHttpWorker = configuration.getHttpWorker();
        mSessionTokenManager = configuration.getSessionTokenManager();
        mApiRequestGenerator = new ApiRequestGenerator(ApiVersion.VERSION_1_2);

        ClientHelperApi clientHelperApi = new ClientHelperApi(mSessionTokenManager);
        apiClient = new ApiClient(clientHelperApi, mHttpWorker);
    }

    public Result getAvatar() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_avatar.php");
        return apiClient.doRequest(request);
    }

    public Result setAvatar(SetAvatarParameters requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/set_avatar.php");

        request.addQueryParameter(PARAM_SET_AVATAR_ACTION, requestParams.getAction());
        request.addQueryParameter(PARAM_QUICK_KEY, requestParams.getQuickKey());
        request.addQueryParameter(PARAM_URL, requestParams.getUrl());

        return apiClient.doRequest(request);
    }

    public Result getInfo() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_info.php");

        return apiClient.doRequest(request);
    }

    public Result getSettings() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/get_settings.php");

        return apiClient.doRequest(request);
    }

    public Result setSettings(SetSettingsParameters requestParams) {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("user/set_settings.php");

        request.addQueryParameter(PARAM_PREVIOUS_FILE_VERSIONS, requestParams.getPreviousFileVersions());
        request.addQueryParameter(PARAM_DEFAULT_SHARE_LINK_STATUS, requestParams.getDefaultShareLinkStatus());
        request.addQueryParameter(PARAM_COLLECT_META_DATA, requestParams.getCollectMetaData());

        return apiClient.doRequest(request);
    }
}
