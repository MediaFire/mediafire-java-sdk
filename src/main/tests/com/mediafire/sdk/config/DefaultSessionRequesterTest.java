package com.mediafire.sdk.config;

import com.mediafire.sdk.*;
import com.mediafire.sdk.api.UserApi;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.UserGetAvatarResponse;
import com.mediafire.sdk.api.responses.UserGetInfoResponse;
import com.mediafire.sdk.api.responses.UserGetSettingsResponse;
import junit.framework.TestCase;

import java.util.*;

public class DefaultSessionRequesterTest extends TestCase {

    private MFSessionRequester sessionRequester;

    public void setUp() throws Exception {
        super.setUp();
        MFCredentials credentials = new DefaultCredentials();
        MFHttpRequester http = new DefaultHttpRequester(5000, 5000);
        MFStore<SessionToken> store = new DefaultSessionStore();
        sessionRequester = new DefaultSessionRequester(credentials, "40767", http, store);
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
        } catch (MFException e) {
            fail("MFException should not be thrown: " + e);
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
            fail("api exception should not have been thrown: " + e);
        } catch (MFException e) {
            fail("api exception should not have been thrown: " + e);
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
            fail("MFApiException should be thrown: " + e);
        }
    }
    public void testHttpApiResponse() {
        MediaFire mediaFire = new MediaFire("40767");
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        } catch (MFApiException e) {
            fail("exception thrown: " + e);
        } catch (MFException e) {
            fail("exception thrown: " + e);
        }

        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("response_format", "json");
        UserGetInfoResponse response = null;
        try {
            response = UserApi.getInfo(mediaFire, params, "1.4", UserGetInfoResponse.class);
        } catch (MFException e) {
            fail("exception thrown: " + e);
        } catch (MFApiException e) {
            fail("exception thrown: " + e);
        } catch (MFSessionNotStartedException e) {
            fail("exception thrown: " + e);
        }

        assertTrue(response.getError() == 0);
    }

    public void testHttpApiResponseMultiThread() {
        MediaFire mediaFire = new MediaFire("40767");
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        } catch (MFApiException e) {
            fail("exception thrown: " + e);
        } catch (MFException e) {
            fail("exception thrown: " + e);
        }

        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("response_format", "json");

        int runs = 100;

        List<UserApiThread> threadList = new ArrayList<UserApiThread>();

        for (int i = 0; i < runs; i++) {
            UserApiThread thread = new UserApiThread(mediaFire, params);
            threadList.add(thread);
        }

        for (UserApiThread thread : threadList) {
            int r = new Random().nextInt(5) + 1;
            switch (r) {
                case 1:
                    thread.fetchTOS();
                    break;
                case 2:
                    thread.getAvatar();
                    break;
                case 3:
                    thread.getInfo();
                    break;
                case 4:
                    thread.getLimits();
                    break;
                case 5:
                    thread.getLimits();
                    break;
            }
        }

        for (UserApiThread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                fail("test failed, exception thrown: " + e);
            }
        }

        int err = 0;
        for (UserApiThread thread : threadList) {
            ApiResponse apiResponse = thread.getResponse();
            if (apiResponse.hasError()) {
                err++;
            }
        }

        assertTrue(err == 0);
    }

    private class UserApiThread extends Thread {
        private static final int CALL_ID_GET_INFO = 1;
        private static final int CALL_ID_FETCH_TOS = 2;
        private static final int CALL_ID_GET_AVATAR = 3;
        private static final int CALL_ID_GET_LIMITS = 4;
        private static final int CALL_ID_GET_SETTINGS = 5;
        private final MediaFire mediaFire;
        private final LinkedHashMap<String, Object> params;
        private ApiResponse apiResponse;
        private int callId;

        public UserApiThread(MediaFire mediaFire, LinkedHashMap<String, Object> params) {
            super();
            this.mediaFire = mediaFire;
            this.params = params;
        }

        @Override
        public void run() {
            super.run();
            if (callId == 0) {
                throw new IllegalStateException("called run() without setting call id");
            }

            try {
                switch (callId) {
                    case CALL_ID_FETCH_TOS:
                        this.apiResponse = UserApi.fetchTermsOfService(mediaFire, params, "1.4", ApiResponse.class);
                        break;
                    case CALL_ID_GET_AVATAR:
                        this.apiResponse = UserApi.getAvatar(mediaFire, params, "1.4", UserGetAvatarResponse.class);
                        break;
                    case CALL_ID_GET_INFO:
                        this.apiResponse = UserApi.getInfo(mediaFire, params, "1.4", UserGetInfoResponse.class);
                        break;
                    case CALL_ID_GET_LIMITS:
                        this.apiResponse = UserApi.getLimits(mediaFire, params, "1.4", ApiResponse.class);
                        break;
                    case CALL_ID_GET_SETTINGS:
                        this.apiResponse = UserApi.getSettings(mediaFire, params, "1.4", UserGetSettingsResponse.class);
                        break;
                    default:
                        throw new IllegalStateException("called run() with invalid call id");
                }
            } catch (MFException e) {
                e.printStackTrace();
            } catch (MFApiException e) {
                e.printStackTrace();
            } catch (MFSessionNotStartedException e) {
                e.printStackTrace();
            }
        }

        public void getInfo() {
            callId = CALL_ID_GET_INFO;
            start();
        }

        public void fetchTOS() {
            callId = CALL_ID_FETCH_TOS;
            start();
        }

        public void getAvatar() {
            callId = CALL_ID_GET_AVATAR;
            start();
        }

        public void getSettings() {
            callId = CALL_ID_GET_SETTINGS;
            start();
        }

        public void getLimits() {
            callId = CALL_ID_GET_LIMITS;
            start();
        }

        public ApiResponse getResponse() {
            return apiResponse;
        }
    }
}