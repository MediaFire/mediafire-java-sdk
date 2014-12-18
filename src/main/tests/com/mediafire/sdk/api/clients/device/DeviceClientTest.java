package com.mediafire.sdk.api.clients.device;

import com.mediafire.sdk.config.HttpInterface;
import com.mediafire.sdk.test_utility.DummyHttp;
import com.mediafire.sdk.test_utility.DummySessionTokenManager;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Result;
import junit.framework.TestCase;

public class DeviceClientTest extends TestCase {

    HttpInterface httpWorker = new DummyHttp();
    SessionTokenManagerInterface sessionTokenManager = new DummySessionTokenManager();
    DeviceClient deviceClient = new DeviceClient(httpWorker, sessionTokenManager);

    public void testGetChangesSingleParamUrl() throws Exception {
        Result result = deviceClient.getChanges("some_revision_number");

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/device/get_changes.php";

        assertEquals(expected, actual);
    }

    public void testGetChangesMultiParamUrl() throws Exception {
        Result result = deviceClient.getChanges("some_revision_number", "some_device_id");

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/device/get_changes.php";

        assertEquals(expected, actual);
    }

    public void testGetChangesSingleParamPayload() throws Exception {
        Result result = deviceClient.getChanges("some_revision_number");

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&revision=some_revision_number&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=4de2705b1b6df0069527d371d238625b";

        assertEquals(expected, actual);
    }

    public void testGetChangesMultiParamPayload() throws Exception {
        Result result = deviceClient.getChanges("some_revision_number", "some_device_id");

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&revision=some_revision_number&device_id=some_device_id&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=b2cb3570339c01352e534ac72a087a6a";

        assertEquals(expected, actual);
    }

    public void testGetStatusNoArgUrl() throws Exception {
        Result result = deviceClient.getStatus();

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/device/get_status.php";

        assertEquals(expected, actual);
    }

    public void testGetStatusNoArgPayload() throws Exception {
        Result result = deviceClient.getStatus();

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=06f0ed954735bc596e14a4ff33b51e3c";

        assertEquals(expected, actual);
    }

    public void testGetStatusSingleParamUrl() throws Exception {
        GetStatusParameters.Builder builder = new GetStatusParameters.Builder();
        builder.deviceId("some_device_id");
        GetStatusParameters getStatusParams = builder.build();
        Result result = deviceClient.getStatus(getStatusParams);

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/device/get_status.php";

        assertEquals(expected, actual);
    }

    public void testGetStatusMultiParamUrl() throws Exception {
        GetStatusParameters.Builder builder = new GetStatusParameters.Builder();
        builder.deviceId("some_device_id");
        GetStatusParameters getStatusParams = builder.build();
        Result result = deviceClient.getStatus(getStatusParams);

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/device/get_status.php";

        assertEquals(expected, actual);
    }

    public void testGetStatusSingleParamPayload() throws Exception {
        GetStatusParameters.Builder builder = new GetStatusParameters.Builder();
        builder.deviceId("some_device_id");
        GetStatusParameters getStatusParams = builder.build();
        Result result = deviceClient.getStatus(getStatusParams);

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&device_id=some_device_id&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=f4fd66b933bc9f83557defcc1525e23a";

        assertEquals(expected, actual);
    }

    public void testGetStatusMultiParamPayload() throws Exception {
        GetStatusParameters.Builder builder = new GetStatusParameters.Builder();
        builder.deviceId("some_device_id");
        GetStatusParameters getStatusParams = builder.build();
        Result result = deviceClient.getStatus(getStatusParams);

        DummyHttp.DummyPOSTResponse response = (DummyHttp.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&device_id=some_device_id&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=f4fd66b933bc9f83557defcc1525e23a";

        assertEquals(expected, actual);
    }
}