package com.mediafire.sdk.config_impl;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.api.clients.ApiClient;
import com.mediafire.sdk.client_helpers.ClientHelperNewActionToken;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.ResponseApiClientError;
import com.mediafire.sdk.http.Result;
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

    private SessionTokenManagerInterface mSessionTokenManagerInterface;
    private HttpWorkerInterface mHttpWorkerInterface;

    /**
     * DefaultActionTokenManager Constructor
     */
    public DefaultActionTokenManager(HttpWorkerInterface httpWorkerInterface, SessionTokenManagerInterface sessionTokenManagerInterface) {
        mSessionTokenManagerInterface = sessionTokenManagerInterface;
        mHttpWorkerInterface = httpWorkerInterface;
    }

    /**
     * Called whenever a new ImageActionToken Object is received
     * Notifies the corresponding lock that a ImageActionToken has been received
     * @param token the ImageActionToken that was received
     */
    @Override
    public void receiveImageActionToken(ImageActionToken token) {
        System.out.printf("%s - %s", TAG, "receiveImageActionToken");
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
        System.out.printf("%s - %s", TAG, "receiveUploadActionToken");
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
        System.out.printf("%s - %s", TAG, "borrowImageActionToken");
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
            Request request = new ApiRequestGenerator().createRequestObjectFromPath("user/get_action_token.php");
            request.addQueryParameter("type", "image");
            request.addQueryParameter("response_format", "json");

            ClientHelperNewActionToken clientHelperNewActionToken = new ClientHelperNewActionToken("image", DefaultActionTokenManager.this, mSessionTokenManagerInterface);
            ApiClient apiClient = new ApiClient(clientHelperNewActionToken, mHttpWorkerInterface);
            Result result = apiClient.doRequest(request);
            
            Response response = result.getResponse();
            
            if(response.getClass() == ResponseApiClientError.class) {
                ResponseApiClientError responseApiClientError = (ResponseApiClientError) result.getResponse();
                System.out.printf("%s - %s", TAG, responseApiClientError.getErrorMessage());
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
        System.out.printf("%s - %s", TAG, "borrowUploadActionToken");
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
            Request request = new ApiRequestGenerator().createRequestObjectFromPath("user/get_action_token.php");
            request.addQueryParameter("type", "upload");
            request.addQueryParameter("response_format", "json");
            
            ClientHelperNewActionToken clientHelperNewActionToken = new ClientHelperNewActionToken("upload", DefaultActionTokenManager.this, mSessionTokenManagerInterface);
            ApiClient apiClient = new ApiClient(clientHelperNewActionToken, mHttpWorkerInterface);
            Result result = apiClient.doRequest(request);

            Response response = result.getResponse();
            
            if(response.getClass() == ResponseApiClientError.class) {
                ResponseApiClientError responseApiClientError = (ResponseApiClientError) result.getResponse();
                System.out.printf("%s - %s", TAG, responseApiClientError.getErrorMessage());
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
        System.out.printf("%s - %s", TAG, "tokensFailed");
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