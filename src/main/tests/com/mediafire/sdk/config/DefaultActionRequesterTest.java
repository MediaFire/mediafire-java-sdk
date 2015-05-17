package com.mediafire.sdk.config;

import com.mediafire.sdk.token.ActionToken;
import com.mediafire.sdk.token.SessionToken;
import junit.framework.TestCase;

public class DefaultActionRequesterTest extends TestCase {

    private long startTime;
    private DefaultActionRequester actionRequester;

    public void setUp() throws Exception {
        super.setUp();
        startTime = System.currentTimeMillis();
        MFCredentials credentials = new DefaultCredentials();
        MFHttpRequester http = new DefaultHttpRequester(5000, 5000);
        MFStore<SessionToken> store = new DefaultSessionStore();
        MFSessionRequester sessionRequester = new DefaultSessionRequester(credentials, "40767", http, store);
        MFStore<ActionToken> imageStore = new DefaultActionStore(2);
        MFStore<ActionToken> uploadStore = new DefaultActionStore(60);
        actionRequester = new DefaultActionRequester(http, sessionRequester, imageStore, uploadStore);
    }

    public void tearDown() throws Exception {
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(getName() + " execution time: " + elapsedTime + "ms");
    }

    public void testDoImageRequest() throws Exception {

    }
}