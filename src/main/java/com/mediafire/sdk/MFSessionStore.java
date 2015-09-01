package com.mediafire.sdk;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MFSessionStore implements MediaFireSessionStore {
    private final Queue<MediaFireSessionToken> sessionTokens = new LinkedBlockingQueue<MediaFireSessionToken>();
    private MediaFireActionToken uploadToken;
    private MediaFireActionToken imageToken;
    private final Object uploadTokenLock = new Object();
    private final Object imageTokenLock = new Object();
    private static final long EXPIRE_THRESHOLD = 1000 * 60;

    public MFSessionStore() {

    }

    @Override
    public MediaFireSessionToken getSessionTokenV2() {
        return sessionTokens.poll();
    }

    @Override
    public boolean put(MediaFireSessionToken token) {
        return sessionTokens.offer(token);
    }

    @Override
    public int getSessionTokenV2Count() {
        return sessionTokens.size();
    }

    @Override
    public boolean isSessionTokenV2Available() {
        return !sessionTokens.isEmpty();
    }

    @Override
    public MediaFireActionToken getActionToken(int type) {
        MediaFireActionToken token;
        switch (type) {
            case MediaFireActionToken.TYPE_IMAGE:
                synchronized (imageTokenLock) {
                    token = imageToken;
                }
                break;
            case MediaFireActionToken.TYPE_UPLOAD:
                synchronized (uploadTokenLock) {
                    token = uploadToken;
                }
                break;
            default:
                token = null;
                break;
        }

        return token;
    }

    @Override
    public boolean put(MediaFireActionToken token) {
        switch (token.getType()) {
            case MediaFireActionToken.TYPE_IMAGE:
                synchronized (imageTokenLock) {
                    this.imageToken = token;
                    return true;
                }
            case MediaFireActionToken.TYPE_UPLOAD:
                synchronized (uploadTokenLock) {
                    this.uploadToken = token;
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean isActionTokenAvailable(int type) {
        boolean available;
        switch (type) {
            case MediaFireActionToken.TYPE_IMAGE:
                available = isImageTokenAvailable();
                break;
            case MediaFireActionToken.TYPE_UPLOAD:
                available = isUploadTokenAvailable();
                break;
            default:
                available = false;
                break;
        }
        return available;
    }

    private synchronized boolean isImageTokenAvailable() {
        return imageToken != null && !isTokenExpired(imageToken);
    }

    private boolean isTokenExpired(MediaFireActionToken imageToken) {
        long requestTime = imageToken.getRequestTime();
        int lifespanMinutes = imageToken.getLifespan();
        long lifespan = lifespanMinutes * 60 * 1000;

        long expireTime = requestTime + lifespan;

        return System.currentTimeMillis() >= expireTime + EXPIRE_THRESHOLD;
    }

    private synchronized boolean isUploadTokenAvailable() {
        return uploadToken != null && !isTokenExpired(uploadToken);
    }

    @Override
    public void clear() {
        synchronized (this) {
            sessionTokens.clear();
            uploadToken = null;
            imageToken = null;
        }
    }
}
