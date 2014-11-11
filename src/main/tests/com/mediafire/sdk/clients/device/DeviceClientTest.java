package com.mediafire.sdk.clients.device;

import com.mediafire.sdk.clients.DummyHttpWorker;
import com.mediafire.sdk.clients.DummySessionTokenManager;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Result;
import junit.framework.TestCase;

public class DeviceClientTest extends TestCase {

    HttpWorkerInterface httpWorker = new DummyHttpWorker();
    SessionTokenManagerInterface sessionTokenManager = new DummySessionTokenManager();
    DeviceClient deviceClient = new DeviceClient(httpWorker, sessionTokenManager);

    public void testGetChangesSingleParamUrl() throws Exception {
        Result result = deviceClient.getChanges("some_revision_number");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/device/get_changes.php";

        assertEquals(expected, actual);
    }

    public void testGetChangesMultiParamUrl() throws Exception {
        Result result = deviceClient.getChanges("some_revision_number", "some_device_id");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/device/get_changes.php";

        assertEquals(expected, actual);
    }

    public void testGetChangesSingleParamPayload() throws Exception {
        Result result = deviceClient.getChanges("some_revision_number");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&revision=some_revision_number&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=e6193d41d7a68323ecb6ea845c5fb3ed";

        assertEquals(expected, actual);
    }

    public void testGetChangesMultiParamPayload() throws Exception {
        Result result = deviceClient.getChanges("some_revision_number", "some_device_id");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&revision=some_revision_number&device_id=some_device_id&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=788915397e44397c48cd60fa66b4091a";

        assertEquals(expected, actual);
    }

    public void testGetStatusNoArgUrl() throws Exception {
        Result result = deviceClient.getStatus();

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/device/get_status.php";

        assertEquals(expected, actual);
    }

    public void testGetStatusNoArgPayload() throws Exception {
        Result result = deviceClient.getStatus();

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=3587110338d9ed309df994c8e95ee08b";

        assertEquals(expected, actual);
    }

    public void testGetStatusSingleParamUrl() throws Exception {
        GetStatusParameters.Builder builder = new GetStatusParameters.Builder();
        builder.deviceId("some_device_id");
        GetStatusParameters getStatusParams = builder.build();
        Result result = deviceClient.getStatus(getStatusParams);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/device/get_status.php";

        assertEquals(expected, actual);
    }

    public void testGetStatusMultiParamUrl() throws Exception {
        GetStatusParameters.Builder builder = new GetStatusParameters.Builder();
        builder.deviceId("some_device_id");
        GetStatusParameters getStatusParams = builder.build();
        Result result = deviceClient.getStatus(getStatusParams);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/device/get_status.php";

        assertEquals(expected, actual);
    }

    public void testGetStatusSingleParamPayload() throws Exception {
        GetStatusParameters.Builder builder = new GetStatusParameters.Builder();
        builder.deviceId("some_device_id");
        GetStatusParameters getStatusParams = builder.build();
        Result result = deviceClient.getStatus(getStatusParams);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&device_id=some_device_id&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=e7011038acde262c55eff8527d1fcf6b";

        assertEquals(expected, actual);
    }

    public void testGetStatusMultiParamPayload() throws Exception {
        GetStatusParameters.Builder builder = new GetStatusParameters.Builder();
        builder.deviceId("some_device_id");
        GetStatusParameters getStatusParams = builder.build();
        Result result = deviceClient.getStatus(getStatusParams);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&device_id=some_device_id&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=e7011038acde262c55eff8527d1fcf6b";

        assertEquals(expected, actual);
    }
}