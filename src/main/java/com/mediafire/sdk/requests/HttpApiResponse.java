package com.mediafire.sdk.requests;

import java.util.List;
import java.util.Map;

public class HttpApiResponse {
    private final int mStatus;
    private final byte[] mBodyBytes;
    private final Map<String, List<String>> mHeaderFields;

    public HttpApiResponse(int status, byte[] bodyBytes, Map<String, List<String>> headerFields) {
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
}
