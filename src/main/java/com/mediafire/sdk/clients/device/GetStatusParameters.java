package com.mediafire.sdk.clients.device;

/**
 * Created by jondh on 11/4/14.
 */
public class GetStatusParameters {
    public String mDeviceId;
    public String mSimpleReport;

    public GetStatusParameters deviceId(String deviceId){
        if(deviceId == null) {
            return this;
        }

        mDeviceId = deviceId;
        return this;
    }

    public GetStatusParameters simpleReport(boolean simpleReport){
        mSimpleReport = simpleReport ? "yes" : "no";
        return this;
    }
}
