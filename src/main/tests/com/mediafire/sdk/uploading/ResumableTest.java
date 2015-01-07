package com.mediafire.sdk.uploading;

import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.Token;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ResumableTest extends TestCase {

    private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nisi nisl, pretium in rhoncus id, mattis ac ligula. Curabitur leo nisi, molestie sed ullamcorper vitae, mattis at lectus. Cras efficitur libero sed risus laoreet pellentesque. Nam suscipit quam ex, interdum imperdiet justo pharetra a. Vivamus laoreet ex massa, iaculis placerat est efficitur quis. Nullam nec nulla vitae lorem suscipit vehicula. In tincidunt vitae lacus a finibus. In a tempor magna, vel ultrices massa.";


    private static final long RESULT_INVALID = 1;
    private static final long API_RESPONSE_NULL = 2;
    private static final long API_ERROR = 3;
    private static final long PROGRESS_SINGLE_CHUNK = 4;
    private static final long PROGRESS_MULTI_CHUNK = 5;
    private static final long FINISHED_MULTI_CHUNK = 6;
    private static final long FINISHED_SINGLE_CHUNK = 7;


    private Upload mUpload;
    private static long mId;

    private static int sLoopCount = 0;

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

            if (mId == RESULT_INVALID) {
                return new Response(0, null, null);
            } else if (mId == API_RESPONSE_NULL) {
                responseString = "";
            } else if (mId == API_ERROR) {
                responseString = "{\"response\":{\"action\":\"upload\\/check\"," +
                        "\"message\":\"The supplied Session Token is expired or invalid\"," +
                        "\"error\":126,\"result\":\"Error\",\"current_api_version\":\"1.2\"}}";
            } else if (mId == PROGRESS_SINGLE_CHUNK) {
                responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                        "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                        "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                        "\"bitmap\":{\"count\":\"1\",\"words\":[\"0\"]}," +
                        "\"number_of_units\":\"1\"," +
                        "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
            } else if (mId == PROGRESS_MULTI_CHUNK || mId == FINISHED_MULTI_CHUNK) {
                switch (sLoopCount) {
                    case 0:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"0\"]}," +
                                "\"number_of_units\":\"10\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                    case 1:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"1\"]}," +
                                "\"number_of_units\":\"9\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                    case 2:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"3\"]}," +
                                "\"number_of_units\":\"8\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                    case 3:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"7\"]}," +
                                "\"number_of_units\":\"7\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                    case 4:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"111\"]}," +
                                "\"number_of_units\":\"6\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                    case 5:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"127\"]}," +
                                "\"number_of_units\":\"5\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                    case 6:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"992\"]}," +
                                "\"number_of_units\":\"4\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                    case 7:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"1008\"]}," +
                                "\"number_of_units\":\"3\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                    case 8:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"1016\"]}," +
                                "\"number_of_units\":\"2\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                    case 9:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"1022\"]}," +
                                "\"number_of_units\":\"1\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                    default:
                        responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                                "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                                "\"resumable_upload\":{\"all_units_ready\":\"yes\"," +
                                "\"bitmap\":{\"count\":\"1\",\"words\":[\"1023\"]}," +
                                "\"number_of_units\":\"0\"," +
                                "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
                        break;
                }
                sLoopCount++;
            } else {
                responseString = "{\"response\":{\"action\":\"upload/resumable\"," +
                        "\"doupload\":{\"result\":\"0\",\"key\":\"b7bpiovaoyv\"}," +
                        "\"resumable_upload\":{\"all_units_ready\":\"no\"," +
                        "\"bitmap\":{\"count\":\"1\",\"words\":[\"0\"]}," +
                        "\"number_of_units\":\"1\"," +
                        "\"unit_size\":\"4194304\"},\"result\":\"Success\",\"current_api_version\":\"1.0\"}}";
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

    private static UploadManagerTestImpl sUploadManager = new UploadManagerTestImpl(sHttp, sTokenManager, new PausableExecutor(1, 1, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()));

    @Override
    public void setUp() throws Exception {
        super.setUp();
        File file = new File("ResumableTest.txt");
        file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(TEXT.getBytes());
        fileOutputStream.close();
        sLoopCount = 0;
    }

    @Override
    public void tearDown() throws Exception {
        File file = new File("ResumableTest.txt");
        file.delete();
        mUpload = null;
        sUploadManager.resetTestFields();
        sLoopCount = 0;
    }

    @Test
    public void testRunResultInvalid() throws Exception {
        mId = RESULT_INVALID;
        File file = new File("ResumableTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(RESULT_INVALID, file, options);

        List<Integer> words = new LinkedList<Integer>();
        Resumable.ResumableUpload upload = new Resumable.ResumableUpload(mUpload, "hash", 1, 1, 1, words);
        Resumable check = new Resumable(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mResultInvalidDuringUpload);
    }

    @Test
    public void testRunApiObjectNull() throws Exception {
        mId = API_RESPONSE_NULL;
        File file = new File("ResumableTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(API_RESPONSE_NULL, file, options);

        List<Integer> words = new LinkedList<Integer>();
        Resumable.ResumableUpload upload = new Resumable.ResumableUpload(mUpload, "hash", 1, 1, 1, words);
        Resumable check = new Resumable(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mResponseObjectNull);
    }

    @Test
    public void testRunApiError() throws Exception {
        mId = API_ERROR;
        File file = new File("ResumableTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(API_ERROR, file, options);

        List<Integer> words = new LinkedList<Integer>();
        Resumable.ResumableUpload upload = new Resumable.ResumableUpload(mUpload, "hash", 1, 1, 1, words);
        Resumable check = new Resumable(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mApiError);
    }

    @Test
    public void testRunResumableProgressSingleChunk() throws Exception {
        mId = PROGRESS_SINGLE_CHUNK;
        File file = new File("ResumableTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(PROGRESS_SINGLE_CHUNK, file, options);

        List<Integer> words = new LinkedList<Integer>();
        Resumable.ResumableUpload upload = new Resumable.ResumableUpload(mUpload, "hash", 1, 1, 1, words);
        Resumable check = new Resumable(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mResumableProgress);
    }

    @Test
    public void testRunResumableProgressMultiChunk() throws Exception {
        mId = PROGRESS_MULTI_CHUNK;
        File file = new File("ResumableTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(PROGRESS_MULTI_CHUNK, file, options);

        List<Integer> words = new LinkedList<Integer>();
        Resumable.ResumableUpload upload = new Resumable.ResumableUpload(mUpload, "hash", 10, 1, 1, words);
        Resumable check = new Resumable(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mResumableProgress);
    }

    @Test
    public void testRunResumableFinishedSingleChunk() throws Exception {
        mId = FINISHED_SINGLE_CHUNK;
        File file = new File("ResumableTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(FINISHED_SINGLE_CHUNK, file, options);

        List<Integer> words = new LinkedList<Integer>();
        Resumable.ResumableUpload upload = new Resumable.ResumableUpload(mUpload, "hash", 10, 1, 1, words);
        Resumable check = new Resumable(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mResumableFinished);
    }

    @Test
    public void testRunResumableFinishedMultiChunk() throws Exception {
        mId = FINISHED_MULTI_CHUNK;
        File file = new File("ResumableTest.txt");
        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(FINISHED_MULTI_CHUNK, file, options);

        List<Integer> words = new LinkedList<Integer>();
        Resumable.ResumableUpload upload = new Resumable.ResumableUpload(mUpload, "hash", 1, 1, 1, words);
        Resumable check = new Resumable(upload, sHttp, sTokenManager, sUploadManager);

        Thread thread = new Thread(check);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mResumableFinished);
    }
}