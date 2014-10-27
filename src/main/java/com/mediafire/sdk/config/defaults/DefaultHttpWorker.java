package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.ResponseApiClientError;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class DefaultHttpWorker implements HttpWorkerInterface {
    private static final String TAG = DefaultHttpWorker.class.getCanonicalName();
    private final int CONNECTION_TIMEOUT_MILLISECONDS = 5000;
    private final int READ_TIMEOUT_MILLISECONDS = 45000;
    private Configuration mConfiguration;

    @Override
    public Response doGet(String url, Map<String, String> headers) {
        mConfiguration.getLogger().v(TAG, "doGet - " + url);
        try {
            HttpURLConnection connection = getURLConnection(url);
            setTimeouts(connection);
            addRequestHeadersToConnection(connection, headers);
            int responseCode = connection.getResponseCode();
            InputStream inputStream;
            if (responseCode / 100 != 2 ) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }
            byte[] response = readStream(inputStream);
            return new Response(responseCode, response);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("IOException while trying to do GET on url '" + url + "'", e);
        } finally {
        }
    }

    @Override
    public Response doPost(String url, Map<String, String> headers, byte[] payload) {
        mConfiguration.getLogger().v(TAG, "doPost - " + url);
        try{
            HttpURLConnection connection = getURLConnection(url);
            setTimeouts(connection);
            connection.setDoOutput(true);
            addRequestHeadersToConnection(connection, headers);
            postData(connection, payload);
            int responseCode = connection.getResponseCode();
            InputStream inputStream;
            if (responseCode / 100 != 2 ) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }
            byte[] response = readStream(inputStream);
            return new Response(responseCode, response);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("IOException while trying to do POST on url '" + url + "'", e);
        }
    }

    private HttpURLConnection getURLConnection(String url) throws IOException {
        String urlScheme = url.substring(0, 5);

        if (urlScheme.equals("http:")) {
            mConfiguration.getLogger().v(TAG, "getURLConnection - HttpUrlConnection");
            return (HttpURLConnection) new URL(url).openConnection();
        }

        if (urlScheme.equals("https")) {
            mConfiguration.getLogger().v(TAG, "getURLConnection - HttpsUrlConnection");
            return (HttpsURLConnection) new URL(url).openConnection();
        }

        return null;
    }

    private void addRequestHeadersToConnection(URLConnection connection, Map<String, String> headers) {
        mConfiguration.getLogger().v(TAG, "addRequestHeadersToConnection - " + headers.size());
        for (String key : headers.keySet()) {
            if (headers.get(key) != null) {
                connection.addRequestProperty(key, headers.get(key));
            }
        }
        mConfiguration.getLogger().v(TAG, "addRequestHeadersToConnection - added request properties:" + connection.getRequestProperties());
    }

    private void setTimeouts(URLConnection connection) {
        mConfiguration.getLogger().v(TAG, "setTimeouts - conn/read = " + CONNECTION_TIMEOUT_MILLISECONDS + "/" + READ_TIMEOUT_MILLISECONDS);
        connection.setConnectTimeout(CONNECTION_TIMEOUT_MILLISECONDS);
        connection.setReadTimeout(READ_TIMEOUT_MILLISECONDS);
    }

    private void postData(URLConnection connection, byte[] payload) throws IOException {
        if (payload == null) {
            mConfiguration.getLogger().w(TAG, "postData - byte array empty, not posting anything");
            return;
        } else {
            mConfiguration.getLogger().w(TAG, "postData - posting " + payload.length + " bytes");
        }
        mConfiguration.getLogger().v(TAG, "postData - request properties: " + connection.getRequestProperties());

        String postDataAsString = new String(payload, "UTF-8");
        if (postDataAsString != null) {
            mConfiguration.getLogger().v(TAG, "postData - payload: " + postDataAsString);
        } else {
            mConfiguration.getLogger().v(TAG, "postData - payload could not be parsed to string, byte length: " + payload.length);
        }
        mConfiguration.getLogger().v(TAG, "postData - writing payload");
        connection.getOutputStream().write(payload);
        mConfiguration.getLogger().v(TAG, "postData - finished writing payload");
    }

    private byte[] readStream(InputStream inputStream) throws IOException {
        mConfiguration.getLogger().v(TAG, "readStream");
        if (inputStream == null) {
            return null;
        }
        byte[] buffer = new byte[1024];
        int count;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        while ((count = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, count);
        }

        inputStream.close();
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        return bytes;
    }

    @Override
    public void initialize(Configuration configuration) {
        mConfiguration = configuration;
    }

    @Override
    public void shutdown() {

    }
}
