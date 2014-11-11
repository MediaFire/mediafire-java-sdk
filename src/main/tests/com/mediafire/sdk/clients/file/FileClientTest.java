package com.mediafire.sdk.clients.file;

import com.mediafire.sdk.clients.DummyHttpWorker;
import com.mediafire.sdk.clients.DummySessionTokenManager;
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
        String expected = "response_format=json&quick_key=some_quick_key&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=83b5bacd3c47e3d834b4c1992828d2d7";

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
        String expected = "response_format=json&quick_key=some_quick_key&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=d1f68138ab95096793e1ca9ce88a5b51";

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
        String expected = "response_format=json&quick_key=some_quick_key&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=5875c088e40fb5336c981f3cf7641267";

        assertEquals(expected, actual);
    }

    public void testCopyMultiParamPayload() throws Exception {
        Result result = fileClient.copy("some_quick_key", "some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&folder_key=some_folder_key&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=3bd70d22e6488af16844cf1c556a6d5c";

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
        String expected = "response_format=json&quick_key=some_quick_key&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=23f1ba5945edfd5a83622466a92e9991";

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
        String expected = "response_format=json&quick_key=some_quick_key&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=8effc4542f464d50a1482ae566d340db";

        assertEquals(expected, actual);
    }

    public void testMoveMultiParamPayload() throws Exception {
        Result result = fileClient.move("some_quick_key", "some_folder_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&folder_key=some_folder_key&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=276f86e8aea65689716dc8bf8858b38e";

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
        String expected = "response_format=json&quick_key=some_quick_key&filename=newfile.txt&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=d12809fcfeef9d730b01c7f94ec03827";

        assertEquals(expected, actual);
    }

    public void testUpdateMultiParamPayload() throws Exception {
        UpdateParameters.Builder builder = new UpdateParameters.Builder();
        builder.fileName("newfile.txt").privacy(UpdateParameters.Builder.Privacy.PRIVATE);
        UpdateParameters params = builder.build();
        Result result = fileClient.update("some_quick_key", params);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&filename=newfile.txt&privacy=private&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=81bb328e2da28620f88a500cd8a70c78";

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
        String expected = "response_format=json&quick_key=some_quick_key&filename=newFile.txt&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=05de651a79d3b4a9e9b1f1329f30a8a7";

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
        String expected = "response_format=json&quick_key=some_quick_key&privacy=public&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=d75fdb58c9ee13150536b6d5623e318c";

        assertEquals(expected, actual);
    }

    public void testMakePrivateUrl() throws Exception {
        Result result = fileClient.makePrivate("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/update.php";

        assertEquals(expected, actual);
    }

    public void testMAkePrivatePayload() throws Exception {
        Result result = fileClient.makePrivate("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&privacy=private&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=e9bb5e101dac700a0b3d9ca8ec7a1e0b";

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
        Result result = fileClient.getLinks("some_quick_key", FileClient.LinkType.ONE_TIME_DOWNLOAD);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/file/get_links.php";

        assertEquals(expected, actual);
    }

    public void testGetLinksSingleParamPayload() throws Exception {
        Result result = fileClient.getLinks("some_quick_key");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=fce2195af452e0164a69ad36e66b479b";

        assertEquals(expected, actual);
    }

    public void testGetLinksSMultiParamPayload() throws Exception {
        Result result = fileClient.getLinks("some_quick_key", FileClient.LinkType.ONE_TIME_DOWNLOAD);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&quick_key=some_quick_key&link_type=one_time&session_token=verylongsessiontokenstring123456789abcdefghijklmnopqrstuvwxyz&signature=b350184746d2231898214fbf682d1287";

        assertEquals(expected, actual);
    }
}