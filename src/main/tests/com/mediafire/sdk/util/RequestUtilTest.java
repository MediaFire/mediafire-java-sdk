package com.mediafire.sdk.util;

import com.mediafire.sdk.requests.ApiPostRequest;
import com.mediafire.sdk.requests.ImageRequest;
import com.mediafire.sdk.requests.UploadPostRequest;
import junit.framework.TestCase;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestUtilTest extends TestCase {

    private static final Map<String, Object> QUERY = new LinkedHashMap<String, Object>();
    static {
        QUERY.put("response_format", "json");
        QUERY.put("other_key", "encoded---- ;?/:#&=+$, %<>~% ----encoded");
    }

    private static final byte[] UPLOAD_PAYLOAD = "payload".getBytes();

    private static final ApiPostRequest API_REQUEST = new ApiPostRequest("scheme", "domain", "/path", QUERY);
    private static final UploadPostRequest UPLOAD_REQUEST = new UploadPostRequest("scheme", "domain", "/path", QUERY, UPLOAD_PAYLOAD);
    private static final ImageRequest IMAGE_REQUEST = new ImageRequest("fd56", "h686zgn6bx3nj7r", "6", false);

    private static final String API_REQUEST_URL = "scheme://domain/path";
    private static final String UPLOAD_REQUEST_URL = "scheme://domain/path?response_format=json&other_key=encoded----+%3B%3F%2F%3A%23%26%3D%2B%24%2C+%25%3C%3E%7E%25+----encoded";
    private static final String IMAGE_REQUEST_URL = "";

    private static final String UNENCODED_QUERY = "response_format=json&other_key=encoded---- ;?/:#&=+$, %<>~% ----encoded";
    private static final String ENCODED_QUERY = "response_format=json&other_key=encoded----+%3B%3F%2F%3A%23%26%3D%2B%24%2C+%25%3C%3E%7E%25+----encoded";




    private long startTime;
    public void setUp() throws Exception {
        super.setUp();
        startTime = System.currentTimeMillis();
    }

    public void tearDown() throws Exception {
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(getName() + " execution time: " + elapsedTime + "ms");
    }

    public void testMakeHeadersFromApiRequestContainsKeyAcceptCharset() throws Exception {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(API_REQUEST);
        assertTrue(headers.containsKey("Accept-Charset"));
    }

    public void testMakeHeadersFromApiRequestContainsKeyContentLength() throws Exception {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(API_REQUEST);
        assertTrue(headers.containsKey("Content-Length"));
    }

    public void testMakeHeadersFromApiRequestContainsKeyContentType() throws Exception {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(API_REQUEST);
        assertTrue(headers.containsKey("Content-Type"));
    }

    public void testMakeHeadersFromApiRequestContainsValueAcceptCharset() throws Exception {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(API_REQUEST);
        assertTrue("UTF-8".equals(headers.get("Accept-Charset")));
    }

    public void testMakeHeadersFromApiRequestContainsValueContentLength() throws Exception {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(API_REQUEST);
        assertTrue(String.valueOf(ENCODED_QUERY.getBytes().length).equals(headers.get("Content-Length")));
    }

    public void testMakeHeadersFromApiRequestContainsValueContentType() throws Exception {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(API_REQUEST);
        assertTrue("application/x-www-form-urlencoded;charset=UTF-8".equals(headers.get("Content-Type")));
    }

    public void testMakeEncodedQueryPayloadFromApiRequest() throws Exception {
        assertEquals(ENCODED_QUERY, RequestUtil.makeQueryStringFromMap(QUERY, true));
    }

    public void testMakeUnEncodedQueryPayloadFromApiRequest() throws Exception {
        assertEquals(UNENCODED_QUERY, RequestUtil.makeQueryStringFromMap(QUERY, false));
    }

    public void testMakeUrlFromApiRequest() throws Exception {
        assertEquals(API_REQUEST_URL, RequestUtil.makeUrlFromApiRequest(API_REQUEST));
    }

    public void testMakeUrlFromUploadRequest() throws Exception {
        assertEquals(UPLOAD_REQUEST_URL, RequestUtil.makeUrlFromUploadRequest(UPLOAD_REQUEST));
    }

    public void testMakeUrlFromImageRequest() throws Exception {
//        assertEquals(IMAGE_REQUEST_URL, RequestUtil.makeUrlFromImageRequest(IMAGE_REQUEST));
        fail("image request not implemented yet");
    }

    public void testMakeHeadersFromUploadRequestContainsKeyAcceptCharset() throws Exception {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(API_REQUEST);
        assertTrue(headers.containsKey("Accept-Charset"));
    }

    public void testMakeHeadersFromUploadRequestContainsKeyContentLength() throws Exception {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(API_REQUEST);
        assertTrue(headers.containsKey("Content-Length"));
    }

    public void testMakeHeadersFromUploadRequestContainsValueAcceptCharset() throws Exception {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(API_REQUEST);

        assertTrue("UTF-8".equals(headers.get("Accept-Charset")));
    }

    public void testMakeHeadersFromUploadRequestContainsValueContentLength() throws Exception {
        Map<String, String> headers = RequestUtil.makeHeadersFromApiRequest(API_REQUEST);
        assertTrue(String.valueOf(ENCODED_QUERY.getBytes().length).equals(headers.get("Content-Length")));
    }
}