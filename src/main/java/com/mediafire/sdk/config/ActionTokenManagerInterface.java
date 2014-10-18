package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ActionToken;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public interface ActionTokenManagerInterface {
    public void receiveNewImageActionToken(ActionToken mfImageActionToken);

    public void receiveNewUploadActionToken(ActionToken mfUploadActionToken);

    public ActionToken borrowMFUploadActionToken();

    public ActionToken borrowMFImageActionToken();

    public void actionTokenSpoiled();
}
