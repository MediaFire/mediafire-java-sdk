package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.SessionToken;

public interface TokenManagerInterface extends SessionTokenManagerInterface, ActionTokenManagerInterface {

    public void receiveSessionToken(SessionToken sessionToken);

    public void sessionTokenSpoiled(SessionToken sessionToken);

    public void receiveNewSessionToken(SessionToken token);

    public void receiveImageActionToken(ActionToken mfImageActionToken);

    public void receiveUploadActionToken(ActionToken mfUploadActionToken);

    public SessionToken borrowSessionToken();

    public ActionToken borrowUploadActionToken();

    public ActionToken borrowImageActionToken();

    public void actionTokenSpoiled();
}
