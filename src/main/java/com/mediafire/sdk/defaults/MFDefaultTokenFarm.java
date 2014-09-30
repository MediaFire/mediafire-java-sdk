package com.mediafire.sdk.defaults;

import com.mediafire.sdk.config.MFConfiguration;
import com.mediafire.sdk.config.MFHttpProcessor;
import com.mediafire.sdk.config.MFTokenFarmInterface;
import com.mediafire.sdk.http.MFApi;
import com.mediafire.sdk.http.MFHost;
import com.mediafire.sdk.http.MFRequest;
import com.mediafire.sdk.token.MFActionToken;
import com.mediafire.sdk.token.MFSessionToken;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class MFDefaultTokenFarm implements MFTokenFarmInterface {
    private static final String TAG = MFDefaultTokenFarm.class.getCanonicalName();
    private final MFConfiguration mfConfiguration;
    private final MFHttpProcessor mfHttpProcessor;
    private final int minimumSessionTokens;
    private final int maximumSessionTokens;

    private final BlockingQueue<MFSessionToken> mfSessionTokens;
    private MFActionToken mfUploadActionToken;
    private MFActionToken mfImageActionToken;

    // borrow token locks
    private final Object sessionTokenLock = new Object();
    private final Lock lockBorrowImageToken = new ReentrantLock();
    private final Lock lockBorrowUploadToken = new ReentrantLock();
    private final Condition conditionImageTokenNotExpired = lockBorrowImageToken.newCondition();
    private final Condition conditionUploadTokenNotExpired = lockBorrowUploadToken.newCondition();

    public MFDefaultTokenFarm(MFConfiguration mfConfiguration) {
        this.mfConfiguration = mfConfiguration;
        this.minimumSessionTokens = mfConfiguration.getMinimumSessionTokens();
        this.maximumSessionTokens = mfConfiguration.getMaximumSessionTokens();
        this.mfSessionTokens = new LinkedBlockingQueue<MFSessionToken>(maximumSessionTokens);
        this.mfHttpProcessor = new MFDefaultHttpProcessor(mfConfiguration, this);
    }

    public MFHttpProcessor getMFHttpRunner() {
        return mfHttpProcessor;
    }

    public MFConfiguration getMFConfiguration() {
        return mfConfiguration;
    }

    private void getNewSessionToken() {
        mfConfiguration.getMFLogger().d(TAG, "getNewSessionToken()");

        Map<String, String> requestParameters = new LinkedHashMap<String, String>();
        requestParameters.put("token_version", "2");
        MFRequest.MFRequestBuilder mfRequestBuilder = new MFRequest.MFRequestBuilder(MFHost.LIVE_HTTPS, MFApi.USER_GET_SESSION_TOKEN);
        mfRequestBuilder.requestParameters(requestParameters);
        MFRequest mfRequest = mfRequestBuilder.build();
        mfHttpProcessor.doRequest(mfRequest);
    }

    private void getNewImageActionToken() {
        mfConfiguration.getMFLogger().d(TAG, "getNewImageActionToken()");

        Map<String, String> requestParameters = new LinkedHashMap<String, String>();
        requestParameters.put("lifespan", "1440");
        requestParameters.put("type", "image");
        MFRequest.MFRequestBuilder mfRequestBuilder = new MFRequest.MFRequestBuilder(MFHost.LIVE_HTTP, MFApi.USER_GET_IMAGE_TOKEN);
        mfRequestBuilder.requestParameters(requestParameters);
        MFRequest mfRequest = mfRequestBuilder.build();
        mfHttpProcessor.doRequest(mfRequest);
    }

    private void getNewUploadActionToken() {
        mfConfiguration.getMFLogger().d(TAG, "getNewUploadActionToken()");

        Map<String, String> requestParameters = new LinkedHashMap<String, String>();
        requestParameters.put("lifespan", "1440");
        requestParameters.put("type", "upload");
        MFRequest.MFRequestBuilder mfRequestBuilder = new MFRequest.MFRequestBuilder(MFHost.LIVE_HTTP, MFApi.USER_GET_UPLOAD_TOKEN);
        mfRequestBuilder.requestParameters(requestParameters);
        MFRequest mfRequest = mfRequestBuilder.build();
        mfHttpProcessor.doRequest(mfRequest);
    }

    @Override
    public void fill() {
        startup();
    }

    @Override
    public void empty() {
        shutdown();
    }

    /**
     * clears user credentials, clears session tokens, and nulls the action tokens.
     */
    public void shutdown() {
        mfConfiguration.getMFLogger().d(TAG, "shutdown()");
        mfConfiguration.getMFCredentials().clearCredentials();
        mfSessionTokens.clear();
        mfUploadActionToken = null;
        mfImageActionToken = null;
    }

    /**
     * fetches session tokens and action tokens.
     */
    public void startup() {
        mfConfiguration.getMFLogger().d(TAG, "startup()");
        // do nothing if credentials aren't stored
        if (!haveStoredCredentials()) {
            throw new IllegalStateException("cannot call startup() without credentials");
        }
        // get one session token on current thread
        getNewSessionToken();
        // get remaining session tokens on another thread
        for (int i = 0; i < mfSessionTokens.remainingCapacity(); i++) {
            mfConfiguration.getMFLogger().v(TAG, "fetching new session token (remaining capacity " + mfSessionTokens.remainingCapacity() + ")");
            startThreadForNewSessionToken();
        }
        // get an upload action token on current thread
        if (mfUploadActionToken == null || mfUploadActionToken.isExpired()) {
            mfConfiguration.getMFLogger().v(TAG, "fetching upload action token");
            getNewUploadActionToken();
        }

        // get an image action token on current thread
        if (mfImageActionToken == null || mfImageActionToken.isExpired()) {
            mfConfiguration.getMFLogger().v(TAG, "fetching image action token()");
            getNewImageActionToken();
        }
    }

    @Override
    public void returnSessionToken(MFSessionToken sessionToken) {
        mfConfiguration.getMFLogger().d(TAG, "returnSessionToken()");
        if (sessionToken == null) {
            mfConfiguration.getMFLogger().w(TAG, "received null session token");
            return;
        }

        try {
            mfSessionTokens.put(sessionToken);
        } catch (InterruptedException e) {
            mfConfiguration.getMFLogger().e(TAG, "exception while trying to return a session token", e);
        }
    }

    @Override
    public void sessionTokenSpoiled(MFSessionToken mfSessionToken) {
        mfConfiguration.getMFLogger().d(TAG, "sessionTokenSpoiled()");
        mfConfiguration.getMFLogger().e(TAG, "MFSessionToken got spoiled: " + mfSessionToken.toString());
        startThreadForNewSessionToken();
    }

    @Override
    public void receiveNewSessionToken(MFSessionToken mfSessionToken) {
        mfConfiguration.getMFLogger().d(TAG, "receiveNewSessionToken()");
        if (mfSessionToken != null) {
            mfConfiguration.getMFLogger().v(TAG, "received good session token");
            try {
                mfSessionTokens.offer(mfSessionToken);
            } catch (IllegalStateException e) {
                getNewSessionToken();
            }
        } else {
            mfConfiguration.getMFLogger().v(TAG, "received bad session token, not keeping it");
        }
    }

    @Override
    public void receiveNewImageActionToken(MFActionToken mfImageActionToken) {
        mfConfiguration.getMFLogger().d(TAG, "receiveNewImageActionToken()");

        if (isActionTokenValid(mfImageActionToken)) {
            this.mfImageActionToken = mfImageActionToken;
            mfConfiguration.getMFLogger().v(TAG, "received good image action token");
        } else {
            mfConfiguration.getMFLogger().v(TAG, "received bad image action token, not keeping it");
        }
    }

    @Override
    public void receiveNewUploadActionToken(MFActionToken mfUploadActionToken) {
        mfConfiguration.getMFLogger().d(TAG, "receiveNewUploadActionToken()");

        if (isActionTokenValid(mfUploadActionToken)) {
            this.mfUploadActionToken = mfUploadActionToken;
            mfConfiguration.getMFLogger().v(TAG, "received good upload action token");
        } else {
            mfConfiguration.getMFLogger().v(TAG, "received bad upload action token, not keeping it");
        }

    }

    @Override
    public MFSessionToken borrowMFSessionToken() {
        mfConfiguration.getMFLogger().d(TAG, "borrowMFSessionToken()");
        MFSessionToken sessionToken;
        synchronized (sessionTokenLock) {
            sessionToken = null;
            try {
                mfConfiguration.getMFLogger().v(TAG, "session token queue size: " + mfSessionTokens.size());
                if (mfSessionTokens.size() == 0 && haveStoredCredentials()) {
                    getNewSessionToken();
                }
                sessionToken = mfSessionTokens.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (mfSessionTokens.remainingCapacity() < minimumSessionTokens) {
                int numberOfTokensToGet = minimumSessionTokens - mfSessionTokens.remainingCapacity();
                for (int i = 0; i < numberOfTokensToGet; i++) {
                    startThreadForNewSessionToken();
                }
            }
        }
        mfConfiguration.getMFLogger().v(TAG, "loaning MFToken: " + sessionToken.toString());
        return sessionToken;
    }

    @Override
    public MFActionToken borrowMFUploadActionToken() {
        mfConfiguration.getMFLogger().d(TAG, "borrowMFUploadActionToken()");
        // lock and fetch new token if necessary
        lockBorrowUploadToken.lock();

        try {
            if (needNewActionToken(mfUploadActionToken)) {
                mfConfiguration.getMFLogger().v(TAG, "fetching new upload token");
                startThreadForNewUploadActionToken();
            }
            // wait while we get an image action token, condition is that image
            // action token is null or action token is expired or action token
            // string is null
            while (needNewActionToken(mfUploadActionToken)) {
                conditionUploadTokenNotExpired.await(1, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            mfConfiguration.getMFLogger().e(TAG, "exception while trying to borrow an upload action token", e);
            return null;
        } finally {
            lockBorrowUploadToken.unlock();
        }
        mfConfiguration.getMFLogger().v(TAG, "loaning MFToken: " + mfUploadActionToken.toString());
        return mfUploadActionToken;
    }

    @Override
    public MFActionToken borrowMFImageActionToken() {
        mfConfiguration.getMFLogger().d(TAG, "borrowMFImageActionToken()");
        // lock and fetch new token if necessary
        lockBorrowImageToken.lock();

        try {
            if (needNewActionToken(mfImageActionToken)) {
                mfConfiguration.getMFLogger().v(TAG, "fetching new action token");
                startThreadForNewImageActionToken();
            }
            // wait while we get an image action token, condition is that image
            // action token is null or action token is expired or action token
            // string is null
            while (needNewActionToken(mfImageActionToken)) {
                conditionImageTokenNotExpired.await(1, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            mfConfiguration.getMFLogger().e(TAG, "exception while trying to borrow an image action token", e);
            return null;
        } finally {
            // attach new one to apiRequestObject
            lockBorrowImageToken.unlock();
        }
        mfConfiguration.getMFLogger().d(TAG, "loaning MFToken: " + mfImageActionToken.toString());
        return mfImageActionToken;
    }

    @Override
    public void actionTokenSpoiled() {
        lockBorrowImageToken.lock();
        lockBorrowUploadToken.lock();
        mfImageActionToken = null;
        mfUploadActionToken = null;
        getNewUploadActionToken();
        getNewImageActionToken();
        lockBorrowImageToken.unlock();
        lockBorrowUploadToken.unlock();
    }

    private boolean needNewActionToken(MFActionToken token) {
        return token == null || token.isExpired() || token.getTokenString() == null;
    }

    private boolean isActionTokenValid(MFActionToken token) {
        mfConfiguration.getMFLogger().d(TAG, "isActionTokenValid()");
        mfConfiguration.getMFLogger().v(TAG, "token null: " + (token == null));
        mfConfiguration.getMFLogger().v(TAG, "token expired: " + token.isExpired());
        mfConfiguration.getMFLogger().v(TAG, "token string null: " + (token == null ? true : token.getTokenString() == null));
        return token != null && !token.isExpired() && token.getTokenString() != null;
    }

    private boolean haveStoredCredentials() {
        return !mfConfiguration.getMFCredentials().getCredentials().isEmpty();
    }

    private void startThreadForNewSessionToken() {
        mfConfiguration.getMFLogger().d(TAG, "startThreadForNewSessionToken()");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getNewSessionToken();
            }
        });
        thread.start();
    }

    private void startThreadForNewImageActionToken() {
        mfConfiguration.getMFLogger().d(TAG, "startThreadForNewImageActionToken()");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getNewImageActionToken();
            }
        });
        thread.start();
    }

    private void startThreadForNewUploadActionToken() {
        mfConfiguration.getMFLogger().d(TAG, "startThreadForNewUploadActionToken()");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getNewUploadActionToken();
            }
        });
        thread.start();
    }
}
