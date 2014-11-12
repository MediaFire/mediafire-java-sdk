package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.api.ApiRequestGenerator;
import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Request;
import junit.framework.TestCase;

public class ApiRequestGeneratorTest extends TestCase {

    /*****************************************************************************************************************
     * VERSIONED
     *****************************************************************************************************************/
    public void testCreateRequestObjectVersionedPath() throws Exception {
        String currentVersion = ApiVersion.VERSION_1_2;
        ApiRequestGenerator apiRequestGenerator = new ApiRequestGenerator();
        Request request = apiRequestGenerator.createRequestObjectFromPath("file/get_info.php");

        String actual = request.getPath();
        String expected = "api/" + currentVersion + "/file/get_info.php";

        assertEquals(expected, actual);
    }

    public void testCreateRequestObjectVersionedDomain() throws Exception {
        ApiRequestGenerator apiRequestGenerator = new ApiRequestGenerator();
        Request request = apiRequestGenerator.createRequestObjectFromPath("file/get_info.php");

        String actual = request.getFullDomain();
        String expected = "www.mediafire.com";

        assertEquals(expected, actual);
    }

    public void testCreateRequestObjectVersionedScheme() throws Exception {
        ApiRequestGenerator apiRequestGenerator = new ApiRequestGenerator();
        Request request = apiRequestGenerator.createRequestObjectFromPath("file/get_info.php");

        String actual = request.getScheme();
        String expected = "https";

        assertEquals(expected, actual);
    }

    public void testCreateRequestObjectVersionedHttpMethod() throws Exception {
        ApiRequestGenerator apiRequestGenerator = new ApiRequestGenerator();
        Request request = apiRequestGenerator.createRequestObjectFromPath("file/get_info.php");

        String actual = request.getHttpMethod();
        String expected = "post";

        assertEquals(expected, actual);
    }

    /*****************************************************************************************************************
     * UNVERSIONED
     *****************************************************************************************************************/
    public void testCreateRequestObjectUnversionedPath() throws Exception {
        ApiRequestGenerator apiRequestGenerator = new ApiRequestGenerator();
        Request request = apiRequestGenerator.createRequestObjectFromPath("file/get_info.php");

        String actual = request.getPath();
        String expected = "api/1.2/file/get_info.php";

        assertEquals(expected, actual);
    }

    public void testCreateRequestObjectUnversionedDomain() throws Exception {
        ApiRequestGenerator apiRequestGenerator = new ApiRequestGenerator();
        Request request = apiRequestGenerator.createRequestObjectFromPath("file/get_info.php");

        String actual = request.getFullDomain();
        String expected = "www.mediafire.com";

        assertEquals(expected, actual);
    }

    public void testCreateRequestObjectUnversionedScheme() throws Exception {
        ApiRequestGenerator apiRequestGenerator = new ApiRequestGenerator();
        Request request = apiRequestGenerator.createRequestObjectFromPath("file/get_info.php");

        String actual = request.getScheme();
        String expected = "https";

        assertEquals(expected, actual);
    }

    public void testCreateRequestObjectUnversionedHttpMethod() throws Exception {
        ApiRequestGenerator apiRequestGenerator = new ApiRequestGenerator();
        Request request = apiRequestGenerator.createRequestObjectFromPath("file/get_info.php");

        String actual = request.getHttpMethod();
        String expected = "post";

        assertEquals(expected, actual);
    }
}