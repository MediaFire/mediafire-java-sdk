package com.mediafire.sdk.clients;

import com.mediafire.sdk.http.Request;

/**
 * Created by Chris Najar on 10/19/2014.
 * HeadersHelper adds appropriate headers for a Request object
 */
public class HeadersHelper {
    private final String CHARSET = "UTF-8";
    private final Request mRequest;

    /**
     * HeadersHelper Constructor
     * @param request the Request to add the headers to
     */
    public HeadersHelper(Request request) {
        mRequest = request;
    }

    public void addHeaders() {
        mRequest.addHeader("Accept-Charset", CHARSET);

        if (!mRequest.getHttpMethod().equalsIgnoreCase("post")) {
            return;
        }

        byte[] payload = mRequest.getPayload();

        if (mRequest.postQuery()) {
            mRequest.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARSET);
        } else {
            mRequest.addHeader("Content-Type", "application/octet-stream");
        }

        if (mRequest.getPayload() != null) {
            mRequest.addHeader("Content-Length", String.valueOf(payload.length));
        }
    }
}
