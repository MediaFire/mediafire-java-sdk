package com.mediafire.sdk.http;

/**
 * Created by Chris Najar on 10/17/2014.
 * HostObject is a class to contain the different parts of a host
 */
public class HostObject {
    private final String mTransferProtocol;
    private final String mHttpMethod;
    private final String mSubdomain;
    private final String mDomain;

    /**
     * HostObject Constructor
     * @param transferProtocol String transfer protocol of the host
     * @param subdomain String subdomain of the host
     * @param domain String domain of the host
     * @param httpMethod String http to perform for a request
     */
    public HostObject(String transferProtocol, String subdomain, String domain, String httpMethod) {
        mTransferProtocol = transferProtocol;
        mSubdomain = subdomain;
        mDomain = domain;
        mHttpMethod = httpMethod;
    }

    /**
     * Gets the subdomain
     * @return String subdomain
     */
    public String getSubdomain() {
        return mSubdomain;
    }

    /**
     * Gets the domain
     * @return String domain
     */
    public String getDomain() {
        return mDomain;
    }

    /**
     * Gets the http method
     * @return String httpMethod
     */
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
