package com.mediafire.sdk;

import com.mediafire.sdk.response_models.ApiResponse;
import junit.framework.TestCase;

/**
 * Created by christophernajar on 9/1/15.
 */
public class MediaFireApiResponseParserTest extends TestCase {

    private MediaFireApiResponseParser parser;
    
    private final byte[] NULL = null;
    private final byte[] EMPTY = new byte[0];
    private final byte[] BLANK = "".getBytes();

    private final byte[] USER_GET_INFO = ("{\"response\":{\"action\":\"user\\/get_info\",\"user_info\":" +
            "{\"ekey\":\"abcdefghijklmnop\",\"email\":\"bob@tom.joe\",\"first_name\":\"bob\"," +
            "\"last_name\":\"tom\",\"display_name\":\"bobtom\",\"gender\":\"Prefer not to answer\"," +
            "\"birth_date\":\"1983-05-08\",\"location\":\"\",\"website\":\"http:\\/\\/www.mediafire.com\"," +
            "\"premium\":\"no\",\"bandwidth\":\"1190676917602\",\"created\":\"2013-06-26\",\"validated\":\"yes\"," +
            "\"tos_accepted\":\"0.14\",\"used_storage_size\":\"466138562\",\"base_storage\":\"10739515392\"," +
            "\"bonus_storage\":\"10737418240\",\"storage_limit\":\"21476933632\",\"storage_limit_exceeded\":\"no\"," +
            "\"options\":\"4194304\",\"facebook\":{\"facebook_id\":\"12345678915645\"," +
            "\"date_created\":\"2015-02-03 17:27:32\",\"service_id\":\"0\",\"token_for_business\":\"\"," +
            "\"email\":\"bob@tom.joe\"," +
            "\"facebook_url\":\"https:\\/\\/www.facebook.com\\/app_scoped_user_id\\/101015\"," +
            "\"name\":\"Bob Tom\",\"firstname\":\"Bob\",\"lastname\":\"Tom\"," +
            "\"hometown\":\"\",\"location\":\"\",\"i18n\":\"en_US\",\"timezone\":\"-6\",\"synced\":\"\"," +
            "\"linked\":\"yes\",\"date_created_utc\":\"2015-02-03T23:27:32Z\"},\"gmail\":{\"linked\":\"no\"}," +
            "\"twitter\":{\"linked\":\"no\"},\"one_time_key_request_max_count\":\"10\"},\"result\":\"Success\"," +
            "\"current_api_version\":\"1.4\"}}").getBytes();

    private final byte[] FILE_GET_INFO = ("{\"response\":{\"action\":\"file\\/get_info\"," +
            "\"file_info\":{\"quickkey\":\"abcdefg\",\"filename\":\"IMG_20150604_074149.jpeg\"," +
            "\"created\":\"2015-08-03 06:46:23\",\"downloads\":\"0\",\"description\":\"\",\"size\":\"96990\"," +
            "\"privacy\":\"private\",\"password_protected\":\"no\"," +
            "\"hash\":\"abcdefg\",\"filetype\":\"image\"," +
            "\"mimetype\":\"image\\/jpeg\",\"owner_name\":\"bob\",\"flag\":\"6\"," +
            "\"parent_folderkey\":\"gfedcba\",\"revision\":\"270087\",\"view\":\"2\",\"edit\":\"0\"," +
            "\"links\":{\"view\":\"http:\\/\\/www.mediafire.com\\/view\\/abcdefg\\/IMG_20150604_074149.jpeg\"," +
            "\"normal_download\":\"http:\\/\\/www.mediafire.com\\/download\\/abcdefg\\/IMG_20150604_074149.jpeg\"}}," +
            "\"result\":\"Success\",\"current_api_version\":\"1.4\"}}").getBytes();

    private final byte[] FOLDER_GET_INFO = ("{\"response\":{\"action\":\"folder\\/get_info\",\"folder_info\":" +
            "{\"folderkey\":\"myfiles\",\"name\":\"myfiles\",\"file_count\":\"13\",\"folder_count\":\"6\"," +
            "\"revision\":\"272536\",\"owner_name\":\"Clockwerk\"," +
            "\"avatar\":\"https:\\/\\/www4.mediafire.com\\/convkey\\/3336\\/w1o911le2gfsgg11g.jpg\"," +
            "\"dropbox_enabled\":\"no\",\"flag\":\"2\"},\"result\":\"Success\",\"current_api_version\":\"1.4\"}}").getBytes();

    private final byte[] SYSTEM_GET_LIMITS = ("{\"response\":{\"action\":\"system\\/get_limits\"," +
            "\"limits\":{\"max_objects\":\"6000\",\"max_keys\":\"500\",\"max_image_size\":\"26214400\"," +
            "\"zip_max_filesize\":\"314572800\",\"zip_max_total_filesize\":\"2147483647\"," +
            "\"folder_content_chunk_size\":\"100\",\"folder_depth_limit\":\"130\",\"limit_search_results\":\"100\"," +
            "\"daily_shares_limit\":\"100\",\"device_changes_list_limit\":\"500\",\"total_folder_items_limit\":\"100\"}," +
            "\"result\":\"Success\",\"current_api_version\":\"1.4\"}}").getBytes();

    private final byte[] CONTACT_FETCH = ("{\"response\":{\"action\":\"contact\\/fetch\"," +
            "\"contact_groups\":[],\"contacts\":[],\"count\":\"0\",\"revision\":\"1\",\"epoch\":\"1441140271\"," +
            "\"result\":\"Success\",\"current_api_version\":\"1.4\"}}").getBytes();

    private final byte[] DEVICE_GET_STATUS = ("{\"response\":{\"action\":\"device\\/get_status\"," +
            "\"device_revision\":\"272536\",\"async_jobs_in_progress\":\"no\",\"result\":\"Success\"," +
            "\"current_api_version\":\"1.4\"}}").getBytes();
    
    private final byte[] ERROR = ("{\"response\":{\"action\":\"file\\/get_info\"," +
            "\"message\":\"The supplied Session Token is expired or invalid\",\"error\":105," +
            "\"result\":\"Error\",\"current_api_version\":\"1.4\"}}").getBytes();

    public void setUp() throws Exception {
        super.setUp();
        parser = new MFApiResponseParser();
    }

    public void tearDown() throws Exception {

    }

    public void testParseResponseNull() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, NULL, null);
        boolean exceptionThrown = false;
        try {
            ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        } catch (MediaFireException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    public void testParseResponseEmpty() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, EMPTY, null);
        boolean exceptionThrown = false;
        try {
            ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        } catch (MediaFireException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    public void testParseResponseBlank() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, BLANK, null);
        boolean exceptionThrown = false;
        try {
            ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        } catch (MediaFireException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    public void testParseResponseUserGetInfo() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, USER_GET_INFO, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertFalse(apiResponse.hasError());
    }

    public void testParseResponseUserGetInfoError() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, ERROR, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertTrue(apiResponse.hasError());
    }

    public void testParseResponseFileGetInfo() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, FILE_GET_INFO, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertFalse(apiResponse.hasError());
    }

    public void testParseResponseFileGetInfoError() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, ERROR, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertTrue(apiResponse.hasError());
    }

    public void testParseResponseFolderGetInfo() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, FOLDER_GET_INFO, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertFalse(apiResponse.hasError());
    }

    public void testParseResponseFolderGetInfoError() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, ERROR, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertTrue(apiResponse.hasError());
    }

    public void testParseResponseSystemGetInfo() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, SYSTEM_GET_LIMITS, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertFalse(apiResponse.hasError());
    }

    public void testParseResponseSystemGetInfoError() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, ERROR, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertTrue(apiResponse.hasError());
    }

    public void testParseResponseContactFetch() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, CONTACT_FETCH, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertFalse(apiResponse.hasError());
    }

    public void testParseResponseContactFetchError() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, ERROR, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertTrue(apiResponse.hasError());
    }

    public void testParseResponseDeviceGetStatus() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, DEVICE_GET_STATUS, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertFalse(apiResponse.hasError());
    }

    public void testParseResponseDeviceGetStatusError() throws Exception {
        MediaFireHttpResponse response = new MFHttpResponse(200, ERROR, null);
        ApiResponse apiResponse = parser.parseResponse(response, ApiResponse.class);
        assertTrue(apiResponse.hasError());
    }

    public void testGetResponseFormatJson() throws Exception {
        assertEquals("json", parser.getResponseFormat());
    }
}