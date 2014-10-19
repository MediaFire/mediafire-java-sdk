package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ActionToken;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public interface ActionTokenManagerInterface {
    public void receiveImageActionToken(ActionToken mfImageActionToken);

    public void receiveUploadActionToken(ActionToken mfUploadActionToken);

    public ActionToken borrowUploadActionToken();

    public ActionToken borrowImageActionToken();
}
