package com.mediafire.sdk.config;

import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MFRuntimeException;
import com.mediafire.sdk.requests.HttpApiResponse;
import com.mediafire.sdk.requests.PostRequest;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class DefaultHttpRequester implements MFHttpRequester {

    private final int connectionTimeout;
    private final int readTimeout;

    public DefaultHttpRequester(int connectionTimeout, int readTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public HttpApiResponse doApiRequest(PostRequest postRequest) throws MFException {
        try {
            String urlString = postRequest.getUrl();
            Map<String, Object> headers = postRequest.getHeaders();
            byte[] payload = postRequest.getPayload();

            HttpURLConnection connection;
            if ("http".equals(postRequest.getScheme())) {
                connection = (HttpURLConnection) new URL(urlString).openConnection();
            } else if ("https".equals(postRequest.getScheme())) {
                connection = (HttpsURLConnection) new URL(urlString).openConnection();
            } else {
                throw new MFException("scheme must be http or https");
            }

            // set up connection parameters
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);

            connection.setDoOutput(true);

            for (String key : headers.keySet()) {
                if (headers.get(key) != null) {
                    connection.addRequestProperty(key, String.valueOf(headers.get(key)));
                }
            }

            connection.getOutputStream().write(payload);

            int responseCode = connection.getResponseCode();
            InputStream inputStream;
            if (responseCode / 100 != 2) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }
            byte[] response = readStream(inputStream);
            Map<String, List<String>> headerFields = connection.getHeaderFields();
            return new HttpApiResponse(responseCode, response, headerFields);
        } catch (MalformedURLException e) {
            throw new MFException("Malformed Url in HttpRequester", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MFRuntimeException("Exception in HttpRequester", e);
        }
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
