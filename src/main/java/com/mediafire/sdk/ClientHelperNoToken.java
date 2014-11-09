package com.mediafire.sdk;

import com.mediafire.sdk.clients.BaseClientHelper;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;

/**
 * Created by Chris on 11/9/2014.
 */
public class ClientHelperNoToken extends BaseClientHelper {
    @Override
    public void borrowToken(Request request) { }

    @Override
    public void addSignatureToRequestParameters(Request request) { }

    @Override
    public void returnToken(Response response, Request request) { }
}
