package com.mediafire.sdk.clients;

import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.token.SessionToken;
import com.mediafire.sdk.token.Token;
import junit.framework.TestCase;

public class ClientHelperApiTest extends TestCase {

    public void testBorrowToken() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        SessionTokenManagerInterface dummySessionTokenManagerInterface = new DummySessionTokenManager();
        ClientHelperApi clientHelper = new ClientHelperApi(dummySessionTokenManagerInterface);
        clientHelper.borrowToken(request);

        Token token = request.getToken();
        boolean tokenIsSessionToken = token instanceof SessionToken;

        assertTrue(tokenIsSessionToken);
    }

    public void testAddSignatureToRequestParameters() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        SessionTokenManagerInterface dummySessionTokenManagerInterface = new DummySessionTokenManager();
        ClientHelperApi clientHelper = new ClientHelperApi(dummySessionTokenManagerInterface);
        clientHelper.borrowToken(request);
        clientHelper.addSignatureToRequestParameters(request);

        String actual = (String) request.getQueryParameters().get("signature");
        String expected = "01d1c4aaeb6af0fad965dd24a33ea8bc";

        assertEquals(expected, actual);
    }
}