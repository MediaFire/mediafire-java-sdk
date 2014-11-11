package com.mediafire.sdk.clients.contact;

import com.mediafire.sdk.clients.DummyHttpWorker;
import com.mediafire.sdk.clients.DummySessionTokenManager;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.ApiVersion;
import com.mediafire.sdk.http.Result;
import junit.framework.TestCase;

public class ContactClientTest extends TestCase {

    SessionTokenManagerInterface sessionTokenManager = new DummySessionTokenManager();
    HttpWorkerInterface httpWorker = new DummyHttpWorker();
    ContactClient contactClient = new ContactClient(httpWorker, sessionTokenManager);

    public void testAddUrl() throws Exception {

        AddParameters.Builder builder = new AddParameters.Builder();
        builder.avatar("some_avatar").firstName("bob").lastName("jones");
        AddParameters addParameters = builder.build();
        Result result = contactClient.add(addParameters);

        DummyHttpWorker.DummyPOSTResponse dummyPOSTResponse = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();

        String actual = dummyPOSTResponse.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/" + ApiVersion.VERSION_CURRENT + "/contact/add.php";

        assertEquals(expected, actual);
    }

    public void testAddPayload() throws Exception {
        AddParameters.Builder builder = new AddParameters.Builder();
        builder.avatar("some_avatar").firstName("bob").lastName("jones");
        AddParameters addParameters = builder.build();
        Result result = contactClient.add(addParameters);

        DummyHttpWorker.DummyPOSTResponse dummyPOSTResponse = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();

        String actual = new String(dummyPOSTResponse.getOriginalPayload());
        String expected = "response_format=json&first_name=bob&last_name=jones&avatar=some_avatar&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=de905580af513e447682e2a40256be18";

        assertEquals(expected, actual);
    }

    public void testDeleteUrl() throws Exception {
        Result result = contactClient.delete("some_contact_key");

        DummyHttpWorker.DummyPOSTResponse dummyPOSTResponse = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();

        String actual = dummyPOSTResponse.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/" + ApiVersion.VERSION_CURRENT + "/contact/delete.php";

        assertEquals(expected, actual);
    }

    public void testDeletePayload() throws Exception {
        Result result = contactClient.delete("some_contact_key");

        DummyHttpWorker.DummyPOSTResponse dummyPOSTResponse = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();

        String actual = new String(dummyPOSTResponse.getOriginalPayload());
        String expected = "response_format=json&contact_key=some_contact_key&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=a56570a96367845aaeddd8893dbe39ca";

        assertEquals(expected, actual);
    }

    public void testFetchUrl() throws Exception {
        FetchParameters.Builder builder = new FetchParameters.Builder();
        builder.limit("some_limit").method(FetchParameters.Builder.Method.NORMAL);
        FetchParameters fetchParameters = builder.build();
        Result result = contactClient.fetch(fetchParameters);

        DummyHttpWorker.DummyPOSTResponse dummyPOSTResponse = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();

        String actual = dummyPOSTResponse.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/contact/fetch.php";

        assertEquals(expected, actual);
    }

    public void testFetchPayload() throws Exception {
        FetchParameters.Builder builder = new FetchParameters.Builder();
        builder.limit("some_limit").method(FetchParameters.Builder.Method.NORMAL);
        FetchParameters fetchParameters = builder.build();
        Result result = contactClient.fetch(fetchParameters);

        DummyHttpWorker.DummyPOSTResponse dummyPOSTResponse = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();

        String actual = new String(dummyPOSTResponse.getOriginalPayload());
        String expected = "response_format=json&method=normal&limit=some_limit&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=27bf7d91221f1fc11540c72ac13911df";

        assertEquals(expected, actual);
    }
}