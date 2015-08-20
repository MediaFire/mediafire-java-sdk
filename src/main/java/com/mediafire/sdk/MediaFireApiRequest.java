package com.mediafire.sdk;

import java.util.Map;

public interface MediaFireApiRequest {

    /**
     * for calls where no authentication is needed. typically these are system calls (e.g. system/get_info)
     */
    int REQUEST_TYPE_NO_AUTHENTICATION = 0;

    /**
     * for calls authentication calls (e.g. user/get_session_token)
     */
    int REQUEST_TYPE_AUTHENTICATION = 1;

    /**
     * for calls that require a session token (e.g. file/get_info)
     */
    int REQUEST_TYPE_USE_SESSION_TOKEN = 2;

    /**
     * for conversion calls where an action token can be used in lieu of a session token (e.g. /conversion_server)
     */
    int REQUEST_TYPE_CONVERSION = 3;

    /**
     * for upload calls where an action token can be used in lieu of a session token (e.g. upload/resumable)
     */
    int REQUEST_TYPE_UPLOAD = 4;

    /**
     * POST request
     */
    int HTTP_REQUEST_METHOD_POST = 1;

    /**
     * GET request
     */
    int HTTP_REQUEST_METHOD_GET = 2;

    /**
     * gets the path of the request (e.g. /api/1.2/user/get_info)
     * @return
     */
    String getPath();

    /**
     * gets the query parameters that shall be used in the request
     * @return
     */
    Map<String, Object> getQueryParameters();

    /**
     * payload for the request. if request type is REQUEST_TYPE_UPLOAD, this is the binary data.
     * @return
     */
    byte[] getPayload();

    /**
     * gets the type of the request
     * @return
     */
    int getRequestMethod();

    /**
     * gets the request type
     * @return
     */
    int getRequestType();
}
