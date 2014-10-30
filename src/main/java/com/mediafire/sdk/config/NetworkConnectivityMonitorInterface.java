package com.mediafire.sdk.config;

/**
 * NetworkConnectivityMonitorInterface provides an interface for network connectivity checking
 */
public interface NetworkConnectivityMonitorInterface {

    /**
     * checks if there is a network connection
     * @return boolean saying if there is a network connection
     */
    public boolean haveNetworkConnection();
}
