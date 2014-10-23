package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.NetworkConnectivityMonitorInterface;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class DefaultNetworkConnectivityMonitor implements NetworkConnectivityMonitorInterface {
    private static final String TAG = DefaultNetworkConnectivityMonitor.class.getCanonicalName();

    @Override
    public boolean haveNetworkConnection() {
        DefaultLogger.log().v(TAG, "haveNetworkConnection");
        return true;
    }
}
