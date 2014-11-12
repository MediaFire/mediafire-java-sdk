package com.mediafire.sdk.api.clients.file;

import com.mediafire.sdk.api.clients.DummyHttpWorker;
import com.mediafire.sdk.api.clients.DummySessionTokenManager;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Result;
import junit.framework.TestCase;

public class FileClientTest extends TestCase {

    HttpWorkerInterface httpWorker = new DummyHttpWorker();
    SessionTokenManagerInterface sessionTokenManager = new DummySessionTokenManager();
    FileClient fileClient = new FileClient(httpWorker, sessionTokenManager);

    public void testGetInfoUrl() throws Exception {
        Result result = fileClient.getInfo("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/get_info.php";

        assertEquals(expected, actual);
    }

    public void testGetInfoPayload() throws Exception {
        Result result = fileClient.getInfo("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=e822f45a4a7168f5131db37b2c5b5755";

        assertEquals(expected, actual);
    }

    public void testDeleteUrl() throws Exception {
        Result result = fileClient.delete("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/delete.php";

        assertEquals(expected, actual);
    }

    public void testDeletePayload() throws Exception {
        Result result = fileClient.delete("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=abb6c69a22723008a08701723a4fab9f";

        assertEquals(expected, actual);
    }

    public void testCopySingleParamUrl() throws Exception {
        Result result = fileClient.copy("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/copy.php";

        assertEquals(expected, actual);
    }

    public void testCopyMultiParamUrl() throws Exception {
        Result result = fileClient.copy("some_quick_key", "some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/copy.php";

        assertEquals(expected, actual);
    }

    public void testCopySingleParamPayload() throws Exception {
        Result result = fileClient.copy("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=898219b95d099689ce6f96ec1383db90";

        assertEquals(expected, actual);
    }

    public void testCopyMultiParamPayload() throws Exception {
        Result result = fileClient.copy("some_quick_key", "some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&folder_key=some_folder_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=35cecc930e36e9c9b9e6a9297a551e28";

        assertEquals(expected, actual);
    }

    public void testGetVersionUrl() throws Exception {
        Result result = fileClient.getVersion("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/get_version.php";

        assertEquals(expected, actual);
    }

    public void testGetVersionPayload() throws Exception {
        Result result = fileClient.getVersion("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=f4a920063e9c8bb947e95e21e56ade18";

        assertEquals(expected, actual);
    }

    public void testMoveSingleParamUrl() throws Exception {
        Result result = fileClient.move("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/move.php";

        assertEquals(expected, actual);
    }

    public void testMoveMultiParamUrl() throws Exception {
        Result result = fileClient.move("some_quick_key", "some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/move.php";

        assertEquals(expected, actual);
    }

    public void testMoveSingleParamPayload() throws Exception {
        Result result = fileClient.move("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=43c8a35c1979890a69168b916583afc7";

        assertEquals(expected, actual);
    }

    public void testMoveMultiParamPayload() throws Exception {
        Result result = fileClient.move("some_quick_key", "some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&folder_key=some_folder_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=05cf8ae0b5e0a6514900a7ce60d993e0";

        assertEquals(expected, actual);
    }

    public void testUpdateSingleParamUrl() throws Exception {
        UpdateParameters.Builder builder = new UpdateParameters.Builder();
        builder.fileName("newfile.txt");
        UpdateParameters params = builder.build();
        Result result = fileClient.update("some_quick_key", params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/update.php";

        assertEquals(expected, actual);
    }

    public void testUpdateMultiParamUrl() throws Exception {
        UpdateParameters.Builder builder = new UpdateParameters.Builder();
        builder.fileName("newfile.txt").privacy(UpdateParameters.Builder.Privacy.PRIVATE);
        UpdateParameters params = builder.build();
        Result result = fileClient.update("some_quick_key", params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/update.php";

        assertEquals(expected, actual);
    }

    public void testUpdateSingleParamPayload() throws Exception {
        UpdateParameters.Builder builder = new UpdateParameters.Builder();
        builder.fileName("newfile.txt");
        UpdateParameters params = builder.build();
        Result result = fileClient.update("some_quick_key", params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&filename=newfile.txt&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=3e193354d4a48b9af6a726e92280d5ac";

        assertEquals(expected, actual);
    }

    public void testUpdateMultiParamPayload() throws Exception {
        UpdateParameters.Builder builder = new UpdateParameters.Builder();
        builder.fileName("newfile.txt").privacy(UpdateParameters.Builder.Privacy.PRIVATE);
        UpdateParameters params = builder.build();
        Result result = fileClient.update("some_quick_key", params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&filename=newfile.txt&privacy=private&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=97868a7e5901c8199c1117d26fa66968";

        assertEquals(expected, actual);
    }

    public void testRenameUrl() throws Exception {
        Result result = fileClient.rename("some_quick_key", "newFile.txt");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/update.php";

        assertEquals(expected, actual);
    }

    public void testRenamePayload() throws Exception {
        Result result = fileClient.rename("some_quick_key", "newFile.txt");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&filename=newFile.txt&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=4c14777d0294b9aead04c6c7d82dbfd4";

        assertEquals(expected, actual);
    }

    public void testMakePublicUrl() throws Exception {
        Result result = fileClient.makePublic("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/update.php";

        assertEquals(expected, actual);
    }

    public void testMakePublicPayload() throws Exception {
        Result result = fileClient.makePublic("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&privacy=public&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=72e7b13ad70b7d56b67171c634a0aea4";

        assertEquals(expected, actual);
    }

    public void testMakePrivateUrl() throws Exception {
        Result result = fileClient.makePrivate("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/update.php";

        assertEquals(expected, actual);
    }

    public void testMakePrivatePayload() throws Exception {
        Result result = fileClient.makePrivate("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&privacy=private&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=ced155c84d9652e895943945b6b0f06d";

        assertEquals(expected, actual);
    }

    public void testGetLinksSingleParamUrl() throws Exception {
        Result result = fileClient.getLinks("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/get_links.php";

        assertEquals(expected, actual);
    }

    public void testGetLinksSMultiParamUrl() throws Exception {
        Result result = fileClient.getLinks("some_quick_key", LinkType.ONE_TIME_DOWNLOAD);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/get_links.php";

        assertEquals(expected, actual);
    }

    public void testGetLinksSingleParamPayload() throws Exception {
        Result result = fileClient.getLinks("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=0221202c9498879ca7bb45ba7c2b151d";

        assertEquals(expected, actual);
    }

    public void testGetLinksSMultiParamPayload() throws Exception {
        Result result = fileClient.getLinks("some_quick_key", LinkType.ONE_TIME_DOWNLOAD);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&link_type=one_time&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6&signature=e1e5bf4a81721e5d203168d7a4d792d9";

        assertEquals(expected, actual);
    }
}