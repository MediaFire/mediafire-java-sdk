package com.mediafire.sdk.config;

/**
 * Configuration contains a set of interface objects used to handle api requests
 */
public class Configuration {
    private final IHttp mHttpWorker;
    private final IUserCredentials mUserCredentials;
    private final IDeveloperCredentials mDeveloperCredentials;
    private final ITokenManager mITokenManager;

    public Configuration(IDeveloperCredentials devCred,
                          IUserCredentials userCred,
                          IHttp httpInterface,
                          ITokenManager tokenManager) {
        mDeveloperCredentials = devCred;
        mUserCredentials = userCred;
        mHttpWorker = httpInterface;
        mITokenManager = tokenManager;
    }

    /**
     * Gets the HttpWorker associated with this class
     * @return HttpWorkerInterface
     */
    public IHttp getHttpWorker() {
        return mHttpWorker;
    }

    /**
     * Gets the user credentials associated with this class
     * @return CredentialsInterface
     */
    public IUserCredentials getUserCredentials() {
        return mUserCredentials;
    }

    /**
     * Gets the developer credentials associated with this class
     * @return CredentialsInterface
     */
    public IDeveloperCredentials getDeveloperCredentials() {
        return mDeveloperCredentials;
    }

    /**
     * Gets the session token manager associated with this class
     * @return SessionTokenManagerInterface
     */
    public ITokenManager getTokenManager() {
        return mITokenManager;
    }
}
