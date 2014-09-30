package com.mediafire.sdk.config;

public interface MFNetworkConnectivityMonitorInterface {
    /**
     * Gets network status. blocking call.
     * @return - true if network is available, false otherwise.
     */
    public boolean haveNetworkConnection();
}
