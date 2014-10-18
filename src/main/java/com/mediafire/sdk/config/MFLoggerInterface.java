package com.mediafire.sdk.config;

import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;

public interface MFLoggerInterface {
    /**
     * verbose log.
     * @param source - source of message.
     * @param message - message from source.
     */
    public void v(String source, String message);

    /**
     * verbose log.
     * @param source - source of message.
     * @param message - message from source.
     * @param throwable - a throwable which triggered the use of this call.
     */
    public void v(String source, String message, Throwable throwable);

    /**
     * debug log.
     * @param source - source of message.
     * @param message - message from source.
     */
    public void d(String source, String message);

    /**
     * debug log.
     * @param source - source of message.
     * @param message - message from source.
     * @param throwable - a throwable which triggered the use of this call.
     */
    public void d(String source, String message, Throwable throwable);

    /**
     * info log.
     * @param source - source of message.
     * @param message - message from source.
     */
    public void i(String source, String message);

    /**
     * info log.
     * @param source - source of message.
     * @param message - message from source.
     * @param throwable - a throwable which triggered the use of this call.
     */
    public void i(String source, String message, Throwable throwable);

    /**
     * warn log.
     * @param source - source of message.
     * @param message - message from source.
     */
    public void w(String source, String message);

    /**
     * warn log.
     * @param source - source of message.
     * @param message - message from source.
     * @param throwable - a throwable which triggered the use of this call.
     */
    public void w(String source, String message, Throwable throwable);

    /**
     * error log.
     * @param source - source of message.
     * @param message - message from source.
     */
    public void e(String source, String message);

    /**
     * error log.
     * @param source - source of message.
     * @param message - message from source.
     * @param throwable - a throwable which triggered the use of this call.
     */
    public void e(String source, String message, Throwable throwable);

    /**
     * api error log.
     * @param source - source of api error.
     * @param requester - the MFRequester used.
     * @param response - the Response received.
     */
    public void logApiError(String source, Request requester, Response response);

}
