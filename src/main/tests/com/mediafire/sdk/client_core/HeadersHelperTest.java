package com.mediafire.sdk.client_core;

import com.mediafire.sdk.test_utility.RequestObjectsForTesting;
import com.mediafire.sdk.http.Request;
import junit.framework.TestCase;

import java.util.Map;

public class HeadersHelperTest extends TestCase {

    /******************************************************************************************************************
     * POST headers
     *****************************************************************************************************************/
    public void testAddHeadersPOSTMethodPOSTParamsNoParam() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 0);

        HeadersHelper headersHelper = new HeadersHelper(request);

        headersHelper.addHeaders();

        Map<String, String> headers = request.getHeaders();

        String acceptCharsetValue = headers.get("Accept-Charset");
        String contentTypeValue = headers.get("Content-Type");
        String contentLengthValue = headers.get("Content-Length");

        boolean charsetValueValid = acceptCharsetValue.equalsIgnoreCase("UTF-8");
        boolean contentTypeValueValid = contentTypeValue.equalsIgnoreCase("application/x-www-form-urlencoded;charset=UTF-8");
        boolean contentLengthValueValid = contentLengthValue.equalsIgnoreCase("0");

        assertTrue(charsetValueValid && contentTypeValueValid && contentLengthValueValid);
    }

    public void testAddHeadersPOSTMethodPOSTParamsSingleParam() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        HeadersHelper headersHelper = new HeadersHelper(request);

        headersHelper.addHeaders();

        Map<String, String> headers = request.getHeaders();

        String acceptCharsetValue = headers.get("Accept-Charset");
        String contentTypeValue = headers.get("Content-Type");
        String contentLengthValue = headers.get("Content-Length");

        boolean charsetValueValid = acceptCharsetValue.equalsIgnoreCase("UTF-8");
        boolean contentTypeValueValid = contentTypeValue.equalsIgnoreCase("application/x-www-form-urlencoded;charset=UTF-8");
        boolean contentLengthValueValid = contentLengthValue.equalsIgnoreCase("11");

        assertTrue(charsetValueValid && contentTypeValueValid && contentLengthValueValid);
    }

    public void testAddHeadersPOSTMethodPOSTParamsMultiParam() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 5);

        HeadersHelper headersHelper = new HeadersHelper(request);

        headersHelper.addHeaders();

        Map<String, String> headers = request.getHeaders();

        String acceptCharsetValue = headers.get("Accept-Charset");
        String contentTypeValue = headers.get("Content-Type");
        String contentLengthValue = headers.get("Content-Length");

        boolean charsetValueValid = acceptCharsetValue.equalsIgnoreCase("UTF-8");
        boolean contentTypeValueValid = contentTypeValue.equalsIgnoreCase("application/x-www-form-urlencoded;charset=UTF-8");
        boolean contentLengthValueValid = contentLengthValue.equalsIgnoreCase("59");

        assertTrue(charsetValueValid && contentTypeValueValid && contentLengthValueValid);
    }

    /******************************************************************************************************************
     * GET headers
     *****************************************************************************************************************/
    public void testAddHeadersGETMethodNoParam() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 0);

        HeadersHelper headersHelper = new HeadersHelper(request);

        headersHelper.addHeaders();

        Map<String, String> headers = request.getHeaders();

        String acceptCharsetValue = headers.get("Accept-Charset");
        int size = headers.size();

        boolean charsetValueValid = acceptCharsetValue.equalsIgnoreCase("UTF-8");
        boolean sizeValid = size == 1;

        assertTrue(charsetValueValid && sizeValid);
    }

    public void testAddHeadersGETMethodSingleParam() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 1);

        HeadersHelper headersHelper = new HeadersHelper(request);

        headersHelper.addHeaders();

        Map<String, String> headers = request.getHeaders();

        String acceptCharsetValue = headers.get("Accept-Charset");
        int size = headers.size();

        boolean charsetValueValid = acceptCharsetValue.equalsIgnoreCase("UTF-8");
        boolean sizeValid = size == 1;

        assertTrue(charsetValueValid && sizeValid);
    }

    public void testAddHeadersGETMethodMultiParam() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 5);

        HeadersHelper headersHelper = new HeadersHelper(request);

        headersHelper.addHeaders();

        Map<String, String> headers = request.getHeaders();

        String acceptCharsetValue = headers.get("Accept-Charset");
        int size = headers.size();

        boolean charsetValueValid = acceptCharsetValue.equalsIgnoreCase("UTF-8");
        boolean sizeValid = size == 1;

        assertTrue(charsetValueValid && sizeValid);
    }
}