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

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Chris Najar on 10/21/2014.
 */
public class ApiClientDeviceTest {
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
    public void testDeviceGetStatus() throws Exception {

    }

    @Test
    public void testDeviceGetChanges() throws Exception {

    }
}
