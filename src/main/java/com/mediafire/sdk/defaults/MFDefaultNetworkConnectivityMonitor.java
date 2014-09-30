package com.mediafire.sdk.defaults;

import com.mediafire.sdk.config.MFNetworkConnectivityMonitorInterface;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MFDefaultNetworkConnectivityMonitor implements MFNetworkConnectivityMonitorInterface {
    private static final String URL_CONNECTION_ADDRESS = "http://www.mediafire.com";
    private static final int PING_TIMEOUT = 500;

    public MFDefaultNetworkConnectivityMonitor() {}

    @Override
    public boolean haveNetworkConnection() {
        try {
            URL url = new URL(URL_CONNECTION_ADDRESS);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(PING_TIMEOUT);
            httpURLConnection.connect();
            return httpURLConnection.getResponseCode() == 200;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
