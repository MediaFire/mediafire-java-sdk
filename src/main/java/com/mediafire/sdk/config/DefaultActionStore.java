package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ActionToken;

import java.util.logging.Logger;

public class DefaultActionStore implements MFStore<ActionToken> {

    private ActionToken token;
    private final Object lock = new Object();
    private final long threshold;
    private final Logger logger;

    public DefaultActionStore(int thresholdMinutes) {
        this.threshold = 1000 * 60 * thresholdMinutes;
        this.logger = Logger.getLogger("com.mediafire.sdk.config.DefaultActionStore");
    }

    @Override
    public boolean available() {
        synchronized (lock) {
            if (token == null) {
                logger.info("token == null, no tokens available");
                return false;
            } else if (token.isExpired()) {
                logger.info("token is expired, no tokens available");
                token = null;
                return false;
            } else if (token.isExpiringWithinMillis(threshold)) {
                logger.info("token is expiring within " + threshold + ", no tokens available");
                token = null;
                return false;
            } else if (token.getToken() == null || token.getToken().isEmpty()) {
                logger.info("token string is null or empty, no tokens available");
                token = null;
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public ActionToken get() {
        synchronized (lock) {
            return token;
        }
    }

    @Override
    public void put(ActionToken actionToken) {
        synchronized (lock) {
            this.token = actionToken;
        }
    }

    @Override
    public void clear() {
        synchronized (lock) {
            this.token = null;
        }
    }

    @Override
    public int getAvailableCount() {
        return available() ? 1 : 0;
    }
}
