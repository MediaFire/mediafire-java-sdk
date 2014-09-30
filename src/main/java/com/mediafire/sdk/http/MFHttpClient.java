package com.mediafire.sdk.http;

import com.mediafire.sdk.config.MFConfiguration;
import com.mediafire.sdk.config.MFNetworkConnectivityMonitorInterface;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public final class MFHttpClient extends MFHttp {
    private static final String TAG = MFHttpClient.class.getCanonicalName();
    private final int readTimeout;
    private final int connectionTimeout;
    private final MFNetworkConnectivityMonitorInterface mfNetworkConnectivityMonitor;

    public MFHttpClient(MFConfiguration mfConfiguration) {
        super(mfConfiguration);
        this.readTimeout = mfConfiguration.getHttpReadTimeout();
        this.connectionTimeout = mfConfiguration.getHttpConnectionTimeout();
        this.mfNetworkConnectivityMonitor = mfConfiguration.getMFNetworkConnectivityMonitor();
    }

    /**
     * creates a URLConnection, posts data if possible, and receives a response.
     * @param mfRequester - the MFRequester to use.
     * @return an MFResponse object.
     */
    public MFResponse sendRequest(MFRequester mfRequester) {
        mfConfiguration.getMFLogger().d(TAG, "sendRequest()");
        // if no network connection as per network connectivity monitor, return failed mf response (null values and -1 status)
        if (!mfNetworkConnectivityMonitor.haveNetworkConnection()) {
            return new MFResponse(-1, null, null, mfRequester);
        }

        URLConnection connection = null;
        MFResponse mfResponse = null;

        try {
            // create the connection
            connection = createHttpConnection(mfRequester);
            // send any data possible via POST
            postData(mfRequester, connection);
            // receive response from request
            mfResponse = getResponseFromStream(connection, mfRequester);

            if (mfResponse == null) {
                mfResponse = new MFResponse(-1, null, null, mfRequester);
            }
            mfConfiguration.getMFLogger().d(TAG, "response: " + mfResponse.getResponseAsString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                ((HttpURLConnection) connection).disconnect();
            }
        }

        return mfResponse;
    }

    private MFResponse getResponseFromStream(URLConnection connection, MFRequester mfRequester) throws IOException {
        mfConfiguration.getMFLogger().d(TAG, "getResponseFromStream()");
        int status = ((HttpURLConnection) connection).getResponseCode();
        MFResponse mfResponse;

        if (status / 100 != 2) {
            mfConfiguration.getMFLogger().v(TAG, "opening error stream");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(((HttpURLConnection) connection).getErrorStream());
            byte[] body = readStream(bufferedInputStream);
            mfResponse = new MFResponse(status, new Hashtable<String, List<String>>(), body, mfRequester);
        } else {
            mfConfiguration.getMFLogger().v(TAG, "opening input stream");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            byte[] body = readStream(bufferedInputStream);
            mfResponse = new MFResponse(status, connection.getHeaderFields(), body, mfRequester);
        }

        return mfResponse;
    }

    private void postData(MFRequester mfRequester, URLConnection connection) throws IOException {
        mfConfiguration.getMFLogger().d(TAG, "postData()");
        byte[] payload = null;
        if (mfRequester.isQueryPostable()) {
            String stringPayload = makeQueryString(mfRequester.getRequestParameters());
            mfConfiguration.getMFLogger().v(TAG, "query will be payload");
            payload = stringPayload.getBytes();
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        } else if (mfRequester.getPayload() != null) {
            mfConfiguration.getMFLogger().v(TAG, "query has byte payload");
            payload = mfRequester.getPayload();
            connection.addRequestProperty("Content-Type", "application/octet-stream");
        }

        if (payload != null) {
            mfConfiguration.getMFLogger().v(TAG, "payload length: " + payload.length);
            connection.addRequestProperty("Content-Length", String.valueOf(payload.length));
            connection.getOutputStream().write(payload);
        }
    }

    private byte[] readStream(InputStream inputStream) throws IOException {
        mfConfiguration.getMFLogger().d(TAG, "readStream()");
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

    private HttpURLConnection createHttpConnection(MFRequester mfRequester) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        mfConfiguration.getMFLogger().d(TAG, "createHttpConnection(type: " + mfRequester.getProtocol() + ")");
        URL url = makeFullUrl(mfRequester);
        mfConfiguration.getMFLogger().v(TAG, "opening connection to: " + url.toString());
        URLConnection connection;
        switch (mfRequester.getProtocol()) {
            case HTTP:
                connection = url.openConnection();
                setConnectionParameters(connection, mfRequester);
                return (HttpURLConnection) connection;
            case HTTPS:
                TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                        }
                };

                // Install trust manager
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                // Create host name verifier
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };

                // Install the all-trusting host verifier
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                connection = url.openConnection();
                setConnectionParameters(connection, mfRequester);
                return (HttpsURLConnection) connection;
            default:
                throw new IllegalStateException("MFHost.TransferProtocol must be HTTP or HTTPS");
        }
    }

    private void setConnectionParameters(URLConnection connection, MFRequester mfRequester) {
        mfConfiguration.getMFLogger().d(TAG, "setConnectionParameters()");
        // if query can be made via POST then set to post
        if (mfRequester.isQueryPostable() || mfRequester.getPayload() != null) {
            connection.setDoOutput(true);
        } else {
        }

        // set timeouts
        connection.setConnectTimeout(connectionTimeout);
        connection.setReadTimeout(readTimeout);

        // set request headers (if any)
        if (mfRequester.getHeaders() != null) {
            for (String key : mfRequester.getHeaders().keySet()) {
                connection.addRequestProperty(key, mfRequester.getHeaders().get(key));
            }
        }
    }

    private URL makeFullUrl(MFRequester mfRequester) throws MalformedURLException, UnsupportedEncodingException {
        mfConfiguration.getMFLogger().d(TAG, "makeFullUrl()");
        StringBuilder stringBuilder = new StringBuilder();
        String baseUrl = makeBaseUrl(mfRequester);
        stringBuilder.append(baseUrl);

        if (!mfRequester.isQueryPostable()) {
            String queryString = makeQueryString(mfRequester.getRequestParameters());
            queryString = makeUrlAttachableQueryString(queryString);
            stringBuilder.append(queryString);
        }

        return new URL(stringBuilder.toString());
    }

    private String makeQueryString(Map<String, String> requestParameters) throws UnsupportedEncodingException {
        return makeQueryString(requestParameters, true);
    }
}
