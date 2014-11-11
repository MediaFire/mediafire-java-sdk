package com.mediafire.sdk.clients.device;

/**
 * Created by jondh on 11/4/14.
 */
public class GetStatusParameters {
    private String mDeviceId;
    private String mSimpleReport;

    public GetStatusParameters(Builder builder) {
        mDeviceId = builder.mDeviceId;
        mSimpleReport = builder.mSimpleReport;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public String getSimpleReport() {
        return mSimpleReport;
    }

    public static class Builder {
        public String mDeviceId;
        public String mSimpleReport;

        public Builder() { }

        public Builder deviceId(String deviceId) {
            if (deviceId == null) {
                return this;
            }

            mDeviceId = deviceId;
            return this;
        }

        public Builder simpleReport(boolean simpleReport) {
            mSimpleReport = simpleReport ? "yes" : "no";
            return this;
        }

        public GetStatusParameters build() {
            return new GetStatusParameters(this);
        }
    }
}