package com.mediafire.sdk.config;

import java.util.Map;

public interface MFCredentials {

    public void setCredentials(Map<String, Object> credentials);

    public Map<String, String> getCredentials();

    public void invalidate();

    public void setValid();

    public boolean isValid();
}
