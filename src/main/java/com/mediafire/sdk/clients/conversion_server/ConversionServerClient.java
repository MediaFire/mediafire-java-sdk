package com.mediafire.sdk.clients.conversion_server;

import com.mediafire.sdk.http.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jondh on 11/5/14.
 */
public class ConversionServerClient {

    private final int mInputBufferSize;
    private final int mOutputBufferSize;

    public ConversionServerClient(int ioBufferSize) {
        this(ioBufferSize, ioBufferSize);
    }

    public ConversionServerClient(int inputBufferSize, int outputBufferSize) {
        mInputBufferSize = inputBufferSize;
        mOutputBufferSize = outputBufferSize;
    }

    public Response imageConversion(String baseUrl, ImageConversionParameters imageConversionParameters) throws IOException{
        String url = baseUrl + imageConversionParameters.makeQuery();

        return downloadUrlToResponse(url);
    }

    public Response documentConversion(String baseUrl, DocumentConversionParameters documentConversionParameters) throws IOException{
        String url = baseUrl + documentConversionParameters.makeQuery();

        return downloadUrlToResponse(url);
    }

    /**
     * Download a bitmap from a URL and write the content to an output stream.
     * @param urlString The URL to fetch
     * @return status of the connection
     * @throws IOException
     */
    public int downloadUrlToStream(String urlString, OutputStream outputStream) throws IOException{
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), mInputBufferSize);
            out = new BufferedOutputStream(outputStream, mOutputBufferSize);
            int status = urlConnection.getResponseCode();
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return status;
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {}
        }
    }

    /**
     * Download a bitmap from a URL and write the content to a Response Object.
     * @param urlString The URL to fetch
     * @return the response (status and bytes) of the connection
     * @throws IOException
     */
    public Response downloadUrlToResponse(String urlString) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int status = downloadUrlToStream(urlString, outputStream);

        byte[] bytes = outputStream.toByteArray();

        return new Response(status, bytes);
    }

}
