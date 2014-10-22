package com.mediafire.sdk.config.defaults;

import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.token.ImageActionToken;
import com.mediafire.sdk.token.UploadActionToken;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class DefaultActionTokenManagerTest {

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
    public void testBorrowImageActionTokenNotNull() throws Exception {
        ActionTokenManagerInterface actionTokenManager = mConfiguration.getActionTokenManager();

        ImageActionToken imageActionToken = actionTokenManager.borrowImageActionToken();

        assertNotNull(imageActionToken);
    }

    @Test
    public void testBorrowUploadActionTokenNotNull() throws Exception {
        ActionTokenManagerInterface actionTokenManager = mConfiguration.getActionTokenManager();

        UploadActionToken uploadActionToken = actionTokenManager.borrowUploadActionToken();

        assertNotNull(uploadActionToken.getTokenString());
    }

    @Test
    public void testBorrowImageActionTokenNotNullToken() throws Exception {
        ActionTokenManagerInterface actionTokenManager = mConfiguration.getActionTokenManager();

        ImageActionToken imageActionToken = actionTokenManager.borrowImageActionToken();

        assertNotNull(imageActionToken.getTokenString());
    }

    @Test
    public void testBorrowUploadActionTokenNotNullToken() throws Exception {
        ActionTokenManagerInterface actionTokenManager = mConfiguration.getActionTokenManager();

        UploadActionToken uploadActionToken = actionTokenManager.borrowUploadActionToken();

        assertNotNull(uploadActionToken.getTokenString());
    }

    @Test
    public void testActionTokenConcurrency() throws Exception {
        ActionTokenManagerInterface actionTokenManager = mConfiguration.getActionTokenManager();

        for(int i = 0; i < 30; i++) {
            int random = new Random().nextInt(3);
            if(random == 0){
                new NewUploadActionTokenThread(actionTokenManager).start();
            }
            else if(random == 1){
                new NewImageActionTokenThread(actionTokenManager).start();
            }
            else{
                actionTokenManager.receiveUploadActionToken(null);
                actionTokenManager.receiveImageActionToken(null);
            }
            Thread.sleep(new Random().nextInt((30-i)*50));
        }

        actionTokenManager.borrowImageActionToken();
        actionTokenManager.borrowUploadActionToken();

        Thread.sleep(3000);
    }
    
    private class NewUploadActionTokenThread extends Thread {
        private ActionTokenManagerInterface mActionTokenManagerInterface;
        
        NewUploadActionTokenThread(ActionTokenManagerInterface actionTokenManagerInterface){
            mActionTokenManagerInterface = actionTokenManagerInterface;
        }
        
        @Override
        public void run(){
            mActionTokenManagerInterface.borrowUploadActionToken();
        }
    }

    private class NewImageActionTokenThread extends Thread {
        private ActionTokenManagerInterface mActionTokenManagerInterface;

        NewImageActionTokenThread(ActionTokenManagerInterface actionTokenManagerInterface){
            mActionTokenManagerInterface = actionTokenManagerInterface;
        }

        @Override
        public void run(){
            mActionTokenManagerInterface.borrowImageActionToken();
        }
    }
}