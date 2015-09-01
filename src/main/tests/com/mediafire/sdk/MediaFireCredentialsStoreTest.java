package com.mediafire.sdk;

import junit.framework.TestCase;

/**
 * Created by christophernajar on 9/1/15.
 */
public class MediaFireCredentialsStoreTest extends TestCase {

    private static final String EMAIL = "tom@bob.joe";
    private static final String PASSWORD = "minions";
    private static final String EKEY = "ekey.tom.bob.joe";
    private static final String FACEBOOK = "fbaccesstoken";
    private static final String TWITTER_TOKEN = "oauthtokentwitter";
    private static final String TWITTER_TOKEN_SECRET = "oauthtokensecrettwitter";
    private MFCredentialsStore credentials;

    public void setUp() throws Exception {
        super.setUp();
        this.credentials = new MFCredentialsStore();
    }

    public void tearDown() throws Exception {
        this.credentials.clear();
    }

    public void testSetEmail() throws Exception {
        MediaFireCredentialsStore.EmailCredentials credentials = new MediaFireCredentialsStore.EmailCredentials(EMAIL, PASSWORD);
        this.credentials.setEmail(credentials);
        MediaFireCredentialsStore.EmailCredentials stored = this.credentials.getEmailCredentials();
        assertEquals(credentials, stored);
        assertTrue(this.credentials.getTypeStored() == MediaFireCredentialsStore.TYPE_EMAIL);
    }

    public void testClearEmail() throws Exception {
        MediaFireCredentialsStore.EmailCredentials credentials = new MediaFireCredentialsStore.EmailCredentials(EMAIL, PASSWORD);
        this.credentials.setEmail(credentials);
        this.credentials.clear();

        assertTrue(this.credentials.getTypeStored() == MediaFireCredentialsStore.TYPE_NONE);
    }

    public void testSetEkey() throws Exception {
        MediaFireCredentialsStore.EkeyCredentials credentials = new MediaFireCredentialsStore.EkeyCredentials(EKEY, PASSWORD);
        this.credentials.setEkey(credentials);
        MediaFireCredentialsStore.EkeyCredentials stored = this.credentials.getEkeyCredentials();
        assertEquals(credentials, stored);
        assertTrue(this.credentials.getTypeStored() == MediaFireCredentialsStore.TYPE_EKEY);
    }

    public void testClearEkey() throws Exception {
        MediaFireCredentialsStore.EkeyCredentials credentials = new MediaFireCredentialsStore.EkeyCredentials(EKEY, PASSWORD);
        this.credentials.setEkey(credentials);
        this.credentials.clear();

        assertTrue(this.credentials.getTypeStored() == MediaFireCredentialsStore.TYPE_NONE);
    }

    public void testSetFacebook() throws Exception {
        MediaFireCredentialsStore.FacebookCredentials credentials = new MediaFireCredentialsStore.FacebookCredentials(FACEBOOK);
        this.credentials.setFacebook(credentials);
        MediaFireCredentialsStore.FacebookCredentials stored = this.credentials.getFacebookCredentials();
        assertEquals(credentials, stored);
        assertTrue(this.credentials.getTypeStored() == MediaFireCredentialsStore.TYPE_FACEBOOK);
    }

    public void testClearFacebook() throws Exception {
        MediaFireCredentialsStore.FacebookCredentials credentials = new MediaFireCredentialsStore.FacebookCredentials(FACEBOOK);
        this.credentials.setFacebook(credentials);
        this.credentials.clear();

        assertTrue(this.credentials.getTypeStored() == MediaFireCredentialsStore.TYPE_NONE);
    }

    public void testSetTwitter() throws Exception {
        MediaFireCredentialsStore.TwitterCredentials credentials = new MediaFireCredentialsStore.TwitterCredentials(TWITTER_TOKEN, TWITTER_TOKEN_SECRET);
        this.credentials.setTwitter(credentials);
        MediaFireCredentialsStore.TwitterCredentials stored = this.credentials.getTwitterCredentials();
        assertEquals(credentials, stored);
        assertTrue(this.credentials.getTypeStored() == MediaFireCredentialsStore.TYPE_TWITTER);
    }

    public void testClearTwitter() throws Exception {
        MediaFireCredentialsStore.TwitterCredentials credentials = new MediaFireCredentialsStore.TwitterCredentials(TWITTER_TOKEN, TWITTER_TOKEN_SECRET);
        this.credentials.setTwitter(credentials);
        this.credentials.clear();

        assertTrue(this.credentials.getTypeStored() == MediaFireCredentialsStore.TYPE_NONE);
    }
}