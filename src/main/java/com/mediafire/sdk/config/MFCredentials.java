package com.mediafire.sdk.config;

import java.util.Map;

public interface MFCredentials {

    public String PARAM_EMAIL = "email";
    public String PARAM_PASSWORD = "password";
    public String PARAM_EKEY = "ekey";

    public void setCredentials(Map<String, String> credentials);

    public Map<String, String> getCredentials();

    public void invalidate();

    public boolean setValid();

    public boolean isValid();
}
