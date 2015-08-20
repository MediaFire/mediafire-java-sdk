package com.mediafire.sdk;

import java.util.HashMap;
import java.util.Map;

public class MFApiRequest implements MediaFireApiRequest {
    private final String path;
    private final Map<String, Object> queryParameters;
    private final int requestMethod;
    private final int requestType;

    public MFApiRequest(String path, Map<String, Object> queryParameters, int requestMethod, int requestType) {
        this.path = path == null ? "/api/system/get_info.php" : path;
        this.queryParameters = queryParameters == null ? new HashMap<String, Object>() : queryParameters;
        this.requestMethod = requestMethod;
        this.requestType = requestType;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Map<String, Object> getQueryParameters() {
        return queryParameters;
    }

    @Override
    public byte[] getPayload() {
        return new byte[0];
    }

    @Override
    public int getRequestMethod() {
        return requestMethod;
    }

    @Override
    public int getRequestType() {
        return requestType;
    }

    @Override
    public String toString() {
        return "MFApiRequest{" +
                "path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                ", requestMethod=" + requestMethod +
                ", requestType=" + requestType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MFApiRequest that = (MFApiRequest) o;

        if (getRequestMethod() != that.getRequestMethod()) return false;
        if (getRequestType() != that.getRequestType()) return false;
        if (!getPath().equals(that.getPath())) return false;
        return getQueryParameters().equals(that.getQueryParameters());

    }

    @Override
    public int hashCode() {
        int result = getPath().hashCode();
        result = 31 * result + getQueryParameters().hashCode();
        result = 31 * result + getRequestMethod();
        result = 31 * result + getRequestType();
        return result;
    }
}
