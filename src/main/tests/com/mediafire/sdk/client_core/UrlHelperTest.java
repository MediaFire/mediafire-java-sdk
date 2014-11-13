package com.mediafire.sdk.client_core;

import com.mediafire.sdk.test_utility.RequestObjectsForTesting;
import com.mediafire.sdk.http.Request;
import junit.framework.TestCase;

public class UrlHelperTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /******************************************************************************************************************
     * POST URL TESTS
     *****************************************************************************************************************/
    
    // testing getRequestSchemeDomainPath
    public void testRequestSchemeDomainPathPOSTMethodPOSTNoQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/some/path/to/a/resource.php";

        assertEquals(expected, actual);
    }

    public void testRequestSchemeDomainPathPOSTMethodPOSTSingleQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/some/path/to/a/resource.php";

        assertEquals(expected, actual);
    }

    public void testRequestSchemeDomainPathPOSTMethodPOSTMultiQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/some/path/to/a/resource.php";

        assertEquals(expected, actual);
    }

    // testing getRequestSchemeDomain
    public void testRequestSchemeDomainPOSTMethodPOSTNoQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomain(true, "post", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/";

        assertEquals(expected, actual);
    }

    public void testRequestSchemeDomainPOSTMethodPOSTSingleQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomain(true, "post", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/";

        assertEquals(expected, actual);
    }

    public void testRequestSchemeDomainPOSTMethodPOSTMultiQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomain(true, "post", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/";

        assertEquals(expected, actual);
    }

    // testing getRequestSchemePath
    public void testRequestSchemePathPOSTMethodPOSTNoQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemePath(true, "post", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/some/path/to/a/resource.php";

        assertEquals(expected, actual);
    }

    public void testRequestSchemePathPOSTMethodPOSTSingleQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemePath(true, "post", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/some/path/to/a/resource.php";

        assertEquals(expected, actual);
    }

    public void testRequestSchemePathPOSTMethodPOSTMultiQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemePath(true, "post", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/some/path/to/a/resource.php";

        assertEquals(expected, actual);
    }

    // testing getRequestScheme
    public void testRequestSchemePOSTMethodPOSTNoQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestScheme(true, "post", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/";

        assertEquals(expected, actual);
    }

    public void testRequestSchemePOSTMethodPOSTSingleQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestScheme(true, "post", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/";

        assertEquals(expected, actual);
    }

    public void testRequestSchemePOSTMethodPOSTMultiQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestScheme(true, "post", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/";

        assertEquals(expected, actual);
    }

    // testing getRequestNoSchemeNoDomainNoPath
    public void testRequestNoSchemeNoDomainNoPathPOSTMethodPOSTNoQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestNoSchemeNoDomainNoPath(true, "post", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "https://www.mediafire.com/";

        assertEquals(expected, actual);
    }

    public void testRequestNoSchemeNoDomainNoPathPOSTMethodPOSTSingleQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestNoSchemeNoDomainNoPath(true, "post", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "https://www.mediafire.com/";

        assertEquals(expected, actual);
    }

    public void testRequestNoSchemeNoDomainNoPathPOSTMethodPOSTMultiQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestNoSchemeNoDomainNoPath(true, "post", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "https://www.mediafire.com/";

        assertEquals(expected, actual);
    }

    /******************************************************************************************************************
     * GET URL TESTS
     *****************************************************************************************************************/

    // testing getRequestSchemeDomainPath
    public void testRequestSchemeDomainPathGETMethodNoQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/some/path/to/a/resource.php";

        assertEquals(expected, actual);
    }

    public void testRequestSchemeDomainPathGETMethodSingleQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/some/path/to/a/resource.php?key0=value0";

        assertEquals(expected, actual);
    }

    public void testRequestSchemeDomainPathGETMethodMultiQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/some/path/to/a/resource.php?key0=value0&key1=value1&key2=value2&key3=value3&key4=value4";

        assertEquals(expected, actual);
    }

    // testing getRequestSchemeDomain
    public void testRequestSchemeDomainGETMethodNoQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomain(false, "get", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/";

        assertEquals(expected, actual);
    }

    public void testRequestSchemeDomainGETMethodSingleQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomain(false, "get", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/?key0=value0";

        assertEquals(expected, actual);
    }

    public void testRequestSchemeDomainGETMethodMultiQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomain(false, "get", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://subdomain.domain.top-level-domain/?key0=value0&key1=value1&key2=value2&key3=value3&key4=value4";

        assertEquals(expected, actual);
    }

    // testing getRequestSchemePath
    public void testRequestSchemePathGETMethodNoQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemePath(false, "get", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/some/path/to/a/resource.php";

        assertEquals(expected, actual);
    }

    public void testRequestSchemePathGETMethodSingleQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemePath(false, "get", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/some/path/to/a/resource.php?key0=value0";

        assertEquals(expected, actual);
    }

    public void testRequestSchemePathGETMethodMultiQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemePath(false, "get", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/some/path/to/a/resource.php?key0=value0&key1=value1&key2=value2&key3=value3&key4=value4";

        assertEquals(expected, actual);
    }

    // testing getRequestScheme
    public void testRequestSchemeGETMethodNoQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestScheme(false, "get", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/";

        assertEquals(expected, actual);
    }

    public void testRequestSchemeGETMethodSingleQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestScheme(false, "get", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/?key0=value0";

        assertEquals(expected, actual);
    }

    public void testRequestSchemeGETMethodMultiQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestScheme(false, "get", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "scheme://www.mediafire.com/?key0=value0&key1=value1&key2=value2&key3=value3&key4=value4";

        assertEquals(expected, actual);
    }

    // testing getRequestNoSchemeNoDomainNoPath
    public void testRequestNoSchemeNoDomainNoPathGETMethodNoQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestNoSchemeNoDomainNoPath(false, "get", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "https://www.mediafire.com/";

        assertEquals(expected, actual);
    }

    public void testRequestNoSchemeNoDomainNoPathGETMethodSingleQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestNoSchemeNoDomainNoPath(false, "get", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "https://www.mediafire.com/?key0=value0";

        assertEquals(expected, actual);
    }

    public void testRequestNoSchemeNoDomainNoPathGETMethodMultiQueryParams() throws Exception {
        Request request = RequestObjectsForTesting.getRequestNoSchemeNoDomainNoPath(false, "get", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getUrlForRequest();
        String expected = "https://www.mediafire.com/?key0=value0&key1=value1&key2=value2&key3=value3&key4=value4";

        assertEquals(expected, actual);
    }

    /******************************************************************************************************************
     * Path tests
     *****************************************************************************************************************/
    public void testGetPathStringForNoPath() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomain(true, "post", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getPathString();
        String expected = "/";

        assertEquals(expected, actual);
    }

    public void testGetPathStringForPathPassed() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getPathString();
        String expected = "/some/path/to/a/resource.php";

        assertEquals(expected, actual);
    }

    /******************************************************************************************************************
     * Query tests
     *****************************************************************************************************************/
    public void testGetQueryStringPOSTMethodNoQueryParamsEncodedRaw() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, true);
        String expected = "";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringPOSTMethodSingleQueryParamsEncodedRaw() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, true);
        String expected = "key0=value0";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringPOSTMethodMultiQueryParamsEncodedRaw() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, true);
        String expected = "key0=value0&key1=value1&key2=value2&key3=value3&key4=value4";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringGETMethodNoQueryParamsEncodedRaw() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, true);
        String expected = "";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringGETMethodSingleQueryParamsEncodedRaw() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, true);
        String expected = "key0=value0";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringGETMethodMultiQueryParamsEncodedRaw() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, true);
        String expected = "key0=value0&key1=value1&key2=value2&key3=value3&key4=value4";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringPOSTMethodNoQueryParamsEncodedAsQuery() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, false);
        String expected = "";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringPOSTMethodSingleQueryParamsEncodedAsQuery() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, false);
        String expected = "?key0=value0";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringPOSTMethodMultiQueryParamsEncodedAsQuery() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, false);
        String expected = "?key0=value0&key1=value1&key2=value2&key3=value3&key4=value4";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringGETMethodNoQueryParamsEncodedAsQuery() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 0);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, false);
        String expected = "";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringGETMethodSingleQueryParamsEncodedAsQuery() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 1);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, false);
        String expected = "?key0=value0";

        assertEquals(expected, actual);
    }

    public void testGetQueryStringGETMethodMultiQueryParamsEncodedAsQuery() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(false, "get", 5);

        UrlHelper urlHelper = new UrlHelper(request);

        String actual = urlHelper.getQueryString(true, false);
        String expected = "?key0=value0&key1=value1&key2=value2&key3=value3&key4=value4";

        assertEquals(expected, actual);
    }
}