package com.mediafire.sdk.clients.user;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ApiRequestGenerator;
import com.mediafire.sdk.clients.ClientHelperNoToken;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris on 11/11/2014.
 */
public class RegisterClient {
    private static final String PARAM_APPLICATION_ID = "application_id";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_FB_ACCESS_TOKEN = "fb_access_token";
    private static final String PARAM_FIRST_NAME = "first_name";
    private static final String PARAM_LAST_NAME = "last_name";
    private static final String PARAM_DISPLAY_NAME = "display_name";

    private final HttpWorkerInterface mHttpWorker;
    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient apiClient;

    public RegisterClient(HttpWorkerInterface httpWorkerInterface) {
        mHttpWorker = httpWorkerInterface;
        mApiRequestGenerator = new ApiRequestGenerator(ApiVersion.VERSION_1_2);

        ClientHelperNoToken clientHelper = new ClientHelperNoToken();
        apiClient = new ApiClient(clientHelper, mHttpWorker);
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

        return apiClient.doRequest(request);
    }
}
