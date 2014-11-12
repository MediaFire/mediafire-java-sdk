package com.mediafire.sdk.api.clients.user;

import com.mediafire.sdk.api.clients.ApiClient;
import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.client_helpers.ClientHelperNewActionToken;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris on 11/11/2014.
 */
public class ActionTokenClient {

    private static final String PARAM_TOKEN_TYPE = "type";
    private static final String PARAM_TOKEN_LIFESPAN = "lifespan";

    private final ApiRequestGenerator mApiRequestGenerator;
    private final ApiClient imageActionTokenClient;
    private final ApiClient uploadActionTokenClient;

    public ActionTokenClient(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManagerInterface, ActionTokenManagerInterface actionTokenManagerInterface) {
        mApiRequestGenerator = new ApiRequestGenerator();

        ClientHelperNewActionToken imageActionTokenClientHelper = new ClientHelperNewActionToken("image", actionTokenManagerInterface, sessionTokenManagerInterface);
        imageActionTokenClient = new ApiClient(imageActionTokenClientHelper, httpWorkerInterface);

        ClientHelperNewActionToken uploadActionTokenClientHelper = new ClientHelperNewActionToken("upload", actionTokenManagerInterface, sessionTokenManagerInterface);
        uploadActionTokenClient = new ApiClient(uploadActionTokenClientHelper, httpWorkerInterface);
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
        request.addQueryParameter(PARAM_TOKEN_TYPE, type);
        request.addQueryParameter(PARAM_TOKEN_LIFESPAN, lifespan);

        Result result;
        if (type.equals("image")) {
            result = imageActionTokenClient.doRequest(request);
        } else if (type.equals("upload")) {
            result = uploadActionTokenClient.doRequest(request);
        } else {
            throw new IllegalArgumentException("no token type " + type + " exists for action token");
        }

        return result;
    }
}
