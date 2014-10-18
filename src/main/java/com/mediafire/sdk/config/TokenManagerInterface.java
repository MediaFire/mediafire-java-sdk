package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.SessionToken;

public interface TokenManagerInterface extends SessionTokenManagerInterface, ActionTokenManagerInterface {

    public void returnSessionToken(SessionToken sessionToken);

    public void sessionTokenSpoiled(SessionToken sessionToken);

    public void receiveNewSessionToken(SessionToken sessionToken);

    public void receiveNewImageActionToken(ActionToken mfImageActionToken);

    public void receiveNewUploadActionToken(ActionToken mfUploadActionToken);

    public SessionToken borrowMFSessionToken();

    public ActionToken borrowMFUploadActionToken();

    public ActionToken borrowMFImageActionToken();

    public void actionTokenSpoiled();
}
