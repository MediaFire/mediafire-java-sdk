package com.mediafire.sdk.config;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.api.responses.UserGetInfoResponse;
import com.mediafire.sdk.requests.ApiPostRequest;
import com.mediafire.sdk.token.SessionToken;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultSessionRequesterTest extends TestCase {

    private MFSessionRequester sessionRequester;
    private long startTime;

    public void setUp() throws Exception {
        super.setUp();
        startTime = System.currentTimeMillis();
        MFCredentials credentials = new DefaultCredentials();
        MFHttpRequester http = new DefaultHttpRequester(5000, 5000);
        MFStore<SessionToken> store = new DefaultSessionStore();
        sessionRequester = new DefaultSessionRequester(credentials, "40767", http, store);
    }

    public void tearDown() throws Exception {
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(getName() + " execution time: " + elapsedTime + "ms");
    }

    public void testStartSessionWithGoodEmail() throws Exception {
        sessionRequester.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        assertTrue(sessionRequester.hasSession());
    }

    public void testStartSessionWithBadEmail() {
        try {
            sessionRequester.startSessionWithEmail("a@b.c", "123456", null);
            fail("exception not thrown on bad credentials");
        } catch (MFApiException e) {
            assertTrue(e.getMessage(), "The Credentials you entered are invalid".equals(e.getMessage()));
        }
    }

    public void testStartSessionWithEkey() throws Exception {
        try {
            sessionRequester.startSessionWithEkey("badekey", "123456", null);
            fail("exception not thrown on bad credentials");
        } catch (MFApiException e) {
            assertTrue(e.getMessage(), "Unknown or invalid user".equals(e.getMessage()));
        }
    }

    public void testStartSessionWithFacebook() throws Exception {
        try {
            sessionRequester.startSessionWithFacebook("badfbtoken", null);
            fail("exception not thrown on bad credentials");
        } catch (MFApiException e) {
            assertTrue(e.getMessage(), "Failed to authenticate to Facebook".equals(e.getMessage()));
        }
    }

    public void testDoApiRequestWhenSessionNotStartedThrowsException() {
        boolean mfExceptionThrown = false;
        LinkedHashMap<String, Object> query = new LinkedHashMap<String, Object>();
        query.put("response_format", "json");
        ApiPostRequest apiPostRequest = new ApiPostRequest("https", "www.mediafire.com", "/api/1.4/system/get_info.php", query);
        try {
            UserGetInfoResponse response = sessionRequester.doApiRequest(apiPostRequest, UserGetInfoResponse.class);
        } catch (MFException e) {
            mfExceptionThrown = true;
        } catch (MFApiException e) {
            fail("api exception shouldn't be thrown, MFException should be thrown");
        }

        assertTrue(mfExceptionThrown);
    }

    public void testDoApiRequestAfterSessionStart() {
        try {
            sessionRequester.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        } catch (MFApiException e) {
            fail("api exception should not have been thrown");
        }

        LinkedHashMap<String, Object> query = new LinkedHashMap<String, Object>();
        query.put("response_format", "json");
        ApiPostRequest apiPostRequest = new ApiPostRequest("https", "www.mediafire.com", "/api/1.4/system/get_info.php", query);
        try {
            UserGetInfoResponse response = sessionRequester.doApiRequest(apiPostRequest, UserGetInfoResponse.class);
            if (response == null) {
                fail("response should not have been null");
            }

            assertTrue("Success".equals(response.getResult()));
        } catch (MFException e) {
            fail("MFException should not have been thrown: " + e.getMessage());
        } catch (MFApiException e) {
            fail("api exception shouldn't be thrown, MFException should be thrown");
        }
    }
}