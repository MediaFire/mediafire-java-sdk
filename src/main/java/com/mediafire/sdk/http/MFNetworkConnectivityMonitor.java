package com.mediafire.sdk.http;

public interface MFNetworkConnectivityMonitor {
    /**
     * Gets network status. blocking call.
     * @return - true if network is available, false otherwise.
     */
    public boolean haveNetworkConnection();
}
