package com.mediafire.sdk.http;

public enum MFHost {
    LIVE_HTTP(Host.LIVE, TransferProtocol.HTTP),
    LIVE_HTTPS(Host.LIVE, TransferProtocol.HTTPS),
    DEV_HTTP(Host.DEV, TransferProtocol.HTTP),
    DEV_HTTPS(Host.DEV, TransferProtocol.HTTPS);

    private final Host host;
    private final TransferProtocol transferProtocol;

    private MFHost(Host host, TransferProtocol transferProtocol) {
        this.host = host;
        this.transferProtocol = transferProtocol;
    }

    /**
     * gets the Host to use for the request.
     * @return the Host.
     */
    public Host getHost() {
        return host;
    }

    /**
     * gets the TransferProtocol to use for the request.
     * @return the TransferProtocol to use for the request.
     */
    public TransferProtocol getTransferProtocol() {
        return transferProtocol;
    }

    public enum TransferProtocol {
        HTTP("http://"),
        HTTPS("https://");

        private final String scheme;

        private TransferProtocol(String scheme) {
            this.scheme = scheme;
        }

        /**
         * gets the scheme for a TransferProtocol which should be used for a request.
         * @return String value representing the TransferProtocol to use.
         */
        public String getScheme() {
            return scheme;
        }
    }

    public enum Host {
        LIVE("www.mediafire.com"),
        DEV("dev.mediafire.com");

        private final String host;

        private Host(String host) {
            this.host = host;
        }

        /**
         * gets the subdomain and the host name for a request.
         * @return a String value containing the subdomain and host to be used for a request.
         */
        public String getSubDomainAndHostName() {
            return host;
        }
    }
}
