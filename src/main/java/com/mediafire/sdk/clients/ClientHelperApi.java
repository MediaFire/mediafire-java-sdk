package com.mediafire.sdk.clients;

import com.mediafire.sdk.api_responses.ApiResponse;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris on 11/9/2014.
 * ClientHelper used for the majority of calls to the MediaFire API.
 * This BaseClientHelper implementation should not be used for calls to get new session tokens or calls that use
 * action tokens
 */
public class ClientHelperApi extends BaseClientHelper {

    private SessionTokenManagerInterface mSessionTokenManagerInterface;

    public ClientHelperApi(SessionTokenManagerInterface sessionTokenManagerInterface) {
        super();
        mSessionTokenManagerInterface = sessionTokenManagerInterface;
    }

    @Override
    public void borrowToken(Request request) {
        SessionToken sessionToken = mSessionTokenManagerInterface.borrowSessionToken();
        request.addToken(sessionToken);
    }

    @Override
    public void addSignatureToRequestParameters(Request request) {
        String signature = makeSignatureForApiRequest(request);
        request.addSignature(signature);
    }

    @Override
    public void returnToken(Response response, Request request) {
        ApiResponse apiResponse = getResponseObject(response, ApiResponse.class);
        if (apiResponse == null) {
            return;
        }

        if (!apiResponse.hasError() || (apiResponse.getError() != 105 && apiResponse.getError() != 127)) {
            if (apiResponse.needNewKey()) {
                ((SessionToken) request.getToken()).updateSessionToken();
            }
            mSessionTokenManagerInterface.receiveSessionToken(((SessionToken) request.getToken()));
        }
    }
}
