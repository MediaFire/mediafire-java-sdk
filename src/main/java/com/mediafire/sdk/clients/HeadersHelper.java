package com.mediafire.sdk.clients;

import com.mediafire.sdk.http.Request;

/**
 * Created by Chris Najar on 10/19/2014.
 * HeadersHelper adds appropriate headers for a Request object
 */
public class HeadersHelper {
    private static final String TAG = HeadersHelper.class.getCanonicalName();
    private final Request mRequest;
    private final String CHARSET = "UTF-8";

    /**
     * HeadersHelper Constructor
     * @param request the Request to add the headers to
     */
    public HeadersHelper(Request request) {
        mRequest = request;
    }

    /**
     * Adds headers for a get http request
     */
    public void addGetHeaders() {
        System.out.printf("%s - %s", TAG, "addGetHeaders");
        mRequest.addHeader("Accept-Charset", CHARSET);
    }

    /**
     * Adds headers for a post http request based on the the requests' query is to be posted
     * @param payload the payload to be sent with the request
     */
    public void addPostHeaders(byte[] payload) {
        System.out.printf("%s - %s", TAG, "addPostHeaders");
        mRequest.addHeader("Accept-Charset", CHARSET);

        if (payload == null) {
            return;
        }

        if (mRequest.postQuery()) {
            mRequest.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARSET);
        } else {
            mRequest.addHeader("Content-Type", "application/octet-stream");
        }

        mRequest.addHeader("Content-Length", String.valueOf(payload.length));
    }
}
