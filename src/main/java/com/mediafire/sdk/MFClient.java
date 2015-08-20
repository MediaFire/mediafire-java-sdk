package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.ApiResponse;

public class MFClient implements MediaFireClient {

    private final String applicationId;
    private final String apiKey;
    private final String globalApiVersion;
    private final MediaFireCredentialsStore credentialsStore;
    private final MediaFireRequestCoordinator requestCoordinator;

    private MFClient(Builder builder) {
        this.applicationId = builder.applicationId;
        this.apiKey = builder.apiKey;
        this.credentialsStore = builder.credentialsStore;
        this.globalApiVersion = builder.globalApiVersion;
        this.requestCoordinator = builder.requestCoordinator;
    }

    @Override
    public <T extends ApiResponse> T doNoAuthRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException {
        return requestCoordinator.noAuthRequest(request, classOfT);
    }

    @Override
    public MediaFireHttpResponse doConversionRequest(MediaFireApiRequest request) throws MediaFireException {
        return requestCoordinator.conversionServerRequest(request);
    }

    @Override
    public <T extends ApiResponse> T doUploadRequest(MediaFireApiRequest request, byte[] payload, Class<T> classOfT) throws MediaFireException {
        return requestCoordinator.uploadRequest(request, payload, classOfT);
    }

    @Override
    public <T extends ApiResponse> T doAuthenticatedRequest(MediaFireApiRequest request, Class<T> classOfT) throws MediaFireException {
        return requestCoordinator.sessionRequest(request, classOfT);
    }

    @Override
    public MediaFireActionToken getActionToken(int type) throws MediaFireException {
        return requestCoordinator.getActionToken(type);
    }

    public static class Builder {

        private final String applicationId;
        private String apiKey;
        private MediaFireCredentialsStore credentialsStore;
        private String globalApiVersion;
        private MediaFireRequestCoordinator requestCoordinator;

        public Builder(String applicationId) {
            this.applicationId = applicationId;
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder globalApiVersion(String globalApiVersion) {
            this.globalApiVersion = globalApiVersion;
            return this;
        }

        public Builder credentialsStore(MediaFireCredentialsStore credentialsStore) {
            this.credentialsStore = credentialsStore;
            return this;
        }

        public Builder requestCoordinator(MediaFireRequestCoordinator requestCoordinator) {
            this.requestCoordinator = requestCoordinator;
            return this;
        }

        public MediaFireClient build() {
            return new MFClient(this);
        }
    }
}
