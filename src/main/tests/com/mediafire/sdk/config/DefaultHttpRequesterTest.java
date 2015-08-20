package com.mediafire.sdk.config;

import com.mediafire.sdk.DefaultHttpRequester;
import com.mediafire.sdk.ApiPostRequest;
import com.mediafire.sdk.PostRequest;
import junit.framework.TestCase;

import java.util.LinkedHashMap;

public class DefaultHttpRequesterTest extends TestCase {

    private static final LinkedHashMap<String, Object> QUERY = new LinkedHashMap<String, Object>();
    static {
        QUERY.put("response_format", "json");
    }
    private static final ApiPostRequest HTTP_API_REQUEST = new ApiPostRequest("http", "www.mediafire.com", "/api/1.4/system/get_version.php", QUERY);
    private static final ApiPostRequest HTTPS_API_REQUEST = new ApiPostRequest("https", "www.mediafire.com", "/api/1.4/system/get_version.php", QUERY);
    private static final PostRequest HTTP_POST_REQUEST = new PostRequest(HTTP_API_REQUEST);
    private static final PostRequest HTTPS_POST_REQUEST = new PostRequest(HTTPS_API_REQUEST);

    private DefaultHttpRequester requester;

    public void setUp() throws Exception {
        super.setUp();
        requester = new DefaultHttpRequester(5000, 5000);
    }

    public void testHttpResponseStatus200SystemGetVersion() throws Exception {
        HttpApiResponse response = requester.doApiRequest(HTTP_POST_REQUEST);
        assertEquals(response.getStatus(), 200);
    }

    public void testHttpResponseByteLengthSystemGetVersion() throws Exception {
        HttpApiResponse response = requester.doApiRequest(HTTP_POST_REQUEST);
        assertEquals(response.getBytes().length, 92);
    }

    public void testHttpsResponseStatus200SystemGetVersion() throws Exception {
        HttpApiResponse response = requester.doApiRequest(HTTPS_POST_REQUEST);
        assertEquals(response.getStatus(), 200);
    }

    public void testHttpsResponseByteLengthSystemGetVersion() throws Exception {
        HttpApiResponse response = requester.doApiRequest(HTTPS_POST_REQUEST);
        assertEquals(response.getBytes().length, 92);
    }

}