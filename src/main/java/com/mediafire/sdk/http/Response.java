package com.mediafire.sdk.http;

/**
 * Response is a response containing a byte array and a status code
 */
public class Response {
    private final int status;
    private final byte[] bodyBytes;

    /**
     * Response Constructor
     * @param status int is the status of the response
     * @param bodyBytes byte[] is the content of the response
     */
    public Response(int status, byte[] bodyBytes) {
        this.status = status;
        this.bodyBytes = bodyBytes;
    }

    /**
     * Gets the status of the response
     * @return int status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets the byte array of the response
     * @return byte[] bodyBytes
     */
    public byte[] getBytes() {
        return bodyBytes;
    }
}
