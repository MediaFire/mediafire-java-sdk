package com.mediafire.sdk.http;

/**
* Created by Chris Najar on 7/17/2014.
*/
public interface MFHttpCallback {
    public void jobStarted();
    public void jobFinished(MFRequest mfRequest, MFResponse mfResponse);
}