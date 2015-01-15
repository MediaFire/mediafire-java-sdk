package com.mediafire.sdk.config;

/**
 * Configuration contains a set of interface objects used to handle api requests
 */
public class Configuration {
    private final HttpHandler mHttpWorker;
    private final UserCredentials mUserCredentials;
    private final DeveloperCredentials mDeveloperCredentials;
    private final TokenManager mITokenManager;

    public Configuration(DeveloperCredentials devCred,
                          UserCredentials userCred,
                          HttpHandler httpInterface,
                          TokenManager tokenManager) {
        mDeveloperCredentials = devCred;
        mUserCredentials = userCred;
        mHttpWorker = httpInterface;
        mITokenManager = tokenManager;
    }

    /**
     * Gets the HttpWorker associated with this class
     * @return HttpWorkerInterface
     */
    public HttpHandler getHttpWorker() {
        return mHttpWorker;
    }

    /**
     * Gets the user credentials associated with this class
     * @return CredentialsInterface
     */
    public UserCredentials getUserCredentials() {
        return mUserCredentials;
    }

    /**
     * Gets the developer credentials associated with this class
     * @return CredentialsInterface
     */
    public DeveloperCredentials getDeveloperCredentials() {
        return mDeveloperCredentials;
    }

    /**
     * Gets the session token manager associated with this class
     * @return SessionTokenManagerInterface
     */
    public TokenManager getTokenManager() {
        return mITokenManager;
    }
}
