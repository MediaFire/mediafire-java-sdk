package com.mediafire.sdk.config;

/**
 * Created by Chris Najar on 10/21/2014.
 */
public interface Initializable {
    public void initialize(Configuration configuration);
    public void shutdown();
}
