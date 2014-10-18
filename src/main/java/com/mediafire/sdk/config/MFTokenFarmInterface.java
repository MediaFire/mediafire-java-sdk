package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.SessionToken;

public interface MFTokenFarmInterface extends MFSessionTokenFarmInterface, ActionTokenManagerInterface {
    /**
     * called when a session token is being returned.
     * @param sessionToken the session token being returned
     */
    public void returnSessionToken(SessionToken sessionToken);

    /**
     * called when a session token is out of sync and unrecoverable.
     * @param sessionToken the session token which failed
     */
    public void sessionTokenSpoiled(SessionToken sessionToken);

    /**
     * called when a new session token is being returned
     * @param sessionToken the new session token received
     */
    public void receiveNewSessionToken(SessionToken sessionToken);

    /**
     * called when a new image action token is being returned.
     * @param mfImageActionToken the new image action token received
     */
    public void receiveNewImageActionToken(ActionToken mfImageActionToken);

    /**
     * called when a new upload action token is being returned.
     * @param mfUploadActionToken the new upload action token received
     */
    public void receiveNewUploadActionToken(ActionToken mfUploadActionToken);

    /**
     * called when there is a request to borrow a SessionToken
     * @return a SessionToken
     */
    public SessionToken borrowMFSessionToken();

    /**
     * called when there is a request to borrow a ActionToken
     * @return a ActionToken
     */
    public ActionToken borrowMFUploadActionToken();

    /**
     * called when there is a requests to borrow a ActionToken
     * @return a ActionToken
     */
    public ActionToken borrowMFImageActionToken();

    /**
     * called when an action token used causes an error 105 ("The supplied Session Token is expired or invalid")
     * this should trigger getting a new action token.
     */
    public void actionTokenSpoiled();
}
