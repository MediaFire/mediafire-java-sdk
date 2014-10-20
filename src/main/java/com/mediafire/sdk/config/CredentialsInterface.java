package com.mediafire.sdk.config;

import java.util.Map;

public interface CredentialsInterface {

    public void setCredentials(Map<String, String> userCredentials);

    public Map<String, String> getCredentials();

    public void clearCredentials();

    public String getConcatenatedCredentials();
}
