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
    private ApiClientActionTokenManager mApiClient;


    private static ImageActionToken mImageActionToken;
    private static UploadActionToken mUploadActionToken;

    private static final Object mImageActionTokenLock = new Object();
    private static final Object mUploadActionTokenLock = new Object();

    public DefaultActionTokenManager(HttpWorkerInterface httpWorker, SessionTokenManagerInterface sessionTokenManager){
        mApiClient = new ApiClientActionTokenManager(httpWorker, sessionTokenManager, this);
    }

    @Override
    public void receiveImageActionToken(ImageActionToken token) {
        synchronized (mImageActionTokenLock) {
            mImageActionToken = token;
            mImageActionTokenLock.notifyAll();
        }
    }

    @Override
    public void receiveUploadActionToken(UploadActionToken token) {
        synchronized (mUploadActionTokenLock) {
            mUploadActionToken = token;
            mUploadActionTokenLock.notifyAll();
        }
    }

    @Override
    public ImageActionToken borrowImageActionToken() {
        return getImageActionToken();
    }

    private synchronized ImageActionToken getImageActionToken() {
        if(mImageActionToken == null) {
            requestNewImageActionToken();
        }

        try {
            mImageActionTokenLock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mImageActionToken;
    }

    private void requestNewImageActionToken() {
        HostObject hostObject = new HostObject("http", "www", "mediafire.com", "post");
        ApiObject apiObject = new ApiObject("user", "get_action_token");
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.NEW_IMAGE, true);
        VersionObject versionObject = new VersionObject(null);
        Request request = new Request(hostObject, apiObject, instructionsObject, versionObject);
        request.addQueryParameter("type", "image");
        mApiClient.doRequest(request);
    }

    @Override
    public UploadActionToken borrowUploadActionToken() {
        return getUploadActionToken();
    }

    private synchronized UploadActionToken getUploadActionToken() {
        if(mUploadActionToken == null) {
            requestNewUploadActionToken();
        }

        try {
            mUploadActionTokenLock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mUploadActionToken;
    }

    private void requestNewUploadActionToken() {
        HostObject hostObject = new HostObject("http", "www", "mediafire.com", "post");
        ApiObject apiObject = new ApiObject("user", "get_action_token");
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.V2, SignatureType.API_REQUEST, ReturnTokenType.NEW_UPLOAD, true);
        VersionObject versionObject = new VersionObject(null);
        Request request = new Request(hostObject, apiObject, instructionsObject, versionObject);
        request.addQueryParameter("type", "upload");
        mApiClient.doRequest(request);
    }

    @Override
    public void tokensFailed() {
        synchronized (mUploadActionTokenLock) {
            mUploadActionToken = null;
        }
        synchronized (mImageActionTokenLock) {
            mImageActionToken = null;
        }

        borrowImageActionToken();
        borrowUploadActionToken();
    }
}
