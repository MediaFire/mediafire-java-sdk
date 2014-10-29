package com.mediafire.sdk.config;

/**
 * Created by Chris Najar on 10/21/2014.
 * Initializable provides a common interface to initialize and shutdown classes/interfaces
 */
public interface Initializable {
    /**
     * Initialize with the passed configuration
     * @param configuration Configuration object to initialize with
     */
    public void initialize(Configuration configuration);

    /**
     * Shutdown something
     */
    public void shutdown();
}
