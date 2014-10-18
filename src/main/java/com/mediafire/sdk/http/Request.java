package com.mediafire.sdk.http;

import com.mediafire.sdk.token.Token;

import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    private final HostObject mMFHostObject;
    private final ApiObject mMFApiObject;

    private Map<String, String> requestParameters = new LinkedHashMap<String, String>();
    private Map<String, String> headers = new LinkedHashMap<String, String>();
    private byte[] payload = new byte[0];
    private Token mMFToken;

    private Request(MFRequestBuilder mfRequestBuilder) {
        mMFHostObject = mfRequestBuilder.mMFHostObject;
        mMFApiObject = mfRequestBuilder.mMFApiObject;
        this.requestParameters = mfRequestBuilder.requestParameters;
        this.requestParameters.put("response_format", "json");
        this.headers = mfRequestBuilder.headers;
        this.payload = mfRequestBuilder.payload;
    }

    public HostObject getMFHostObjectHost() {
        return mMFHostObject;
    }

    public ApiObject getmMFApiObject() {
        return mMFApiObject;
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getPayload() {
        return payload;
    }

    public Token getToken() {
        return mMFToken;
    }

    public void setToken(Token MFToken) {
        this.mMFToken = MFToken;
    }

    public static class MFRequestBuilder {
        private final HostObject mMFHostObject;
        private final ApiObject mMFApiObject;

        private Map<String, String> requestParameters = new LinkedHashMap<String, String>();
        private Map<String, String> headers = new LinkedHashMap<String, String>();
        private byte[] payload = new byte[0];

        public MFRequestBuilder(HostObject hostObject, ApiObject apiObject) {
            mMFHostObject = hostObject;
            mMFApiObject = apiObject;
        }

        public MFRequestBuilder requestParameters(Map<String, String> requestParameters) {
            if (requestParameters == null) {
                requestParameters = new LinkedHashMap<String, String>();
            }
            this.requestParameters = requestParameters;
            return this;
        }

        public MFRequestBuilder headers(Map<String, String> headers) {
            if (headers == null) {
                headers = new LinkedHashMap<String, String>();
            }
            this.headers = headers;
            return this;
        }

        public MFRequestBuilder payload(byte[] payload) {
            if (payload == null) {
                payload = new byte[0];
            }
            this.payload = payload;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
