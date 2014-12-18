package com.mediafire.sdk.config_impl;

import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.token.Token;

/**
 * Created by Jonathan Harrison on 10/21/2014.
 * DefaultSessionTokenManager is a default implementation of the SessionTokenManagerInterface
 * A custom implementation is recommended
 */
public class TokenManagerImpl implements ITokenManager {
    /**
     * DefaultSessionTokenManager Constructor
     */
    public TokenManagerImpl(){
    }

    @Override
    public <T extends Token> T take(Class<T> token) {
        return null;
    }

    @Override
    public <T extends Token> void give(T token) {

    }

    @Override
    public void tokensBad() {

    }
}
