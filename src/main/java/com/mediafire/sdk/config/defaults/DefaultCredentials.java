package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.CredentialsInterface;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Chris Najar on 10/19/2014.
 * DefaultCredentials is a default implementation of the CredentialsInterface
 * A custom implementation is recommended
 */
public class DefaultCredentials implements CredentialsInterface {
    private static final String TAG = DefaultCredentials.class.getCanonicalName();
    private Map<String, String> mCredentials = new LinkedHashMap<String, String>();

    /**
     * DefaultCredentials Constructor
     */
    public DefaultCredentials() {}

    /**
     * Sets the passed credentials
     * @param credentials the credentials to be set (override current credentials)
     */
    @Override
    public void setCredentials(Map<String, String> credentials) {
        mCredentials = credentials;
    }

    /**
     * Gets the stored credentials
     * @return a Map<String, String> containing the stored credentials
     */
    @Override
    public Map<String, String> getCredentials() {
        return mCredentials;
    }

    /**
     * Clears the stored credentials
     */
    @Override
    public void clearCredentials() {
        mCredentials.clear();
    }

    /**
     * Concatenates the stored credentials (only the value, not the key)
     * @return a new String of the concatenated credentials
     */
    @Override
    public String getConcatenatedCredentials() {
        String str = "";
        for (String key : mCredentials.keySet()) {
            str += mCredentials.get(key);
        }
        return str;
    }
}
