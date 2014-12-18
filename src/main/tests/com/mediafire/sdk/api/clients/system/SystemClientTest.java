package com.mediafire.sdk.api.clients.system;

import com.mediafire.sdk.config.HttpInterface;
import com.mediafire.sdk.test_utility.DummyHttp;
import com.mediafire.sdk.http.Result;
import junit.framework.TestCase;

public class SystemClientTest extends TestCase {

    HttpInterface httpWorker = new DummyHttp();
    SystemClient systemClient = new SystemClient(httpWorker);

    public void testGetInfoUrl() throws Exception {
        Result result = systemClient.getInfo();

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/system/get_info.php";

        assertEquals(expected, actual);
    }


    public void testGetInfoPayload() throws Exception {
        Result result = systemClient.getInfo();

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json";

        assertEquals(expected, actual);
    }
}