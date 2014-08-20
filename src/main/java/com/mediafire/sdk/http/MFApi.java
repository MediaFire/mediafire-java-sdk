package com.mediafire.sdk.http;

public enum MFApi {
    // contact api calls
    CONTACT_ADD("/api/1.0/contact/add.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    CONTACT_DELETE("/api/1.0/contact/delete.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    CONTACT_FETCH("/api/1.0/contact/fetch.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    // file api calls
    FILE_COPY("/api/1.0/file/copy.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FILE_DELETE("/api/1.0/file/delete.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FILE_PURGE("/api/1.0/file/purge.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FILE_MOVE("/api/1.0/file/move.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FILE_UPDATE("/api/1.0/file/update.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FILE_GET_INFO("/api/file/get_info.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FILE_GET_LINKS("/api/1.1/file/get_links.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    // folder api calls
    FOLDER_COPY("/api/1.0/folder/copy.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FOLDER_CREATE("/api/1.0/folder/create.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FOLDER_MOVE("/api/1.0/folder/move.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FOLDER_DELETE("/api/1.0/folder/delete.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FOLDER_PURGE("/api/1.0/folder/purge.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FOLDER_UPDATE("/api/1.0/folder/update.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FOLDER_GET_INFO("/api/1.0/folder/get_info.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FOLDER_GET_CONTENT("/api/folder/get_content.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FOLDER_GET_REVISION("/api/1.0/folder/get_revision.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    FOLDER_SEARCH("/api/1.0/folder/search.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    // system api calls
    SYSTEM_GET_INFO("/api/1.0/system/get_info.php", TokenType.NONE, TokenType.NONE, TokenType.NONE, true),
    // user api calls
    USER_GET_INFO("/api/1.0/user/get_info.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    USER_REGISTER("/api/1.0/user/register.php", TokenType.NONE, TokenType.NONE, TokenType.NONE, true),
    USER_LINK_FACEBOOK("/api/1.0/user/link_facebook.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    USER_LINK_TWITTER("/api/1.0/user/link_twitter.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    USER_UNLINK_FACEBOOK("/api/1.0/user/unlink_facebook.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    USER_UNLINK_TWITTER("/api/1.0/user/unlink_twitter.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    USER_GET_AVATAR("/api/1.0/user/get_avatar.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    USER_SET_AVATAR("/api/1.0/user/set_avatar.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    USER_GET_SESSION_TOKEN("/api/1.2/user/get_session_token.php", TokenType.NEW, TokenType.NEW, TokenType.NEW, true),
    USER_GET_UPLOAD_TOKEN("/api/1.0/user/get_action_token.php", TokenType.V2, TokenType.V2, TokenType.UPLOAD, true),
    USER_GET_IMAGE_TOKEN("/api/1.0/user/get_action_token.php", TokenType.V2, TokenType.V2, TokenType.IMAGE, true),
    // upload api calls
    UPLOAD_CHECK("/api/1.0/upload/check.php", TokenType.UPLOAD, TokenType.NONE, TokenType.NONE, true),
    UPLOAD_INSTANT("/api/1.0/upload/instant.php", TokenType.UPLOAD, TokenType.NONE, TokenType.NONE, true),
    UPLOAD_POLL_UPLOAD("/api/1.0/upload/poll_upload.php", TokenType.NONE, TokenType.NONE, TokenType.NONE, true),
    UPLOAD_RESUMABLE("/api/upload/resumable.php", TokenType.UPLOAD, TokenType.NONE, TokenType.NONE, false),
    // device api calls
    DEVICE_GET_CHANGES("/api/1.0/device/get_changes.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    DEVICE_GET_STATUS("/api/1.0/device/get_status.php", TokenType.V2, TokenType.V2, TokenType.V2, true),
    // empty custom mfapi that should not be passed
    CUSTOM_API_USED();

    private final String uri;
    private final TokenType typeOfTokenToBorrow;
    private final TokenType typeOfSignatureToAdd;
    private final TokenType typeOfTokenToReturn;
    private final boolean queryPostable;

    private MFApi(String uri, TokenType typeOfTokenToBorrow, TokenType typeOfSignatureToAdd, TokenType typeOfTokenToReturn, boolean queryPostable) {
        this.uri = uri;
        this.typeOfTokenToBorrow = typeOfTokenToBorrow;
        this.typeOfSignatureToAdd = typeOfSignatureToAdd;
        this.typeOfTokenToReturn = typeOfTokenToReturn;
        this.queryPostable = queryPostable;
    }

    private MFApi() {
        uri = "custom api used";
        typeOfTokenToBorrow = TokenType.NONE;
        typeOfSignatureToAdd = TokenType.NONE;
        typeOfTokenToReturn = TokenType.NONE;
        queryPostable = false;
    }

    /**
     * gets the uri for this enum that should be used for a request.
     * @return uri as a String that should be used for a request.
     */
    public String getUri() {
        return uri;
    }

    /**
     * gets the type of Token to borrow for a request.
     * @return a TokenType to borrow for a request.
     */
    public TokenType getTypeOfTokenToBorrow() {
        return typeOfTokenToBorrow;
    }

    /**
     * gets the TokenType of signature to add for a request.
     * @return a TokenType representing the type of signature to use.
     */
    public TokenType getTypeOfSignatureToAdd() {
        return typeOfSignatureToAdd;
    }

    /**
     * gets the type of Token to return after the requset is made.
     * @return a TokenType to return for a request.
     */
    public TokenType getTypeOfTokenToReturn() {
        return typeOfTokenToReturn;
    }

    /**
     * gets whether or not the query can be sent via POST.
     * @return true if query can be sent via POST, false if the query must be sent via GET.
     */
    public boolean isQueryPostable() {
        return queryPostable;
    }

    /**
     * gets whether or not a Token must be used.
     * @return true if a Token must be used, false if a Token does not need to be used.
     */
    public boolean isTokenRequired() {
        return typeOfTokenToBorrow != TokenType.NONE;
    }

    public enum TokenType {
        NEW, V2, UPLOAD, IMAGE, NONE,
    }
}
