package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.token.SessionToken;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DefaultSessionTokenManagerTest {

    Configuration mConfiguration;

    @Before
    public void setUp() throws Exception {
        mConfiguration = new Configuration.Builder().build();
        mConfiguration.init();

        Map<String, String> devCredentials = new LinkedHashMap<String, String>();
        devCredentials.put("application_id", "18");
        mConfiguration.getDeveloperCredentials().setCredentials(devCredentials);

        Map<String, String> userCredentials = new LinkedHashMap<String, String>();
        userCredentials.put("email", "javasdktest@example.com");
        userCredentials.put("password", "74107410");
        mConfiguration.getUserCredentials().setCredentials(userCredentials);
    }

    @Test
    public void testBorrowSessionTokenNotNull() throws Exception {
        SessionTokenManagerInterface sessionTokenManager = mConfiguration.getSessionTokenManager();

        SessionToken sessionToken = sessionTokenManager.borrowSessionToken();

        assertNotNull(sessionToken);
    }

    @Test
    public void testBorrowSessionTokenNotNullToken() throws Exception {
        SessionTokenManagerInterface sessionTokenManager = mConfiguration.getSessionTokenManager();

        SessionToken sessionToken = sessionTokenManager.borrowSessionToken();

        assertNotNull(sessionToken.getTokenString());
    }

    @Test
    public void testBorrowSessionTokenConcurrency() throws Exception {
        SessionTokenManagerInterface sessionTokenManager = mConfiguration.getSessionTokenManager();
        for(int i = 0; i < 20; i++){
            new BorrowSessionTokenThread(sessionTokenManager).start();
        }
        sessionTokenManager.borrowSessionToken();
        Thread.sleep(3000);
    }

    private class BorrowSessionTokenThread extends Thread {

        private SessionTokenManagerInterface mSessionTokenManagerInterface;

        BorrowSessionTokenThread(SessionTokenManagerInterface sessionTokenManagerInterface){
            mSessionTokenManagerInterface = sessionTokenManagerInterface;
        }

        @Override
        public void run(){
            mSessionTokenManagerInterface.borrowSessionToken();
        }
    }
}