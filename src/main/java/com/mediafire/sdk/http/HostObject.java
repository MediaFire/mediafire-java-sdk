package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/17/2014.
 */
public class HostObject {
    private final String mTransferProtocol;
    private String mHttpMethod;
    private String mSubdomain;
    private String mDomain;

    private HostObject(String subdomain, String domain, String transferProtocol, String httpMethod) {
        mSubdomain = subdomain;
        mDomain = domain;
        mTransferProtocol = transferProtocol;
        mHttpMethod = httpMethod;
    }

    public String getSubDomainAndHostName() {
        return mSubdomain + "." + mDomain;
    }

    public String getFullUrl() {
        return mTransferProtocol + "://" + getSubDomainAndHostName();
    }

    public String getSubdomain() {
        return mSubdomain;
    }

    public String getDomain() {
        return mDomain;
    }

    public String getHttpMethod() {
        return mHttpMethod;
    }

    /**
     * gets the TransferProtocol to use for the request.
     * @return the TransferProtocol to use for the request.
     */
    public String getTransferProtocol() {
        return mTransferProtocol;
    }
}
