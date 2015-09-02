package com.mediafire.sdk;

import java.util.Arrays;
import java.util.Map;

public class MFApiRequest implements MediaFireApiRequest {
    private final String path;
    private final Map<String, Object> queryParameters;
    private final String version;
    private final byte[] payload;
    private final Map<String, Object> headers;

    public MFApiRequest(String path, Map<String, Object> queryParameters, String version, byte[] payload, Map<String, Object> headers) {
        this.version = version;
        this.payload = payload;
        this.path = path;
        this.queryParameters = queryParameters;
        this.headers = headers;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public Map<String, Object> getQueryParameters() {
        return queryParameters;
    }

    @Override
    public Map<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public byte[] getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "MFApiRequest{" +
                "path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                ", version='" + version + '\'' +
                ", payload=" + Arrays.toString(payload) +
                ", headers=" + headers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MFApiRequest that = (MFApiRequest) o;

        if (getPath() != null ? !getPath().equals(that.getPath()) : that.getPath() != null) return false;
        if (getQueryParameters() != null ? !getQueryParameters().equals(that.getQueryParameters()) : that.getQueryParameters() != null)
            return false;
        if (getVersion() != null ? !getVersion().equals(that.getVersion()) : that.getVersion() != null) return false;
        if (!Arrays.equals(getPayload(), that.getPayload())) return false;
        return !(getHeaders() != null ? !getHeaders().equals(that.getHeaders()) : that.getHeaders() != null);

    }

    @Override
    public int hashCode() {
        int result = getPath() != null ? getPath().hashCode() : 0;
        result = 31 * result + (getQueryParameters() != null ? getQueryParameters().hashCode() : 0);
        result = 31 * result + (getVersion() != null ? getVersion().hashCode() : 0);
        result = 31 * result + (getPayload() != null ? Arrays.hashCode(getPayload()) : 0);
        result = 31 * result + (getHeaders() != null ? getHeaders().hashCode() : 0);
        return result;
    }
}
