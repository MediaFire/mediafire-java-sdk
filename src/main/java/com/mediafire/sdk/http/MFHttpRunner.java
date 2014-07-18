package com.mediafire.sdk.http;

import com.mediafire.sdk.config.MFConfiguration;
import com.mediafire.sdk.token.MFTokenFarmCallback;

import java.io.UnsupportedEncodingException;

public final class MFHttpRunner {
    private static final String TAG = MFHttpRunner.class.getCanonicalName();
    private final MFHttpClientSetup mfHttpClientSetup;
    private final MFHttpClient mfHttpClient;
    private final MFHttpClientCleanup mfHttpClientCleanup;

    public MFHttpRunner(MFConfiguration mfConfiguration, MFTokenFarmCallback mfTokenFarmCallback) {
        this.mfHttpClientSetup = new MFHttpClientSetup(mfTokenFarmCallback, mfConfiguration);
        this.mfHttpClient = new MFHttpClient(mfConfiguration);
        this.mfHttpClientCleanup = new MFHttpClientCleanup(mfTokenFarmCallback, mfConfiguration);
    }

    public MFResponse doRequest(MFRequest mfRequest) {
        MFConfiguration.getStaticMFLogger().v(TAG, "doRequest()");

        MFResponse mfResponse = null;
        try {
            mfHttpClientSetup.prepareMFRequestForHttpClient(mfRequest);
            mfResponse = mfHttpClient.sendRequest(mfRequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MFConfiguration.getStaticMFLogger().logApiError(TAG, mfRequest, mfResponse);

        mfHttpClientCleanup.returnToken(mfRequest, mfResponse);

        return mfResponse;
    }
}
