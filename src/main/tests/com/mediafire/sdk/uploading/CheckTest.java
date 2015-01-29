package com.mediafire.sdk.uploading;

import com.mediafire.sdk.config.HttpHandler;
import com.mediafire.sdk.config.TokenManager;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.Token;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CheckTest extends TestCase {
    private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nisi nisl, pretium in rhoncus id, mattis ac ligula. Curabitur leo nisi, molestie sed ullamcorper vitae, mattis at lectus. Cras efficitur libero sed risus laoreet pellentesque. Nam suscipit quam ex, interdum imperdiet justo pharetra a. Vivamus laoreet ex massa, iaculis placerat est efficitur quis. Nullam nec nulla vitae lorem suscipit vehicula. In tincidunt vitae lacus a finibus. In a tempor magna, vel ultrices massa.";

    private static final long RESULT_INVALID = 1;
    private static final long API_RESPONSE_NULL = 2;
    private static final long API_ERROR = 3;
    private static final long FILE_WILL_EXCEED_STORAGE = 4;
    private static final long STORAGE_LIMIT_EXCEEDED = 5;
    private static final long RESUMABLE_UPLOAD_NULL = 6;
    private static final long RESUMABLE_BITMAP_NULL = 7;
    private static final long RESUMABLE_GOOD = 8;


    private Upload mUpload;
    private static int mId;

    private static HttpHandler sHttp = new HttpHandler() {

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

            if (mId == RESULT_INVALID) {
                return new Response(0, null, null);
            } else if (mId == API_RESPONSE_NULL) {
                responseString = "";
            } else if (mId == API_ERROR) {
                responseString = "{\"response\":{\"action\":\"upload\\/check\"," +
                        "\"message\":\"The supplied Session Token is expired or invalid\"," +
                        "\"error\":126,\"result\":\"Error\",\"current_api_version\":\"1.2\"}}";
            } else if (mId == FILE_WILL_EXCEED_STORAGE) {
                responseString = "{\"response\":{\"action\":\"upload/check\",\"hash_exists\":\"no\"," +
                        "\"file_exists\":\"no\",\"available_space\":\"0\",\"used_storage_size\":\"53687091199\"," +
                        "\"storage_limit\":\"53687091200\",\"storage_limit_exceeded\":\"no\"," +
                        "\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
            } else if (mId == STORAGE_LIMIT_EXCEEDED) {
                responseString = "{\"response\":{\"action\":\"upload/check\",\"hash_exists\":\"no\"," +
                        "\"file_exists\":\"no\",\"available_space\":\"0\"," +
                        "\"used_storage_size\":\"54882441579\",\"storage_limit\":\"53687091200\"," +
                        "\"storage_limit_exceeded\":\"yes\",\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
            } else if (mId == RESUMABLE_UPLOAD_NULL) {
                responseString = "{\"response\":{\"action\":\"upload/check\"," +
                        "\"hash_exists\":\"no\",\"file_exists\":\"no\"," +
                        "\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
            } else if (mId == RESUMABLE_BITMAP_NULL) {
                responseString = "{\"response\":{\"action\":\"upload/check\",\"hash_exists\":\"no\"," +
                        "\"file_exists\":\"no\",\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                        "\"number_of_units\":\"28\",\"unit_size\":\"2097152\"}," +
                        "\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
            } else {
                responseString = "{\"response\":{\"action\":\"upload\\/check\",\"hash_exists\":\"yes\"," +
                        "\"in_account\":\"yes\",\"in_folder\":\"yes\",\"file_exists\":\"yes\"," +
                        "\"different_hash\":\"no\",\"duplicate_quickkey\":\"oqu8a32pt7nkou1\"," +
                        "\"resumable_upload\":{\"all_units_ready\":\"no\",\"number_of_units\":\"21\"," +
                        "\"unit_size\":\"4194304\",\"bitmap\":{\"count\":\"2\",\"words\":[\"0\",\"0\"]}}," +
                        "\"available_space\":\"19024902978\",\"used_storage_size\":\"2452030654\"," +
                        "\"storage_limit\":\"21476933632\",\"storage_limit_exceeded\":\"no\"," +
                        "\"result\":\"Success\",\"current_api_version\":\"1.2\"}}";
            }

            return new Response(200, responseString.getBytes(), headerFields);
        }
    };

    private static TokenManager sTokenManager = new TokenManager() {
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

    @Override
    public void setUp() throws Exception {
        super.setUp();
        File file = new File("CheckTest.txt");
        file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(TEXT.getBytes());
        fileOutputStream.close();
    }

    @Override
    public void tearDown() throws Exception {
        File file = new File("CheckTest.txt");
        file.delete();
        mUpload = null;
    }

    @Test
    public void testRunResultInvalid() throws Exception {
        mId = 1;
        File file = new File("CheckTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        UploadProcessTestImpl sUploadManager = new UploadProcessTestImpl(sHttp, sTokenManager, mUpload);
        Check check = new Check(mUpload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mGeneralError);
    }

    @Test
    public void testRunApiResponseNull() throws Exception {
        mId = 2;
        File file = new File("CheckTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        UploadProcessTestImpl sUploadManager = new UploadProcessTestImpl(sHttp, sTokenManager, mUpload);
        Check check = new Check(mUpload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mGeneralError);
    }

    @Test
    public void testRunApiError() throws Exception {
        mId = 3;
        File file = new File("CheckTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        UploadProcessTestImpl sUploadManager = new UploadProcessTestImpl(sHttp, sTokenManager, mUpload);
        Check check = new Check(mUpload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mApiError);
    }

    @Test
    public void testRunFileWillExceedStorage() throws Exception {
        mId = 4;
        File file = new File("CheckTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        UploadProcessTestImpl sUploadManager = new UploadProcessTestImpl(sHttp, sTokenManager, mUpload);
        Check check = new Check(mUpload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mStorageLimitExceeded);
    }

    @Test
    public void testRunStorageLimitExceeded() throws Exception {
        mId = 5;
        File file = new File("CheckTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        UploadProcessTestImpl sUploadManager = new UploadProcessTestImpl(sHttp, sTokenManager, mUpload);
        Check check = new Check(mUpload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mStorageLimitExceeded);
    }

    @Test
    public void testRunResumableUploadNull() throws Exception {
        mId = 6;
        File file = new File("CheckTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        UploadProcessTestImpl sUploadManager = new UploadProcessTestImpl(sHttp, sTokenManager, mUpload);
        Check check = new Check(mUpload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mCheckFinished);
    }

    @Test
    public void testRunResumableUploadBitmapNull() throws Exception {
        mId = 7;
        File file = new File("CheckTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        UploadProcessTestImpl sUploadManager = new UploadProcessTestImpl(sHttp, sTokenManager, mUpload);
        Check check = new Check(mUpload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mCheckFinished);
    }

    @Test
    public void testRunGood() throws Exception {
        mId = 8;
        File file = new File("CheckTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        UploadProcessTestImpl sUploadManager = new UploadProcessTestImpl(sHttp, sTokenManager, mUpload);
        Check check = new Check(mUpload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mCheckFinished);
    }

}