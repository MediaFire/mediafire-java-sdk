package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.UploadActionToken;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public interface ActionTokenManagerInterface {
    public void receiveImageActionToken(ImageActionToken token);

    public void receiveUploadActionToken(UploadActionToken token);

    public UploadActionToken borrowUploadActionToken();

    public ImageActionToken borrowImageActionToken();

    public void tokensFailed();
}
