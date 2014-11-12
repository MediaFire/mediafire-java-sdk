package com.mediafire.sdk.api.clients;

import com.mediafire.sdk.client_helpers.BaseClientHelper;
import com.mediafire.sdk.client_helpers.ClientHelperApi;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.token.SessionToken;
import junit.framework.TestCase;

public class BaseClientHelperTest extends TestCase {

    private static SessionTokenManagerInterface sessionTokenManagerInterface = new DummySessionTokenManager();
    private static BaseClientHelper baseClientHelper = new ClientHelperApi(sessionTokenManagerInterface);
    private SessionToken sessionToken = sessionTokenManagerInterface.borrowSessionToken();

    static {
        baseClientHelper.debug(true);
    }

    public void testMakeSignatureForApiRequestNoParamsUsingRequestBuilder() throws Exception {
        Request request = new Request.Builder().scheme("https").fullDomain("www.mediafire.com").path("api/user/get_info.php").httpMethod("post").postQuery(true).build();
        request.addToken(sessionToken);
        request.addQueryParameter("session_token", sessionToken.getTokenString());

        String actual = baseClientHelper.makeSignatureForApiRequest(request);
        String expected = "e0a38a0b7725c2072c79d39c2412f02e";

        assertEquals(expected, actual);
    }

    public void testMakeSignatureForApiRequestSingleParamsUsingRequestBuilder() throws Exception {
        Request request = new Request.Builder().scheme("https").fullDomain("www.mediafire.com").path("api/file/get_info.php").httpMethod("post").postQuery(true).build();
        request.addToken(sessionToken);
        request.addQueryParameter("session_token", sessionToken.getTokenString());
        request.addQueryParameter("quick_key", "irof4ikgmbi79fq");

        String actual = baseClientHelper.makeSignatureForApiRequest(request);
        String expected = "815e9347bbe24ec2ad5fc7be0249e998";

        assertEquals(expected, actual);
    }

    public void testMakeSignatureForApiRequestMultiParamsUsingRequestBuilder() throws Exception {
        Request request = new Request.Builder().scheme("https").fullDomain("www.mediafire.com").path("api/file/update.php").httpMethod("post").postQuery(true).build();
        request.addToken(sessionToken);
        request.addQueryParameter("session_token", sessionToken.getTokenString());
        request.addQueryParameter("quick_key", "irof4ikgmbi79fq");
        request.addQueryParameter("filename", "new_name.txt");
        request.addQueryParameter("description", "test_desc");
        request.addQueryParameter("tags", "tag1");
        request.addQueryParameter("privacy", "public");

        String actual = baseClientHelper.makeSignatureForApiRequest(request);
        String expected = "25547d015bb8d8d887ea364eaaf6ca09";

        assertEquals(expected, actual);
    }

    public void testMakeSignatureForApiRequestNoParamsUsingRequestUrl() throws Exception {
        Request request = new Request("https://www.mediafire.com/api/user/get_info.php?session_token=" + sessionToken.getTokenString());
        request.addToken(sessionToken);

        String actual = baseClientHelper.makeSignatureForApiRequest(request);
        String expected = "e0a38a0b7725c2072c79d39c2412f02e";

        assertEquals(expected, actual);
    }

    public void testMakeSignatureForApiRequestSingleParamsUsingRequestUrl() throws Exception {
        Request request = new Request("https://www.mediafire.com/api/file/get_info.php?session_token=" + sessionToken.getTokenString() + "&quick_key=irof4ikgmbi79fq");
        request.addToken(sessionToken);

        String actual = baseClientHelper.makeSignatureForApiRequest(request);
        String expected = "815e9347bbe24ec2ad5fc7be0249e998";

        assertEquals(expected, actual);
    }

    public void testMakeSignatureForApiRequestMultiParamsUsingRequestUrl() throws Exception {
        Request request = new Request("https://www.mediafire.com/api/file/update.php?session_token=" + sessionToken.getTokenString() + "&quick_key=irof4ikgmbi79fq&filename=new_name.txt&description=test_desc&tags=tag1&privacy=public");
        request.addToken(sessionToken);

        String actual = baseClientHelper.makeSignatureForApiRequest(request);
        String expected = "25547d015bb8d8d887ea364eaaf6ca09";

        assertEquals(expected, actual);
    }
}