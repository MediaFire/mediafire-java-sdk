package com.mediafire.sdk.uploading;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mediafire.sdk.api.responses.upload.InstantResponse;
import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.token.Token;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstantTest extends TestCase {
    private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nisi nisl, pretium in rhoncus id, mattis ac ligula. Curabitur leo nisi, molestie sed ullamcorper vitae, mattis at lectus. Cras efficitur libero sed risus laoreet pellentesque. Nam suscipit quam ex, interdum imperdiet justo pharetra a. Vivamus laoreet ex massa, iaculis placerat est efficitur quis. Nullam nec nulla vitae lorem suscipit vehicula. In tincidunt vitae lacus a finibus. In a tempor magna, vel ultrices massa.";

    private static final long RESULT_INVALID = 1;
    private static final long RESPONSE_OBJECT_NULL = 2;
    private static final long API_ERROR = 3;
    private static final long HASH_GOOD = 4;

    private Upload mUpload;
    private static int mId;

    private static IHttp sHttp = new IHttp() {
        @Override
        public Response doGet(String url, Map<String, Object> headers) {
            return null;
        }

        @Override
        public Response doPost(String url, Map<String, Object> headers, byte[] payload) {
            Map<String, List<String>> headerFields = new HashMap<String, List<String>>();
            List<String> contentTypeList = new ArrayList<String>();
            contentTypeList.add("application/json");
            headerFields.put("Content-Type", contentTypeList);

            String responseString;

            if (mId == RESPONSE_OBJECT_NULL) {
                responseString = "";
            } else if (mId == API_ERROR) {
                responseString = "{\"response\":{\"action\":\"upload\\/instant\"," +
                        "\"message\":\"The supplied Session Token is expired or invalid\"," +
                        "\"error\":105,\"result\":\"Error\",\"current_api_version\":\"1.2\"}}";
            } else if (mId == RESULT_INVALID) {
                return new Response(0, null, null);
            } else {
                responseString = "{\"response\":" +
                        "{\"action\":\"upload/instant\",\"quickkey\":\"32d392v90o3pvd3\",\"filename\":\"whatever.gif\"," +
                        "\"newrevision\":{\"revision\":\"456222\",\"epoch\":\"1392312822\"},\"newfolderrevision\":" +
                        "{\"revision\":\"123\",\"epoch\":\"1392670667\"},\"result\":\"Success\"," +
                        "\"device_revision\":\"22551\",\"current_api_version\":\"1.0\"}}";
            }

            return new Response(200, responseString.getBytes(), headerFields);
        }
    };

    private static ITokenManager sTokenManager = new ITokenManager() {
        @Override
        public <T extends Token> T take(Class<T> token) {
            return null;
        }

        @Override
        public <T extends Token> void give(T token) {

        }

        @Override
        public void tokensBad() {

        }
    };

    private static UploadManagerTestImpl sUploadManager = new UploadManagerTestImpl(1, sHttp, sTokenManager);

    @Override
    public void setUp() throws Exception {
        super.setUp();
        File file = new File("InstantTest.txt");
        file.createNewFile();


        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(TEXT.getBytes());
        fileOutputStream.close();
    }

    @Override
    public void tearDown() throws Exception {
        File file = new File("InstantTest.txt");
        file.delete();
        mUpload = null;
        sUploadManager.resetTestFields();
    }

    public void testRunResultInvalid() throws Exception {
        mId = 1;
        File file = new File("InstantTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Instant.InstantUpload upload = new Instant.InstantUpload(mUpload, "hash_result_invalid");
        Instant instant = new Instant(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(instant);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mResultInvalidDuringUpload);
    }

    public void testRunResponseObjectNull() throws Exception {
        mId = 2;
        File file = new File("InstantTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Instant.InstantUpload upload = new Instant.InstantUpload(mUpload, "hash_response_object_null");
        Instant instant = new Instant(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(instant);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mResponseObjectNull);
    }
    public void testRunApiError() throws Exception {
        mId = 3;
        File file = new File("InstantTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Instant.InstantUpload upload = new Instant.InstantUpload(mUpload, "hash");
        Instant instant = new Instant(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(instant);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mApiError);
    }

    public void testRunSuccess() throws Exception {
        mId = 4;
        File file = new File("InstantTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Instant.InstantUpload upload = new Instant.InstantUpload(mUpload, "hash");
        Instant instant = new Instant(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(instant);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mInstantFinished);
    }
}