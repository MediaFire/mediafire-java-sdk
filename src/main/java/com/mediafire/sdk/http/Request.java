package com.mediafire.sdk.http;

import com.mediafire.sdk.token.Token;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Request is an object used to perform an Api Request
 */
public class Request {
    private final String mPath;
    private final String mHttpMethod;
    private final String mDomain;
    private final String mScheme;
    private final boolean mPostQuery;

    private Map<String, Object> mQueryParameters;
    private Map<String, String> mHeaders;
    private byte[] mPayload;
    private Token mToken;
    private String mSignature;

    private Request(Builder builder) {
        mPath = builder.mPath;
        mHttpMethod = builder.mHttpMethod;
        mDomain = builder.mFullDomain;
        mScheme = builder.mScheme;
        mPostQuery = builder.mPostQuery;
    }

    // TODO - finish writing constructor for url
    public Request(String url) {
        mPath = null;
        mDomain = null;
        mHttpMethod = "get";
        mScheme = null;
        mPostQuery = false;
    }

    public String getPath() {
        return mPath;
    }

    public String getHttpMethod() {
        return mHttpMethod;
    }

    public String getFullDomain() {
        return mDomain;
    }

    public String getScheme() {
        return mScheme;
    }

    public boolean postQuery() {
        return mPostQuery;
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

    /**
     * Builder used to create a request
     */
    public static class Builder {
        private static final String DEFAULT_SCHEME = "https";
        private static final String DEFAULT_DOMAIN = "www.mediafire.com";
        private static final boolean DEFAULT_POST_QUERY = false;
        private static final String DEFAULT_HTTP_METHOD = "get";

        private String mPath;
        private String mHttpMethod = DEFAULT_HTTP_METHOD;
        private String mFullDomain = DEFAULT_DOMAIN;
        private String mScheme = DEFAULT_SCHEME;
        private boolean mPostQuery = DEFAULT_POST_QUERY;
            
        public Builder() { }

        public Builder path(String value) {
            if (value == null || value.isEmpty()) {
                return this;
            }

            mPath = value;
            return this;
        }

        public Builder httpMethod(String value) {
            if (value == null || value.isEmpty()) {
                return this;
            }

            mHttpMethod = value;
            return this;
        }
        
        public Builder fullDomain(String value) {
            if (value == null || value.isEmpty()) {
                return this;
            }

            mFullDomain = value;
            return this;
        }
                
        public Builder scheme(String value) {
            if (value == null || value.isEmpty()) {
                return this;
            }

            mScheme = value;
            return this;
        }
        
        public Builder postQuery(boolean postQuery) {
            mPostQuery = postQuery;
            
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
