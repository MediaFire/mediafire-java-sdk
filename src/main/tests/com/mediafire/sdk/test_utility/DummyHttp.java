package com.mediafire.sdk.test_utility;

import com.mediafire.sdk.config.HttpInterface;
import com.mediafire.sdk.http.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by Chris on 11/10/2014.
 */
public class DummyHttp implements HttpInterface {
    @Override
    public Response doGet(String url, Map<String, String> headers) {
        return new DummyGETResponse(200, null, null, url);
    }

    @Override
    public Response doPost(String url, Map<String, String> headers, byte[] payload) {
        return new DummyPOSTResponse(200, null, null, url, payload);
    }

    public static class DummyGETResponse extends Response {
        private String mOriginalUrl;

        public DummyGETResponse(int status, byte[] bodyBytes, Map<String, List<String>> headers, String originalUrl) {
            super(status, bodyBytes, headers);
            mOriginalUrl = originalUrl;
        }

        public String getOriginalUrl() {
            return mOriginalUrl;
        }
    }

    public static class DummyPOSTResponse extends DummyGETResponse {
        private byte[] mOriginalPayload;

        public DummyPOSTResponse(int status, byte[] bodyBytes, Map<String, List<String>> headers, String originalUrl, byte[] originalPayload) {
            super(status, bodyBytes, headers, originalUrl);
            mOriginalPayload = originalPayload;
        }

        public byte[] getOriginalPayload() {
            return mOriginalPayload;
        }
    }
}
