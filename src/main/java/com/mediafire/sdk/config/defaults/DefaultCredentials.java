package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.CredentialsInterface;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class DefaultCredentials implements CredentialsInterface {
    private static final String TAG = DefaultCredentials.class.getCanonicalName();
    Map<String, String> mCredentials = new LinkedHashMap<String, String>();
    private String mName;

    public DefaultCredentials(String name) {

        mName = name;
    }

    @Override
    public void setCredentials(Map<String, String> credentials) {
        DefaultLogger.log().v(TAG, "setCredentials - on " + mName + " with size - " + credentials.size());
        mCredentials = credentials;
    }

    @Override
    public Map<String, String> getCredentials() {
        DefaultLogger.log().v(TAG, "getCredentials - on " + mName);
        return mCredentials;
    }

    @Override
    public void clearCredentials() {
        DefaultLogger.log().v(TAG, "clearCredentials - on " + mName);
        mCredentials.clear();
    }

    @Override
    public String getConcatenatedCredentials() {
        DefaultLogger.log().v(TAG, "getConcatenatedCredentials - on " + mName);
        String str = new String();
        for (String key : mCredentials.keySet()) {
            str += mCredentials.get(key);
        }
        return str;
    }
}
