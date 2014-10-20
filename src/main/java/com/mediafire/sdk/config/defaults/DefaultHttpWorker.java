package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public final class DefaultHttpWorker implements HttpWorkerInterface {
    private final int CONNECTION_TIMEOUT_MILLISECONDS = 5000;
    private final int READ_TIMEOUT_MILLISECONDS = 45000;

    @Override
    public Response doGet(Request request, String url) {
        return null;
    }

    @Override
    public Response doPost(Request request, String url, Map<String, String> headers, byte[] payload) {
        return null;
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
