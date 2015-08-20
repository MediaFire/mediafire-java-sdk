package com.mediafire.sdk.api.responses;

/**
 * Created by christophernajar on 8/20/15.
 */
public interface MediaFireApiResponse {
    String getAction();

    String getMessage();

    int getError();

    String getResult();

    String getCurrentApiVersion();

    boolean hasError();

    boolean needNewKey();
}
