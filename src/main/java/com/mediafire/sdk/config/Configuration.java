package com.mediafire.sdk.config;

public class Configuration {
    private final String mAppId;
    private final String mApiKey;
    private final LoggerInterface mLoggerInterface;
    private final UserCredentialsInterface mUserCredentialsInterface;
    private final NetworkConnectivityMonitorInterface mNetworkConnectivityMonitor;
    private final ActionTokenManagerInterface mActionTokenManagerInterface;
    private final SessionTokenManagerInterface mSessionTokenManagerInterface;
    private final HttpWorkerInterface mHttpWorkerInterface;

    private Configuration(MFConfigurationBuilder mfConfigurationBuilder) {
        mAppId = mfConfigurationBuilder.mAppId;
        mApiKey = mfConfigurationBuilder.mApiKey;
        mLoggerInterface = mfConfigurationBuilder.mLoggerInterface;
        mUserCredentialsInterface = mfConfigurationBuilder.mCredentialsInterface;
        mNetworkConnectivityMonitor = mfConfigurationBuilder.mNetworkConnectivityMonitorInterface;
        mActionTokenManagerInterface = mfConfigurationBuilder.mActionTokenManagerInterface;
        mSessionTokenManagerInterface = mfConfigurationBuilder.mSessionTokenManagerInterface;
        mHttpWorkerInterface = mfConfigurationBuilder.mHttpWorkerInterface;
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

    public UserCredentialsInterface getUserCredentialsInterface() {
        return mUserCredentialsInterface;
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

    public static class MFConfigurationBuilder {
        private final LoggerInterface mLoggerInterface;
        private final UserCredentialsInterface mCredentialsInterface;
        private final NetworkConnectivityMonitorInterface mNetworkConnectivityMonitorInterface;
        private final ActionTokenManagerInterface mActionTokenManagerInterface;
        private final SessionTokenManagerInterface mSessionTokenManagerInterface;
        private final HttpWorkerInterface mHttpWorkerInterface;

        private String mApiKey;
        private final String mAppId;

        public MFConfigurationBuilder(String appId,
                                      LoggerInterface logger,
                                      UserCredentialsInterface userCredentials,
                                      NetworkConnectivityMonitorInterface netConnectMonitor,
                                      ActionTokenManagerInterface actionTokenManager,
                                      SessionTokenManagerInterface sessionTokenManager,
                                      HttpWorkerInterface httpWorker) {
            if (appId == null) {
                throw new IllegalArgumentException("app id cannot be null");
            }
            if (logger == null) {
                throw new IllegalArgumentException("LoggerInterface cannot be null");
            }
            if (userCredentials == null) {
                throw new IllegalArgumentException("UserCredentialsInterface cannot be null");
            }
            if (netConnectMonitor == null) {
                throw new IllegalArgumentException("NetworkConnectivityMonitorInterface cannot be null");
            }
            if (actionTokenManager == null) {
                throw new IllegalArgumentException("ActionTokenManagerInterface cannot be null");
            }
            if (sessionTokenManager == null) {
                throw new IllegalArgumentException("SessionTokenManagerInterface cannot be null");
            }
            if (httpWorker == null) {
                throw new IllegalArgumentException("HttpWorkerInterface cannot be null");
            }

            mAppId = appId;
            mLoggerInterface = logger;
            mCredentialsInterface = userCredentials;
            mNetworkConnectivityMonitorInterface = netConnectMonitor;
            mActionTokenManagerInterface = actionTokenManager;
            mSessionTokenManagerInterface = sessionTokenManager;
            mHttpWorkerInterface = httpWorker;
        }

        public MFConfigurationBuilder apiKey(String apiKey) {
            if (apiKey == null) {
                return this;
            }
            mApiKey = apiKey;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
