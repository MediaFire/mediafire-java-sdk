package com.mediafire.sdk.config;

import com.mediafire.sdk.config.defaults.*;

public class Configuration {
    private final String mAppId;
    private final String mApiKey;
    private final LoggerInterface mLoggerInterface;
    private final CredentialsInterface mCredentialsInterface;
    private final NetworkConnectivityMonitorInterface mNetworkConnectivityMonitor;
    private final ActionTokenManagerInterface mActionTokenManagerInterface;
    private final SessionTokenManagerInterface mSessionTokenManagerInterface;
    private final HttpWorkerInterface mHttpWorkerInterface;

    private Configuration(Builder builder) {
        mAppId = builder.mAppId;
        mApiKey = builder.mApiKey;
        mLoggerInterface = builder.mLoggerInterface;
        mCredentialsInterface = builder.mCredentialsInterface;
        mNetworkConnectivityMonitor = builder.mNetworkConnectivityMonitorInterface;
        mActionTokenManagerInterface = builder.mActionTokenManagerInterface;
        mSessionTokenManagerInterface = builder.mSessionTokenManagerInterface;
        mHttpWorkerInterface = builder.mHttpWorkerInterface;
    }

    public String getAppId() {
        return mAppId;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public LoggerInterface getLogger() {
        return mLoggerInterface;
    }

    public CredentialsInterface getUserCredentialsInterface() {
        return mCredentialsInterface;
    }

    public NetworkConnectivityMonitorInterface getNetworkConnectivityMonitorInterface() {
        return mNetworkConnectivityMonitor;
    }

    public ActionTokenManagerInterface getActionTokenManagerInterface() {
        return mActionTokenManagerInterface;
    }

    public SessionTokenManagerInterface getSessionTokenManagerInterface() {
        return mSessionTokenManagerInterface;
    }

    public HttpWorkerInterface getHttpWorkerInterface() {
        return mHttpWorkerInterface;
    }

    public static class Builder {
        private static final LoggerInterface DEFAULT_LOGGER = new DefaultLogger();
        private static final CredentialsInterface DEFAULT_CREDENTIALS = new DefaultCredentials();
        private static final NetworkConnectivityMonitorInterface DEFAULT_NET_MONITOR = new DefaultNetworkConnectivityMonitor();
        private static final HttpWorkerInterface DEFAULT_HTTP_WORKER = new DefaultHttpWorker();

        private LoggerInterface mLoggerInterface = DEFAULT_LOGGER;
        private CredentialsInterface mCredentialsInterface = DEFAULT_CREDENTIALS;
        private NetworkConnectivityMonitorInterface mNetworkConnectivityMonitorInterface = DEFAULT_NET_MONITOR;
        private HttpWorkerInterface mHttpWorkerInterface = DEFAULT_HTTP_WORKER;
        private ActionTokenManagerInterface mActionTokenManagerInterface;
        private SessionTokenManagerInterface mSessionTokenManagerInterface;

        private String mApiKey;
        private final String mAppId;

        public Builder(String appId, ActionTokenManagerInterface actionTokenManagerInterface, SessionTokenManagerInterface sessionTokenManagerInterface) {
            if (appId == null) {
                throw new IllegalArgumentException("app id cannot be null");
            }

            if (actionTokenManagerInterface == null) {
                throw new IllegalArgumentException("ActionTokenManagerInterface cannot be null");
            }

            if (sessionTokenManagerInterface == null) {
                throw new IllegalArgumentException("SessionTokenManagerInterface cannot be null");
            }

            mAppId = appId;
            mActionTokenManagerInterface = actionTokenManagerInterface;
            mSessionTokenManagerInterface = sessionTokenManagerInterface;
        }

        public Builder apiKey(String apiKey) {
            if (apiKey == null) {
                return this;
            }
            mApiKey = apiKey;
            return this;
        }

        public Builder logger(LoggerInterface logger) {
            if (logger == null) {
                return this;
            }

            mLoggerInterface = logger;
            return this;
        }

        public Builder userCredentials(CredentialsInterface userCredentials) {
            if (userCredentials == null) {
                return this;
            }

            mCredentialsInterface = userCredentials;
            return this;
        }

        public Builder networkConnectivityMonitor(NetworkConnectivityMonitorInterface networkConnectivityMonitor) {
            if (networkConnectivityMonitor == null) {
                return this;
            }

            mNetworkConnectivityMonitorInterface = networkConnectivityMonitor;
            return this;
        }

        public Builder httpWorker(HttpWorkerInterface httpWorker) {
            if (httpWorker == null) {
                return this;
            }

            mHttpWorkerInterface = httpWorker;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
