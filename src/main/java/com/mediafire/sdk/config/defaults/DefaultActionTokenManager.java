package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.clients.ApiClientHelper;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.*;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.UploadActionToken;

/**
 * Created by Jonathan Harrison on 10/21/2014.
 * DefaultActionTokenManager provides a default implementation of the ActionTokenManagerInterface
 * A custom implementation is recommended
 */
public class DefaultActionTokenManager implements ActionTokenManagerInterface {
    private static final String TAG = DefaultActionTokenManager.class.getCanonicalName();

    private ImageActionToken mImageActionToken;
    private UploadActionToken mUploadActionToken;

    private static final Object mImageActionTokenLock = new Object();
    private static final Object mUploadActionTokenLock = new Object();
    private Configuration mConfiguration;

    /**
     * DefaultActionTokenManager Constructor
     */
    public DefaultActionTokenManager() { }

    /**
     * Initialized this class, should be called before class methods are called
     * @param configuration Configuration Object to be used in class methods
     */
    @Override
    public void initialize(Configuration configuration) {
        mConfiguration = configuration;
    }

    /**
     * A shutdown method for this class
     */
    @Override
    public void shutdown() {
        mConfiguration.getLogger().v(TAG, "shutdown");
    }

    /**
     * Called whenever a new ImageActionToken Object is received
     * Notifies the corresponding lock that a ImageActionToken has been received
     * @param token the ImageActionToken that was received
     */
    @Override
    public void receiveImageActionToken(ImageActionToken token) {
        mConfiguration.getLogger().v(TAG, "receiveImageActionToken");
        synchronized (mImageActionTokenLock) {
            mImageActionToken = token;
            mImageActionTokenLock.notifyAll();
        }
    }

    /**
     * Called whenever a new UploadActionToken Object is received
     * Notifies the corresponding lock that a UploadActionToken has been received
     * @param token the UploadActionToken that was received
     */
    @Override
    public void receiveUploadActionToken(UploadActionToken token) {
        mConfiguration.getLogger().v(TAG, "receiveUploadActionToken");
        synchronized (mUploadActionTokenLock) {
            mUploadActionToken = token;
            mUploadActionTokenLock.notifyAll();
        }
    }

    /**
     * Called to borrow an ImageActionToken, asks server for a new one if not available (blocking)
     * @return an ImageActionToken once one is available
     */
    @Override
    public ImageActionToken borrowImageActionToken() {
        mConfiguration.getLogger().v(TAG, "borrowImageActionToken");
        synchronized (mImageActionTokenLock) {
            if (mImageActionToken == null) {
                new NewImageActionTokenThread().start();
            }

            if (mImageActionToken != null) {
                return mImageActionToken;
            }

            try {
                mImageActionTokenLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mImageActionToken;
        }
    }

    private class NewImageActionTokenThread extends Thread {
        @Override
        public void run(){
            mConfiguration.getLogger().v(TAG, "NewImageActionTokenThread - run");
            HostObject hostObject = new HostObject("http", "www", "mediafire.com", "post");
            ApiObject apiObject = new ApiObject("user", "get_action_token.php");
            InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.NEW_IMAGE, true);
            VersionObject versionObject = new VersionObject("1.2");
            Request request = new Request(hostObject, apiObject, instructionsObject, versionObject);
            request.addQueryParameter("type", "image");
            request.addQueryParameter("response_format", "json");
            ApiClientHelper apiClientHelper = new ApiClientHelper(mConfiguration);
            Result result = new ApiClient(apiClientHelper, mConfiguration.getHttpWorker()).doRequest(request);
            Response response = result.getResponse();
            if(response.getClass() == ResponseApiClientError.class) {
                ResponseApiClientError responseApiClientError = (ResponseApiClientError) result.getResponse();
                mConfiguration.getLogger().e(TAG, responseApiClientError.getErrorMessage());
                receiveUploadActionToken(null);
            }
        }
    }

    /**
     * Called to borrow an UploadActionToken, asks server for a new one if not available (blocking)
     * @return an UploadActionToken once one is available
     */
    @Override
    public UploadActionToken borrowUploadActionToken() {
        mConfiguration.getLogger().v(TAG, "borrowUploadActionToken");
        synchronized (mUploadActionTokenLock) {
            if (mUploadActionToken == null) {
                new NewUploadActionTokenThread().start();
            }

            if (mUploadActionToken != null) {
                return mUploadActionToken;
            }

            try {
                mUploadActionTokenLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mUploadActionToken;
        }
    }

    private class NewUploadActionTokenThread extends Thread {
        @Override
        public void run(){
            mConfiguration.getLogger().v(TAG, "NewUploadActionTokenThread - run");
            HostObject hostObject = new HostObject("http", "www", "mediafire.com", "post");
            ApiObject apiObject = new ApiObject("user", "get_action_token.php");
            InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.NEW_UPLOAD, true);
            VersionObject versionObject = new VersionObject("1.2");
            Request request = new Request(hostObject, apiObject, instructionsObject, versionObject);
            request.addQueryParameter("type", "upload");
            request.addQueryParameter("response_format", "json");
            ApiClientHelper apiClientHelper = new ApiClientHelper(mConfiguration);
            Result result = new ApiClient(apiClientHelper, mConfiguration.getHttpWorker()).doRequest(request);
            Response response = result.getResponse();
            if(response.getClass() == ResponseApiClientError.class) {
                ResponseApiClientError responseApiClientError = (ResponseApiClientError) result.getResponse();
                mConfiguration.getLogger().e(TAG, responseApiClientError.getErrorMessage());
                receiveUploadActionToken(null);
            }
        }
    }

    /**
     * called whenever either a ImageActionToken or a UploadActionToken fails
     * the current tokens are cleared and new ones are asked for
     */
    @Override
    public void tokensFailed() {
        mConfiguration.getLogger().v(TAG, "tokensFailed");
        synchronized (mUploadActionTokenLock) {
            //noinspection AssignmentToNull
            mUploadActionToken = null;
        }
        synchronized (mImageActionTokenLock) {
            //noinspection AssignmentToNull
            mImageActionToken = null;
        }

        borrowImageActionToken();
        borrowUploadActionToken();
    }
}