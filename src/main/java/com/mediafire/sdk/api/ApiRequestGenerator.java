package com.mediafire.sdk.api;

import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.Request;

public class ApiRequestGenerator {
    public static final String LATEST_STABLE_VERSION = "1.2";
    public static Request createRequestObjectFromPath(String path, String version) {
        String fullPath = "api/";

        if (version != null) {
            fullPath += version + "/";
        }

        fullPath += path;

        Request.Builder builder = new Request.Builder();
        builder.scheme("https").fullDomain(Configuration.getFullyQualifiedDomain()).httpMethod("post").path(fullPath);

        if ("upload/resumable.php".equalsIgnoreCase(path)) {
            builder.postQuery(false);
        } else if("upload/update.php".equalsIgnoreCase(path)) {
            builder.postQuery(false);
        } else {
            builder.postQuery(true);
        }

        Request request = builder.build();
        request.addQueryParameter("response_format", "json");
        return request;
    }

    public static Request createRequestObjectFromPath(String path) {
        return createRequestObjectFromPath(path, LATEST_STABLE_VERSION);
    }
}
