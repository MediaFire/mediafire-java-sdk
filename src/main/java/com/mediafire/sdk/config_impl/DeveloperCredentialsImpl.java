package com.mediafire.sdk.config_impl;

import com.mediafire.sdk.config.IDeveloperCredentials;

/**
 * Created by Chris on 12/17/2014.
 */
public final class DeveloperCredentialsImpl implements IDeveloperCredentials {

    private final String mApplicationId;
    private final String mApiKey;

    public DeveloperCredentialsImpl(String applicationId, String apiKey) {
        mApplicationId = applicationId;
        mApiKey = apiKey;
    }

    public DeveloperCredentialsImpl(String applicationId) {
        this(applicationId, null);
    }

    @Override
    public String getApplicationId() {
        return mApplicationId;
    }

    @Override
    public String getApiKey() {
        return mApiKey;
    }

    @Override
    public boolean requiresSecretKey() {
        return mApiKey != null;
    }
}
