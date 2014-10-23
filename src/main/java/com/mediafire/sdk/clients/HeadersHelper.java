package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.defaults.DefaultLogger;
import com.mediafire.sdk.http.Request;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class HeadersHelper {
    private static final String TAG = HeadersHelper.class.getCanonicalName();
    private final Request mRequest;
    private final String CHARSET = "UTF-8";

    public HeadersHelper(Request request) {
        mRequest = request;
    }

    public void addGetHeaders() {
        DefaultLogger.log().v(TAG, "addGetHeaders");
        mRequest.addHeader("Accept-Charset", CHARSET);
    }

    public void addPostHeaders(byte[] payload) {
        DefaultLogger.log().v(TAG, "addPostHeaders");
        mRequest.addHeader("Accept-Charset", CHARSET);

        if (payload == null) {
            return;
        }

        if (mRequest.getInstructionsObject().postQuery()) {
            mRequest.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + CHARSET);
        } else {
            mRequest.addHeader("Content-Type", "application/octet-stream");
        }

        mRequest.addHeader("Content-Length", String.valueOf(payload.length));
    }
}
