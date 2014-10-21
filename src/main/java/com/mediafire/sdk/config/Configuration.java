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

    private Configuration(Builder builder) {
        mHttpWorker = builder.mHttpWorker;
        mUserCredentials = builder.mUserCredentials;
        mDeveloperCredentials = builder.mDeveloperCredentials;
        mSessionTokenManager = builder.mSessionTokenManager;
        mActionTokenManager = builder.mActionTokenManager;
        mLogger = builder.mLogger;
        mNetworkConnectivityMonitor = builder.mNetworkConnectivityMonitor;
    }

    public HttpWorkerInterface getHttpWorker() {
        return mHttpWorker;
    }

    public CredentialsInterface getUserCredentials() {
        return mUserCredentials;
    }

    public CredentialsInterface getDeveloperCredentials() {
        return mDeveloperCredentials;
    }

    public SessionTokenManagerInterface getSessionTokenManager() {
        return mSessionTokenManager;
    }

    public ActionTokenManagerInterface getActionTokenManager() {
        return mActionTokenManager;
    }

    public LoggerInterface getLogger() {
        return mLogger;
    }

    public NetworkConnectivityMonitorInterface getNetworkConnectivityMonitor() {
        return mNetworkConnectivityMonitor;
    }

    public static class Builder {
        private static final HttpWorkerInterface DEFAULT_HTTP_WORKER = new DefaultHttpWorker();
        private static final CredentialsInterface DEFAULT_USER_CREDENTIALS = new DefaultCredentials("user");
        private static final CredentialsInterface DEFAULT_DEVELOPER_CREDENTIALS = new DefaultCredentials("dev");
        private static final SessionTokenManagerInterface DEFAULT_SESSION_TOKEN_MANAGER = new DefaultSessionTokenManager(DEFAULT_HTTP_WORKER, DEFAULT_USER_CREDENTIALS, DEFAULT_DEVELOPER_CREDENTIALS);
        private static final ActionTokenManagerInterface DEFAULT_ACTION_TOKEN_MANAGER = new DefaultActionTokenManager(DEFAULT_HTTP_WORKER, DEFAULT_SESSION_TOKEN_MANAGER);
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
