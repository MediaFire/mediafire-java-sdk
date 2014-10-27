package com.mediafire.sdk.config;

import com.mediafire.sdk.config.defaults.*;

public class Configuration {
    private HttpWorkerInterface mHttpWorker;
    private CredentialsInterface mUserCredentials;
    private CredentialsInterface mDeveloperCredentials;
    private SessionTokenManagerInterface mSessionTokenManager;
    private ActionTokenManagerInterface mActionTokenManager;
    private LoggerInterface mLogger;
    private NetworkConnectivityMonitorInterface mNetworkConnectivityMonitor;
    private boolean initialized = false;

    private Configuration(Builder builder) {
        mHttpWorker = builder.mHttpWorker;
        mUserCredentials = builder.mUserCredentials;
        mDeveloperCredentials = builder.mDeveloperCredentials;
        mSessionTokenManager = builder.mSessionTokenManager;
        mActionTokenManager = builder.mActionTokenManager;
        mLogger = builder.mLogger;
        mNetworkConnectivityMonitor = builder.mNetworkConnectivityMonitor;
    }

    public void init() {
        initialized = true;
        mSessionTokenManager.initialize(this);
        mActionTokenManager.initialize(this);
    }

    public HttpWorkerInterface getHttpWorker() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mHttpWorker;
    }

    public CredentialsInterface getUserCredentials() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mUserCredentials;
    }

    public CredentialsInterface getDeveloperCredentials() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mDeveloperCredentials;
    }

    public SessionTokenManagerInterface getSessionTokenManager() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mSessionTokenManager;
    }

    public ActionTokenManagerInterface getActionTokenManager() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mActionTokenManager;
    }

    public LoggerInterface getLogger() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mLogger;
    }

    public NetworkConnectivityMonitorInterface getNetworkConnectivityMonitor() {
        if (!initialized) {
            throw new IllegalStateException("Configuration.init() must be called to finish configuration");
        }
        return mNetworkConnectivityMonitor;
    }

    public static class Builder {
        private static final HttpWorkerInterface DEFAULT_HTTP_WORKER = new DefaultHttpWorker();
        private static final CredentialsInterface DEFAULT_USER_CREDENTIALS = new DefaultCredentials();
        private static final CredentialsInterface DEFAULT_DEVELOPER_CREDENTIALS = new DefaultCredentials();
        private static final SessionTokenManagerInterface DEFAULT_SESSION_TOKEN_MANAGER = new DefaultSessionTokenManager();
        private static final ActionTokenManagerInterface DEFAULT_ACTION_TOKEN_MANAGER = new DefaultActionTokenManager();
        private static final LoggerInterface DEFAULT_LOGGER = new DefaultLogger();
        private static final NetworkConnectivityMonitorInterface DEFAULT_NET_MONITOR = new DefaultNetworkConnectivityMonitor();

        private HttpWorkerInterface mHttpWorker = DEFAULT_HTTP_WORKER;
        private CredentialsInterface mUserCredentials = DEFAULT_USER_CREDENTIALS;
        private CredentialsInterface mDeveloperCredentials = DEFAULT_DEVELOPER_CREDENTIALS;
        private SessionTokenManagerInterface mSessionTokenManager = DEFAULT_SESSION_TOKEN_MANAGER;
        private ActionTokenManagerInterface mActionTokenManager = DEFAULT_ACTION_TOKEN_MANAGER;
        private LoggerInterface mLogger = DEFAULT_LOGGER;
        private NetworkConnectivityMonitorInterface mNetworkConnectivityMonitor = DEFAULT_NET_MONITOR;

        public Builder() {
        }

        public Builder httpWorker(HttpWorkerInterface httpWorker) {
            if (httpWorker == null) {
                return this;
            }

            mHttpWorker = httpWorker;
            return this;
        }

        public Builder developerCredentials(CredentialsInterface developerCredentials) {
            if (developerCredentials == null) {
                return this;
            }
            mDeveloperCredentials = developerCredentials;
            return this;
        }

        public Builder userCredentials(CredentialsInterface userCredentials) {
            if (userCredentials == null) {
                return this;
            }

            mUserCredentials = userCredentials;
            return this;
        }

        public Builder tokenManagers(SessionTokenManagerInterface sessionTokenManager, ActionTokenManagerInterface actionTokenManager) {
            if (sessionTokenManager == null || actionTokenManager == null) {
                return this;
            }

            mSessionTokenManager = sessionTokenManager;
            mActionTokenManager = actionTokenManager;
            return this;
        }

        public Builder logger(LoggerInterface logger) {
            if (logger == null) {
                return this;
            }

            mLogger = logger;
            return this;
        }

        public Builder networkConnectivityMonitor(NetworkConnectivityMonitorInterface networkConnectivityMonitor) {
            if (networkConnectivityMonitor == null) {
                return this;
            }

            mNetworkConnectivityMonitor = networkConnectivityMonitor;
            return this;
        }


        public Configuration build() {
            return new Configuration(this);
        }
    }
}
