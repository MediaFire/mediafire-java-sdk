package com.mediafire.sdk;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MFSessionStore implements MediaFireSessionStore {
    private final Queue<MediaFireSessionTokenV2> sessionTokens = new LinkedBlockingQueue<MediaFireSessionTokenV2>();
    private MediaFireActionToken uploadToken;
    private MediaFireActionToken imageToken;
    private final Object uploadTokenLock = new Object();
    private final Object imageTokenLock = new Object();
    private static final long EXPIRE_THRESHOLD = 1000 * 60;

    public MFSessionStore() {

    }

    @Override
    public MediaFireSessionTokenV2 getMediaFireSessionTokenV2() {
        return sessionTokens.poll();
    }

    @Override
    public boolean putMediaFireSessionTokenV2(MediaFireSessionTokenV2 mediaFireSessionTokenV2) {
        return sessionTokens.offer(mediaFireSessionTokenV2);
    }

    @Override
    public int getMediaFireSessionTokenV2Count() {
        return sessionTokens.size();
    }

    @Override
    public boolean isMediaFireSessionTokenV2Available() {
        return !sessionTokens.isEmpty();
    }

    @Override
    public MediaFireActionToken getMediaFireActionToken(int type) {
        MediaFireActionToken token;
        switch (type) {
            case MediaFireActionToken.IMAGE:
                synchronized (imageTokenLock) {
                    token = imageToken;
                }
                break;
            case MediaFireActionToken.UPLOAD:
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
    public boolean putMediaFireActionToken(MediaFireActionToken mediaFireActionToken) {
        return false;
    }

    @Override
    public boolean isMediaFireActionTokenAvailable(int type) {
        boolean available;
        switch (type) {
            case MediaFireActionToken.IMAGE:
                available = isImageTokenAvailable();
                break;
            case MediaFireActionToken.UPLOAD:
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
