package com.mediafire.sdk;

import com.arkhive.components.core.module_errors.ErrorTracker;

/**
 * Created by on 6/17/2014.
 */
public final class MFConfiguration {
    public static final int DEFAULT_HTTP_READ_TIMEOUT = 30000;
    public static final int DEFAULT_HTTP_CONNECTION_TIMEOUT = 30000;
    public static final int DEFAULT_MINIMUM_SESSION_TOKENS = 1;
    public static final int DEFAULT_MAXIMUM_SESSION_TOKENS = 3;
    public static final int DEFAULT_HTTP_POOL_SIZE = 6;

    private int httpReadTimeout;
    private int httpConnectionTimeout;
    private int minimumSessionTokens;
    private int maximumSessionTokens;
    private int httpPoolSize;
    private String appId;
    private String apiKey;
    private static ErrorTracker errorTracker;

    public MFConfiguration(ErrorTracker errorTracker, String appId, String apiKey) {
        if (errorTracker == null) {
            throw new IllegalStateException("ErrorTracker must not be null");
        }
        if (appId == null) {
            throw new IllegalStateException("appId must not be null");
        }
        if (apiKey == null) {
            throw new IllegalStateException("apiKey must not be null");
        }
        MFConfiguration.errorTracker = errorTracker;
        this.appId = appId;
        this.apiKey = apiKey;
        httpReadTimeout = DEFAULT_HTTP_READ_TIMEOUT;
        httpConnectionTimeout = DEFAULT_HTTP_CONNECTION_TIMEOUT;
        minimumSessionTokens = DEFAULT_MINIMUM_SESSION_TOKENS;
        maximumSessionTokens = DEFAULT_MAXIMUM_SESSION_TOKENS;
        httpPoolSize = DEFAULT_HTTP_POOL_SIZE;
        
    }

    public int getHttpReadTimeout() {
        return httpReadTimeout;
    }

    /**
     * Sets the http connection timeout time.
     * @param httpReadTimeout - between 5 and 60 seconds
     * @return false if not set (due to bad input), true if set.
     */
    public boolean setHttpReadTimeout(int httpReadTimeout) {
        if (httpReadTimeout < 5 || httpReadTimeout > 60) {
            return false;
        }
        this.httpReadTimeout = httpReadTimeout * 1000;
        return true;
    }

    public int getHttpConnectionTimeout() {
        return httpConnectionTimeout;
    }

    /**
     * Sets the http connection timeout time.
     * @param httpConnectionTimeout - between 5 and 60 seconds
     * @return false if not set (due to bad input), true if set.
     */
    public boolean setHttpConnectionTimeout(int httpConnectionTimeout) {
        if (httpConnectionTimeout < 5 || httpConnectionTimeout > 60) {
            return false;
        }
        this.httpConnectionTimeout = httpConnectionTimeout * 1000;
        return true;
    }

    public int getMinimumSessionTokens() {
        return minimumSessionTokens;
    }

    public int getMaximumSessionTokens() {
        return maximumSessionTokens;
    }

    /**
     * Sets the limit on the number of session tokens stored.
     *
     * @param min - between 0 and 10, must be less than maximumSessionTokens
     * @param max - between 1 and 10, must be greater than minimumSessionTokens
     * @return false if not set (due to bad input), true if set.
     */
    public boolean setSessionTokensInBlockingQueueMinMax(int min, int max) {
        if (min > max || min < 0 || max < 1 || min > 10 || max > 10) {
            return false;
        }
        maximumSessionTokens = max;
        minimumSessionTokens = min;
        return true;
    }

    public String getAppId() {
        return appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public int getHttpPoolSize() {
        return httpPoolSize;
    }

    /**
     * Sets the thread pool size for the http processor
     * @param httpPoolSize - minimum 1
     * @return false if not set (bad input), true if set
     */
    public boolean setHttpPoolSize(int httpPoolSize) {
        if (httpPoolSize < 1) {
            return false;
        }
        this.httpPoolSize = httpPoolSize;
        return true;
    }

    public static ErrorTracker getErrorTracker() {
        return errorTracker;
    }
}