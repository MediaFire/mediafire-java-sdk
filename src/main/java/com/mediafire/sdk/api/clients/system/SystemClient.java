package com.mediafire.sdk.api.clients.system;

import com.mediafire.sdk.client_helpers.ClientHelperNoToken;
import com.mediafire.sdk.api.clients.ApiClient;
import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by jondh on 11/4/14.
 */
public class SystemClient {

    private final ApiClient apiClient;
    private final ApiRequestGenerator mApiRequestGenerator;

    public SystemClient(HttpWorkerInterface httpWorkerInterface) {
        mApiRequestGenerator = new ApiRequestGenerator();

        ClientHelperNoToken clientHelperNoToken = new ClientHelperNoToken();
        apiClient = new ApiClient(clientHelperNoToken, httpWorkerInterface);
    }

    public Result getInfo() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("system/get_info.php");
        return apiClient.doRequest(request);
    }
}
