package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.ResponseApiClientError;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class DefaultHttpWorker implements HttpWorkerInterface {
    private final int CONNECTION_TIMEOUT_MILLISECONDS = 5000;
    private final int READ_TIMEOUT_MILLISECONDS = 45000;

    @Override
    public Response doGet(String url, Map<String, String> headers) {
        if (url.startsWith("http")) {
            return doGetHttp(url, headers);
        } else if (url.startsWith("https")) {
            return doGetHttps(url, headers);
        } else {
            return new ResponseApiClientError("Url did not start with http or https");
        }
    }

    private Response doGetHttp(String url, Map<String, String> headers) {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ResponseApiClientError("Exception: " + e, e);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("Exception: " + e, e);
        }

        for (String key : headers.keySet()) {
            if (headers.get(key) != null) {
                connection.addRequestProperty(key, headers.get(key));
            }
        }

        InputStream response;
        try {
            response = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("Exception while trying to get input stream: " + e, e);
        }
        byte[] responseStream;
        try {
            responseStream = readStream(response);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("Exception while trying to read input stream: " + e, e);
        }

        int responseCode;
        try {
            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("Exception while trying to get response code: " + e, e);
        }

        connection.disconnect();

        return new Response(responseCode, responseStream);
    }

    private Response doGetHttps(String url, Map<String, String> headers) {
        HttpsURLConnection connection;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ResponseApiClientError("Exception: " + e, e);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("Exception: " + e, e);
        }

        for (String key : headers.keySet()) {
            if (headers.get(key) != null) {
                connection.addRequestProperty(key, headers.get(key));
            }
        }

        InputStream response;
        try {
            response = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("Exception while trying to get input stream: " + e, e);
        }
        byte[] responseStream;
        try {
            responseStream = readStream(response);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("Exception while trying to read input stream: " + e, e);
        }

        int responseCode;
        try {
            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("Exception while trying to get response code: " + e, e);
        }

        connection.disconnect();

        return new Response(responseCode, responseStream);
    }

    @Override
    public Response doPost(String url, Map<String, String> headers, byte[] payload) {
        return new ResponseApiClientError("post not implemented yet");
    }

    private byte[] readStream(InputStream inputStream) throws IOException {
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
}
