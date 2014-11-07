package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.http.VersionObject;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public abstract class PathSpecificApiClient extends ApiClient {
    private static final String PARAM_RESPONSE_FORMAT = "response_format";

    protected final VersionObject mVersionObject;

    public PathSpecificApiClient(ApiClientHelper apiClientHelper, HttpWorkerInterface httpWorker, String apiVersion) {
        super(apiClientHelper, httpWorker);
        // init version object
        if (apiVersion == null || apiVersion.isEmpty()) {
            mVersionObject = new VersionObject(null);
        } else {
            mVersionObject = new VersionObject(apiVersion);
        }
    }

    /**
     * calls do request from super after adding query parameter response_format=json
     * @param request - the Request to use in the api call
     * @return a Result
     */
    protected Result doRequestJson(Request request) {
        request.addQueryParameter(PARAM_RESPONSE_FORMAT, "json");
        return doRequest(request);
    }
}
