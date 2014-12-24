package com.mediafire.sdk.api;

import com.mediafire.sdk.http.Request;

public class ApiRequestGenerator {

    public static Request createRequestObjectFromPath(String path, String version) {
        String fullPath = "api/";

        if (version != null) {
            fullPath += version + "/";
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

    public static Request createRequestObjectFromPath(String path) {
        return createRequestObjectFromPath(path, null);
    }
}
