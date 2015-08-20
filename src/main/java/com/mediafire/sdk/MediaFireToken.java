package com.mediafire.sdk;

public interface MediaFireToken {
    /**
     * The token used to authenticate the user in API calls.
     * @return
     */
    String getSessionToken();
}
