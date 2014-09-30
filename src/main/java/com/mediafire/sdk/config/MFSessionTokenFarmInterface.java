package com.mediafire.sdk.config;

import com.mediafire.sdk.token.MFSessionToken;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public interface MFSessionTokenFarmInterface extends MFAbstractContainerInterface {
    /**
     * called when a session token is being returned.
     * @param mfSessionToken the session token being returned
     */
    public void returnSessionToken(MFSessionToken mfSessionToken);

    /**
     * called when a session token is out of sync and unrecoverable.
     * @param mfSessionToken the session token that failed
     */
    public void sessionTokenSpoiled(MFSessionToken mfSessionToken);

    /**
     * called when a new session token is being returned
     * @param mfSessionToken the session token that was received
     */
    public void receiveNewSessionToken(MFSessionToken mfSessionToken);

    /**
     * called when there is a request to borrow a MFSessionToken
     * @return a MFSessionToken
     */
    public MFSessionToken borrowMFSessionToken();
}
