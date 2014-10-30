package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.UploadActionToken;

/**
 * Created by Chris Najar on 9/16/2014.
 * ActionTokenManagerInterface helps to control the flow of action tokens
 */
public interface ActionTokenManagerInterface extends Initializable {

    /**
     * Should be called whenever a new ImageActionToken is received
     * @param token the new ImageActionToken
     */
    public void receiveImageActionToken(ImageActionToken token);

    /**
     * Should be called whenever a new UploadActionToken is received
     * @param token the new UploadActionToken
     */
    public void receiveUploadActionToken(UploadActionToken token);

    /**
     * Gets an UploadActionToken
     * @return an UploadActionToken
     */
    public UploadActionToken borrowUploadActionToken();

    /**
     * Get an ImageActionToken
     * @return an ImageActionToken
     */
    public ImageActionToken borrowImageActionToken();

    /**
     * Should be called whenever a token fails (is not valid)
     */
    public void tokensFailed();
}
