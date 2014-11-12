package com.mediafire.sdk.api.clients.folder;

import com.mediafire.sdk.api.clients.DummyHttpWorker;
import com.mediafire.sdk.api.clients.DummySessionTokenManager;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Result;
import junit.framework.TestCase;

public class FolderClientTest extends TestCase {

    HttpWorkerInterface httpWorker = new DummyHttpWorker();
    SessionTokenManagerInterface sessionManager = new DummySessionTokenManager();
    FolderClient folderClient = new FolderClient(httpWorker, sessionManager);

    public void testCopySingleParamUrl() throws Exception {
        Result result = folderClient.copy("some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/copy.php";

        assertEquals(expected, actual);
    }

    public void testCopyMultiParamUrl() throws Exception {
        Result result = folderClient.copy("some_folder_key", "some_destination_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/copy.php";

        assertEquals(expected, actual);
    }

    public void testCopySingleParamPayload() throws Exception {
        Result result = folderClient.copy("some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key_src=some_folder_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=27b512e24ee70c23a08df6e890b72f8f";

        assertEquals(expected, actual);
    }

    public void testCopyMultiParamPayload() throws Exception {
        Result result = folderClient.copy("some_folder_key", "some_destination_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key_src=some_folder_key&folder_key_dst=some_destination_folder_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=a7003181a232af69600baec63590f524";

        assertEquals(expected, actual);
    }

    public void testCreateBaseParamUrl() throws Exception {
        Result result = folderClient.create("some_new_folder_name", "some_parent_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/create.php";

        assertEquals(expected, actual);
    }

    public void testCreateMultiParamUrl() throws Exception {
        CreateParameters.Builder builder = new CreateParameters.Builder();
        builder.allowDuplicateName(true).mTime("some_mtime").parentKey("some_parent_folder_key");
        CreateParameters createParameters = builder.build();
        Result result = folderClient.create("some_new_folder_name", createParameters);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/create.php";

        assertEquals(expected, actual);
    }

    public void testCreateBaseParamPayload() throws Exception {
        Result result = folderClient.create("some_new_folder_name", "some_parent_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&foldername=some_new_folder_name&parent_key=some_parent_folder_key&allow_duplicate_name=yes&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=d2f0fe1c283917b747eb06269a4feddd";

        assertEquals(expected, actual);
    }

    public void testCreateMultiParamPayload() throws Exception {
        CreateParameters.Builder builder = new CreateParameters.Builder();
        builder.allowDuplicateName(true).mTime("some_mtime").parentKey("some_parent_folder_key");
        CreateParameters createParameters = builder.build();
        Result result = folderClient.create("some_new_folder_name", createParameters);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&foldername=some_new_folder_name&parent_key=some_parent_folder_key&allow_duplicate_name=yes&mtime=some_mtime&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=6a0679aea55d795663f32f0b0816f7c0";

        assertEquals(expected, actual);
    }

    public void testMoveSingleParamUrl() throws Exception {
        Result result = folderClient.move("some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/move.php";

        assertEquals(expected, actual);
    }

    public void testMoveMultiParamUrl() throws Exception {
        Result result = folderClient.move("some_folder_key", "some_destination_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/move.php";

        assertEquals(expected, actual);
    }

    public void testMoveSingleParamPayload() throws Exception {
        Result result = folderClient.move("some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key_src=some_folder_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=9eefd14b8ae14cd40b6d9971833b0dde";

        assertEquals(expected, actual);
    }

    public void testMoveMultiParamPayload() throws Exception {
        Result result = folderClient.move("some_folder_key", "some_destination_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key_src=some_folder_key&folder_key_dst=some_destination_folder_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=a0bb69bb10f8a712c3954a0386e03479";

        assertEquals(expected, actual);
    }

    public void testDeleteUrl() throws Exception {
        Result result = folderClient.delete("some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/delete.php";

        assertEquals(expected, actual);
    }

    public void testDeletePayload() throws Exception {
        Result result = folderClient.delete("some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key=some_folder_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=dbb4b3de25ee2f03bc669021f50b7220";

        assertEquals(expected, actual);
    }

    public void testPurgeUrl() throws Exception {
        Result result = folderClient.purge("some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/purge.php";

        assertEquals(expected, actual);
    }

    public void testPurgePayload() throws Exception {
        Result result = folderClient.purge("some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key=some_folder_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=14d5b83c4034bc4dce4d93d58f468db8";

        assertEquals(expected, actual);
    }

    public void testUpdate() throws Exception {

    }

    public void testGetInfoSingleParamUrl() throws Exception {
        GetInfoParameters.Builder builder = new GetInfoParameters.Builder();
        builder.folderKey("some_folder_key");
        GetInfoParameters params = builder.build();
        Result result = folderClient.getInfo(params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/get_info.php";

        assertEquals(expected, actual);
    }

    public void testGetInfoMultiParamUrl() throws Exception {
        GetInfoParameters.Builder builder = new GetInfoParameters.Builder();
        builder.folderKey("some_folder_key").details(Details.YES).deviceId("some_device_id");
        GetInfoParameters params = builder.build();
        Result result = folderClient.getInfo(params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/get_info.php";

        assertEquals(expected, actual);
    }

    public void testGetInfoSingleParamPayload() throws Exception {
        GetInfoParameters.Builder builder = new GetInfoParameters.Builder();
        builder.folderKey("some_folder_key");
        GetInfoParameters params = builder.build();
        Result result = folderClient.getInfo(params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key=some_folder_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=fe7b7bea684b18db94b8ed4a75c0a8cc";

        assertEquals(expected, actual);
    }

    public void testGetInfoMultiParamPayload() throws Exception {
        GetInfoParameters.Builder builder = new GetInfoParameters.Builder();
        builder.folderKey("some_folder_key").details(Details.YES).deviceId("some_device_id");
        GetInfoParameters params = builder.build();
        Result result = folderClient.getInfo(params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key=some_folder_key&device_id=some_device_id&details=yes&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=46a0ebe82c837ed33d22362119c4a310";

        assertEquals(expected, actual);
    }
    
    public void testGetContentSingleParamUrl() throws Exception {
        GetContentParameters.Builder builder = new GetContentParameters.Builder();
        builder.folderKey("some_folder_key");
        GetContentParameters params = builder.build();
        Result result = folderClient.getContent(params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/get_content.php";

        assertEquals(expected, actual);
    }

    public void testGetContentMultiParamUrl() throws Exception {
        GetContentParameters.Builder builder = new GetContentParameters.Builder();
        builder.folderKey("some_folder_key").chunk(1);
        GetContentParameters params = builder.build();
        Result result = folderClient.getContent(params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/get_content.php";

        assertEquals(expected, actual);
    }

    public void testGetContentSingleParamPayload() throws Exception {
        GetContentParameters.Builder builder = new GetContentParameters.Builder();
        builder.folderKey("some_folder_key");
        GetContentParameters params = builder.build();
        Result result = folderClient.getContent(params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key=some_folder_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=e7168d22f3363816e055f3513b032dbd";

        assertEquals(expected, actual);
    }

    public void testGetContentMultiParamPayload() throws Exception {
        GetContentParameters.Builder builder = new GetContentParameters.Builder();

        GetContentParameters params = builder.build();
        Result result = folderClient.getContent(params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=4cda84598670fdf21dde9877a86e8d2a";

        assertEquals(expected, actual);
    }

    public void testGetRevisionSingleParamUrl() throws Exception {
        Result result = folderClient.getRevision("some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/get_revision.php";

        assertEquals(expected, actual);
    }

    public void testGetRevisionMultiParamUrl() throws Exception {
        Result result = folderClient.getRevision("some_folder_key", true);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/get_revision.php";

        assertEquals(expected, actual);
    }

    public void testGetRevisionSingleParamPayload() throws Exception {
        Result result = folderClient.getRevision("some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key=some_folder_key&return_changes=no&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=10ed0ac372d9e33ac6765ad9e5f2867d";

        assertEquals(expected, actual);
    }

    public void testGetRevisionMultiParamPayload() throws Exception {
        Result result = folderClient.getRevision("some_folder_key", true);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key=some_folder_key&return_changes=yes&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=8a9c2f8532e6c7d478fa8962b6eadd66";

        assertEquals(expected, actual);
    }

    public void testSearchSingleParamUrl() throws Exception {
        SearchParameters.Builder builder = new SearchParameters.Builder("some_search_text");
        SearchParameters searchParameters = builder.build();
        Result result = folderClient.search(searchParameters);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/search.php";

        assertEquals(expected, actual);
    }

    public void testSearchMultiParamUrl() throws Exception {
        SearchParameters.Builder builder = new SearchParameters.Builder("some_search_text");
        builder.folderKey("some_folder_key").searchAll(true);
        SearchParameters searchParameters = builder.build();
        Result result = folderClient.search(searchParameters);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/folder/search.php";

        assertEquals(expected, actual);
    }

    public void testSearchSingleParamPayload() throws Exception {
        SearchParameters.Builder builder = new SearchParameters.Builder("some_search_text");
        SearchParameters searchParameters = builder.build();
        Result result = folderClient.search(searchParameters);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&search_text=some_search_text&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=d71f175cf66a0d78e5f08e28a8c8ff54";

        assertEquals(expected, actual);
    }

    public void testSearchMultiParamPayload() throws Exception {
        SearchParameters.Builder builder = new SearchParameters.Builder("some_search_text");
        builder.folderKey("some_folder_key").searchAll(true);
        SearchParameters searchParameters = builder.build();
        Result result = folderClient.search(searchParameters);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&folder_key=some_folder_key&search_all=yes&search_text=some_search_text&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=9eb3099030b5b5b2df91cc3cfe519a59";

        assertEquals(expected, actual);
    }

}