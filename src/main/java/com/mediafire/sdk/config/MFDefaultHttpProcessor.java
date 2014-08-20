package com.mediafire.sdk.config;

import com.mediafire.sdk.http.*;
import com.mediafire.sdk.token.MFTokenFarmCallback;

import java.io.UnsupportedEncodingException;

public final class MFDefaultHttpProcessor implements MFHttpProcessor {
    private static final String TAG = MFDefaultHttpProcessor.class.getCanonicalName();
    private final MFHttpClientSetup mfHttpClientSetup;
    private final MFHttpClient mfHttpClient;
    private final MFHttpClientCleanup mfHttpClientCleanup;

    /**
     * Implementation of MFHttpProcessor. This constructor requires an MFConfiguration and MFTokenFarmCallback
     * @param mfConfiguration
     * @param mfTokenFarmCallback
     */
    public MFDefaultHttpProcessor(MFConfiguration mfConfiguration, MFTokenFarmCallback mfTokenFarmCallback) {
        this.mfHttpClientSetup = new MFHttpClientSetup(mfTokenFarmCallback, mfConfiguration);
        this.mfHttpClient = new MFHttpClient(mfConfiguration);
        this.mfHttpClientCleanup = new MFHttpClientCleanup(mfTokenFarmCallback, mfConfiguration);
    }

    @Override
    public MFResponse doRequest(final MFRequester mfRequester) {
        MFConfiguration.getStaticMFLogger().d(TAG, "doRequest()");

        MFResponse mfResponse;
        try {
            mfHttpClientSetup.prepareMFRequestForHttpClient(mfRequester);
            mfResponse = mfHttpClient.sendRequest(mfRequester);
            final MFResponse finalMfResponse = mfResponse;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mfHttpClientCleanup.returnToken(mfRequester, finalMfResponse);
                    } catch (MFHttpException e) {
                        MFConfiguration.getStaticMFLogger().e(TAG, e.getMessage(), e);
                    }
                }
            });
            thread.start();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            mfResponse = new MFResponse(-1, null, null, mfRequester);
        } catch (MFHttpException e) {
            e.printStackTrace();
            mfResponse = new MFResponse(-1, null, null, mfRequester);
        }

        return mfResponse;
    }
}
