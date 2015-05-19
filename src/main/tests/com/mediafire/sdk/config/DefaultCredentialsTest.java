package com.mediafire.sdk.config;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class DefaultCredentialsTest extends TestCase {

    private MFCredentials credentials;
    private static final Map<String, String> TEST_CREDENTIALS = new HashMap<String, String>();
    static {
        TEST_CREDENTIALS.put("email", "a@b.c");
        TEST_CREDENTIALS.put("password", "12345");
    }

    public void setUp() throws Exception {
        super.setUp();
        credentials = new DefaultCredentials();
    }

    public void testSetCredentials() throws Exception {
        credentials.setCredentials(TEST_CREDENTIALS);
        Map<String, String> stored = credentials.getCredentials();
        boolean containsAll = true;
        for (String key : TEST_CREDENTIALS.keySet()) {
            if (!stored.containsKey(key) || !stored.get(key).equals(TEST_CREDENTIALS.get(key))) {
                containsAll = false;
            }
        }
        assertTrue(containsAll);
    }

    public void testGetCredentialsIsEmptyOnInstantiation() throws Exception {
        assertTrue(credentials.getCredentials().isEmpty());
    }

    public void testGetCredentialsIsEmptyAfterInvalidate() throws Exception {
        credentials.setCredentials(TEST_CREDENTIALS);
        credentials.invalidate();
        assertTrue(credentials.getCredentials().isEmpty());
    }

    public void testGetCredentialsIsEmptyAfterValidateIfMapIsEmpty() throws Exception {
        credentials.setCredentials(new HashMap<String, String>());
        credentials.setValid();
        assertTrue(credentials.getCredentials().isEmpty());
    }

    public void testSetValidForGoodCredentials() throws Exception {
        credentials.setCredentials(TEST_CREDENTIALS);
        assertTrue(credentials.setValid());
    }

    public void testSetValidForEmptyCredentials() throws Exception {
        credentials.setCredentials(new HashMap<String, String>());
        assertFalse(credentials.setValid());
    }

    public void testInvalidate() throws Exception {
        credentials.setCredentials(TEST_CREDENTIALS);
        credentials.setValid();
        credentials.invalidate();
        assertFalse(credentials.isValid());
    }
}