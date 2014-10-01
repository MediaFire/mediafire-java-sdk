package com.mediafire.sdk.http_beta;

/**
 * Created by Chris Najar on 10/1/2014.
 */
public interface MFApiInterface {
    /**
     * gets the protocol to use for a request.
     * @return - the protocol passed to this object.
     */
    public String getProtocol();

    /**
     * gets the domain to use for a request.
     * @return  the domain passed to this object.
     */
    public String getDomain();

    /**
     * gets the version to use for a request.
     * @return the version passed to this object.
     */
    public String getVersion();

    /**
     * gets the uri for this enum that should be used for a request.
     * @return the uri passed to this object.
     */
    public String getUri();
}
