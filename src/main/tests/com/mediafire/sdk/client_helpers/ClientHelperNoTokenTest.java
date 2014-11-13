package com.mediafire.sdk.client_helpers;

import com.mediafire.sdk.test_utility.RequestObjectsForTesting;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.token.Token;
import junit.framework.TestCase;

public class ClientHelperNoTokenTest extends TestCase {

    public void testBorrowToken() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        ClientHelperNoToken clientHelper = new ClientHelperNoToken();
        clientHelper.borrowToken(request);

        Token token = request.getToken();
        boolean tokenIsNull = token == null;

        assertTrue(tokenIsNull);
    }

    public void testAddSignatureToRequestParameters() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        ClientHelperNoToken clientHelper = new ClientHelperNoToken();
        clientHelper.borrowToken(request);
        clientHelper.addSignatureToRequestParameters(request);

        String actual = (String) request.getQueryParameters().get("signature");
        String expected = null;

        assertEquals(expected, actual);
    }
}