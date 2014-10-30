package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.NetworkConnectivityMonitorInterface;

/**
 * Created by Chris Najar on 10/19/2014.
 * DefaultNetworkConnectivityMonitor is a default implementation of the NetworkConnectivityMonitorInterface
 * A custom implementation is strongly recommended
 */
public class DefaultNetworkConnectivityMonitor implements NetworkConnectivityMonitorInterface {
    private static final String TAG = DefaultNetworkConnectivityMonitor.class.getCanonicalName();

    /**
     * Always returns true, should test network connection in other implementations
     * @return a boolean signifying if there is a network connection
     */
    @Override
    public boolean haveNetworkConnection() {
        DefaultLogger.log().v(TAG, "haveNetworkConnection");
        return true;
    }
}
