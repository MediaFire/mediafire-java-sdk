package com.mediafire.sdk.client;

import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;
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
    public void testResultNotNull() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        Request request = new RequestGenerator().generateRequestObject("1.0", "system", "get_info.php");
        request.addQueryParameter("response_format", "json");
        Result result = apiClient.doRequest(request);

        assertNotNull(result);
    }

    @Test
    public void testResultResponseNotNull() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        Request request = new RequestGenerator().generateRequestObject("1.0", "system", "get_info.php");
        request.addQueryParameter("response_format", "json");
        Result result = apiClient.doRequest(request);

        Response response = result.getResponse();

        assertNotNull(response);
    }

    @Test
    public void testResultRequestNotNull() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        Request request = new RequestGenerator().generateRequestObject("1.0", "system", "get_info.php");
        request.addQueryParameter("response_format", "json");
        Result result = apiClient.doRequest(request);

        Request requestFromResult = result.getRequest();

        assertNotNull(requestFromResult);
    }
}
