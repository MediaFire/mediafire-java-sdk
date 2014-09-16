package com.mediafire.sdk.http.requests;

import com.mediafire.sdk.config.MFConfiguration;
import com.mediafire.sdk.http.MFApi;
import com.mediafire.sdk.http.MFHost;
import com.mediafire.sdk.http.MFRequest;
import com.mediafire.sdk.token.MFTokenFarmCallback;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public class SystemApi extends AbstractApiRequest {

    /**
     * Subclasses of AbstractApiRequest should define methods to create MFRequester objects which will be used when calling doRequest();
     *
     * @param mfConfiguration
     * @param mfTokenFarmCallback
     */
    public SystemApi(MFConfiguration mfConfiguration, MFTokenFarmCallback mfTokenFarmCallback) {
        super(mfConfiguration, mfTokenFarmCallback);
    }

    public void getInfo() {
        MFRequest mfRequest = new MFRequest(MFHost.LIVE_HTTP, MFApi.SYSTEM_GET_INFO);
        setMFRequester(mfRequest);
    }
}
