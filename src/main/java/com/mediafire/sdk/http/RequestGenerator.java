package com.mediafire.sdk.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class RequestGenerator {

    public RequestGenerator() {}

    private static Map<String, ApiObject> apiObjects = new HashMap<String, ApiObject>();

    static {
        // contact api calls
        apiObjects.put("contact/add.php", new ApiObject("contact", "add.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("contact/delete.php", new ApiObject("contact", "delete.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("contact/fetch.php", new ApiObject("contact", "fetch.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        // file api calls
        apiObjects.put("file/copy.php", new ApiObject("file", "copy.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("file/delete.php", new ApiObject("file", "delete.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("file/purge.php", new ApiObject("file", "purge.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("file/move.php", new ApiObject("file", "move.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("file/update.php", new ApiObject("file", "update.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("file/get_info.php", new ApiObject("file", "get_info.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("file/get_links.php", new ApiObject("file", "get_links.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        // folder api calls
        apiObjects.put("folder/copy.php", new ApiObject("folder", "copy.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("folder/create.php", new ApiObject("folder", "create.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("folder/move.php", new ApiObject("folder", "move.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("folder/delete.php", new ApiObject("folder", "delete.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("folder/purge.php", new ApiObject("folder", "purge.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("folder/update.php", new ApiObject("folder", "update.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("folder/get_info.php", new ApiObject("folder", "get_info.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("folder/get_content.php", new ApiObject("folder", "get_content.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("folder/get_revision.php", new ApiObject("folder", "get_revision.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("folder/search.php", new ApiObject("folder", "search.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        // system api calls
        apiObjects.put("system/get_info.php", new ApiObject("system", "get_info.php", TokenType.NONE, TokenType.NONE, TokenType.NONE, true));
        // user api calls
        apiObjects.put("user/get_info.php", new ApiObject("user", "get_info.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("user/register.php", new ApiObject("user", "register.php", TokenType.NONE, TokenType.NONE, TokenType.NONE, true));
        apiObjects.put("user/get_avatar.php", new ApiObject("user", "get_avatar.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("user/set_avatar.php", new ApiObject("user", "set_avatar.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("user/get_session_token.php", new ApiObject("user", "get_session_token.php", TokenType.NEW, TokenType.NEW, TokenType.NEW, true));
        apiObjects.put("user/get_action_token.php", new ApiObject("user", "get_action_token.php", TokenType.V2, TokenType.V2, TokenType.UPLOAD, true));
        apiObjects.put("user/get_action_token.php", new ApiObject("user", "get_action_token.php", TokenType.V2, TokenType.V2, TokenType.IMAGE, true));
        apiObjects.put("user/get_settings.php", new ApiObject("user", "get_settings.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("user/set_settings.php", new ApiObject("user", "set_settings.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        // upload api calls
        apiObjects.put("upload/check.php", new ApiObject("upload", "check.php", TokenType.UPLOAD, TokenType.NONE, TokenType.NONE, true));
        apiObjects.put("upload/instant.php", new ApiObject("upload", "instant.php", TokenType.UPLOAD, TokenType.NONE, TokenType.NONE, true));
        apiObjects.put("upload/poll_upload.php", new ApiObject("upload", "poll_upload.php", TokenType.NONE, TokenType.NONE, TokenType.NONE, true));
        apiObjects.put("upload/resumable.php", new ApiObject("upload", "resumable.php", TokenType.UPLOAD, TokenType.NONE, TokenType.NONE, false));
        // device api calls
        apiObjects.put("device/get_changes.php", new ApiObject("device", "get_changes.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
        apiObjects.put("device/get_status.php", new ApiObject("device", "get_status.php", TokenType.V2, TokenType.V2, TokenType.V2, true));
    }

    public Request generateRequestObject(String apiVersion, String path, String file) {
        ApiObject apiObject = apiObjects.get(path + "/" + file);

        if (apiObject == null) {
            return null;
            // TODO throw custom exception
        }

        HostObject hostObject = new HostObject("https", "www", "mediafire.com", "post");
        VersionObject versionObject = new VersionObject(apiVersion);

        return new Request(hostObject, apiObject, versionObject);
    }
}
