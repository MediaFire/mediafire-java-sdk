package com.mediafire.sdk;

import com.mediafire.sdk.api.FolderApi;
import com.mediafire.sdk.api.responses.FolderCreateResponse;
import junit.framework.TestCase;

import java.util.LinkedHashMap;

public class MediaFireTest extends TestCase {

    private long startTime;
    private MediaFire mediaFire;

    public void setUp() throws Exception {
        super.setUp();
        startTime = System.currentTimeMillis();
        mediaFire = new MediaFire("40767");
    }

    public void tearDown() throws Exception {
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(getName() + " execution time: " + elapsedTime + "ms");
    }

    public void testMediaFireInstantiationNoSession() {
        assertFalse(mediaFire.isSessionStarted());
    }

    public void testMediaFireCannotApiRequestWithoutSession() {
        boolean mfExceptionThrown = false;
        try {
            mediaFire.doApiRequest(null, null);
        } catch (MFException e) {
            mfExceptionThrown = true;
        } catch (MFApiException e) {
            fail("should not be able to make request: " + e.getMessage());
        }

        assertTrue(mfExceptionThrown);
    }

    public void testMediaFireCannotUploadRequestWithoutSession() {
        boolean mfExceptionThrown = false;
        try {
            mediaFire.doUploadRequest(null, null);
        } catch (MFException e) {
            mfExceptionThrown = true;
        } catch (MFApiException e) {
            fail("should not be able to make request: " + e.getMessage());
        }

        assertTrue(mfExceptionThrown);
    }

    public void testMediaFireCannotImageRequestWithoutSession() {
        boolean mfExceptionThrown = false;
        try {
            mediaFire.doImageRequest(null);
        } catch (MFException e) {
            mfExceptionThrown = true;
        } catch (MFApiException e) {
            fail("should not be able to make request: " + e.getMessage());
        }

        assertTrue(mfExceptionThrown);
    }

    public void testMediaFireCannotDocumentRequestWithoutSession() {
        boolean mfExceptionThrown = false;
        try {
            mediaFire.doDocumentRequest(null);
        } catch (MFException e) {
            mfExceptionThrown = true;
        } catch (MFApiException e) {
            fail(e.getMessage());
        }

        assertTrue(mfExceptionThrown);
    }

    public void testMediaFireStartSession() {
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
            assertTrue(mediaFire.isSessionStarted());
        } catch (MFApiException e) {
            fail("should not throw api exception: " + e.getMessage());
        } catch (MFException e) {
            fail("should not throw mf exception: " + e.getMessage());
        }
    }

    public void testMediaFireStartSessionCanMakeRequest() {
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);

            if (!mediaFire.isSessionStarted()) {
                fail("session should be started");
            }

            LinkedHashMap<String, Object> requestParams = new LinkedHashMap<String, Object>();
            requestParams.put("response_format", "json");
            requestParams.put("foldername", "test_folder");
            requestParams.put("action_on_duplicate", "keep");
            try {
                FolderCreateResponse response = FolderApi.create(mediaFire, requestParams, "1.4", FolderCreateResponse.class);
                String folderKey = response.getFolderKey();
                assertTrue("folder key created: " + folderKey, folderKey != null);
            } catch (MFException e) {
                fail("should not throw mf exception: " + e.getMessage());
            }

        } catch (MFApiException e) {
            fail("should not throw api exception: " + e.getMessage());
        } catch (MFException e) {
            fail("should not throw mf exception: " + e.getMessage());
        }

    }
}