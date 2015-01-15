package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.http.Response;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris Najar on 10/19/2014.
 * DefaultHttpWorker is a default implementation of the HttpWorkerInterface
 * A custom implementation is recommended
 */
public class HttpWorker implements HttpHandler {
    private static final String TAG = HttpWorker.class.getCanonicalName();
    private static final int CONNECTION_TIMEOUT_MILLISECONDS = 5000;
    private static final int READ_TIMEOUT_MILLISECONDS = 45000;
    private boolean mDebug = false;

    public void debug(boolean debug) {
        mDebug = debug;
    }
    
    private void printMessage(String message) {
        if (mDebug) {
            System.out.println(TAG + " - " + message);
        }
    }
    
    /**
     * Performs a http get request
     * @param url the url to open the connection to
     * @param headers the headers to add to the connection
     * @return a Response containing the response from the connection
     */
    @Override
    public Response doGet(String url, Map<String, Object> headers) {
        printMessage("doGet - " + url);
        HttpURLConnection connection;
        InputStream inputStream;
        try {
            connection = getURLConnection(url);
            setTimeouts(connection);
            addRequestHeadersToConnection(connection, headers);
            int responseCode = connection.getResponseCode();

            if (responseCode / 100 != 2 ) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }
            byte[] response = readStream(inputStream);

            Map<String, List<String>> headerFields = connection.getHeaderFields();
            inputStream.close();
            connection.disconnect();
            return new Response(responseCode, response, headerFields);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("IOException while trying to do GET on url '" + url + "'", e);
        } finally {
            //noinspection UnusedAssignment,AssignmentToNull
            connection = null;
            //noinspection UnusedAssignment
            inputStream = null;
        }
    }

    /**
     * Performs a http post request
     * @param url the url to open the connection to
     * @param headers the headers to add to the connection
     * @param payload a payload to send to the connection
     * @return a Response containing the response from the connection
     */
    @Override
    public Response doPost(String url, Map<String, Object> headers, byte[] payload) {
        printMessage("doPost - " + url);
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
            Map<String, List<String>> headerFields = connection.getHeaderFields();
            return new Response(responseCode, response, headerFields);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseApiClientError("IOException while trying to do POST on url '" + url + "'", e);
        }
    }

    private HttpURLConnection getURLConnection(String url) throws IOException {
        String urlScheme = url.substring(0, 5);

        if ("http:".equals(urlScheme)) {
            printMessage("getURLConnection - HttpUrlConnection");
            return (HttpURLConnection) new URL(url).openConnection();
        }

        if ("https".equals(urlScheme)) {
            printMessage("getURLConnection - HttpsUrlConnection");
            return (HttpsURLConnection) new URL(url).openConnection();
        }

        return null;
    }

    private void addRequestHeadersToConnection(URLConnection connection, Map<String, Object> headers) {
        printMessage("addRequestHeadersToConnection - " + headers.size());
        for (String key : headers.keySet()) {
            if (headers.get(key) != null) {
                connection.addRequestProperty(key, headers.get(key).toString());
            }
        }
        printMessage("addRequestHeadersToConnection - added request properties:" + connection.getRequestProperties());
    }

    private void setTimeouts(URLConnection connection) {
        printMessage("setTimeouts - conn/read = " + CONNECTION_TIMEOUT_MILLISECONDS + "/" + READ_TIMEOUT_MILLISECONDS);
        connection.setConnectTimeout(CONNECTION_TIMEOUT_MILLISECONDS);
        connection.setReadTimeout(READ_TIMEOUT_MILLISECONDS);
    }

    private void postData(URLConnection connection, byte[] payload) throws IOException {
        if (payload == null) {
            printMessage("postData - byte array empty, not posting anything");
            return;
        } else {
            printMessage("postData - posting " + payload.length + " bytes");
        }
        printMessage("postData - request properties: " + connection.getRequestProperties());

        String postDataAsString = new String(payload, "UTF-8");
        printMessage("postData - payload: " + postDataAsString);
        printMessage("postData - writing payload");
        connection.getOutputStream().write(payload);
        printMessage("postData - finished writing payload");
    }

    private byte[] readStream(InputStream inputStream) throws IOException {
        printMessage("readStream");
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

    /**
     * Created by Chris Najar on 10/19/2014.
     * ResponseApiClientError is an error class for the Response Object
     */
    public static class ResponseApiClientError extends Response {

        private final String mMessage;
        private final Throwable mThrowable;

        /**
         * ResponseApiClientError Constructor
         * @param message String message for the error
         * @param throwable Throwable throwable for the error
         */
        public ResponseApiClientError(String message, Throwable throwable) {
            super(-1, null, null);
            mMessage = message;
            mThrowable = throwable;
        }

        /**
         * ResponseApiClientError Constructor
         * @param message String message for the error
         */
        public ResponseApiClientError(String message) {
            this(message, null);
        }

        /**
         * Gets the error message
         * @return String message
         */
        public String getErrorMessage() {
            return mMessage;
        }

        /**
         * Gets the throwable
         * @return Throwable throwable
         */
        public Throwable getThrowable() {
            return mThrowable;
        }
    }
}
