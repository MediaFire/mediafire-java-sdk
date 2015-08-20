package com.mediafire.sdk.util;

import junit.framework.TestCase;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestUtilTest extends TestCase {

    private static final Map<String, Object> HEADERS = new LinkedHashMap<String, Object>();
    static {
        HEADERS.put("some_header", "something");
    }

    private static final LinkedHashMap<String, Object> QUERY = new LinkedHashMap<String, Object>();
    static {
        QUERY.put("response_format", "json");
        QUERY.put("other_key", "encoded---- ;?/:#&=+$, %<>~% ----encoded");
    }

    private static final byte[] UPLOAD_PAYLOAD = "payload".getBytes();

    private static final ApiPostRequest API_REQUEST = new ApiPostRequest("scheme", "domain", "/path", QUERY);
    private static final UploadPostRequest UPLOAD_REQUEST = new UploadPostRequest("scheme", "domain", "/path", QUERY, HEADERS, UPLOAD_PAYLOAD);
    private static final ImageRequest IMAGE_REQUEST = new ImageRequest("fd56", "h686zgn6bx3nj7r", '6', false);

    private static final String API_REQUEST_URL = "scheme://domain/path";
    private static final String UPLOAD_REQUEST_URL = "scheme://domain/path?response_format=json&other_key=encoded----+%3B%3F%2F%3A%23%26%3D%2B%24%2C+%25%3C%3E%7E%25+----encoded";
    private static final String IMAGE_REQUEST_URL = "https://www.mediafire.com/conversion_server.php?fd56&quickkey=h686zgn6bx3nj7r&doc_type=i&size_id=6&session_token=abcd1234";

    private static final String UNENCODED_QUERY = "response_format=json&other_key=encoded---- ;?/:#&=+$, %<>~% ----encoded";
    private static final String ENCODED_QUERY = "response_format=json&other_key=encoded----+%3B%3F%2F%3A%23%26%3D%2B%24%2C+%25%3C%3E%7E%25+----encoded";
    private static final ActionToken DUMMY_ACTION_TOKEN = new ActionToken("abcd1234", 1000);

    public void setUp() throws Exception {
        super.setUp();
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
        assertEquals(IMAGE_REQUEST_URL, new GetRequest(IMAGE_REQUEST, DUMMY_ACTION_TOKEN).getUrl());
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

    public void testSignatureCalculation() {
        LinkedHashMap<String, Object> query = new LinkedHashMap<String, Object>();
        query.put("session_token", "48e6312cf575eaabdeb9e0712b5005edc9ce0d086f6c36a027dd6eb51e627ff6c85e181191ffbf56eee0fa3734a75fcc31dd22bd914efbd414e37061ab5a53681745778019458878");
        query.put("response_format", "json");
        ApiPostRequest apiPostRequest = new ApiPostRequest("/api/1.4/user/get_info.php", query);
        String signature = RequestUtil.makeSignatureForApiRequest(1735586625, "1431991058.7072", apiPostRequest);
        assertEquals("49d837ce846c1667f21d687dbc23a654", signature);
    }
}