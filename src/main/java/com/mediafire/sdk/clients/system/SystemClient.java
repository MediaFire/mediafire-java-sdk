package com.mediafire.sdk.clients.system;

import com.mediafire.sdk.clients.ClientHelperNoToken;
import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ApiRequestGenerator;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by jondh on 11/4/14.
 */
public class SystemClient {

    private HttpWorkerInterface mHttpWorkerInterface;
    private final ApiRequestGenerator mApiRequestGenerator;

    public SystemClient(HttpWorkerInterface httpWorkerInterface, String apiVersion) {
        mHttpWorkerInterface = httpWorkerInterface;
        mApiRequestGenerator = new ApiRequestGenerator(apiVersion);
    }

    public SystemClient(HttpWorkerInterface httpWorkerInterface) {
        this(httpWorkerInterface, ApiVersion.VERSION_CURRENT);
    }

    public Result getInfo() {
        Request request = mApiRequestGenerator.createRequestObjectFromPath("system/get_info.php");
        ClientHelperNoToken clientHelper = new ClientHelperNoToken();
        ApiClient apiClient = new ApiClient(clientHelper, mHttpWorkerInterface);
        return apiClient.doRequest(request);
    }
}
