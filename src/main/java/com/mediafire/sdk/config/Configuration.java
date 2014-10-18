package com.mediafire.sdk.config;

public class Configuration {
    private final int httpReadTimeout;
    private final int httpConnectionTimeout;
    private final int minimumSessionTokens;
    private final int maximumSessionTokens;
    private final String appId;
    private final String apiKey;
    private final MFLoggerInterface mfLogger;
    private final MFCredentialsInterface mfCredentials;
    private final MFNetworkConnectivityMonitorInterface mfNetworkConnectivityMonitor;
    private final ActionTokenManagerInterface actionTokenManagerInterface;
    private final MFSessionTokenFarmInterface mfSessionTokenFarmInterface;

    private Configuration(MFConfigurationBuilder mfConfigurationBuilder) {
        this.httpReadTimeout = mfConfigurationBuilder.httpReadTimeout;
        this.httpConnectionTimeout = mfConfigurationBuilder.httpConnectionTimeout;
        this.minimumSessionTokens = mfConfigurationBuilder.minimumSessionTokens;
        this.maximumSessionTokens = mfConfigurationBuilder.maximumSessionTokens;
        this.appId = mfConfigurationBuilder.appId;
        this.apiKey = mfConfigurationBuilder.apiKey;
        mfLogger = mfConfigurationBuilder.mfLogger;
        this.mfCredentials = mfConfigurationBuilder.mfCredentials;
        this.mfNetworkConnectivityMonitor = mfConfigurationBuilder.mfNetworkConnectivityMonitor;
        this.actionTokenManagerInterface = mfConfigurationBuilder.actionTokenManagerInterface;
        this.mfSessionTokenFarmInterface = mfConfigurationBuilder.mfSessionTokenFarmInterface;
    }

    public int getHttpReadTimeout() {
        return httpReadTimeout;
    }

    public int getHttpConnectionTimeout() {
        return httpConnectionTimeout;
    }

    public int getMinimumSessionTokens() {
        return minimumSessionTokens;
    }

    public int getMaximumSessionTokens() {
        return maximumSessionTokens;
    }

    public String getAppId() {
        return appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public MFLoggerInterface getMFLogger() {
        return mfLogger;
    }

    public MFCredentialsInterface getMFCredentials() {
        return mfCredentials;
    }

    public MFNetworkConnectivityMonitorInterface getMFNetworkConnectivityMonitor() {
        return mfNetworkConnectivityMonitor;
    }

    public ActionTokenManagerInterface getMFActionTokenFarmInterface() {
        return actionTokenManagerInterface;
    }

    public MFSessionTokenFarmInterface getMFSessionTokenFarmInterface() {
        return mfSessionTokenFarmInterface;
    }

    public static class MFConfigurationBuilder {
        private static final int DEFAULT_HTTP_READ_TIMEOUT = 45000;
        private static final int DEFAULT_HTTP_CONNECTION_TIMEOUT = 45000;
        private static final int DEFAULT_MINIMUM_SESSION_TOKENS = 1;
        private static final int DEFAULT_MAXIMUM_SESSION_TOKENS = 3;

        private int httpReadTimeout = DEFAULT_HTTP_READ_TIMEOUT;
        private int httpConnectionTimeout = DEFAULT_HTTP_CONNECTION_TIMEOUT;
        private int minimumSessionTokens = DEFAULT_MINIMUM_SESSION_TOKENS;
        private int maximumSessionTokens = DEFAULT_MAXIMUM_SESSION_TOKENS;
        private final MFLoggerInterface mfLogger;
        private final MFCredentialsInterface mfCredentials;
        private final MFNetworkConnectivityMonitorInterface mfNetworkConnectivityMonitor;
        private final ActionTokenManagerInterface actionTokenManagerInterface;
        private final MFSessionTokenFarmInterface mfSessionTokenFarmInterface;

        private String apiKey;
        private final String appId;

        public MFConfigurationBuilder(String appId,
                                      MFLoggerInterface mfLogger,
                                      MFCredentialsInterface mfCredentials,
                                      MFNetworkConnectivityMonitorInterface mfNetworkConnectivityMonitor,
                                      ActionTokenManagerInterface actionTokenManagerInterface,
                                      MFSessionTokenFarmInterface mfSessionTokenFarmInterface) {
            if (appId == null) {
                throw new IllegalArgumentException("app id cannot be null");
            }
            if (mfLogger == null) {
                throw new IllegalArgumentException("MFLoggerInterface cannot be null");
            }
            if (mfCredentials == null) {
                throw new IllegalArgumentException("MFCredentialsInterface cannot be null");
            }
            if (mfNetworkConnectivityMonitor == null) {
                throw new IllegalArgumentException("MFNetworkConnectivityMonitorInterface cannot be null");
            }
            if (actionTokenManagerInterface == null) {
                throw new IllegalArgumentException("ActionTokenManagerInterface cannot be null");
            }
            if (mfSessionTokenFarmInterface == null) {
                throw new IllegalArgumentException("MFSessionTokenFarmInterface cannot be null");
            }

            this.appId = appId;
            this.mfLogger = mfLogger;
            this.mfCredentials = mfCredentials;
            this.mfNetworkConnectivityMonitor = mfNetworkConnectivityMonitor;
            this.actionTokenManagerInterface = actionTokenManagerInterface;
            this.mfSessionTokenFarmInterface = mfSessionTokenFarmInterface;
        }

        public MFConfigurationBuilder apiKey(String apiKey) {
            if (apiKey == null) {
                throw new IllegalArgumentException("apiKey cannot be null");
            }
            this.apiKey = apiKey;
            return this;

        }

        public MFConfigurationBuilder httpReadTimeout(int httpReadTimeout) {
            if (httpReadTimeout < 0) {
                throw new IllegalArgumentException("http read timeout must not be negative");
            }
            this.httpReadTimeout = httpReadTimeout;
            return this;
        }

        public MFConfigurationBuilder httpConnectionTimeout(int httpConnectionTimeout) {
            if (httpConnectionTimeout < 0) {
                throw new IllegalArgumentException("http connection timeout must not be negative");
            }
            this.httpConnectionTimeout = httpConnectionTimeout;
            return this;
        }

        public MFConfigurationBuilder minimumSessionTokens(int minimumSessionTokens) {
            if (minimumSessionTokens < 1) {
                throw new IllegalArgumentException("minimumSessionTokens session tokens must be greater than 0");
            }
            this.minimumSessionTokens = minimumSessionTokens;
            return this;
        }

        public MFConfigurationBuilder maximumSessionTokens(int maximumSessionTokens) {
            if (maximumSessionTokens < 1) {
                throw new IllegalArgumentException("maximum session tokens must be greater than 0");
            }
            this.maximumSessionTokens = maximumSessionTokens;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
