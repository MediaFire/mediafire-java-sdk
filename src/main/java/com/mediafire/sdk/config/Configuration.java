package com.mediafire.sdk.config;

import com.mediafire.sdk.api.clients.*;

/**
 * Configuration contains a set of interface objects used to handle api requests
 */
public class Configuration {
    // custom domain
    public static final String DEFAULT_DOMAIN = "www.mediafire.com";
    private static String sFullyQualifiedDomain;
    // core sdk interfaces
    private final HttpHandler mHttpWorker;
    private final UserCredentials mUserCredentials;
    private final DeveloperCredentials mDeveloperCredentials;
    private final TokenManager mITokenManager;
    // client objects created so user does not have to create these
    private final ContactClient mContactClient;
    private final DeviceClient mDeviceClient;
    private final FileClient mFileClient;
    private final FolderClient mFolderClient;
    private final NotificationsClient mNotificationsClient;
    private final SystemClient mSystemClient;
    private final TokenClient mTokenClient;
    private final UploadClient mUploadClient;
    private final UserClient mUserClient;
    private final ConversionClient mConversionClient;

    public Configuration(DeveloperCredentials devCred, UserCredentials userCred, HttpHandler httpInterface,
                          TokenManager tokenManager, String fullyQualifiedDomain) {
        mDeveloperCredentials = devCred;
        mUserCredentials = userCred;
        mHttpWorker = httpInterface;
        mITokenManager = tokenManager;
        mContactClient = new ContactClient(httpInterface, tokenManager);
        mDeviceClient = new DeviceClient(httpInterface, tokenManager);
        mFileClient = new FileClient(httpInterface, tokenManager);
        mFolderClient = new FolderClient(httpInterface, tokenManager);
        mNotificationsClient = new NotificationsClient(httpInterface, tokenManager);
        mSystemClient = new SystemClient(httpInterface);
        mTokenClient = new TokenClient(httpInterface, userCred, devCred, tokenManager);
        mUploadClient = new UploadClient(httpInterface, tokenManager);
        mUserClient = new UserClient(httpInterface, tokenManager);
        mConversionClient = new ConversionClient(httpInterface, tokenManager);
        if (fullyQualifiedDomain == null) {
            sFullyQualifiedDomain = DEFAULT_DOMAIN;
        } else {
            sFullyQualifiedDomain = fullyQualifiedDomain;
        }
    }

    public Configuration(DeveloperCredentials devCred,
                         UserCredentials userCred,
                         HttpHandler httpInterface,
                         TokenManager tokenManager) {
        this(devCred, userCred, httpInterface, tokenManager, null);
    }

    public static String getFullyQualifiedDomain() {
        return sFullyQualifiedDomain;
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

    public ContactClient makeContactApiRequest() {
        return mContactClient;
    }

    public DeviceClient makeDeviceApiRequest() {
        return mDeviceClient;
    }

    public FileClient makeFileApiRequest() {
        return mFileClient;
    }

    public FolderClient makeFolderApiRequest() {
        return mFolderClient;
    }

    public NotificationsClient makeNotificationsApiRequest() {
        return mNotificationsClient;
    }
    
    public SystemClient makeSystemApiRequest() {
        return mSystemClient;
    }
    
    public TokenClient makeTokenApiRequest() {
        return mTokenClient;
    }
    
    public UploadClient makeUploadApiRequest() {
        return mUploadClient;
    }
    
    public UserClient makeUserApiRequest() {
        return mUserClient;
    }

    public ConversionClient makeConversionRequest() {
        return mConversionClient;
    }
}
