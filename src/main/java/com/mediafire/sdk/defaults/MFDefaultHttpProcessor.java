package com.mediafire.sdk.defaults;

import com.mediafire.sdk.config.MFConfiguration;
import com.mediafire.sdk.config.MFHttpProcessor;
import com.mediafire.sdk.config.MFTokenFarmInterface;
import com.mediafire.sdk.http.*;

import java.io.UnsupportedEncodingException;

public final class MFDefaultHttpProcessor implements MFHttpProcessor {
    private static final String TAG = MFDefaultHttpProcessor.class.getCanonicalName();
    private final MFHttpClientSetup mfHttpClientSetup;
    private final MFHttpClient mfHttpClient;
    private final MFHttpClientCleanup mfHttpClientCleanup;
    private MFConfiguration mfConfiguration;

    /**
     * Implementation of MFHttpProcessor. This constructor requires an MFConfiguration and MFTokenFarmInterface
     * @param mfConfiguration
     * @param mfTokenFarmCallback
     */
    public MFDefaultHttpProcessor(MFConfiguration mfConfiguration, MFTokenFarmInterface mfTokenFarmCallback) {
        this.mfConfiguration = mfConfiguration;
        this.mfHttpClientSetup = new MFHttpClientSetup(mfConfiguration, mfTokenFarmCallback);
        this.mfHttpClient = new MFHttpClient(mfConfiguration);
        this.mfHttpClientCleanup = new MFHttpClientCleanup(mfConfiguration, mfTokenFarmCallback);
    }

    @Override
    public MFResponse doRequest(final MFRequester mfRequester) {
        mfConfiguration.getMFLogger().d(TAG, "doRequest()");

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
                        mfConfiguration.getMFLogger().e(TAG, e.getMessage(), e);
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
