package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.UserGetSessionTokenResponse;
import junit.framework.TestCase;

import java.util.*;

/**
 * Created by christophernajar on 9/1/15.
 */
public class MediaFireClientTest extends TestCase {

    private MFClient mediaFire;

    public void setUp() throws Exception {
        super.setUp();
        MFClient.Builder builder = new MFClient.Builder("40767", null);
        builder.apiVersion("1.5");
        mediaFire = builder.build();
        mediaFire.getCredentialStore().setEmail(new MediaFireCredentialsStore.EmailCredentials("badtestemail@badtestemail.com", "badtestemail"));
    }

    public void tearDown() throws Exception {
        mediaFire = null;
    }

    public void testAuthRequestNoCredentials() throws Exception {
        MFClient.Builder builder = new MFClient.Builder("40767", null);
        builder.apiVersion("1.5");
        MFClient mediaFire = builder.build();
        mediaFire.getCredentialStore().clear();
        assertTrue("type stored should be NONE", mediaFire.getCredentialStore().getTypeStored() == MediaFireCredentialsStore.TYPE_NONE);
        UserGetSessionTokenResponse response = mediaFire.authenticationRequest(UserGetSessionTokenResponse.class);
        assertTrue("response: " + response.getMessage(), response.hasError());
    }

    public void testAuthRequestBadCredentials() throws Exception {
        MFClient.Builder builder = new MFClient.Builder("40767", null);
        builder.apiVersion("1.5");
        MFClient mediaFire = builder.build();
        mediaFire.getCredentialStore().clear();
        mediaFire.getCredentialStore().setEmail(new MediaFireCredentialsStore.EmailCredentials("a@b.c", "abc"));
        UserGetSessionTokenResponse response = mediaFire.authenticationRequest(UserGetSessionTokenResponse.class);
        assertTrue("response: " + response.getMessage(), response.hasError());
    }

    public void testNoAuthRequest() throws Exception {
        MediaFireApiRequest request = new MFApiRequest("/system/get_info.php", null, null, null);
        ApiResponse response = this.mediaFire.noAuthRequest(request, ApiResponse.class);
        assertFalse(response.hasError());
    }

    public void testConversionServerRequest() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("quickkey", "bnv3ne309r2ab06");
        params.put("size_id", "z");
        params.put("doc_type", "i");
        String hash = "5f3df7078a450fe4ccbea773b07117caefa6e1b572abc63928f5e2d57cc9d0be";
        MediaFireHttpResponse response = this.mediaFire.conversionServerRequest(hash, params);
        assertTrue(response.getStatusCode() == 200);
    }

    public void testSessionRequest() throws Exception {
        MediaFireApiRequest request = new MFApiRequest("/user/get_info.php", null, null, null);
        ApiResponse response = this.mediaFire.sessionRequest(request, ApiResponse.class);
        assertFalse(response.hasError());
    }

    public void testAuthenticationRequest() throws Exception {
        UserGetSessionTokenResponse response = this.mediaFire.authenticationRequest(UserGetSessionTokenResponse.class);
        assertFalse("response: " + response.getMessage(), response.hasError());
    }

    public void testSessionRequestSingleThread() throws Exception {
        MediaFireApiRequest request = new MFApiRequest("/user/get_info.php", null, null, null);
        for (int i = 0; i < 10; i++) {
            ApiResponse response = this.mediaFire.sessionRequest(request, ApiResponse.class);
            if (response.hasError()) {
                fail("error in api request #" + (i + 1) + ": " + response.getMessage() + " (" + response.getError() + ")");
            }
        }
        assertTrue(true);
    }

    public void testSessionRequestMultipleThread() throws Exception {
        MediaFireApiRequest request1 = new MFApiRequest("/user/get_info.php", null, null, null);
        MediaFireApiRequest request2 = new MFApiRequest("/folder/get_info.php", null, null, null);
        MediaFireApiRequest request3 = new MFApiRequest("/system/get_info.php", null, null, null);
        MediaFireApiRequest request4 = new MFApiRequest("/device/get_status.php", null, null, null);
        MediaFireApiRequest request5 = new MFApiRequest("/contact/fetch.php", null, null, null);
        MediaFireApiRequest request6 = new MFApiRequest("/device/get_trash.php", null, null, null);

        List<RequestThread> threads = new ArrayList<RequestThread>();
        for (int i = 0; i < 100; i++) {
            int r = new Random().nextInt(6);
            switch (r) {
                case 0:
                    threads.add(new RequestThread(request1));
                    break;
                case 1:
                    threads.add(new RequestThread(request2));
                    break;
                case 2:
                    threads.add(new RequestThread(request3));
                    break;
                case 3:
                    threads.add(new RequestThread(request4));
                    break;
                case 4:
                    threads.add(new RequestThread(request5));
                    break;
                case 5:
                    threads.add(new RequestThread(request6));
                    break;
                default:
                    throw new IllegalArgumentException("invalid case: " + r);
            }
        }

        for (RequestThread thread : threads) {
            thread.start();
        }

        for (RequestThread thread : threads) {
            thread.join();
        }

        int numErrors = 0;

        for (RequestThread thread : threads) {
            if (thread.hasError()) {
                numErrors++;
            }
        }

        assertTrue("expected 0 errors but had " + numErrors + " errors and session store has " + mediaFire.getSessionStore().getSessionTokenV2Count() + " session tokens", numErrors == 0);
    }

    private class RequestThread extends Thread {
        private final MediaFireApiRequest request;
        private ApiResponse response;

        private RequestThread(MediaFireApiRequest request) {
            this.request = request;
        }

        @Override
        public void run() {
            try {
                this.response = mediaFire.sessionRequest(request, ApiResponse.class);
            } catch (MediaFireException e) {
                e.printStackTrace();
            }
        }

        public boolean hasError() {
            return response == null || response.hasError();
        }
    }
}