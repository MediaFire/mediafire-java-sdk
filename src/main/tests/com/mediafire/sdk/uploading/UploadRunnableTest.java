package com.mediafire.sdk.uploading;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

public class UploadRunnableTest extends TestCase {
    private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nisi nisl, pretium in rhoncus id, mattis ac ligula. Curabitur leo nisi, molestie sed ullamcorper vitae, mattis at lectus. Cras efficitur libero sed risus laoreet pellentesque. Nam suscipit quam ex, interdum imperdiet justo pharetra a. Vivamus laoreet ex massa, iaculis placerat est efficitur quis. Nullam nec nulla vitae lorem suscipit vehicula. In tincidunt vitae lacus a finibus. In a tempor magna, vel ultrices massa.";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        File file = new File("QueryParamsTest.txt");
        file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(TEXT.getBytes());
        fileOutputStream.close();
    }

    @Override
    public void tearDown() throws Exception {
        File file = new File("QueryParamsTest.txt");
        file.delete();
    }

    @Test
    public void testCheckQueryFilename() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        Check check = new Check(upload, null, null, null);
        Map<String, Object> query = check.makeQueryParams();

        assertTrue(query.containsKey("filename") && query.get("filename").equals("QueryParamsTest.txt"));
    }

    @Test
    public void testCheckQueryHash() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        Check check = new Check(upload, null, null, null);
        Map<String, Object> query = check.makeQueryParams();

        assertTrue(query.containsKey("hash") && query.get("hash").equals("5c2781ead10335aab511f92230861d2601e96e2affca1abae22ad35e02f37e24"));
    }

    @Test
    public void testCheckQuerySize() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        Check check = new Check(upload, null, null, null);
        Map<String, Object> query = check.makeQueryParams();

        assertTrue(query.containsKey("size") && query.get("size").equals(485L));
    }

    @Test
    public void testCheckQueryResumable() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        Check check = new Check(upload, null, null, null);
        Map<String, Object> query = check.makeQueryParams();

        assertTrue(query.containsKey("resumable") && query.get("resumable").equals("yes"));
    }

    @Test
    public void testCheckQueryResponseFormat() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        Check check = new Check(upload, null, null, null);
        Map<String, Object> query = check.makeQueryParams();

        assertTrue(query.containsKey("response_format") && query.get("response_format").equals("json"));
    }

    @Test
    public void testPollQueryKey() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        upload.setPollKey("polling_key");
        Poll poll = new Poll(upload, null, null, null);

        Map<String, Object> query = poll.makeQueryParams();
        assertTrue(query.containsKey("key") && query.get("key").equals("polling_key"));
    }

    @Test
    public void testPollQueryResponseFormat() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        upload.setPollKey("polling_key");
        Poll poll = new Poll(upload, null, null, null);

        Map<String, Object> query = poll.makeQueryParams();
        assertTrue(query.containsKey("response_format") && query.get("response_format").equals("json"));
    }

    @Test
    public void testInstantQueryResponseFormat() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        upload.setHash("some_hash");
        Instant instant = new Instant(upload, null, null, null);

        Map<String, Object> query = instant.makeQueryParams();
        assertTrue(query.containsKey("response_format") && query.get("response_format").equals("json"));
//        {hash=some_hash, size=485, filename=QueryParamsTest.txt, action_on_duplicate=keep, response_format=json}
    }

    @Test
    public void testInstantQueryActionOnDuplicate() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        upload.setHash("some_hash");
        Instant instant = new Instant(upload, null, null, null);

        Map<String, Object> query = instant.makeQueryParams();
        assertTrue(query.containsKey("action_on_duplicate") && query.get("action_on_duplicate").equals("keep"));
    }

    @Test
    public void testInstantQueryFileName() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        upload.setHash("some_hash");
        Instant instant = new Instant(upload, null, null, null);

        Map<String, Object> query = instant.makeQueryParams();
        assertTrue(query.containsKey("filename") && query.get("filename").equals("QueryParamsTest.txt"));
    }

    @Test
    public void testInstantQuerySize() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        upload.setHash("some_hash");
        Instant instant = new Instant(upload, null, null, null);

        Map<String, Object> query = instant.makeQueryParams();
        assertTrue(query.containsKey("size") && query.get("size").equals(485L));
    }

    @Test
    public void testInstantQueryHash() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        upload.setHash("some_hash");
        Instant instant = new Instant(upload, null, null, null);

        Map<String, Object> query = instant.makeQueryParams();
        assertTrue(query.containsKey("hash") && query.get("hash").equals("some_hash"));
    }

    @Test
    public void testResumableQueryResponseFormat() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        upload.setHash("some_hash");
        upload.setNumberOfUnits(1);
        upload.setUnitSize(1);
        upload.updateUploadBitmap(1, null);
        Resumable resumable = new Resumable(upload, null, null, null);

        Map<String, Object> query = resumable.makeQueryParams();
        assertTrue(query.containsKey("response_format") && query.get("response_format").equals("json"));
    }

    @Test
    public void testResumableQueryActionOnDuplicate() throws Exception {
        Upload upload = new Upload(1, "QueryParamsTest.txt");
        upload.setHash("some_hash");
        upload.setNumberOfUnits(1);
        upload.setUnitSize(1);
        upload.updateUploadBitmap(1, null);
        Resumable resumable = new Resumable(upload, null, null, null);

        Map<String, Object> query = resumable.makeQueryParams();
        assertTrue(query.containsKey("action_on_duplicate") && query.get("action_on_duplicate").equals("keep"));
    }
}