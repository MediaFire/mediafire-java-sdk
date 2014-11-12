package com.mediafire.sdk.api.responses.file;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.LinkedList;
import java.util.List;

public class GetLinksResponse extends ApiResponse {

    private String direct_download_free_bandwidth;
    private String one_time_download_request_count;

    private List<Link> links;

    public int getDirectDownloadFreeBandwidth() {
        if (this.direct_download_free_bandwidth == null) {
            this.direct_download_free_bandwidth = "0";
        }
        return Integer.valueOf(this.direct_download_free_bandwidth);
    }

    public int getOneTimeDownloadRequestCount() {
        if (this.one_time_download_request_count == null) {
            this.one_time_download_request_count = "0";
        }
        return Integer.valueOf(this.one_time_download_request_count);
    }

    public List<Link> getLinks() {
        if (this.links == null) {
            this.links = new LinkedList<Link>();
        }

        return links;
    }

    public class Link {
        private String quickkey;
        private String view;

        private String listen;

        private String edit;
        private String edit_error_message;

        private String streaming;
        private String streaming_error_message;
        private String streaming_error;

        private String direct_download;
        private String direct_download_error_message;
        private String direct_download_error;

        private String one_time_download_error_message;
        private String normal_download;

        private OneTime one_time;

        public class OneTime {
            private String download;
            private String view;

            public String getOneTimeDownloadLink() {
                if (download == null) {
                    download = "";
                }
                return download;
            }

            public String getOneTimeViewLink() {
                if (view == null) {
                    view = "";
                }
                return view;
            }
        }

        public OneTime getOneTimeLinks() {
            if (one_time == null) {
                one_time = new OneTime();
            }
            return one_time;
        }

        public String getListenLink() {
            if (listen == null) {
                listen = "";
            }
            return listen;
        }

        public String getStreaming() {
            if (this.streaming == null) {
                this.streaming = "";
            }
            return this.streaming;
        }

        public int getStreamingError() {
            if (streaming_error == null) {
                streaming_error = "";
            }
            return Integer.valueOf(streaming_error);
        }

        public String getStreamingErrorMessage() {
            if (streaming_error_message == null) {
                streaming_error_message = "";
            }

            return streaming_error_message;
        }

        public String getEditErrorMessage() {
            if (this.edit_error_message == null) {
                this.edit_error_message = "";
            }
            return edit_error_message;
        }

        public String getDirectDownloadErrorMessage() {
            if (this.direct_download_error_message == null) {
                this.direct_download_error_message = "";
            }
            return direct_download_error_message;
        }

        public int getDirectDownloadErrorCode() {
            if (this.direct_download_error == null) {
                this.direct_download_error = "0";
            }
            return Integer.valueOf(direct_download_error);
        }

        public String getOneTimeDownloadErrorMessage() {
            if (this.one_time_download_error_message == null) {
                this.one_time_download_error_message = "";
            }
            return one_time_download_error_message;
        }

        public String getQuickkey() {
            if (this.quickkey == null) {
                this.quickkey = "";
            }
            return this.quickkey;
        }

        public String getViewLink() {
            if (this.view == null) {
                this.view = "";
            }
            return this.view;
        }

        public String getNormalDownloadLink() {
            if (this.normal_download == null) {
                this.normal_download = "";
            }
            return this.normal_download;
        }

        public String getDirectDownloadLink() {
            if (this.direct_download == null) {
                this.direct_download = "";
            }
            return this.direct_download;
        }

        public String getEditLink() {
            if (this.edit == null) {
                this.edit = "";
            }
            return this.edit;
        }
    }
}
