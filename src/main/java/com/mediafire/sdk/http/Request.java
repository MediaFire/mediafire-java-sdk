package com.mediafire.sdk.http;

import com.mediafire.sdk.token.Token;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Request is an object used to perform an Api Request
 */
public class Request {
    private final HostObject mHostObject;
    private final ApiObject mApiObject;
    private final InstructionsObject mInstructionsObject;
    private final VersionObject mVersionObject;

    private Map<String, Object> mQueryParameters;
    private Map<String, String> mHeaders;
    private byte[] mPayload;
    private Token mToken;
    private String mSignature;

    /**
     * Request Constructor
     * @param hostObject HostObject is the host for the request
     * @param apiObject ApiObject is the api for the request
     * @param instructionsObject InstructionsObject are the instruction for the request
     * @param versionObject VersionObject is the api version for the request
     */
    public Request(HostObject hostObject, ApiObject apiObject, InstructionsObject instructionsObject, VersionObject versionObject) {
        mHostObject = hostObject;
        mApiObject = apiObject;
        mInstructionsObject = instructionsObject;
        mVersionObject = versionObject;
    }

    private Request(Builder builder) {
        mHostObject = builder.mHostObject;
        mApiObject = builder.mApiObject;
        mInstructionsObject = builder.mInstructionsObject;
        mVersionObject = builder.mVersionObject;
    }

    public String getApiVersion() {
        return mVersionObject.getApiVersion();
    }

    public BorrowTokenType getBorrowTokenType() {
        return mInstructionsObject.getBorrowTokenType();
    }

    public ReturnTokenType getReturnTokenType() {
        return mInstructionsObject.getReturnTokenType();
    }

    public SignatureType getSignatureType() {
        return mInstructionsObject.getSignatureType();
    }

    public String getPath() {
        return mApiObject.getPath();
    }

    public String getFile() {
        return mApiObject.getFile();
    }

    public String getHttpMethod() {
        return mHostObject.getHttpMethod();
    }

    public String getDomain() {
        return mHostObject.getDomain();
    }

    public String getSubdomain() {
        return mHostObject.getSubdomain();
    }

    public String getTransferProtocol() {
        return mHostObject.getTransferProtocol();
    }

    public boolean postQuery() {
        return mInstructionsObject.postQuery();
    }

    /**
     * Gets the query parameters
     * @return Map of query parameters  (null possible)
     */

    public Map<String, Object> getQueryParameters() {
        return mQueryParameters;
    }

    /**
     * Adds a query parameter to the query parameters map
     * @param key String key for the parameter to be added
     * @param value Object value for the parameter to be added
     */
    public void addQueryParameter(String key, Object value) {
        if (key == null || key.isEmpty()) {
            return;
        }

        if (value == null) {
            return;
        }

        if (value.toString().isEmpty()) {
            return;
        }

        if (mQueryParameters == null) {
            mQueryParameters = new LinkedHashMap<String, Object>();
        }

        mQueryParameters.put(key, value);
    }

    /**
     * Gets the headers
     * @return Map of headers (null possible)
     */
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    /**
     * Adds a header to the headers map
     * @param key String key for the header to be added
     * @param value String value for the header to be added
     */
    public void addHeader(String key, String value) {
        if (key == null || value == null) {
            return;
        }

        if (mHeaders == null) {
            mHeaders = new LinkedHashMap<String, String>();
        }

        mHeaders.put(key, value);
    }

    /**
     * Gets the payload
     * @return byte[] for the payload (null possible)
     */
    public byte[] getPayload() {
        return mPayload;
    }

    /**
     * Adds a payload to the request
     * @param payload byte[] payload to add to the request
     */
    public void addPayload(byte[] payload) {
        mPayload = payload;
    }

    /**
     * Gets the token
     * @return Token (null possible)
     */
    public Token getToken() {
        return mToken;
    }

    /**
     * Adds a token to the request
     * @param token Token to add to the request
     */
    public void addToken(Token token) {
        mToken = token;
    }

    /**
     * gets the signature for this Request.
     * @return String (null possible)
     */
    public String getSignature() {
        return mSignature;
    }

    /**
     * Adds a signature to the request
     * @param signature
     */
    public void addSignature(String signature) {
        mSignature = signature;
        addQueryParameter("signature", signature);
    }

    public static class Builder {
        private HostObject mHostObject;
        private ApiObject mApiObject;
        private InstructionsObject mInstructionsObject;
        private VersionObject mVersionObject;

        public Builder() { }

        public Builder hostObject(HostObject value) {
            mHostObject = value;
            return this;
        }

        public Builder apiObject(ApiObject value) {
            mApiObject = value;
            return this;
        }

        public Builder instructionsObject(InstructionsObject value) {
            mInstructionsObject = value;
            return this;
        }

        public Builder versionObject(VersionObject value) {
            mVersionObject = value;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
