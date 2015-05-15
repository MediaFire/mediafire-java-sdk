package com.mediafire.sdk.util;

import com.mediafire.sdk.MFException;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.requests.HttpApiResponse;

/**
 * Created by Chris on 5/15/2015.
 */
public class ResponseUtil {
    public static void validateHttpResponse(HttpApiResponse httpResponse) throws MFException {
        if (httpResponse.getBytes() == null || httpResponse.getBytes().length == 0) {
            throw new MFException("Server gave back a null response");
        }

        if (httpResponse.getHeaderFields() == null || httpResponse.getHeaderFields().isEmpty()) {
            throw new MFException("Server gave back null response headers");
        }

        if (httpResponse.getStatus() == 0) {
            throw new MFException("Server gave back no response status");
        }
    }

    public static <T extends ApiResponse> T makeApiResponseFromHttpResponse(HttpApiResponse httpResponse, Class<T> classOfT) {
        return null;
    }
}
