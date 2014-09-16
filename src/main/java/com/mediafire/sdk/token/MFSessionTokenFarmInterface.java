package com.mediafire.sdk.token;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public interface MFSessionTokenFarmInterface extends MFTokenFarmInterface {
    /**
     * called when a session token is being returned.
     * @param mfSessionToken
     */
    public void returnSessionToken(MFSessionToken mfSessionToken);

    /**
     * called when a session token is out of sync and unrecoverable.
     * @param mfSessionToken
     */
    public void sessionTokenSpoiled(MFSessionToken mfSessionToken);

    /**
     * called when a new session token is being returned
     * @param mfSessionToken
     */
    public void receiveNewSessionToken(MFSessionToken mfSessionToken);

    /**
     * called when there is a request to borrow a MFSessionToken
     * @return a MFSessionToken
     */
    public MFSessionToken borrowMFSessionToken();
}
