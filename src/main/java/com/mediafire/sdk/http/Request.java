package com.mediafire.sdk.http;

import com.mediafire.sdk.token.Token;

import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    private final HostObject mHostObject;
    private final ApiObject mApiObject;
    private InstructionsObject mInstructionsObject;
    private final VersionObject mVersionObject;

    private Map<String, Object> mQueryParameters;
    private Map<String, String> mHeaders;
    private byte[] mPayload;
    private Token mToken;

    public Request(HostObject hostObject, ApiObject apiObject, InstructionsObject instructionsObject, VersionObject versionObject) {
        mHostObject = hostObject;
        mApiObject = apiObject;
        mInstructionsObject = instructionsObject;
        mVersionObject = versionObject;
    }

    public HostObject getHostObject() {
        return mHostObject;
    }

    public ApiObject getApiObject() {
        return mApiObject;
    }

    public InstructionsObject getInstructionsObject() {
        return mInstructionsObject;
    }

    public VersionObject getVersionObject() {
        return mVersionObject;
    }

    public Map<String, Object> getQueryParameters() {
        return mQueryParameters;
    }

    public void addQueryParameter(String key, Object value) {
        if (key == null || value == null) {
            return;
        }

        if (mQueryParameters == null) {
            mQueryParameters = new LinkedHashMap<String, Object>();
        }

        mQueryParameters.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void addHeader(String key, String value) {
        if (key == null || value == null) {
            return;
        }

        if (mHeaders == null) {
            mHeaders = new LinkedHashMap<String, String>();
        }

        mHeaders.put(key, value);
    }

    public byte[] getPayload() {
        return mPayload;
    }

    public void addPayload(byte[] payload) {
        mPayload = payload;
    }

    public Token getToken() {
        return mToken;
    }

    public void addToken(Token token) {
        mToken = token;
    }
}
