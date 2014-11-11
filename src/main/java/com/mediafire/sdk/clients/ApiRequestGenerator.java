package com.mediafire.sdk.clients;

import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;

import java.util.HashMap;

/**
 * Created by Chris on 11/9/2014.
 */
public class ApiRequestGenerator {

    public ApiRequestGenerator() { }

    public Request createRequestObjectFromPath(String path, String version) {
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
        
        return builder.build();
    }

    public Request createRequestObjectFromPath(String path) {
        return createRequestObjectFromPath(path, ApiVersion.VERSION_CURRENT);
    }
}
