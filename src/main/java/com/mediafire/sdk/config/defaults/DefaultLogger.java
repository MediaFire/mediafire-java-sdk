package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.LoggerInterface;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris Najar on 10/19/2014.
 * DefaultLogger is a default implementation of the LoggerInterface
 * A custom implementation is recommended
 */
public class DefaultLogger implements LoggerInterface {
    private static DefaultLogger instance;

    /**
     * A "singleton constructor" to get an instance of this class
     * @return the DefaultLogger instance
     */
    public static DefaultLogger log() {
        if (instance == null) {
            instance = new DefaultLogger();
        }
        return instance;
    }

    /**
     * Verbose Log
     * @param source The source of the log
     * @param message The message of the log
     */
    @Override
    public void v(String source, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    /**
     * Debug Log
     * @param source The source of the log
     * @param message The message of the log
     */
    @Override
    public void d(String source, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    /**
     * Info Log
     * @param source The source of the log
     * @param message The message of the log
     */
    @Override
    public void i(String source, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    /**
     * Warning Log
     * @param source The source of the log
     * @param message The message of the log
     */
    @Override
    public void w(String source, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    /**
     * Error Log
     * @param source The source of the log
     * @param message The message of the log
     */
    @Override
    public void e(String source, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    /**
     * Error Log
     * @param source The source of the log
     * @param message The message of the log
     * @param throwable A Throwable that is not used in this implementation
     */
    @Override
    public void e(String source, String message, Throwable throwable) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    /**
     * Api Error Log
     * @param source The source of the log
     * @param result The api result
     */
    @Override
    public void logApiError(String source, Result result) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + result + "]");
    }
}
