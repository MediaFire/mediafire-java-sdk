package com.mediafire.sdk.client;

import com.mediafire.sdk.http.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris Najar on 10/18/2014.
 */
public class RequestGenerator {

    public RequestGenerator() {}

    private static final Map<String, Holder> holderMap = new HashMap<String, Holder>();

    public Request generateRequestObject(String apiVersion, String path, String file) {
        Holder holder = holderMap.get(path + "/" + file);
        if (holder == null) {
            return null;
        }

        VersionObject versionObject = new VersionObject(apiVersion);
        HostObject hostObject = new HostObject("https", "www", "mediafire.com", "post");

        Request request = new Request(hostObject, holder.mApiObject, holder.mInstructionsObject, versionObject);

        return request;
    }

    public Request generateRequestObject(String path, String file) {
        return generateRequestObject(null, path, file);
    }

    static {
        // contact api calls
        holderMap.put("contact/add.php", new Holder(
                new ApiObject("contact", "add.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("contact/delete.php", new Holder(
                new ApiObject("contact", "delete.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("contact/fetch.php", new Holder(
                new ApiObject("contact", "fetch.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        // file api calls
        holderMap.put("file/copy.php", new Holder(
                new ApiObject("file", "copy.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("file/delete.php", new Holder(
                new ApiObject("file", "delete.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("file/purge.php", new Holder(
                new ApiObject("file", "purge.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("file/move.php", new Holder(
                new ApiObject("file", "move.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("file/update.php", new Holder(
                new ApiObject("file", "update.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("file/get_info.php", new Holder(
                new ApiObject("file", "get_info.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("file/get_links.php", new Holder(
                new ApiObject("file", "get_links.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        // folder api calls
        holderMap.put("folder/copy.php", new Holder(
                new ApiObject("folder", "copy.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("folder/create.php", new Holder(
                new ApiObject("folder", "create.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("folder/move.php", new Holder(
                new ApiObject("folder", "move.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("folder/delete.php", new Holder(
                new ApiObject("folder", "delete.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("folder/purge.php", new Holder(
                new ApiObject("folder", "purge.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("folder/update.php", new Holder(
                new ApiObject("folder", "update.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("folder/get_info.php", new Holder(
                new ApiObject("folder", "get_info.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("folder/get_content.php", new Holder(
                new ApiObject("folder", "get_content.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("folder/get_revision.php", new Holder(
                new ApiObject("folder", "get_revision.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("folder/search.php", new Holder(
                new ApiObject("folder", "search.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        // system api calls
        holderMap.put("system/get_info.php", new Holder(
                new ApiObject("system", "get_info.php"),
                new InstructionsObject(TokenType.NONE, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NONE, true)));
        // user api calls
        holderMap.put("user/get_info.php", new Holder(
                new ApiObject("user", "get_info.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("user/register.php", new Holder(
                new ApiObject("user", "register.php"),
                new InstructionsObject(TokenType.NONE, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NONE, true)));
        holderMap.put("user/get_avatar.php", new Holder(
                new ApiObject("user", "get_avatar.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("user/set_avatar.php", new Holder(
                new ApiObject("user", "set_avatar.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("user/get_session_token.php", new Holder(
                new ApiObject("user", "get_session_token.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.NEW_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("user/get_action_token.php", new Holder(
                new ApiObject("user", "get_action_token.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NEW_UPLOAD, true)));
        holderMap.put("user/get_action_token.php", new Holder(
                new ApiObject("user", "get_action_token.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NEW_IMAGE, true)));
        holderMap.put("user/get_settings.php", new Holder(
                new ApiObject("user", "get_settings.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("user/set_settings.php", new Holder(
                new ApiObject("user", "set_settings.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        // upload api calls
        holderMap.put("upload/check.php", new Holder(
                new ApiObject("upload", "check.php"),
                new InstructionsObject(TokenType.NEW_UPLOAD, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NONE, true)));
        holderMap.put("upload/instant.php", new Holder(
                new ApiObject("upload", "instant.php"),
                new InstructionsObject(TokenType.NEW_UPLOAD, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NONE, true)));
        holderMap.put("upload/poll_upload.php", new Holder(
                new ApiObject("upload", "poll_upload.php"),
                new InstructionsObject(TokenType.NONE, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NONE, true)));
        holderMap.put("upload/resumable.php", new Holder(
                new ApiObject("upload", "resumable.php"),
                new InstructionsObject(TokenType.NEW_UPLOAD, SignatureType.NO_SIGNATURE_REQUIRED, TokenType.NONE, false)));
        // device api calls
        holderMap.put("device/get_changes.php", new Holder(
                new ApiObject("device", "get_changes.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
        holderMap.put("device/get_status.php", new Holder(
                new ApiObject("device", "get_status.php"),
                new InstructionsObject(TokenType.VERSION_2, SignatureType.USING_SESSION_TOKEN_SIGNATURE, TokenType.VERSION_2, true)));
    }

    private static class Holder {
        private final ApiObject mApiObject;
        private final InstructionsObject mInstructionsObject;

        public Holder(ApiObject apiObject, InstructionsObject instructionsObject) {
            mApiObject = apiObject;
            mInstructionsObject = instructionsObject;
        }
    }
}
