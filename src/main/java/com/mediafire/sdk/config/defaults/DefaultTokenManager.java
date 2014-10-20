package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.clients.ApiClient;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.*;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.token.UploadActionToken;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class DefaultTokenManager implements SessionTokenManagerInterface, ActionTokenManagerInterface {

    private Configuration mConfiguration;
    private ApiClient mApiClient;

    private static final int MIN_SESSION_TOKEN = 3;
    private static final int MAX_SESSION_TOKEN = 7;
    private static final BlockingQueue<SessionToken> mSessionTokens = new LinkedBlockingQueue<SessionToken>(MAX_SESSION_TOKEN);

    private static ImageActionToken mImageActionToken;
    private static UploadActionToken mUploadActionToken;

    private static final Object mImageActionTokenLock = new Object();
    private static final Object mUploadActionTokenLock = new Object();

    public DefaultTokenManager(Configuration configuration){
        mConfiguration = configuration;
        mApiClient = new ApiClient(configuration);
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

    @Override
    public void receiveSessionToken(SessionToken token) {
        addNewSessionToken(token);
    }

    @Override
    public SessionToken borrowSessionToken() {
        return getNewSessionToken();
    }

    private synchronized void addNewSessionToken(SessionToken token) {
        if(mSessionTokens.size() < MAX_SESSION_TOKEN) {
            mSessionTokens.offer(token);
        }
    }

    private synchronized SessionToken getNewSessionToken() {

        if(mSessionTokens.size() <= MIN_SESSION_TOKEN) {
            for(int i = mSessionTokens.size(); i <= MIN_SESSION_TOKEN + 1; i++) {
                requestNewSessionToken();
            }
        }

        try {
            return mSessionTokens.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    private void requestNewSessionToken() {
        HostObject hostObject = new HostObject("http", "www", "mediafire.com", "post");
        ApiObject apiObject = new ApiObject("user", "get_session_token");
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.NONE, SignatureType.NEW_SESSION_TOKEN_SIGNATURE, ReturnTokenType.NEW_V2, true);
        VersionObject versionObject = new VersionObject("1.2");
        Request request = new Request(hostObject, apiObject, instructionsObject, versionObject);
        request.addQueryParameter("application_id", mConfiguration.getAppId());
        mApiClient.doRequest(request);
    }

}
