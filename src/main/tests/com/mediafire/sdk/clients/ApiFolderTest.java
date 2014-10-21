package com.mediafire.sdk.clients;

import com.mediafire.sdk.api_responses.user.GetSessionTokenResponse;
import com.mediafire.sdk.config.Configuration;
import com.mediafire.sdk.http.Request;
import com.mediafire.sdk.http.Result;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Chris Najar on 10/21/2014.
 */
public class ApiFolderTest {
    Configuration mConfiguration;

    @Before
    public void setUp() throws Exception {
        mConfiguration = new Configuration.Builder().build();
        mConfiguration.init();

        Map<String, String> devCredentials = new LinkedHashMap<String, String>();
        devCredentials.put("application_id", "18");
        mConfiguration.getDeveloperCredentials().setCredentials(devCredentials);

        Map<String, String> userCredentials = new LinkedHashMap<String, String>();
        userCredentials.put("email", "javasdktest@example.com");
        userCredentials.put("password", "74107410");
        mConfiguration.getUserCredentials().setCredentials(userCredentials);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFolderCreate() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        Request request = new RequestGenerator().generateRequestObject("1.2", "folder", "create.php");
        request.addQueryParameter("response_format", "json");
        request.addQueryParameter("foldername", "test folder " + new Random().nextInt(10000));
        Result result = apiClient.doRequest(request);

        ResponseHelper responseHelper = new ResponseHelper(result.getResponse());
        GetSessionTokenResponse responseObject = responseHelper.getResponseObject(GetSessionTokenResponse.class);

        String actual = responseObject.getResult();
        String expected = "Success";

        assertEquals(expected, actual);
    }

    @Test
    public void testFolderGetInfo() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        Request request = new RequestGenerator().generateRequestObject("1.2", "folder", "get_info.php");
        request.addQueryParameter("response_format", "json");
        Result result = apiClient.doRequest(request);

        ResponseHelper responseHelper = new ResponseHelper(result.getResponse());
        GetSessionTokenResponse responseObject = responseHelper.getResponseObject(GetSessionTokenResponse.class);

        String actual = responseObject.getResult();
        String expected = "Success";

        assertEquals(expected, actual);
    }

    @Test
    public void testFolderGetRevision() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        Request request = new RequestGenerator().generateRequestObject("1.2", "folder", "get_revision.php");
        request.addQueryParameter("response_format", "json");
        Result result = apiClient.doRequest(request);

        ResponseHelper responseHelper = new ResponseHelper(result.getResponse());
        GetSessionTokenResponse responseObject = responseHelper.getResponseObject(GetSessionTokenResponse.class);

        String actual = responseObject.getResult();
        String expected = "Success";

        assertEquals(expected, actual);
    }

    @Test
    public void testFolderGetContentFiles() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        Request request = new RequestGenerator().generateRequestObject("1.2", "folder", "get_content.php");
        request.addQueryParameter("response_format", "json");
        request.addQueryParameter("content_type", "files");
        Result result = apiClient.doRequest(request);

        ResponseHelper responseHelper = new ResponseHelper(result.getResponse());
        GetSessionTokenResponse responseObject = responseHelper.getResponseObject(GetSessionTokenResponse.class);

        String actual = responseObject.getResult();
        String expected = "Success";

        assertEquals(expected, actual);
    }

    @Test
    public void testFolderGetContentFolders() throws Exception {
        ApiClient apiClient = new ApiClient(mConfiguration);
        Request request = new RequestGenerator().generateRequestObject("1.2", "folder", "get_content.php");
        request.addQueryParameter("response_format", "json");
        request.addQueryParameter("content_type", "folders");
        Result result = apiClient.doRequest(request);

        ResponseHelper responseHelper = new ResponseHelper(result.getResponse());
        GetSessionTokenResponse responseObject = responseHelper.getResponseObject(GetSessionTokenResponse.class);

        String actual = responseObject.getResult();
        String expected = "Success";

        assertEquals(expected, actual);
    }
}
