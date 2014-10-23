package com.mediafire.sdk.config;

import com.mediafire.sdk.http.Result;

public interface LoggerInterface {

    public void v(String source, String message);

    public void d(String source, String message);

    public void i(String source, String message);

    public void w(String source, String message);

    public void e(String source, String message);

    public void e(String source, String message, Throwable throwable);

    public void logApiError(String source, Result result);

}
