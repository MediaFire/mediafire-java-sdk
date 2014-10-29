package com.mediafire.sdk.clients.user;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.PathSpecificApiClient;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.BorrowTokenType;
import com.mediafire.sdk.http.HostObject;
import com.mediafire.sdk.http.InstructionsObject;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.http.ReturnTokenType;
import com.mediafire.sdk.http.SignatureType;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class UserClient extends PathSpecificApiClient {

    private HostObject mHostObject;
    private InstructionsObject mInstructionsObject;

    public UserClient(Configuration configuration, String apiVersion) {
        super(configuration, apiVersion);

        // init host object
        mHostObject = new HostObject("https", "www", "mediafire.com", "post");
        // init instructions object
        mInstructionsObject = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.V2, false);
    }

    public UserClient(Configuration configuration) {
        this(configuration, null);
    }

    public Result getSessionToken() {
        return null;
    }

    public Result getAvatar() {
        return null;
    }

    public Result getInfo() {

        return null;
    }

    public Result getSettings() {
        return null;
    }

    public Result register() {
        return null;
    }

    public Result setAvatar() {
        return null;
    }

    public Result setSettings() {
        return null;
    }
}
