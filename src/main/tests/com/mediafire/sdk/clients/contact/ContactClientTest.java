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
        String expected = "response_format=json&first_name=bob&last_name=jones&avatar=some_avatar&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=b10a5b069e1a8003a12b3a73cd2dfeaf";

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
        String expected = "response_format=json&contact_key=some_contact_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=cebbd1d3d4c5cd1dec8346ff66cfd8c9";

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
        String expected = "response_format=json&method=normal&limit=some_limit&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=9a007c434e7e6358a4a5a98a677c7208";

        assertEquals(expected, actual);
    }
}