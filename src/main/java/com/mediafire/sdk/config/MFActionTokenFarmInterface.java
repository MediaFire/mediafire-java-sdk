package com.mediafire.sdk.config;

import com.mediafire.sdk.token.MFActionToken;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public interface MFActionTokenFarmInterface extends MFAbstractContainerInterface {
    /**
     * called when a new image action token is being returned.
     * @param mfImageActionToken - the new image action token received
     */
    public void receiveNewImageActionToken(MFActionToken mfImageActionToken);

    /**
     * called when a new upload action token is being returned.
     * @param mfUploadActionToken - the new upload action token received
     */
    public void receiveNewUploadActionToken(MFActionToken mfUploadActionToken);

    /**
     * called when there is a request to borrow a MFActionToken
     * @return a MFActionToken
     */
    public MFActionToken borrowMFUploadActionToken();

    /**
     * called when there is a requests to borrow a MFActionToken
     * @return a MFActionToken
     */
    public MFActionToken borrowMFImageActionToken();

    /**
     * called when an action token used causes an error 105 ("The supplied Session Token is expired or invalid")
     * this should trigger getting a new action token.
     */
    public void actionTokenSpoiled();
}
