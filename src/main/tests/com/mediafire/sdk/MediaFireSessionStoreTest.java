package com.mediafire.sdk;

import junit.framework.TestCase;

/**
 * Created by christophernajar on 9/1/15.
 */
public class MediaFireSessionStoreTest extends TestCase {

    private final MediaFireSessionToken sessionToken = new MFSessionToken("abcdefg", "123.45", 12345L, null, "ekey");
    private final MediaFireActionToken uploadToken = new MFActionToken("abcdefg", MediaFireActionToken.TYPE_UPLOAD, System.currentTimeMillis(), 10);
    private final MediaFireActionToken imageToken = new MFActionToken("gfedcba", MediaFireActionToken.TYPE_IMAGE, System.currentTimeMillis(), 10);

    private MediaFireSessionStore sessionStore;

    public void setUp() throws Exception {
        super.setUp();
        this.sessionStore = new MFSessionStore();
    }

    public void tearDown() throws Exception {

    }

    public void testGetSessionTokenV2WhenEmpty() throws Exception {
        assertNull(this.sessionStore.getSessionTokenV2());
    }

    public void testGetSessionTokenV2() throws Exception {
        if (!this.sessionStore.put(sessionToken)) {
            fail("didn't store session token");
        }
        assertEquals(sessionToken, this.sessionStore.getSessionTokenV2());
    }

    public void testPutSessionTokenV2() throws Exception {
        assertTrue(this.sessionStore.put(sessionToken));
    }

    public void testGetSessionTokenV2CountWhenEmpty() throws Exception {
        assertEquals(0, this.sessionStore.getSessionTokenV2Count());
    }

    public void testGetSessionTokenV2Count() throws Exception {
        if (!this.sessionStore.put(sessionToken)) {
            fail("didn't store session token");
        }
        assertEquals(1, this.sessionStore.getSessionTokenV2Count());
    }

    public void testIsSessionTokenV2AvailableWhenEmpty() throws Exception {
        assertFalse(this.sessionStore.isSessionTokenV2Available());
    }

    public void testIsSessionTokenV2Available() throws Exception {
        if (!this.sessionStore.put(sessionToken)) {
            fail("didn't store session token");
        }
        assertTrue(this.sessionStore.isSessionTokenV2Available());
    }

    public void testGetImageTokenWhenEmpty() throws Exception {
        assertNull(this.sessionStore.getActionToken(MediaFireActionToken.TYPE_IMAGE));
    }

    public void testGetUploadTokenWhenEmpty() throws Exception {
        assertNull(this.sessionStore.getActionToken(MediaFireActionToken.TYPE_UPLOAD));
    }

    public void testGetUploadToken() throws Exception {
        if (!this.sessionStore.put(uploadToken)) {
            fail("didn't store upload token");
        }

        assertEquals(uploadToken, this.sessionStore.getActionToken(MediaFireActionToken.TYPE_UPLOAD));
    }

    public void testGetImageToken() throws Exception {
        if (!this.sessionStore.put(imageToken)) {
            fail("didn't store image token");
        }

        assertEquals(imageToken, this.sessionStore.getActionToken(MediaFireActionToken.TYPE_IMAGE));
    }

    public void testPutUploadToken() throws Exception {
        assertTrue(this.sessionStore.put(uploadToken));
    }

    public void testPutImageToken() throws Exception {
        assertTrue(this.sessionStore.put(imageToken));
    }

    public void testIsUploadTokenAvailableWhenEmpty() throws Exception {
        assertFalse(this.sessionStore.isActionTokenAvailable(MediaFireActionToken.TYPE_UPLOAD));
    }

    public void testIsImageTokenAvailableWhenEmpty() throws Exception {
        assertFalse(this.sessionStore.isActionTokenAvailable(MediaFireActionToken.TYPE_IMAGE));
    }

    public void testIsUploadTokenAvailable() throws Exception {
        if (!this.sessionStore.put(uploadToken)) {
            fail("didn't store upload token");
        }

        assertTrue(this.sessionStore.isActionTokenAvailable(MediaFireActionToken.TYPE_UPLOAD));
    }

    public void testIsImageTokenAvailable() throws Exception {
        if (!this.sessionStore.put(imageToken)) {
            fail("didn't store image token");
        }

        assertTrue(this.sessionStore.isActionTokenAvailable(MediaFireActionToken.TYPE_IMAGE));
    }

    public void testClear() throws Exception {
        testPutSessionTokenV2();
        testPutImageToken();
        testPutUploadToken();

        this.sessionStore.clear();

    }
}