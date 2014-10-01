package com.mediafire.sdk.http_beta;

/**
 * pojo - stored basic information regarding an api call.
 * example:
 * create an MFApi object used to make api call to http://www.mediafire.com/api/1.0/file/get_info.php
 * new MFApi(http, www.mediafire.com, 1.0, file/get_info.php)
 */
public class MFApi implements MFApiInterface {
    private final String protocol;
    private final String domain;
    private final String version;
    private final String uri;

    public MFApi(String protocol, String domain, String version, String uri) {
        this.protocol = protocol;
        this.domain = domain;
        this.version = version;
        this.uri = uri;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getUri() {
        return uri;
    }
}
