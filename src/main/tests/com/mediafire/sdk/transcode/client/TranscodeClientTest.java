package com.mediafire.sdk.transcode.client;

import com.mediafire.sdk.config.HttpWorkerInterface;
import com.mediafire.sdk.http.Result;
import com.mediafire.sdk.test_utility.DummyHttpWorker;
import junit.framework.TestCase;

public class TranscodeClientTest extends TestCase {

    private HttpWorkerInterface httpWorker = new DummyHttpWorker();
    TranscodeClient transcodeClient = new TranscodeClient(httpWorker);
    private String streamingUrl = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3";
    private String container = "mp3";

    public void testCreate240p() throws Exception {
        Result result = transcodeClient.create(streamingUrl, container, MediaSize._240P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=240p&exists=create&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCreate480p() throws Exception {
        Result result = transcodeClient.create(streamingUrl, container, MediaSize._480P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=480p&exists=create&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCreate720p() throws Exception {
        Result result = transcodeClient.create(streamingUrl, container, MediaSize._720P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=720p&exists=create&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCreate1080p() throws Exception {
        Result result = transcodeClient.create(streamingUrl, container, MediaSize._1080P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=1080p&exists=create&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCreateSizeDefault() throws Exception {
        Result result = transcodeClient.create(streamingUrl, container);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=480p&exists=create&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCreateSizeNull() throws Exception {
        Result result = transcodeClient.create(streamingUrl, container, null);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=480p&exists=create&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCheck240p() throws Exception {
        Result result = transcodeClient.check(streamingUrl, container, MediaSize._240P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=240p&exists=check&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCheck480p() throws Exception {
        Result result = transcodeClient.check(streamingUrl, container, MediaSize._480P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=480p&exists=check&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCheck720p() throws Exception {
        Result result = transcodeClient.check(streamingUrl, container, MediaSize._720P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=720p&exists=check&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCheck1080p() throws Exception {
        Result result = transcodeClient.check(streamingUrl, container, MediaSize._1080P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=1080p&exists=check&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCheckSizeDefault() throws Exception {
        Result result = transcodeClient.check(streamingUrl, container);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=480p&exists=check&response_format=json";

        assertEquals(expected, actual);
    }

    public void testCheckSizeNull() throws Exception {
        Result result = transcodeClient.check(streamingUrl, container, null);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=480p&exists=check&response_format=json";

        assertEquals(expected, actual);
    }

    public void testStatus240p() throws Exception {
        Result result = transcodeClient.status(streamingUrl, container, MediaSize._240P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=240p&exists=status&response_format=json";

        assertEquals(expected, actual);
    }

    public void testStatus480p() throws Exception {
        Result result = transcodeClient.status(streamingUrl, container, MediaSize._480P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=480p&exists=status&response_format=json";

        assertEquals(expected, actual);
    }

    public void testStatus720p() throws Exception {
        Result result = transcodeClient.status(streamingUrl, container, MediaSize._720P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=720p&exists=status&response_format=json";

        assertEquals(expected, actual);
    }

    public void testStatus1080p() throws Exception {
        Result result = transcodeClient.status(streamingUrl, container, MediaSize._1080P);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=1080p&exists=status&response_format=json";

        assertEquals(expected, actual);
    }

    public void testStatusSizeDefault() throws Exception {
        Result result = transcodeClient.status(streamingUrl, container);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=480p&exists=status&response_format=json";

        assertEquals(expected, actual);
    }

    public void testStatusSizeNull() throws Exception {
        Result result = transcodeClient.status(streamingUrl, container, null);
        DummyHttpWorker.DummyGETResponse response = (DummyHttpWorker.DummyGETResponse) result.getResponse();
        String actual = response.getOriginalUrl();
        String expected = "http://transcode1.mediafire.com/m22ztdtwu0rg/172pwp7c8dyngpq/e95a/Mario.mp3?container=mp3&media_size=480p&exists=status&response_format=json";

        assertEquals(expected, actual);
    }
}