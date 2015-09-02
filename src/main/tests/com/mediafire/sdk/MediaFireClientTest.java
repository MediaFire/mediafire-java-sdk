package com.mediafire.sdk;

import com.mediafire.sdk.api.responses.ApiResponse;
import com.mediafire.sdk.api.responses.UserGetSessionTokenResponse;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by christophernajar on 9/1/15.
 */
public class MediaFireClientTest extends TestCase {

    private MFClient mediaFire;

    public void setUp() throws Exception {
        super.setUp();
        MFClient.Builder builder = new MFClient.Builder("40767", null);
        mediaFire = builder.build();
        mediaFire.getCredentialStore().setEmail(new MediaFireCredentialsStore.EmailCredentials("badtestemail@badtestemail.com", "badtestemail"));
    }

    public void tearDown() throws Exception {

    }

    public void testNoAuthRequest() throws Exception {
        MediaFireApiRequest request = new MFApiRequest("/system/get_info.php", null, null, null, null);
        ApiResponse response = this.mediaFire.noAuthRequest(request, ApiResponse.class);
        assertFalse(response.hasError());
    }

    public void testConversionServerRequest() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("quickkey", "71j7iiaf2q5fu90");
        params.put("size_id", "z");
        params.put("doc_type", "i");
        String hash = "5f3df7078a450fe4ccbea773b07117caefa6e1b572abc63928f5e2d57cc9d0be";
        MediaFireHttpResponse response = this.mediaFire.conversionServerRequest(hash, params);
        assertTrue(response.getStatusCode() == 200);
    }

    public void testUploadRequest() throws Exception {
        fail("not implemented");
    }

    public void testSessionRequest() throws Exception {
        MediaFireApiRequest request = new MFApiRequest("/user/get_info.php", null, null, null, null);
        ApiResponse response = this.mediaFire.sessionRequest(request, ApiResponse.class);
        assertFalse(response.hasError());
    }

    public void testAuthenticationRequest() throws Exception {
        UserGetSessionTokenResponse response = this.mediaFire.authenticationRequest(UserGetSessionTokenResponse.class);
        assertFalse("response: " + response.getMessage(), response.hasError());
    }
}