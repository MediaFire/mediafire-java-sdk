package com.mediafire.sdk.client;

import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    public void testDoRequest() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        Request request = new RequestGenerator().generateRequestObject("1.0", "system", "get_info.php");
        Result result = apiClient.doRequest(request);

        Response response = result.getResponse();

        System.out.println("response code: " + response.getStatus());
        System.out.println("response: " + new String(response.getBytes()));
        System.out.println("response headers: " + response.getHeaders());
    }
}
