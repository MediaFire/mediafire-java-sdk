package com.mediafire.sdk.client_helpers;

import com.mediafire.sdk.test_utility.DummySessionTokenManager;
import com.mediafire.sdk.test_utility.RequestObjectsForTesting;
import com.mediafire.sdk.config.CredentialsInterface;
import com.mediafire.sdk.config.SessionTokenManagerInterface;
import com.mediafire.sdk.config_impl.DefaultCredentials;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.token.Token;
import junit.framework.TestCase;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientHelperNewSessionTokenTest extends TestCase {

    public void testBorrowToken() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        SessionTokenManagerInterface dummySessionTokenManagerInterface = new DummySessionTokenManager();

        CredentialsInterface userCredentials = new DefaultCredentials();
        Map<String, String> userCredentialsMap = new LinkedHashMap<String, String>();
        userCredentialsMap.put("email", "test@test.com");
        userCredentialsMap.put("password", "test");
        userCredentials.setCredentials(userCredentialsMap);

        CredentialsInterface developerCredentials = new DefaultCredentials();
        Map<String, String> devCredentialsMap = new LinkedHashMap<String, String>();
        devCredentialsMap.put("application_id", "12345");
        devCredentialsMap.put("api_key", "abcd");
        developerCredentials.setCredentials(devCredentialsMap);

        ClientHelperNewSessionToken clientHelper = new ClientHelperNewSessionToken(userCredentials, developerCredentials, dummySessionTokenManagerInterface);
        clientHelper.borrowToken(request);

        Token token = request.getToken();
        boolean tokenNull = token == null;

        assertTrue(tokenNull);
    }

    public void testAddSignatureToRequestParameters() throws Exception {
        Request request = RequestObjectsForTesting.getRequestSchemeDomainPath(true, "post", 1);

        SessionTokenManagerInterface dummySessionTokenManagerInterface = new DummySessionTokenManager();
        CredentialsInterface userCredentials = new DefaultCredentials();
        Map<String, String> userCredentialsMap = new LinkedHashMap<String, String>();
        userCredentialsMap.put("email", "test@test.com");
        userCredentialsMap.put("password", "test");
        userCredentials.setCredentials(userCredentialsMap);

        CredentialsInterface developerCredentials = new DefaultCredentials();
        Map<String, String> devCredentialsMap = new LinkedHashMap<String, String>();
        devCredentialsMap.put("application_id", "12345");
        developerCredentials.setCredentials(devCredentialsMap);

        ClientHelperNewSessionToken clientHelper = new ClientHelperNewSessionToken(userCredentials, developerCredentials, dummySessionTokenManagerInterface);
        clientHelper.borrowToken(request);
        clientHelper.addSignatureToRequestParameters(request);

        String actual = (String) request.getQueryParameters().get("signature");
        String expected = "9636390b0a0d8d946026b2006c9f5da0bfc466dd";

        assertEquals(expected, actual);
    }
}