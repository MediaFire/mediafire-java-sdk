package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.LoggerInterface;
import com.mediafire.sdk.http.Result;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class DefaultLogger implements LoggerInterface {
    @Override
    public void v(String source, String message) {
        System.out.println(source + ", " + message);
    }

    @Override
    public void d(String source, String message) {
        System.out.println(source + ", " + message);
    }

    @Override
    public void i(String source, String message) {
        System.out.println(source + ", " + message);
    }

    @Override
    public void w(String source, String message) {
        System.out.println(source + ", " + message);
    }

    @Override
    public void e(String source, String message) {
        System.out.println(source + ", " + message);
    }

    @Override
    public void e(String source, String message, Throwable throwable) {
        System.out.println(source + ", " + message);
    }

    @Override
    public void logApiError(String source, Result result) {
        System.out.println(source + ", " + result);
    }
}
