package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.CredentialsInterface;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class DefaultCredentials implements CredentialsInterface {
    Map<String, String> mCredentials = new LinkedHashMap<String, String>();

    @Override
    public void setCredentials(Map<String, String> userCredentials) {
        mCredentials = userCredentials;
    }

    @Override
    public Map<String, String> getCredentials() {
        return mCredentials;
    }

    @Override
    public void clearCredentials() {
        mCredentials.clear();
    }

    @Override
    public String getConcatenatedCredentials() {
        String str = new String();
        for (String key : mCredentials.keySet()) {
            str += mCredentials.get(key);
        }
        return str;
    }
}
