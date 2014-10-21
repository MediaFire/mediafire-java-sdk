package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.LoggerInterface;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class DefaultLogger implements LoggerInterface {
    private static DefaultLogger instance;

    public static DefaultLogger log() {
        if (instance == null) {
            instance = new DefaultLogger();
        }
        return instance;
    }

    @Override
    public void v(String source, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    @Override
    public void d(String source, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    @Override
    public void i(String source, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    @Override
    public void w(String source, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    @Override
    public void e(String source, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    @Override
    public void e(String source, String message, Throwable throwable) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + message + "]");
    }

    @Override
    public void logApiError(String source, Result result) {
        System.out.println("[" + Thread.currentThread().getName() + "] [" + source + "] [" + result + "]");
    }
}
