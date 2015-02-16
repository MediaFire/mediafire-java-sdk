package com.mediafire.sdk.config;

import com.mediafire.sdk.token.Token;

/**
 * Created by Chris on 12/17/2014.
 */
public interface TokenManager {
    public <T extends Token> T take(Class<T> token);
    public <T extends Token> void give(T token);
    public void destroyUploadToken();
    public void destroyImageToken();
}
