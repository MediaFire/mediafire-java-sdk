package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Response;

import java.util.Map;

/**
 * Created by Chris on 11/10/2014.
 */
public class DummyHttpWorker implements HttpWorkerInterface {
    @Override
    public Response doGet(String url, Map<String, String> headers) {
        return new DummyGETResponse(200, null, url);
    }

    @Override
    public Response doPost(String url, Map<String, String> headers, byte[] payload) {
        return new DummyPOSTResponse(200, null, url, payload);
    }

    public static class DummyGETResponse extends Response {
        private String mOriginalUrl;

        public DummyGETResponse(int status, byte[] bodyBytes, String originalUrl) {
            super(status, bodyBytes);
            mOriginalUrl = originalUrl;
        }

        public String getOriginalUrl() {
            return mOriginalUrl;
        }
    }

    public static class DummyPOSTResponse extends DummyGETResponse {
        private byte[] mOriginalPayload;

        public DummyPOSTResponse(int status, byte[] bodyBytes, String originalUrl, byte[] originalPayload) {
            super(status, bodyBytes, originalUrl);
            mOriginalPayload = originalPayload;
        }

        public byte[] getOriginalPayload() {
            return mOriginalPayload;
        }
    }
}
