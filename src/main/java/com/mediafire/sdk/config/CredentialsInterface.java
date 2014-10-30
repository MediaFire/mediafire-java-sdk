package com.mediafire.sdk.config;

import java.util.Map;

/**
 * CredentialsInterface stores a set of credentials
 */
public interface CredentialsInterface {

    /**
     * Sets the credentials passed as a param
     * @param credentials a Map<String, String> containing the credentials to set
     */
    public void setCredentials(Map<String, String> credentials);

    /**
     * Returns the stored credentials
     * @return a Map<String, String> of the credentials
     */
    public Map<String, String> getCredentials();

    /**
     * Clears the currently stored credentials
     */
    public void clearCredentials();

    /**
     * Returns the values of the stored credentials concatenated together
     * @return String of the concatenated credentials
     */
    public String getConcatenatedCredentials();
}
