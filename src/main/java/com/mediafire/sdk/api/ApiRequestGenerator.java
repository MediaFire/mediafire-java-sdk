package com.mediafire.sdk.api;

import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;

/**
 * Created by Chris on 11/9/2014.
 */
public class ApiRequestGenerator {

    private final String mApiVersion;

    public ApiRequestGenerator() {
        mApiVersion = ApiVersion.VERSION_1_2;
    }

    public Request createRequestObjectFromPath(String path) {
        String fullPath = "api/";

        if (mApiVersion != null) {
            fullPath += mApiVersion + "/";
        }

        fullPath += path;

        Request.Builder builder = new Request.Builder();
        builder.scheme("https").fullDomain("www.mediafire.com").httpMethod("post").path(fullPath);

        if (path.equalsIgnoreCase("upload/resumable.php")) {
            builder.postQuery(false);
        } else {
            builder.postQuery(true);
        }

        Request request = builder.build();
        request.addQueryParameter("response_format", "json");
        return request;
    }
}