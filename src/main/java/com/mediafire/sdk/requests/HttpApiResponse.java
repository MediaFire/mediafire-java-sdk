package com.mediafire.sdk.requests;

import java.util.Arrays;
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

    @Override
    public String toString() {
        return "HttpApiResponse{" +
                "mStatus=" + mStatus +
                ", mBodyBytes=" + Arrays.toString(mBodyBytes) +
                ", mHeaderFields=" + mHeaderFields +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpApiResponse that = (HttpApiResponse) o;

        if (mStatus != that.mStatus) return false;
        if (!Arrays.equals(mBodyBytes, that.mBodyBytes)) return false;
        if (mHeaderFields != null ? !mHeaderFields.equals(that.mHeaderFields) : that.mHeaderFields != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mStatus;
        result = 31 * result + (mBodyBytes != null ? Arrays.hashCode(mBodyBytes) : 0);
        result = 31 * result + (mHeaderFields != null ? mHeaderFields.hashCode() : 0);
        return result;
    }
}
