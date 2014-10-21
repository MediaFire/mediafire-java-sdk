package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.clients.ApiClientActionTokenManager;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.*;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.UploadActionToken;

/**
 * Created by Chris Najar on 10/21/2014.
 */
public class DefaultActionTokenManager implements ActionTokenManagerInterface {
    private static final String TAG = DefaultActionTokenManager.class.getCanonicalName();
    private ApiClientActionTokenManager mApiClient;

    private ImageActionToken mImageActionToken;
    private UploadActionToken mUploadActionToken;

    private static final Object mImageActionTokenLock = new Object();
    private static final Object mUploadActionTokenLock = new Object();

    public DefaultActionTokenManager(HttpWorkerInterface httpWorker, SessionTokenManagerInterface sessionTokenManager){
        mApiClient = new ApiClientActionTokenManager(httpWorker, sessionTokenManager, this);
    }

    @Override
    public void receiveImageActionToken(ImageActionToken token) {
        DefaultLogger.log().v(TAG, "receiveUploadActionToken");
        if (token != null) {
            mImageActionToken = token;
        }
        mImageActionTokenLock.notifyAll();
    }

    @Override
    public void receiveUploadActionToken(UploadActionToken token) {
        DefaultLogger.log().v(TAG, "receiveUploadActionToken");
        if (token != null) {
            mUploadActionToken = token;
            mUploadActionTokenLock.notifyAll();
        }
    }

    @Override
    public ImageActionToken borrowImageActionToken() {
        DefaultLogger.log().v(TAG, "borrowImageActionToken");
        synchronized (mImageActionToken) {
            if (mImageActionToken == null) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        requestNewActionToken("image");
                    }
                };

                Thread thread = new Thread(runnable);
                thread.start();
            }

            while (mImageActionToken == null) {
                DefaultLogger.log().v(TAG, "borrowUploadActionToken - waiting for token");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return mImageActionToken;
        }
    }

    @Override
    public UploadActionToken borrowUploadActionToken() {
        DefaultLogger.log().v(TAG, "borrowUploadActionToken");
        synchronized (mUploadActionTokenLock) {
            if (mUploadActionToken == null) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        requestNewActionToken("upload");
                    }
                };

                Thread thread = new Thread(runnable);
                thread.start();
            }

            while (mUploadActionToken == null) {
                DefaultLogger.log().v(TAG, "borrowUploadActionToken - waiting for token");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return mUploadActionToken;
        }
    }

    @Override
    public void tokensFailed() {
        DefaultLogger.log().v(TAG, "tokensFailed");
        synchronized (mUploadActionTokenLock) {
            mUploadActionToken = null;
        }
        synchronized (mImageActionTokenLock) {
            mImageActionToken = null;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                requestNewActionToken("upload");
                requestNewActionToken("image");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void requestNewActionToken(String type) {
        DefaultLogger.log().v(TAG, "requestNewActionToken");
        ReturnTokenType returnTokenType = type.equals("image") ? ReturnTokenType.NEW_IMAGE : ReturnTokenType.NEW_UPLOAD;
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, returnTokenType, true);
        HostObject hostObject = new HostObject("http", "www", "mediafire.com", "post");
        ApiObject apiObject = new ApiObject("user", "get_action_token.php");
        VersionObject versionObject = new VersionObject(null);
        Request request = new Request(hostObject, apiObject, instructionsObject, versionObject);
        request.addQueryParameter("type", type);
        request.addQueryParameter("response_format", "json");
        mApiClient.doRequest(request);
    }
}
