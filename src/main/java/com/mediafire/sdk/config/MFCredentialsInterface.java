package com.mediafire.sdk.config;

import java.util.Map;

public interface MFCredentialsInterface {
    /**
     * sets user credentials.
     * @param userCredentials - a map of user credentials.
     * @return true if credentials were set, false otherwise.
     */
    public boolean setCredentials(Map<String, String> userCredentials);

    /**
     * retrieves credentials set by setCredentials()
     * @return a map of user credentials.
     */
    public Map<String, String> getCredentials();

    /**
     * clears any credentials which were stored.
     */
    public void clearCredentials();

    /**
     * gets the MFCredentialsType of credentials stored.
     * @return MFCredentialsType representing the current stored credentials.
     */
    public MFCredentialsType getUserCredentialsType();
}
