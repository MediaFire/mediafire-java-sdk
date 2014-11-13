package com.mediafire.sdk.config;

import com.mediafire.sdk.config_impl.*;

/**
 * Configuration contains a set of interface objects used to handle api requests
 */
public class Configuration {
    private final HttpWorkerInterface mHttpWorker;
    private final CredentialsInterface mUserCredentials;
    private final CredentialsInterface mDeveloperCredentials;
    private final SessionTokenManagerInterface mSessionTokenManager;
    private final ActionTokenManagerInterface mActionTokenManager;
    private final NetworkConnectivityMonitorInterface mNetworkConnectivityMonitor;
    private boolean initialized = false;

    private Configuration(Builder builder) {
        mHttpWorker = builder.mHttpWorker;
        mUserCredentials = builder.mUserCredentials;
        mDeveloperCredentials = builder.mDeveloperCredentials;
        mSessionTokenManager = builder.mSessionTokenManager;
        mActionTokenManager = builder.mActionTokenManager;
        mNetworkConnectivityMonitor = builder.mNetworkConnectivityMonitor;
    }

    public void init() {
        initialized = true;
        mSessionTokenManager.initialize(this);
        mActionTokenManager.initialize(this);
    }

    /**
     * Gets the HttpWorker associated with this class
     * @return HttpWorkerInterface
     */
    public HttpWorkerInterface getHttpWorker() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mHttpWorker;
    }

    /**
     * Gets the user credentials associated with this class
     * @return CredentialsInterface
     */
    public CredentialsInterface getUserCredentials() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mUserCredentials;
    }

    /**
     * Gets the developer credentials associated with this class
     * @return CredentialsInterface
     */
    public CredentialsInterface getDeveloperCredentials() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mDeveloperCredentials;
    }

    /**
     * Gets the session token manager associated with this class
     * @return SessionTokenManagerInterface
     */
    public SessionTokenManagerInterface getSessionTokenManager() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mSessionTokenManager;
    }

    /**
     * Gets the action token manager associated with this class
     * @return ActionTokenManagerInterface
     */
    public ActionTokenManagerInterface getActionTokenManager() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mActionTokenManager;
    }

    /**
     * Gets the network connectivity monitor associated with this class
     * @return NetworkConnectivityMonitorInterface
     */
    public NetworkConnectivityMonitorInterface getNetworkConnectivityMonitor() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mNetworkConnectivityMonitor;
    }

    /**
     * Builder is a class used to build a Configuration object (see the builder pattern)
     */
    public static class Builder {
        private static final HttpWorkerInterface DEFAULT_HTTP_WORKER = new DefaultHttpWorker();
        private static final CredentialsInterface DEFAULT_USER_CREDENTIALS = new DefaultCredentials();
        private static final CredentialsInterface DEFAULT_DEVELOPER_CREDENTIALS = new DefaultCredentials();
        private static final SessionTokenManagerInterface DEFAULT_SESSION_TOKEN_MANAGER = new DefaultSessionTokenManager(DEFAULT_HTTP_WORKER, DEFAULT_USER_CREDENTIALS, DEFAULT_DEVELOPER_CREDENTIALS);
        private static final ActionTokenManagerInterface DEFAULT_ACTION_TOKEN_MANAGER = new DefaultActionTokenManager(DEFAULT_HTTP_WORKER, DEFAULT_SESSION_TOKEN_MANAGER);
        private static final NetworkConnectivityMonitorInterface DEFAULT_NET_MONITOR = new DefaultNetworkConnectivityMonitor();

        private HttpWorkerInterface mHttpWorker = DEFAULT_HTTP_WORKER;
        private CredentialsInterface mUserCredentials = DEFAULT_USER_CREDENTIALS;
        private CredentialsInterface mDeveloperCredentials = DEFAULT_DEVELOPER_CREDENTIALS;
        private SessionTokenManagerInterface mSessionTokenManager = DEFAULT_SESSION_TOKEN_MANAGER;
        private ActionTokenManagerInterface mActionTokenManager = DEFAULT_ACTION_TOKEN_MANAGER;
        private NetworkConnectivityMonitorInterface mNetworkConnectivityMonitor = DEFAULT_NET_MONITOR;

        /**
         * Builder Constructor
         */
        public Builder() { }

        /**
         * Adds a HttpWorkerInterface to the class
         * @param httpWorker HttpWorkerInterface to be added
         * @return the updated Builder object
         */
        public Builder httpWorker(HttpWorkerInterface httpWorker) {
            if (httpWorker == null) {
                return this;
            }

            mHttpWorker = httpWorker;
            return this;
        }

        /**
         * Adds a developer CredentialsInterface to the class
         * @param developerCredentials CredentialsInterface to be added
         * @return the updated Builder object
         */
        public Builder developerCredentials(CredentialsInterface developerCredentials) {
            if (developerCredentials == null) {
                return this;
            }
            mDeveloperCredentials = developerCredentials;
            return this;
        }

        /**
         * Adds a user CredentialsInterface to the class
         * @param userCredentials CredentialsInterface to be added
         * @return the updated Builder object
         */
        public Builder userCredentials(CredentialsInterface userCredentials) {
            if (userCredentials == null) {
                return this;
            }

            mUserCredentials = userCredentials;
            return this;
        }

        /**
         * Adds a session token manager and an action token manager to the class
         * @param sessionTokenManager SessionTokenManagerInterface to be added
         * @param actionTokenManager ActionTokenManagerInterface to be added
         * @return the updated Builder object
         */
        public Builder tokenManagers(SessionTokenManagerInterface sessionTokenManager, ActionTokenManagerInterface actionTokenManager) {
            if (sessionTokenManager == null || actionTokenManager == null) {
                return this;
            }

            mSessionTokenManager = sessionTokenManager;
            mActionTokenManager = actionTokenManager;
            return this;
        }

        /**
         * Adds a NetworkConnectivityMonitorInterface to the class
         * @param networkConnectivityMonitor NetworkConnectivityMonitorInterface to be added
         * @return the updated Builder object
         */
        public Builder networkConnectivityMonitor(NetworkConnectivityMonitorInterface networkConnectivityMonitor) {
            if (networkConnectivityMonitor == null) {
                return this;
            }

            mNetworkConnectivityMonitor = networkConnectivityMonitor;
            return this;
        }

        /**
         * Creates a new Configuration object based on the parameters in this class
         * @return a new Configuration object
         */
        public Configuration build() {
            return new Configuration(this);
        }
    }
}
