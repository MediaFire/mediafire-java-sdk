package com.mediafire.sdk.http;

public class Response {
    private final int status;
    private final byte[] bodyBytes;

    public Response(int status, byte[] bodyBytes) {
        this.status = status;
        this.bodyBytes = bodyBytes;
    }

    public int getStatus() {
        return status;
    }

    public byte[] getBytes() {
        return bodyBytes;
    }
}
