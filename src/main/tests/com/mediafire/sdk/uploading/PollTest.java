package com.mediafire.sdk.uploading;

import com.mediafire.sdk.config.IHttp;
import com.mediafire.sdk.config.ITokenManager;
import com.mediafire.sdk.http.Response;
import com.mediafire.sdk.token.Token;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PollTest extends TestCase {
    private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nisi nisl, pretium in rhoncus id, mattis ac ligula. Curabitur leo nisi, molestie sed ullamcorper vitae, mattis at lectus. Cras efficitur libero sed risus laoreet pellentesque. Nam suscipit quam ex, interdum imperdiet justo pharetra a. Vivamus laoreet ex massa, iaculis placerat est efficitur quis. Nullam nec nulla vitae lorem suscipit vehicula. In tincidunt vitae lacus a finibus. In a tempor magna, vel ultrices massa.";

    private Upload mUpload;

    private static final long LOOP_1_TIME = 1;
    private static final long LOOP_10_TIMES = 2;
    private static final long PARAMS_FILE_ERROR = 3;
    private static final long PARAMS_INVALID = 4;
    private static final long HTTP_WORKER_RETURN_NULL = 5;
    private static final long RESPONSE_OBJECT_NULL = 6;
    private static final long MAX_POLLS_REACHED = 7;

    private static long mId;

    private static IHttp sHttp = new IHttp() {
        public int sRunNumber = 0;

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

            String payloadString = new String(payload);
            String responseString;

            if (mId == MAX_POLLS_REACHED) {
                responseString = "{" +
                        "\"response\": {\"action\": \"upload/poll_upload\"," +
                        "\"doupload\": {" +
                        "\"result\": \"0\",\"status\": \"5\",\"description\": \"Waiting for verification\",\"size\": \"1334903\"," +
                        "\"revision\": \"456222\"}," +
                        "\"result\": \"Success\"," +
                        "\"current_api_version\": \"1.0\"" +
                        "}" +
                        "}";
            } else if (mId == RESPONSE_OBJECT_NULL) {
                responseString = "";
                resetRuns();
            } else if (mId == HTTP_WORKER_RETURN_NULL) {
                resetRuns();
                return null;
            } else if (mId == PARAMS_INVALID) {
                responseString = "{\"response\":{\"action\":\"upload\\/poll_upload\",\"message\":\"One or more parameters for this request are invalid\",\"error\":129,\"result\":\"Error\",\"current_api_version\":\"1.2\"}}";
                resetRuns();
            } else if (mId == PARAMS_FILE_ERROR) {
                responseString = "{" +
                        "\"response\": {\"action\": \"upload/poll_upload\"," +
                        "\"doupload\": {" +
                        "\"result\": \"0\",\"status\": \"4\",\"description\": \"Upload is completed\",\"size\": \"1334903\",\"fileerror\": \"8\"," +
                        "\"revision\": \"456222\"}," +
                        "\"result\": \"Success\"," +
                        "\"current_api_version\": \"1.0\"" +
                        "}" +
                        "}";
                resetRuns();
            } else if (mId == LOOP_10_TIMES) {
                switch (sRunNumber) {
                    case 0:
                        responseString = "{" +
                                "\"response\": {\"action\": \"upload/poll_upload\"," +
                                "\"doupload\": {" +
                                "\"result\": \"0\",\"status\": \"4\",\"description\": \"Upload is completed\",\"size\": \"1334903\"," +
                                "\"revision\": \"456222\"}," +
                                "\"result\": \"Success\"," +
                                "\"current_api_version\": \"1.0\"" +
                                "}" +
                                "}";
                        break;
                    case 1:
                        responseString = "{" +
                                "\"response\": {\"action\": \"upload/poll_upload\"," +
                                "\"doupload\": {" +
                                "\"result\": \"0\",\"status\": \"5\",\"description\": \"Waiting for verification\",\"size\": \"1334903\"," +
                                "\"revision\": \"456222\"}," +
                                "\"result\": \"Success\"," +
                                "\"current_api_version\": \"1.0\"" +
                                "}" +
                                "}";
                        break;
                    case 2:
                        responseString = "{" +
                                "\"response\": {\"action\": \"upload/poll_upload\"," +
                                "\"doupload\": {" +
                                "\"result\": \"0\",\"status\": \"5\",\"description\": \"Waiting for verification\",\"size\": \"1334903\"," +
                                "\"revision\": \"456222\"}," +
                                "\"result\": \"Success\"," +
                                "\"current_api_version\": \"1.0\"" +
                                "}" +
                                "}";
                        break;
                    case 3:
                        responseString = "{" +
                                "\"response\": {\"action\": \"upload/poll_upload\"," +
                                "\"doupload\": {" +
                                "\"result\": \"0\",\"status\": \"6\",\"description\": \"Verifying File\",\"size\": \"1334903\"," +
                                "\"revision\": \"456222\"}," +
                                "\"result\": \"Success\"," +
                                "\"current_api_version\": \"1.0\"" +
                                "}" +
                                "}";
                        break;
                    case 4:
                        responseString = "{" +
                                "\"response\": {\"action\": \"upload/poll_upload\"," +
                                "\"doupload\": {" +
                                "\"result\": \"0\",\"status\": \"6\",\"description\": \"Verifying File\",\"size\": \"1334903\"," +
                                "\"revision\": \"456222\"}," +
                                "\"result\": \"Success\"," +
                                "\"current_api_version\": \"1.0\"" +
                                "}" +
                                "}";
                        break;
                    case 5:
                        responseString = "{" +
                                "\"response\": {\"action\": \"upload/poll_upload\"," +
                                "\"doupload\": {" +
                                "\"result\": \"0\",\"status\": \"6\",\"description\": \"Verifying File\",\"size\": \"1334903\"," +
                                "\"revision\": \"456222\"}," +
                                "\"result\": \"Success\"," +
                                "\"current_api_version\": \"1.0\"" +
                                "}" +
                                "}";
                        break;
                    case 6:
                        responseString = "{" +
                                "\"response\": {\"action\": \"upload/poll_upload\"," +
                                "\"doupload\": {" +
                                "\"result\": \"0\",\"status\": \"11\",\"description\": \"Finished verification\",\"size\": \"1334903\"," +
                                "\"revision\": \"456222\"}," +
                                "\"result\": \"Success\"," +
                                "\"current_api_version\": \"1.0\"" +
                                "}" +
                                "}";
                        break;
                    case 7:
                        responseString = "{" +
                                "\"response\": {\"action\": \"upload/poll_upload\"," +
                                "\"doupload\": {" +
                                "\"result\": \"0\",\"status\": \"18\",\"description\": \"Waiting for assembly\",\"size\": \"1334903\"," +
                                "\"revision\": \"456222\"}," +
                                "\"result\": \"Success\"," +
                                "\"current_api_version\": \"1.0\"" +
                                "}" +
                                "}";
                        break;
                    case 8:
                        responseString = "{" +
                                "\"response\": {\"action\": \"upload/poll_upload\"," +
                                "\"doupload\": {" +
                                "\"result\": \"0\",\"status\": \"19\",\"description\": \"Assembling File\",\"size\": \"1334903\"," +
                                "\"revision\": \"456222\"}," +
                                "\"result\": \"Success\"," +
                                "\"current_api_version\": \"1.0\"" +
                                "}" +
                                "}";
                        break;
                    case 9:
                        responseString = "{" +
                                "\"response\": {\"action\": \"upload/poll_upload\"," +
                                "\"doupload\": {" +
                                "\"result\": \"0\",\"status\": \"99\",\"description\": \"No more requests for this key\",\"quickkey\": \"l86be19e1nrla8p\",\"size\": \"1334903\"," +
                                "\"revision\": \"456222\"}," +
                                "\"result\": \"Success\"," +
                                "\"current_api_version\": \"1.0\"" +
                                "}" +
                                "}";
                        resetRuns();
                        break;
                    default:
                        responseString = null;
                }
            } else {
                resetRuns();
                responseString = "{" +
                        "\"response\": {\"action\": \"upload/poll_upload\"," +
                        "\"doupload\": {" +
                        "\"result\": \"0\",\"status\": \"99\",\"description\": \"No more requests for this key\",\"quickkey\": \"l86be19e1nrla8p\",\"size\": \"1334903\"," +
                        "\"revision\": \"456222\"}," +
                        "\"result\": \"Success\"," +
                        "\"current_api_version\": \"1.0\"" +
                        "}" +
                        "}";
            }

            sRunNumber++;
            return new Response(200, responseString == null ? null : responseString.getBytes(), headerFields);
        }

        public void resetRuns() {
            sRunNumber = 0;
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
        File file = new File("PollTest.txt");
        file.createNewFile();


        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(TEXT.getBytes());
        fileOutputStream.close();
    }

    @Override
    public void tearDown() throws Exception {
        File file = new File("PollTest.txt");
        file.delete();
        mUpload = null;
        sUploadManager.resetTestFields();
    }

    @Test
    public void testRun1Loop() throws Exception {
        mId = 1;
        File file = new File("PollTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Poll.PollUpload upload = new Poll.PollUpload(mUpload, "poll_key");
        Poll poll = new Poll(upload, sHttp, sTokenManager, sUploadManager, 1, 60);

        Thread thread = new Thread(poll);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mPollFinished);
    }

    @Test
    public void testRun10Loop() throws Exception {
        mId = 2;
        File file = new File("PollTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Poll.PollUpload upload = new Poll.PollUpload(mUpload, "poll_key");
        Poll poll = new Poll(upload, sHttp, sTokenManager, sUploadManager, 1, 60);

        Thread thread = new Thread(poll);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mPollFinished);
    }

    @Test
    public void testRunFileError() throws Exception {
        mId = 3;
        File file = new File("PollTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Poll.PollUpload upload = new Poll.PollUpload(mUpload, "poll_key");
        Poll poll = new Poll(upload, sHttp, sTokenManager, sUploadManager, 1, 60);

        Thread thread = new Thread(poll);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mApiError);
    }

    @Test
    public void testRunParamsInvalid() throws Exception {
        mId = 4;
        File file = new File("PollTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Poll.PollUpload upload = new Poll.PollUpload(mUpload, "poll_key");
        Poll poll = new Poll(upload, sHttp, sTokenManager, sUploadManager, 1, 60);

        Thread thread = new Thread(poll);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mApiError);
    }

    @Test
    public void testRunHttpWorkerReturnNull() throws Exception {
        mId = 5;
        File file = new File("PollTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Poll.PollUpload upload = new Poll.PollUpload(mUpload, "poll_key");
        Poll poll = new Poll(upload, sHttp, sTokenManager, sUploadManager, 1, 60);

        Thread thread = new Thread(poll);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mResultInvalidDuringUpload);
    }

    @Test
    public void testRunResponseObjectNull() throws Exception {
        mId = 6;
        File file = new File("PollTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Poll.PollUpload upload = new Poll.PollUpload(mUpload, "poll_key");
        Poll poll = new Poll(upload, sHttp, sTokenManager, sUploadManager, 1, 60);

        Thread thread = new Thread(poll);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mResponseObjectNull);
    }

    @Test
    public void testRunMaxPollsReached() throws Exception {
        mId = 7;
        File file = new File("PollTest.txt");

        Upload.Options options = new Upload.Options.Builder().build();
        mUpload = new Upload(mId, file, options);
        Poll.PollUpload upload = new Poll.PollUpload(mUpload, "poll_key");
        Poll poll = new Poll(upload, sHttp, sTokenManager, sUploadManager, 1, 60);

        Thread thread = new Thread(poll);
        thread.start();
        thread.join();

        assertEquals(true, sUploadManager.mPollMaxAttemptsReached);
    }
}