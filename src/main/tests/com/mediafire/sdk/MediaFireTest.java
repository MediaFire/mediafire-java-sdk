package com.mediafire.sdk;

import com.mediafire.sdk.api.FolderApi;
import com.mediafire.sdk.api.responses.FolderCreateResponse;
import com.mediafire.sdk.requests.HttpApiResponse;
import com.mediafire.sdk.requests.ImageRequest;
import junit.framework.TestCase;

import java.util.LinkedHashMap;

public class MediaFireTest extends TestCase {

    private MediaFire mediaFire;

    public void setUp() throws Exception {
        super.setUp();
        mediaFire = new MediaFire("40767");
    }

    public void testMediaFireInstantiationNoSession() {
        assertFalse(mediaFire.isSessionStarted());
    }

    public void testMediaFireCannotApiRequestWithoutSession() {
        boolean mfExceptionThrown = false;
        try {
            mediaFire.doApiRequest(null, null);
        } catch (MFException e) {
            fail("should not be able to make request: " + e.getMessage());
        } catch (MFApiException e) {
            fail("should not be able to make request: " + e.getMessage());
        } catch (MFSessionNotStartedException e) {
            mfExceptionThrown = true;
        }

        assertTrue(mfExceptionThrown);
    }

    public void testMediaFireCannotUploadRequestWithoutSession() {
        boolean mfExceptionThrown = false;
        try {
            mediaFire.doUploadRequest(null, null);
        } catch (MFException e) {
            fail("should not be able to make request: " + e.getMessage());
        } catch (MFApiException e) {
            fail("should not be able to make request: " + e.getMessage());
        } catch (MFSessionNotStartedException e) {
            mfExceptionThrown = true;
        }

        assertTrue(mfExceptionThrown);
    }

    public void testMediaFireCannotImageRequestWithoutSession() {
        boolean mfExceptionThrown = false;
        try {
            mediaFire.doImageRequest(null);
        } catch (MFException e) {
            fail("should not be able to make request: " + e.getMessage());
        } catch (MFApiException e) {
            fail("should not be able to make request: " + e.getMessage());
        } catch (MFSessionNotStartedException e) {
            mfExceptionThrown = true;
        }

        assertTrue(mfExceptionThrown);
    }

    public void testMediaFireCannotDocumentRequestWithoutSession() {
        boolean mfExceptionThrown = false;
        try {
            mediaFire.doDocumentRequest(null);
        } catch (MFException e) {
            fail("should not be able to make request: " + e.getMessage());
        } catch (MFApiException e) {
            fail("should not be able to make request: " + e.getMessage());
        } catch (MFSessionNotStartedException e) {
            mfExceptionThrown = true;
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
            } catch (MFSessionNotStartedException e) {
                fail("should not throw mf exception: " + e.getMessage());
            }

        } catch (MFApiException e) {
            fail("should not throw api exception: " + e.getMessage());
        } catch (MFException e) {
            fail("should not throw mf exception: " + e.getMessage());
        }
    }

    public void testDoImageRequest() {
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
            if (!mediaFire.isSessionStarted()) {
                fail("session should be started");
            }
        } catch (MFApiException e) {
            fail("exception should not have been thrown: " + e);
        } catch (MFException e) {
            fail("exception should not have been thrown: " + e);
        }

        ImageRequest imageRequest = new ImageRequest("aaaa", "tka3a7tzytezo42", '4', false);
        try {
            HttpApiResponse response = mediaFire.doImageRequest(imageRequest);
            assertTrue(response.getStatus() == 200);
        } catch (MFException e) {
            fail("exception should not have been thrown: " + e);
        } catch (MFApiException e) {
            fail("exception should not have been thrown: " + e);
        } catch (MFSessionNotStartedException e) {
            fail("exception should not have been thrown: " + e);
        }
    }

    public void testDoImageRequestConversionOnly() {
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
            if (!mediaFire.isSessionStarted()) {
                fail("session should be started");
            }
        } catch (MFApiException e) {
            fail("exception should not have been thrown: " + e);
        } catch (MFException e) {
            fail("exception should not have been thrown: " + e);
        }

        ImageRequest imageRequest = new ImageRequest("aaaa", "tka3a7tzytezo42", '4', true);
        try {
            HttpApiResponse response = mediaFire.doImageRequest(imageRequest);
            assertTrue(response.getStatus() == 202 || response.getStatus() == 200);
        } catch (MFException e) {
            fail("exception should not have been thrown: " + e);
        } catch (MFApiException e) {
            fail("exception should not have been thrown: " + e);
        } catch (MFSessionNotStartedException e) {
            fail("exception should not have been thrown: " + e);
        }
    }
}