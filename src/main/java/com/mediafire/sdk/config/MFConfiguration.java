package com.mediafire.sdk.config;

import com.mediafire.sdk.http.MFNetworkConnectivityMonitor;

public final class MFConfiguration {
    private final int httpReadTimeout;
    private final int httpConnectionTimeout;
    private final int minimumSessionTokens;
    private final int maximumSessionTokens;
    private final String appId;
    private final String apiKey;
    private final MFCredentials mfCredentials;
    private final MFNetworkConnectivityMonitor mfNetworkConnectivityMonitor;
    private static MFLogger staticMFLogger;

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
        this.mfCredentials = mfConfigurationBuilder.mfCredentials;
        this.mfNetworkConnectivityMonitor = mfConfigurationBuilder.mfNetworkConnectivityMonitor;
        staticMFLogger = mfConfigurationBuilder.mfLogger;
    }

    /**
     * gets the MFLogger set when this object was constructed.
     * @return MFLogger
     */
    public static MFLogger getStaticMFLogger() {
        return staticMFLogger;
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
     * gets the MFCredentials set when this object was constructed.
     * @return MFCredentials
     */
    public MFCredentials getMFCredentials() {
        return mfCredentials;
    }

    /**
     * gets the MFNetworkConnectivityMonitor set when this object was constructed.
     * @return MFNetworkConnectivityMonitor.
     */
    public MFNetworkConnectivityMonitor getMfNetworkConnectivityMonitor() {
        return mfNetworkConnectivityMonitor;
    }

    public static class MFConfigurationBuilder {
        private static final int DEFAULT_HTTP_READ_TIMEOUT = 45000;
        private static final int DEFAULT_HTTP_CONNECTION_TIMEOUT = 45000;
        private static final int DEFAULT_MINIMUM_SESSION_TOKENS = 1;
        private static final int DEFAULT_MAXIMUM_SESSION_TOKENS = 3;
        private static final MFLogger DEFAULT_MF_LOGGER = new MFDefaultLogger();
        private static final MFCredentials DEFAULT_MF_CREDENTIALS = new MFDefaultCredentials();
        private static final MFNetworkConnectivityMonitor DEFAULT_MF_CONNECTIVITY_MONITOR = new MFDefaultNetworkConnectivityMonitor();

        private int httpReadTimeout = DEFAULT_HTTP_READ_TIMEOUT;
        private int httpConnectionTimeout = DEFAULT_HTTP_CONNECTION_TIMEOUT;
        private int minimumSessionTokens = DEFAULT_MINIMUM_SESSION_TOKENS;
        private int maximumSessionTokens = DEFAULT_MAXIMUM_SESSION_TOKENS;
        private MFLogger mfLogger = DEFAULT_MF_LOGGER;
        private MFCredentials mfCredentials = DEFAULT_MF_CREDENTIALS;
        private MFNetworkConnectivityMonitor mfNetworkConnectivityMonitor = DEFAULT_MF_CONNECTIVITY_MONITOR;
        private String apiKey;
        private final String appId;

        /**
         * Constructs a new MFConfigurationBuilder object.
         * @param appId - the developer's app id.
         */
        public MFConfigurationBuilder(String appId) {
            if (appId == null) {
                throw new IllegalArgumentException("app id cannot be null");
            }
            this.appId = appId;
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
         * sets the MFLogger to use.
         * @param mfLogger - MFLogger implementation.
         * @return static MFConfigurationBuilder object to allow chaining calls.
         */
        public MFConfigurationBuilder mfLogger(MFLogger mfLogger) {
            if (mfLogger == null) {
                throw new IllegalArgumentException("MFLogger cannot be null");
            }
            this.mfLogger = mfLogger;
            return this;
        }

        /**
         * sets the MFCredentials to use.
         * @param mfCredentials - MFCredentials implementation.
         * @return static MFConfigurationBuilder object to allow chaining calls.
         */
        public MFConfigurationBuilder mfCredentials(MFCredentials mfCredentials) {
            if (mfCredentials == null) {
                throw new IllegalArgumentException("MFCredentials cannot be null");
            }
            this.mfCredentials = mfCredentials;
            return this;
        }

        /**
         * set the minimum session tokens retained.
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
         * sets the network connectivity monitor.
         * @param mfNetworkConnectivityMonitor - MFNetworkConnectivityMonitor implementation.
         * @return static MFConfigurationBuilder object to allow chaining calls.
         */
        public MFConfigurationBuilder mfNetworkConnectivityMonitor(MFNetworkConnectivityMonitor mfNetworkConnectivityMonitor) {
            if (mfNetworkConnectivityMonitor == null) {
                throw new IllegalArgumentException("MFNetworkConnectivityMonitor cannot be null");
            }
            this.mfNetworkConnectivityMonitor = mfNetworkConnectivityMonitor;
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
