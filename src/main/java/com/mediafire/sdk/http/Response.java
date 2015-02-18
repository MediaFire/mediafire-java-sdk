package com.mediafire.sdk.http;

import java.util.List;
import java.util.Map;

/**
 * Response is a response containing a byte array and a mStatus code
 */
public class Response {
    private final int mStatus;
    private final byte[] mBodyBytes;
    private Map<String, List<String>> mHeaderFields;

    /**
     * Response Constructor
     * @param status int is the mStatus of the response
     * @param bodyBytes byte[] is the content of the response
     * @param headerFields
     */
    public Response(int status, byte[] bodyBytes, Map<String, List<String>> headerFields) {
        mStatus = status;
        mBodyBytes = bodyBytes;
        mHeaderFields = headerFields;
    }

    /**
     * Gets the mStatus of the response
     * @return int mStatus
     */
    public int getStatus() {
        return mStatus;
    }

    /**
     * Gets the byte array of the response
     * @return byte[] mBodyBytes
     */
    public byte[] getBytes() {
        return mBodyBytes;
    }

    public Map<String, List<String>> getHeaderFields() {
        return mHeaderFields;
    }

    @Override
    public String toString() {
        return "[status:" + mStatus + "]" +
                "[response:" + (mBodyBytes == null ? null : new String(mBodyBytes)) + "]" +
                "[headers:" + mHeaderFields + "]";
    }
}
