package com.mediafire.sdk.http.requests;

import com.mediafire.sdk.config.MFConfiguration;
import com.mediafire.sdk.http.*;
import com.mediafire.sdk.token.MFTokenFarmCallback;

import java.io.UnsupportedEncodingException;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public abstract class AbstractApiRequest {
    private static final String TAG = AbstractApiRequest.class.getCanonicalName();
    private MFConfiguration mfConfiguration;
    private MFTokenFarmCallback mfTokenFarmCallback;
    private MFRequester mfRequester;

    /**
     * Subclasses of AbstractApiRequest should define methods to create MFRequester objects which will be used when calling doRequest();
     * @param mfConfiguration
     * @param mfTokenFarmCallback
     */
    public AbstractApiRequest(MFConfiguration mfConfiguration, MFTokenFarmCallback mfTokenFarmCallback) {
        this.mfConfiguration = mfConfiguration;
        this.mfTokenFarmCallback = mfTokenFarmCallback;
    }

    protected MFResponse doRequest() {
        MFConfiguration.getStaticMFLogger().d(TAG, "doRequest()");
        if (mfRequester == null) {
            throw new IllegalStateException("Cannot call doRequest() without calling setMFRequester()");
        }

        MFResponse mfResponse;
        try {
            MFHttpClientSetup mfHttpClientSetup = new MFHttpClientSetup(mfTokenFarmCallback, mfConfiguration);
            mfHttpClientSetup.prepareMFRequestForHttpClient(mfRequester);

            MFHttpClient mfHttpClient = new MFHttpClient(mfConfiguration);
            mfResponse = mfHttpClient.sendRequest(mfRequester);

            MFHttpClientCleanup mfHttpClientCleanup = new MFHttpClientCleanup(mfTokenFarmCallback);
            mfHttpClientCleanup.returnToken(mfRequester, mfResponse);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            mfResponse = new MFResponse(-1, null, null, mfRequester);
        } catch (MFHttpException e) {
            e.printStackTrace();
            mfResponse = new MFResponse(-1, null, null, mfRequester);
        }

        return mfResponse;
    }

    protected void setMFRequester(MFRequester mfRequester) {
        this.mfRequester = mfRequester;
    }
}
