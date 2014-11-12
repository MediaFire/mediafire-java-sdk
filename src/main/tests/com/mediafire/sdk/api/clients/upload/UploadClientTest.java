package com.mediafire.sdk.api.clients.upload;

import com.mediafire.sdk.api.clients.DummyActionTokenManager;
import com.mediafire.sdk.api.clients.DummyHttpWorker;
import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Result;
import junit.framework.TestCase;

public class UploadClientTest extends TestCase {

    private HttpWorkerInterface httpWorker = new DummyHttpWorker();
    private ActionTokenManagerInterface actionTokenManager = new DummyActionTokenManager();
    UploadClient uploadClient = new UploadClient(httpWorker, actionTokenManager);

    public void testCheckSingleParamUrl() throws Exception {
        CheckParameters.Builder builder = new CheckParameters.Builder("some_file.ext");
        CheckParameters checkParameters = builder.build();
        Result result = uploadClient.check(checkParameters);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/upload/check.php";

        assertEquals(expected, actual);
    }

    public void testCheckMultiParamUrl() throws Exception {
        CheckParameters.Builder builder = new CheckParameters.Builder("some_file.ext");
        builder.resumable(true);
        builder.hash("abcdefg1234567890");
        CheckParameters checkParameters = builder.build();
        Result result = uploadClient.check(checkParameters);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/upload/check.php";

        assertEquals(expected, actual);
    }

    public void testCheckSingleParamPayload() throws Exception {
        CheckParameters.Builder builder = new CheckParameters.Builder("some_file.ext");
        CheckParameters checkParameters = builder.build();
        Result result = uploadClient.check(checkParameters);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&filename=some_file.ext&resumable=yes&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testCheckMultiParamPayload() throws Exception {
        CheckParameters.Builder builder = new CheckParameters.Builder("some_file.ext");
        builder.resumable(true);
        builder.hash("abcdefg1234567890");
        CheckParameters checkParameters = builder.build();
        Result result = uploadClient.check(checkParameters);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&filename=some_file.ext&hash=abcdefg1234567890&resumable=yes&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }
    
    public void testInstantBaseParamsUrl() throws Exception {
        InstantParameters.Builder builder = new InstantParameters.Builder("abcdefg1234567890", 1234567L);
        InstantParameters instantParams = builder.build();
        Result result = uploadClient.instant(instantParams);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/upload/instant.php";

        assertEquals(expected, actual);
    }

    public void testInstantMultiParamsUrl() throws Exception {
        InstantParameters.Builder builder = new InstantParameters.Builder("abcdefg1234567890", 1234567L);
        builder.actionOnDuplicate(ActionOnDuplicate.KEEP);
        builder.versionControl(VersionControl.CREATE_PATCHES);
        InstantParameters instantParams = builder.build();
        Result result = uploadClient.instant(instantParams);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/upload/instant.php";

        assertEquals(expected, actual);
    }

    public void testInstantBaseParamsPayload() throws Exception {
        InstantParameters.Builder builder = new InstantParameters.Builder("abcdefg1234567890", 1234567L);
        InstantParameters instantParams = builder.build();
        Result result = uploadClient.instant(instantParams);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&hash=abcdefg1234567890&size=1234567&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testInstantMultiParamsPayload() throws Exception {
        InstantParameters.Builder builder = new InstantParameters.Builder("abcdefg1234567890", 1234567L);
        builder.actionOnDuplicate(ActionOnDuplicate.KEEP);
        builder.versionControl(VersionControl.CREATE_PATCHES);
        InstantParameters instantParams = builder.build();
        Result result = uploadClient.instant(instantParams);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&hash=abcdefg1234567890&size=1234567&action_on_duplicate=keep&version_control=create_patches&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testPollUploadUrl() throws Exception {
        Result result = uploadClient.pollUpload("abcdef1234567890");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/upload/poll_upload.php";

        assertEquals(expected, actual);
    }

    public void testPollUploadPayload() throws Exception {
        Result result = uploadClient.pollUpload("abcdef1234567890");

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = "response_format=json&key=abcdef1234567890";

        assertEquals(expected, actual);
    }

    public void testResumableNoArgParamUrl() throws Exception {
        ResumableParameters.Builder rBuilder = new ResumableParameters.Builder();
        ResumableParameters resumableParams = rBuilder.build();
        HeaderParameters headerData = new HeaderParameters(123456L, "abcdef123456789", 1234L, 1, "ab123");
        byte[] payload = new String("blah").getBytes();
        Result result = uploadClient.resumable(resumableParams, headerData, payload);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/upload/resumable.php?response_format=json&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testResumableSingleParamUrl() throws Exception {
        ResumableParameters.Builder rBuilder = new ResumableParameters.Builder();
        rBuilder.actionOnDuplicate(ActionOnDuplicate.KEEP);
        ResumableParameters resumableParams = rBuilder.build();
        HeaderParameters headerData = new HeaderParameters(123456L, "abcdef123456789", 1234L, 1, "ab123");
        byte[] payload = new String("blah").getBytes();
        Result result = uploadClient.resumable(resumableParams, headerData, payload);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/upload/resumable.php?response_format=json&action_on_duplicate=keep&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testResumableMultiParamUrl() throws Exception {
        ResumableParameters.Builder rBuilder = new ResumableParameters.Builder();
        rBuilder.actionOnDuplicate(ActionOnDuplicate.KEEP).versionControl(VersionControl.NONE).folderKey("folder_keyabc1234");
        ResumableParameters resumableParams = rBuilder.build();
        HeaderParameters headerData = new HeaderParameters(123456L, "abcdef123456789", 1234L, 1, "ab123");
        byte[] payload = new String("blah").getBytes();
        Result result = uploadClient.resumable(resumableParams, headerData, payload);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/api/1.2/upload/resumable.php?response_format=json&folder_key=folder_keyabc1234&action_on_duplicate=keep&version_control=none&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testResumableNoArgParamPayload() throws Exception {
        ResumableParameters.Builder rBuilder = new ResumableParameters.Builder();
        ResumableParameters resumableParams = rBuilder.build();
        HeaderParameters headerData = new HeaderParameters(123456L, "abcdef123456789", 1234L, 1, "ab123");
        byte[] payload = new String("blah").getBytes();
        Result result = uploadClient.resumable(resumableParams, headerData, payload);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = new String(payload);

        assertEquals(expected, actual);
    }

    public void testResumableSingleParamPayload() throws Exception {
        ResumableParameters.Builder rBuilder = new ResumableParameters.Builder();
        rBuilder.actionOnDuplicate(ActionOnDuplicate.KEEP);
        ResumableParameters resumableParams = rBuilder.build();
        HeaderParameters headerData = new HeaderParameters(123456L, "abcdef123456789", 1234L, 1, "ab123");
        byte[] payload = new String("blah").getBytes();
        Result result = uploadClient.resumable(resumableParams, headerData, payload);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = new String(payload);

        assertEquals(expected, actual);
    }

    public void testResumableMultiParamPayload() throws Exception {
        ResumableParameters.Builder rBuilder = new ResumableParameters.Builder();
        rBuilder.actionOnDuplicate(ActionOnDuplicate.KEEP).versionControl(VersionControl.NONE).folderKey("folder_keyabc1234");
        ResumableParameters resumableParams = rBuilder.build();
        HeaderParameters headerData = new HeaderParameters(123456L, "abcdef123456789", 1234L, 1, "ab123");
        byte[] payload = new String("blah").getBytes();
        Result result = uploadClient.resumable(resumableParams, headerData, payload);

        DummyHttpWorker.DummyPOSTResponse response = (DummyHttpWorker.DummyPOSTResponse) result.getResponse();
        String actual = new String(response.getOriginalPayload());
        String expected = new String(payload);

        assertEquals(expected, actual);
    }
}