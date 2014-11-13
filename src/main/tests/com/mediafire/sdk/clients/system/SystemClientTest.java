package com.mediafire.sdk.clients.system;

import com.mediafire.sdk.clients.DummyHttpWorker;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Result;
import junit.framework.TestCase;

public class SystemClientTest extends TestCase {

    HttpWorkerInterface httpWorker = new DummyHttpWorker();
    SystemClient systemClient = new SystemClient(httpWorker);

    public void testGetInfoUrl() throws Exception {
        Result result = systemClient.getInfo();

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/system/get_info.php";

        assertEquals(expected, actual);
    }


    public void testGetInfoPayload() throws Exception {
        Result result = systemClient.getInfo();

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json";

        assertEquals(expected, actual);
    }
}