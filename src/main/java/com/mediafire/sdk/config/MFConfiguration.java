package com.mediafire.sdk.config;

public class MFConfiguration {
    private final int httpReadTimeout;
    private final int httpConnectionTimeout;
    private final int minimumSessionTokens;
    private final int maximumSessionTokens;
    private final String appId;
    private final String apiKey;
    private final MFLoggerInterface mfLogger;
    private final MFCredentialsInterface mfCredentials;
    private final MFNetworkConnectivityMonitorInterface mfNetworkConnectivityMonitor;
    private final MFActionTokenFarmInterface mfActionTokenFarmInterface;
    private final MFSessionTokenFarmInterface mfSessionTokenFarmInterface;

    /**
     * Constructor to create an MFConfiguration instance.
     * @param mfConfigurationBuilder - builder pattern object.
     */
    private MFConfiguration(MFConfigurationBuilder mfConfigurationBuilder) {
        this.httpReadTimeout = mfConfigurationBuilder.httpReadTimeout;
        this.httpConnectionTimeout = mfConfigurationBuilder.httpConnectionTimeout;
        this.minimumSessionTokens = mfConfigurationBuilder.minimumSessionTokens;
        this.maximumSessionTokens = mfConfigurationBuilder.maximumSessionTokens;
        this.appId = mfConfigurationBuilder.appId;
        this.apiKey = mfConfigurationBuilder.apiKey;
        mfLogger = mfConfigurationBuilder.mfLogger;
        this.mfCredentials = mfConfigurationBuilder.mfCredentials;
        this.mfNetworkConnectivityMonitor = mfConfigurationBuilder.mfNetworkConnectivityMonitor;
        this.mfActionTokenFarmInterface = mfConfigurationBuilder.mfActionTokenFarmInterface;
        this.mfSessionTokenFarmInterface = mfConfigurationBuilder.mfSessionTokenFarmInterface;
    }

    /**
     * gets the http read timeout set when this object was constructed.
     * @return http read timeout
     */
    public int getHttpReadTimeout() {
        return httpReadTimeout;
    }

    /**
     * gets the http connection timeout set when this object was constructed.
     * @return http connection timeout
     */
    public int getHttpConnectionTimeout() {
        return httpConnectionTimeout;
    }

    /**
     * gets the min session tokens set when this object was constructed.
     * @return min session tokens
     */
    public int getMinimumSessionTokens() {
        return minimumSessionTokens;
    }

    /**
     * gets the max session tokens set when this object was constructed.
     * @return max session tokens
     */
    public int getMaximumSessionTokens() {
        return maximumSessionTokens;
    }

    /**
     * gets the developer's app id set when this object was constructed.
     * @return developer's app id.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * gets the developer's api key set when this object was constructed.
     * @return developer's api key.
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * gets the MFLoggerInterface set when this object was constructed.
     * @return MFLoggerInterface.
     */
    public MFLoggerInterface getMFLogger() {
        return mfLogger;
    }

    /**
     * gets the MFCredentialsInterface set when this object was constructed.
     * @return MFCredentialsInterface
     */
    public MFCredentialsInterface getMFCredentials() {
        return mfCredentials;
    }

    /**
     * gets the MFNetworkConnectivityMonitorInterface set when this object was constructed.
     * @return MFNetworkConnectivityMonitorInterface.
     */
    public MFNetworkConnectivityMonitorInterface getMFNetworkConnectivityMonitor() {
        return mfNetworkConnectivityMonitor;
    }

    /**
     * gets the MFActionTokenFarmInterface set when this object was constructed.
     * @return MFActionTokenFarmInterface
     */
    public MFActionTokenFarmInterface getMFActionTokenFarmInterface() {
        return mfActionTokenFarmInterface;
    }

    /**
     * gets the MFSessionTokenFarmInterface set when this object was constructed.
     * @return MFSessionTokenFarmInterface
     */
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
        private final MFActionTokenFarmInterface mfActionTokenFarmInterface;
        private final MFSessionTokenFarmInterface mfSessionTokenFarmInterface;

        private String apiKey;
        private final String appId;

        /**
         * Constructs a new MFConfigurationBuilder object.
         * @param appId - the developer's app id.
         */
        public MFConfigurationBuilder(String appId,
                                      MFLoggerInterface mfLogger,
                                      MFCredentialsInterface mfCredentials,
                                      MFNetworkConnectivityMonitorInterface mfNetworkConnectivityMonitor,
                                      MFActionTokenFarmInterface mfActionTokenFarmInterface,
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
            if (mfActionTokenFarmInterface == null) {
                throw new IllegalArgumentException("MFActionTokenFarmInterface cannot be null");
            }
            if (mfSessionTokenFarmInterface == null) {
                throw new IllegalArgumentException("MFSessionTokenFarmInterface cannot be null");
            }

            this.appId = appId;
            this.mfLogger = mfLogger;
            this.mfCredentials = mfCredentials;
            this.mfNetworkConnectivityMonitor = mfNetworkConnectivityMonitor;
            this.mfActionTokenFarmInterface = mfActionTokenFarmInterface;
            this.mfSessionTokenFarmInterface = mfSessionTokenFarmInterface;
        }

        /**
         * sets the developers api key.
         * @param apiKey - developers api key.
         * @return static MFConfigurationBuilder object to allow chaining calls.
         */
        public MFConfigurationBuilder apiKey(String apiKey) {
            if (apiKey == null) {
                throw new IllegalArgumentException("apiKey cannot be null");
            }
            this.apiKey = apiKey;
            return this;

        }

        /**
         * sets the read timeout for http requests.
         * @param httpReadTimeout - timeout in milliseconds.
         * @return static MFConfigurationBuilder object to allow chaining calls.
         */
        public MFConfigurationBuilder httpReadTimeout(int httpReadTimeout) {
            if (httpReadTimeout < 0) {
                throw new IllegalArgumentException("http read timeout must not be negative");
            }
            this.httpReadTimeout = httpReadTimeout;
            return this;
        }

        /**
         * sets the connection timeout for http requests.
         * @param httpConnectionTimeout - timeout in milliseconds.
         * @return static MFConfigurationBuilder object to allow chaining calls.
         */
        public MFConfigurationBuilder httpConnectionTimeout(int httpConnectionTimeout) {
            if (httpConnectionTimeout < 0) {
                throw new IllegalArgumentException("http connection timeout must not be negative");
            }
            this.httpConnectionTimeout = httpConnectionTimeout;
            return this;
        }

        /**
         * set the minimum session tokens to maintain.
         * @param minimumSessionTokens - min session tokens retained.
         * @return static MFConfigurationBuilder object to allow chaining calls.
         */
        public MFConfigurationBuilder minimumSessionTokens(int minimumSessionTokens) {
            if (minimumSessionTokens < 1) {
                throw new IllegalArgumentException("minimumSessionTokens session tokens must be greater than 0");
            }
            this.minimumSessionTokens = minimumSessionTokens;
            return this;
        }

        /**
         * set the maximum session tokens stored.
         * @param maximumSessionTokens - max session tokens stored.
         * @return static MFConfigurationBuilder object to allow chaining calls.
         */
        public MFConfigurationBuilder maximumSessionTokens(int maximumSessionTokens) {
            if (maximumSessionTokens < 1) {
                throw new IllegalArgumentException("maximum session tokens must be greater than 0");
            }
            this.maximumSessionTokens = maximumSessionTokens;
            return this;
        }

        /**
         * constructs an MFConfiguration object.
         * @return - a new MFConfiguration object.
         */
        public MFConfiguration build() {
            return new MFConfiguration(this);
        }
    }
}
