package com.mediafire.sdk.clients;

import com.mediafire.sdk.http.Request;

import java.util.HashMap;

/**
 * Created by Chris on 11/9/2014.
 */
public class ApiRequestGenerator {
    private String mApiVersion; // TODO finish implementing

    public ApiRequestGenerator(String apiVersion) {
        mApiVersion = apiVersion;
    }

    public ApiRequestGenerator() {
        this(null);
    }

    private static final HashMap<String, Request> map = new HashMap<String, Request>();

    static {
        // contact
        map.put("contact/add.php", new Request.Builder().build());
        map.put("contact/delete.php", new Request.Builder().build());
        map.put("contact/fetch.php", new Request.Builder().build());
    }

    static {
        // device
        map.put("device/get_status.php", new Request.Builder().build());
        map.put("device/get_changes.php", new Request.Builder().build());
    }

    static {
        // file
        map.put("file/get_changes.php", new Request.Builder().build());
        map.put("file/get_changes.php", new Request.Builder().build());
        map.put("file/get_changes.php", new Request.Builder().build());
        map.put("file/get_changes.php", new Request.Builder().build());
        map.put("file/get_changes.php", new Request.Builder().build());
        map.put("file/get_changes.php", new Request.Builder().build());
        map.put("file/get_changes.php", new Request.Builder().build());
        map.put("file/get_changes.php", new Request.Builder().build());
        map.put("file/get_changes.php", new Request.Builder().build());
    }

    static {
        // folder
        map.put("folder/get_changes.php", new Request.Builder().build());
        map.put("folder/get_changes.php", new Request.Builder().build());
        map.put("folder/get_changes.php", new Request.Builder().build());
        map.put("folder/get_changes.php", new Request.Builder().build());
        map.put("folder/get_changes.php", new Request.Builder().build());
        map.put("folder/get_changes.php", new Request.Builder().build());
        map.put("folder/get_changes.php", new Request.Builder().build());
        map.put("folder/get_changes.php", new Request.Builder().build());
        map.put("folder/get_changes.php", new Request.Builder().build());
    }

    static {
        // meta
        map.put("meta/get_changes.php", new Request.Builder().build());
        map.put("meta/get_changes.php", new Request.Builder().build());
        map.put("meta/get_changes.php", new Request.Builder().build());
        map.put("meta/get_changes.php", new Request.Builder().build());
        map.put("meta/get_changes.php", new Request.Builder().build());
        map.put("meta/get_changes.php", new Request.Builder().build());
        map.put("meta/get_changes.php", new Request.Builder().build());
        map.put("meta/get_changes.php", new Request.Builder().build());
        map.put("meta/get_changes.php", new Request.Builder().build());
    }

    static {
        // system
        map.put("system/get_changes.php", new Request.Builder().build());
        map.put("system/get_changes.php", new Request.Builder().build());
        map.put("system/get_changes.php", new Request.Builder().build());
        map.put("system/get_changes.php", new Request.Builder().build());
        map.put("system/get_changes.php", new Request.Builder().build());
        map.put("system/get_changes.php", new Request.Builder().build());
    }

    static {
        // upload
        map.put("upload/get_changes.php", new Request.Builder().build());
        map.put("upload/get_changes.php", new Request.Builder().build());
        map.put("upload/get_changes.php", new Request.Builder().build());
        map.put("upload/get_changes.php", new Request.Builder().build());
        map.put("upload/get_changes.php", new Request.Builder().build());
    }

    static {
        // user
        map.put("user/get_changes.php", new Request.Builder().build());
        map.put("user/get_changes.php", new Request.Builder().build());
        map.put("user/get_changes.php", new Request.Builder().build());
        map.put("user/get_changes.php", new Request.Builder().build());
        map.put("user/get_changes.php", new Request.Builder().build());
        map.put("user/get_changes.php", new Request.Builder().build());
        map.put("user/get_changes.php", new Request.Builder().build());
    }

    public Request createRequestObjectFromPath(String path) {
        return map.get(path);
    }
}
