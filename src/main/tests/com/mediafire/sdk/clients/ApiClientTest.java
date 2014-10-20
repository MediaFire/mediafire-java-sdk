package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Chris Najar on 10/19/2014.
 */
public class ApiClientTest {
    Configuration mConfiguration;

    @Before
    public void setUp() throws Exception {
        mConfiguration = new Configuration.Builder("40767").build();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSystemGetStatusGet() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        HostObject hostObject = new HostObject("http", "www", "mediafire.com", "get");
        ApiObject apiObject = new ApiObject("system", "get_status.php");
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.NONE, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, false);
        VersionObject versionObject = new VersionObject("1.2");
        Request request = new Request(hostObject, apiObject, instructionsObject, versionObject);
        request.addQueryParameter("response_format", "json");
        Result result = apiClient.doRequest(request);

        assertNotNull(result);
    }

    @Test
    public void testSystemGetStatusPost() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        HostObject hostObject = new HostObject("http", "www", "mediafire.com", "post");
        ApiObject apiObject = new ApiObject("system", "get_status.php");
        InstructionsObject instructionsObject = new InstructionsObject(BorrowTokenType.NONE, SignatureType.NO_SIGNATURE_REQUIRED, ReturnTokenType.NONE, true);
        VersionObject versionObject = new VersionObject("1.2");
        Request request = new Request(hostObject, apiObject, instructionsObject, versionObject);
        request.addQueryParameter("response_format", "json");
        Result result = apiClient.doRequest(request);

        assertNotNull(result);
    }
}
