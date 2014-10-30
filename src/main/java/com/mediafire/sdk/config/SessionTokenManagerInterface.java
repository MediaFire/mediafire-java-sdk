package com.mediafire.sdk.config;

import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris Najar on 9/16/2014.
 * SessionTokenManagerInterface helps to control the flow of SessionTokens
 */
public interface SessionTokenManagerInterface extends Initializable {

    /**
     * Should be called whenever a new SessionToken is received
     * @param token the new SessionToken
     */
    public void receiveSessionToken(SessionToken token);

    /**
     * Gets an SessionToken
     * @return an SessionToken
     */
    public SessionToken borrowSessionToken();

    /**
     * Should be called when the process of getting a new SessionToken fails
     * @param response the Response associated with the failure
     */
    public void getNewSessionTokenFailed(Response response);
}
