package com.mediafire.sdk;

import junit.framework.TestCase;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by christophernajar on 9/1/15.
 */
public class MediaFireApiRequestTest extends TestCase {


    private final String PATH_NULL = "user/get_info.php";
    private final String PATH_EMPTY = "user/get_info.php";
    private final String PATH = "user/get_info.php";
    private final String VERSION_NULL = null;
    private final String VERSION_EMPTY = "";
    private final String VERSION = "1";

    private final byte[] PAYLOAD_NULL = null;
    private final byte[] PAYLOAD_EMPTY = new byte[0];
    private final byte[] PAYLOAD_EMPTY_NULL = new byte[10];
    private final byte[] PAYLOAD = "payload".getBytes();


    private static final Map<String, Object> QUERY_NULL = null;
    private static final Map<String, Object> QUERY_EMPTY = new LinkedHashMap<String, Object>();
    private static final Map<String, Object> QUERY = new LinkedHashMap<String, Object>();
    static {
        QUERY.put("response_format", "json");
    }

    private static final Map<String, Object> HEADERS_NULL = null;
    private static final Map<String, Object> HEADERS_EMPTY = new LinkedHashMap<String, Object>();
    private static final Map<String, Object> HEADERS = new LinkedHashMap<String, Object>();
    static {
        HEADERS.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {

    }
}