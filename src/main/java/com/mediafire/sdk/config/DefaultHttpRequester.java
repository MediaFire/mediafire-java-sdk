package com.mediafire.sdk.config;

import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MFRuntimeException;
import com.mediafire.sdk.requests.GetRequest;
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
import java.util.logging.Handler;
import java.util.logging.Logger;

public class DefaultHttpRequester implements MFHttpRequester {

    private final int connectionTimeout;
    private final int readTimeout;
    private final Logger logger;

    public DefaultHttpRequester(int connectionTimeout, int readTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
        this.logger = Logger.getLogger("com.mediafire.sdk.config.DefaultHttpRequester");
    }

    @Override
    public HttpApiResponse doApiRequest(PostRequest postRequest) throws MFException {
        logger.info("doApiRequest()");
        try {
            String urlString = postRequest.getUrl();
            Map<String, Object> headers = postRequest.getHeaders();
            byte[] payload = postRequest.getPayload();

            logger.info("request url: " + urlString);
            logger.info("request headers: " + headers);
            logger.info("request payload: " + (payload.length < 1000 ? new String(payload) : payload.length));
            
            HttpURLConnection connection;
            if ("http".equals(postRequest.getScheme())) {
                connection = (HttpURLConnection) new URL(urlString).openConnection();
            } else if ("https".equals(postRequest.getScheme())) {
                connection = (HttpsURLConnection) new URL(urlString).openConnection();
            } else {
                throw new MFRuntimeException("scheme must be http or https", new IllegalArgumentException());
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

            logger.info("server response: " + new String(response));
            logger.info("response headers: " + headerFields);
            return new HttpApiResponse(responseCode, response, headerFields);
        } catch (MalformedURLException e) {
            logger.severe("exception: " + e);
            throw new MFException("Malformed Url in HttpRequester", e);
        } catch (IOException e) {
            logger.severe("exception:" + e);
            throw new MFException("Exception in HttpRequester", e);
        }
    }

    @Override
    public HttpApiResponse doApiRequest(GetRequest getRequest) throws MFException {
        try {
            String urlString = getRequest.getUrl();
            Map<String, Object> headers = getRequest.getHeaders();

            logger.info("request url: " + urlString);
            logger.info("request headers: " + headers);

            HttpURLConnection connection = (HttpsURLConnection) new URL(urlString).openConnection();

            // set up connection parameters
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);

            for (String key : headers.keySet()) {
                if (headers.get(key) != null) {
                    connection.addRequestProperty(key, String.valueOf(headers.get(key)));
                }
            }

            int responseCode = connection.getResponseCode();
            InputStream inputStream;
            if (responseCode / 100 != 2) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }
            byte[] response = readStream(inputStream);
            Map<String, List<String>> headerFields = connection.getHeaderFields();

            logger.info("server response: " + new String(response));
            logger.info("response headers: " + headerFields);
            return new HttpApiResponse(responseCode, response, headerFields);
        } catch (MalformedURLException e) {
            logger.severe("exception: " + e);
            throw new MFException("Malformed Url in HttpRequester", e);
        } catch (IOException e) {
            logger.severe("exception:" + e);
            throw new MFException("Exception in HttpRequester", e);
        }
    }

    @Override
    public void setLoggerHandler(Handler loggerHandler) {
        logger.addHandler(loggerHandler);
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
