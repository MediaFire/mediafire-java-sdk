package com.mediafire.sdk.config;

import com.mediafire.sdk.token.SessionToken;
import junit.framework.TestCase;

public class DefaultSessionStoreTest extends TestCase {

    private MFStore<SessionToken> store;
    private final String TOKEN_STRING = "37f332f68db77bd9d7edd4969571ad671cf9dd3b";
    private final SessionToken SESSION_TOKEN = new SessionToken.Builder(TOKEN_STRING).build();

    private long startTime;

    public void setUp() throws Exception {
        super.setUp();
        startTime = System.currentTimeMillis();
        store = new DefaultSessionStore();
    }

    public void tearDown() throws Exception {
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(getName() + " execution time: " + elapsedTime + "ms");
    }

    public void testAvailableOnInstantiation() throws Exception {
        assertFalse(store.available());
    }

    public void testAvailableAfterPut() throws Exception {
        store.put(SESSION_TOKEN);
        assertTrue(store.available());
    }

    public void testGet() throws Exception {
        store.put(SESSION_TOKEN);
        assertTrue(store.get() != null);
    }

    public void testPut() throws Exception {
        store.put(SESSION_TOKEN);
        SessionToken token = store.get();
        assertEquals(token, SESSION_TOKEN);
    }

    public void testClear() throws Exception {
        store.put(SESSION_TOKEN);
        SessionToken token = store.get();
        assertEquals(token, SESSION_TOKEN);
    }
}