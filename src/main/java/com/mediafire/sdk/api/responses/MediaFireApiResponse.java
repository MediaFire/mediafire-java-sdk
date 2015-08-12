package com.mediafire.sdk.api.responses;

public interface MediaFireApiResponse {
    String getAction();

    String getMessage();

    int getError();

    String getResult();

    String getCurrentApiVersion();

    boolean hasError();

    boolean needNewKey();
}
