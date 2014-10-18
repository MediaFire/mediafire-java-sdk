package com.mediafire.sdk.config;

import com.mediafire.sdk.token.SessionToken;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public interface MFSessionTokenFarmInterface extends MFAbstractContainerInterface {
    /**
     * called when a session token is being returned.
     * @param sessionToken the session token being returned
     */
    public void returnSessionToken(SessionToken sessionToken);

    /**
     * called when a session token is out of sync and unrecoverable.
     * @param sessionToken the session token that failed
     */
    public void sessionTokenSpoiled(SessionToken sessionToken);

    /**
     * called when a new session token is being returned
     * @param sessionToken the session token that was received
     */
    public void receiveNewSessionToken(SessionToken sessionToken);

    /**
     * called when there is a request to borrow a SessionToken
     * @return a SessionToken
     */
    public SessionToken borrowMFSessionToken();
}
