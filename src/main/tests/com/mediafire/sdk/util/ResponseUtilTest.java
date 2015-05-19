package com.mediafire.sdk.util;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import com.mediafire.sdk.api.UserApi;
import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.UserGetAvatarResponse;
import com.mediafire.sdk.api.responses.UserGetInfoResponse;
import com.mediafire.sdk.api.responses.UserGetSettingsResponse;
import com.mediafire.sdk.requests.HttpApiResponse;
import junit.framework.TestCase;

import java.util.*;

public class ResponseUtilTest extends TestCase {

    private static final byte[] INVALID_RESPONSE_BYTES_1 = new byte[0];
    private static final byte[] INVALID_RESPONSE_BYTES_2 = null;
    private static final byte[] VALID_RESPONSE_BYTES = "some response".getBytes();

    private static final int INVALID_RESPONSE_CODE_1 = 99;
    private static final int INVALID_RESPONSE_CODE_2 = 0;
    private static final int VALID_RESPONSE_CODE = 200;

    private static final Map<String, List<String>> INVALID_RESPONSE_HEADERS_1 = new HashMap<String, List<String>>();
    private static final Map<String, List<String>> INVALID_RESPONSE_HEADERS_2 = null;
    private static final Map<String, List<String>> VALID_RESPONSE_HEADERS = new HashMap<String, List<String>>();
    static {
        List<String> headerValues = new LinkedList<String>();
        headerValues.add("*");
        VALID_RESPONSE_HEADERS.put("Access-Control-Allow-Origin", headerValues);
        VALID_RESPONSE_HEADERS.put("API-Response-Time", headerValues);
        VALID_RESPONSE_HEADERS.put("Cache-Control", headerValues);
        VALID_RESPONSE_HEADERS.put("Connection", headerValues);
        VALID_RESPONSE_HEADERS.put("Content-Encoding", headerValues);
        VALID_RESPONSE_HEADERS.put("Content-Type", headerValues);
        VALID_RESPONSE_HEADERS.put("Date", headerValues);
        VALID_RESPONSE_HEADERS.put("Etag", headerValues);
        VALID_RESPONSE_HEADERS.put("Server", headerValues);
        VALID_RESPONSE_HEADERS.put("Transfer-Encoding", headerValues);
        VALID_RESPONSE_HEADERS.put("X-AX-Real-Port", headerValues);
        VALID_RESPONSE_HEADERS.put("X-AX-Real-Server", headerValues);
    }

    private static final HttpApiResponse VALID_RESPONSE = new HttpApiResponse(VALID_RESPONSE_CODE, VALID_RESPONSE_BYTES, VALID_RESPONSE_HEADERS);
    private static final HttpApiResponse INVALID_RESPONSE_1 = new HttpApiResponse(VALID_RESPONSE_CODE, INVALID_RESPONSE_BYTES_1, VALID_RESPONSE_HEADERS);
    private static final HttpApiResponse INVALID_RESPONSE_2 = new HttpApiResponse(VALID_RESPONSE_CODE, INVALID_RESPONSE_BYTES_2, VALID_RESPONSE_HEADERS);
    private static final HttpApiResponse INVALID_RESPONSE_3 = new HttpApiResponse(VALID_RESPONSE_CODE, VALID_RESPONSE_BYTES, INVALID_RESPONSE_HEADERS_1);
    private static final HttpApiResponse INVALID_RESPONSE_4 = new HttpApiResponse(VALID_RESPONSE_CODE, VALID_RESPONSE_BYTES, INVALID_RESPONSE_HEADERS_2);
    private static final HttpApiResponse INVALID_RESPONSE_5 = new HttpApiResponse(INVALID_RESPONSE_CODE_1, VALID_RESPONSE_BYTES, INVALID_RESPONSE_HEADERS_2);
    private static final HttpApiResponse INVALID_RESPONSE_6 = new HttpApiResponse(INVALID_RESPONSE_CODE_2, VALID_RESPONSE_BYTES, INVALID_RESPONSE_HEADERS_2);

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testValidateHttpResponse() throws Exception {
        ResponseUtil.validateHttpResponse(VALID_RESPONSE);
    }

    public void testInvalidHttpResponse1() {
        try {
            ResponseUtil.validateHttpResponse(INVALID_RESPONSE_1);
            fail("invalid response should have thrown exception");
        } catch (MFException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    public void testInvalidHttpResponse2() {
        try {
            ResponseUtil.validateHttpResponse(INVALID_RESPONSE_2);
            fail("invalid response should have thrown exception");
        } catch (MFException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    public void testInvalidHttpResponse3() {
        try {
            ResponseUtil.validateHttpResponse(INVALID_RESPONSE_3);
            fail("invalid response should have thrown exception");
        } catch (MFException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    public void testInvalidHttpResponse4() {
        try {
            ResponseUtil.validateHttpResponse(INVALID_RESPONSE_4);
            fail("invalid response should have thrown exception");
        } catch (MFException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    public void testInvalidHttpResponse5() {
        try {
            ResponseUtil.validateHttpResponse(INVALID_RESPONSE_5);
            fail("invalid response should have thrown exception");
        } catch (MFException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    public void testInvalidHttpResponse6() {
        try {
            ResponseUtil.validateHttpResponse(INVALID_RESPONSE_6);
            fail("invalid response should have thrown exception");
        } catch (MFException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    public void testInvalidHttpResponseNull() {
        try {
            ResponseUtil.validateHttpResponse(null);
            fail("invalid response should have thrown exception");
        } catch (MFException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    /******************************************************************************************************************
     *
     *****************************************************************************************************************/

    public void testNullHttpApiResponsePassedToResponseUtil() {
        try {
            ResponseUtil.makeApiResponseFromHttpResponse(null, ApiResponse.class);
            fail("exception should have been thrown");
        } catch (MFException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    public void testMalformedHttpApiResponsePassedToResponseUtil() {
        try {
            byte[] bytes = "{\"response\":{\"action\":\"fake\\/api\",\"message\":\"Session Token is missing\",\"error\":\"104\",\"result\":\"Error\",\"current_api_version\":\"1.3\"}}\0".getBytes();
            HttpApiResponse httpApiResponse = new HttpApiResponse(400, bytes, null);
            ApiResponse response = ResponseUtil.makeApiResponseFromHttpResponse(httpApiResponse, ApiResponse.class);
            fail("exception should have been thrown");
        } catch (MFException e) {
            assertTrue(e.getMessage(), true);
        }
    }
}