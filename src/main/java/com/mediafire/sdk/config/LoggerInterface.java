package com.mediafire.sdk.config;

import com.mediafire.sdk.http.Result;

/**
 * LoggerInterface provides an interface to perform logging through
 */
public interface LoggerInterface {

    /**
     * Verbose logger
     * @param source String source of the log
     * @param message String message of the log
     */
    public void v(String source, String message);

    /**
     * Debug logger
     * @param source String source of the log
     * @param message String message of the log
     */
    public void d(String source, String message);

    /**
     * Info logger
     * @param source String source of the log
     * @param message String message of the log
     */
    public void i(String source, String message);

    /**
     * Warning logger
     * @param source String source of the log
     * @param message String message of the log
     */
    public void w(String source, String message);

    /**
     * Error logger
     * @param source String source of the log
     * @param message String message of the log
     */
    public void e(String source, String message);

    /**
     * Error logger
     * @param source String source of the log
     * @param message String message of the log
     * @param throwable Throwable associated with the error
     */
    public void e(String source, String message, Throwable throwable);

    /**
     * Api Error Logger
     * @param source String source of the log
     * @param result Result associated with the error
     */
    public void logApiError(String source, Result result);

}
