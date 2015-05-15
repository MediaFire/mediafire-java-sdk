package com.mediafire.sdk.config;

import java.util.Map;

public class DefaultCredentials implements MFCredentials {
    @Override
    public void setCredentials(Map<String, Object> credentials) {

    }

    @Override
    public Map<String, String> getCredentials() {
        return null;
    }

    @Override
    public void invalidate() {

    }

    @Override
    public void setValid() {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}
