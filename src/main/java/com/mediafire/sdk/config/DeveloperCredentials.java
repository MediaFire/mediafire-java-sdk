package com.mediafire.sdk.config;

/**
 * Created by Chris on 12/17/2014.
 */
public interface DeveloperCredentials {
    public String getApplicationId();

    public String getApiKey();

    public boolean requiresSecretKey();
}
