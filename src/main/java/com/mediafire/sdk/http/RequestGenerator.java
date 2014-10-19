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
        apiObjects.put("contact/add.php", new ApiObject("contact", "add.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("contact/delete.php", new ApiObject("contact", "delete.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("contact/fetch.php", new ApiObject("contact", "fetch.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        // file api calls
        apiObjects.put("file/copy.php", new ApiObject("file", "copy.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("file/delete.php", new ApiObject("file", "delete.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("file/purge.php", new ApiObject("file", "purge.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("file/move.php", new ApiObject("file", "move.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("file/update.php", new ApiObject("file", "update.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("file/get_info.php", new ApiObject("file", "get_info.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("file/get_links.php", new ApiObject("file", "get_links.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        // folder api calls
        apiObjects.put("folder/copy.php", new ApiObject("folder", "copy.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("folder/create.php", new ApiObject("folder", "create.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("folder/move.php", new ApiObject("folder", "move.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("folder/delete.php", new ApiObject("folder", "delete.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("folder/purge.php", new ApiObject("folder", "purge.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("folder/update.php", new ApiObject("folder", "update.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("folder/get_info.php", new ApiObject("folder", "get_info.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("folder/get_content.php", new ApiObject("folder", "get_content.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("folder/get_revision.php", new ApiObject("folder", "get_revision.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("folder/search.php", new ApiObject("folder", "search.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        // system api calls
        apiObjects.put("system/get_info.php", new ApiObject("system", "get_info.php", TokenType.NO_TOKEN_NEEDS_TO_BE_RETURNED, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NO_TOKEN_NEEDS_TO_BE_RETURNED, true));
        // user api calls
        apiObjects.put("user/get_info.php", new ApiObject("user", "get_info.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("user/register.php", new ApiObject("user", "register.php", TokenType.NO_TOKEN_NEEDS_TO_BE_RETURNED, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NO_TOKEN_NEEDS_TO_BE_RETURNED, true));
        apiObjects.put("user/get_avatar.php", new ApiObject("user", "get_avatar.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("user/set_avatar.php", new ApiObject("user", "set_avatar.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("user/get_session_token.php", new ApiObject("user", "get_session_token.php", TokenType.VERSION_2, SignatureType.NEW_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("user/get_action_token.php", new ApiObject("user", "get_action_token.php", TokenType.VERSION_2, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NEW_UPLOAD, true));
        apiObjects.put("user/get_action_token.php", new ApiObject("user", "get_action_token.php", TokenType.VERSION_2, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NEW_IMAGE, true));
        apiObjects.put("user/get_settings.php", new ApiObject("user", "get_settings.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("user/set_settings.php", new ApiObject("user", "set_settings.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        // upload api calls
        apiObjects.put("upload/check.php", new ApiObject("upload", "check.php", TokenType.NEW_UPLOAD, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NO_TOKEN_NEEDS_TO_BE_RETURNED, true));
        apiObjects.put("upload/instant.php", new ApiObject("upload", "instant.php", TokenType.NEW_UPLOAD, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NO_TOKEN_NEEDS_TO_BE_RETURNED, true));
        apiObjects.put("upload/poll_upload.php", new ApiObject("upload", "poll_upload.php", TokenType.NO_TOKEN_NEEDS_TO_BE_RETURNED, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NO_TOKEN_NEEDS_TO_BE_RETURNED, true));
        apiObjects.put("upload/resumable.php", new ApiObject("upload", "resumable.php", TokenType.NEW_UPLOAD, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NO_TOKEN_NEEDS_TO_BE_RETURNED, false));
        // device api calls
        apiObjects.put("device/get_changes.php", new ApiObject("device", "get_changes.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
        apiObjects.put("device/get_status.php", new ApiObject("device", "get_status.php", TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true));
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
