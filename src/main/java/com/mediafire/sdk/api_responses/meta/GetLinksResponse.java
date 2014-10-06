package com.mediafire.sdk.api_responses.meta;

import com.mediafire.sdk.api_responses.ApiResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Chris Najar on 10/2/2014.
 */
public class GetLinksResponse extends ApiResponse {
    private MetaLinks links;

    public MetaLinks getMetaLinks() {
        if (links == null) {
            links = new MetaLinks();
        }
        return links;
    }

    public class MetaLinks {
        public List<Links> link;

        public List<Links> getLinks() {
            if (link == null) {
                link = new LinkedList<Links>();
            }
            return link;
        }

        public class Links {
            private String quickkey;
            private String view;
            private String normal_download;
            private String direct_download;
            private String streaming;
            private String streaming_error_message;
            private String streaming_error;

            public String getQuickkey() {
                return quickkey;
            }

            public String getViewLink() {
                return view;
            }

            public String getNormalDownloadLink() {
                return normal_download;
            }

            public String getDirectDownloadLink() {
                return direct_download;
            }

            public String getStreamingLink() {
                return streaming;
            }

            public String getStreamingErrorMessage() {
                return streaming_error_message;
            }

            public int getStreamingError() {
                if (streaming_error == null) {
                    streaming_error = "0";
                }

                return Integer.parseInt(streaming_error);
            }
        }
    }
}
