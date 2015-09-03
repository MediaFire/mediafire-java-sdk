package com.mediafire.sdk;

import junit.framework.TestCase;

import javax.net.ssl.HttpsURLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by christophernajar on 9/1/15.
 */
public class MediaFireHttpRequesterTest extends TestCase {

    private final String GET_URL = "https://www.mediafire.com/api/1.4/system/get_info.php?response_format=json";
    private final String POST_URL = "https://www.mediafire.com/api/1.4/system/get_info.php";
    private final String INVALID_GET_URL = "https://www.mediafire.com/api/1.4/invalid/get_info.php?response_format=json";
    private final String INVALID_POST_URL = "https://www.mediafire.com/api/1.4/invalid/get_info.php";

    private final byte[] GET_PAYLOAD = null;
    private final byte[] POST_PAYLOAD = "response_format=json".getBytes();

    private final Map<String, Object> GET_HEADERS = new LinkedHashMap<String, Object>();
    private final Map<String, Object> POST_HEADERS = new LinkedHashMap<String, Object>();

    private MediaFireHttpRequester httpRequester;

    public void setUp() throws Exception {
        super.setUp();
        httpRequester = new MFHttpRequester(new MediaFireHttpsAgent() {
            @Override
            public void configureHttpsUrlConnection(HttpsURLConnection connection) {

            }
        }, 45000, 45000);
    }

    public void tearDown() throws Exception {

    }

    public void testGetValidUrl() throws Exception {
        MediaFireHttpRequest request = new MFHttpRequest(GET_URL, GET_PAYLOAD, GET_HEADERS);
        MediaFireHttpResponse response = httpRequester.get(request);
        assertTrue(response.getStatusCode() == 200);
    }

    public void testPostValidUrl() throws Exception {
        MediaFireHttpRequest request = new MFHttpRequest(POST_URL, POST_PAYLOAD, POST_HEADERS);
        MediaFireHttpResponse response = httpRequester.post(request);
        assertTrue(response.getStatusCode() == 200);
    }

    public void testGetInvalidUrl() throws Exception {
        MediaFireHttpRequest request = new MFHttpRequest(INVALID_GET_URL, GET_PAYLOAD, GET_HEADERS);
        MediaFireHttpResponse response = httpRequester.get(request);
        assertTrue(response.getStatusCode() == 400);
    }

    public void testPostInvalidUrl() throws Exception {
        MediaFireHttpRequest request = new MFHttpRequest(INVALID_POST_URL, POST_PAYLOAD, POST_HEADERS);
        MediaFireHttpResponse response = httpRequester.post(request);
        assertTrue(response.getStatusCode() == 400);
    }

    public void testGetHttpsAgent() throws Exception {
        assertNotNull(httpRequester.getHttpsAgent());
    }
}