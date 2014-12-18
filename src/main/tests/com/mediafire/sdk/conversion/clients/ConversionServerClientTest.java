package com.mediafire.sdk.conversion.clients;

import com.mediafire.sdk.config.ActionTokenManagerInterface;
import com.mediafire.sdk.config.HttpInterface;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.test_utility.DummyActionTokenManager;
import com.mediafire.sdk.test_utility.DummyHttp;
import junit.framework.TestCase;

public class ConversionServerClientTest extends TestCase {

    private HttpInterface mHttpWorker = new DummyHttp();
    private ActionTokenManagerInterface mActionTokenManager = new DummyActionTokenManager();

    public void testImageConversionNoParamsConstructorFull() throws Exception {
        ConversionServerClient conversionServerClient = new ConversionServerClient(mHttpWorker, mActionTokenManager);

        ConversionServerClient.ImageParams params = new ConversionServerClient.ImageParams("some_quick_key", "some_hash", "0");
        Result result = conversionServerClient.imageConversion(params);

        DummyHttp.DummyGETResponse response = (DummyHttp.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/conversion_server.php?some=&quickkey=some_quick_key&doc_type=i&size_id=0&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testImageConversionNoParamsConstructorSub() throws Exception {
        ConversionServerClient conversionServerClient = new ConversionServerClient(mHttpWorker, mActionTokenManager);

        ConversionServerClient.ImageParams params = new ConversionServerClient.ImageParams("some_quick_key", "some_hash");
        Result result = conversionServerClient.imageConversion(params);

        DummyHttp.DummyGETResponse response = (DummyHttp.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/conversion_server.php?some=&quickkey=some_quick_key&doc_type=i&size_id=6&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testImageConversionParams() throws Exception {
        ConversionServerClient conversionServerClient = new ConversionServerClient(mHttpWorker, mActionTokenManager);

        ConversionServerClient.ImageParams params = new ConversionServerClient.ImageParams("some_quick_key", "some_hash");
        params.requestConversionOnly(true);

        Result result = conversionServerClient.imageConversion(params);

        DummyHttp.DummyGETResponse response = (DummyHttp.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/conversion_server.php?some=&quickkey=some_quick_key&doc_type=i&size_id=6&request_conversion_only=1&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testDocumentConversionNoParamsConstructorFull() throws Exception {
        ConversionServerClient conversionServerClient = new ConversionServerClient(mHttpWorker, mActionTokenManager);

        ConversionServerClient.DocumentParams params = new ConversionServerClient.DocumentParams("some_quick_key", "some_hash", "some_page");
        Result result = conversionServerClient.documentConversion(params);

        DummyHttp.DummyGETResponse response = (DummyHttp.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/conversion_server.php?some=&quickkey=some_quick_key&doc_type=d&page=some_page&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testDocumentConversionNoParamsConstructorSub() throws Exception {
        ConversionServerClient conversionServerClient = new ConversionServerClient(mHttpWorker, mActionTokenManager);

        ConversionServerClient.DocumentParams params = new ConversionServerClient.DocumentParams("some_quick_key", "some_hash");
        Result result = conversionServerClient.documentConversion(params);

        DummyHttp.DummyGETResponse response = (DummyHttp.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/conversion_server.php?some=&quickkey=some_quick_key&doc_type=d&page=initial&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testDocumentConversionParamsSizeId() throws Exception {
        ConversionServerClient conversionServerClient = new ConversionServerClient(mHttpWorker, mActionTokenManager);

        ConversionServerClient.DocumentParams params = new ConversionServerClient.DocumentParams("some_quick_key", "some_hash", "some_page");
        params.sizeId(2);
        Result result = conversionServerClient.documentConversion(params);

        DummyHttp.DummyGETResponse response = (DummyHttp.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/conversion_server.php?some=&quickkey=some_quick_key&doc_type=d&page=some_page&size_id=2&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testDocumentConversionParamsSizeIdInvalid() throws Exception {
        ConversionServerClient conversionServerClient = new ConversionServerClient(mHttpWorker, mActionTokenManager);

        ConversionServerClient.DocumentParams params = new ConversionServerClient.DocumentParams("some_quick_key", "some_hash", "some_page");
        params.sizeId(9);
        Result result = conversionServerClient.documentConversion(params);

        DummyHttp.DummyGETResponse response = (DummyHttp.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/conversion_server.php?some=&quickkey=some_quick_key&doc_type=d&page=some_page&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testDocumentConversionParamsOutputImage() throws Exception {
        ConversionServerClient conversionServerClient = new ConversionServerClient(mHttpWorker, mActionTokenManager);

        ConversionServerClient.DocumentParams params = new ConversionServerClient.DocumentParams("some_quick_key", "some_hash", "some_page");
        params.output(ConversionServerClient.DocumentParams.Output.IMAGE);
        Result result = conversionServerClient.documentConversion(params);

        DummyHttp.DummyGETResponse response = (DummyHttp.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/conversion_server.php?some=&quickkey=some_quick_key&doc_type=d&page=some_page&output=img&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }

    public void testDocumentConversionParamsOutputNull() throws Exception {
        ConversionServerClient conversionServerClient = new ConversionServerClient(mHttpWorker, mActionTokenManager);

        ConversionServerClient.DocumentParams params = new ConversionServerClient.DocumentParams("some_quick_key", "some_hash", "some_page");
        params.output(null);
        Result result = conversionServerClient.documentConversion(params);

        DummyHttp.DummyGETResponse response = (DummyHttp.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "https://www.mediafire.com/conversion_server.php?some=&quickkey=some_quick_key&doc_type=d&page=some_page&session_token=0dc472b926a1fef9878fa95a9332d8299ad4f93f3cad147de8a9bce1c540b729d20d0f7080a9051f308eedc7d11764f1055cdeb7e7115a2d8c9adcc8a2d8d79885a4a0ec91f306f6";

        assertEquals(expected, actual);
    }
}