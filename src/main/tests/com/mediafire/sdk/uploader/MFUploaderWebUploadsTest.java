package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MFClient;
import com.mediafire.sdk.MediaFireCredentialsStore;
import junit.framework.TestCase;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MFUploaderWebUploadsTest extends TestCase {

    private static final List<String> URLS = new ArrayList<String>();
    static {
        URLS.add("http://i.imgur.com/rOddQjb.png");
        URLS.add("http://i.imgur.com/aQz4J8y.gif");
        URLS.add("http://i.imgur.com/zUDo2Wn.gif");
        URLS.add("http://i.imgur.com/xwIMOYI.png");
        URLS.add("http://i.imgur.com/gn6UqIW.gif");
        URLS.add("http://i.imgur.com/VQvc25F.png");
        URLS.add("http://i.imgur.com/R9l2rIg.jpg");
        URLS.add("http://i.imgur.com/Voc4RU2.png");
        URLS.add("http://i.imgur.com/YOI7iLr.png");
        URLS.add("http://i.imgur.com/oOa9iUC.jpg");
        URLS.add("http://i.imgur.com/HpAWYbA.jpg");
        URLS.add("http://i.imgur.com/EBu6oFg.png");
        URLS.add("http://i.imgur.com/Gi6rtcV.gif");
        URLS.add("http://i.imgur.com/tNj8M2L.gif");
    }

    private MFClient mediaFire;
    private MFUploader uploader;
    private MFUploadStore store;

    public void setUp() throws Exception {
        super.setUp();
        MFClient.Builder builder = new MFClient.Builder("40767", null);
        builder.apiVersion("1.5");
        mediaFire = builder.build();
        mediaFire.getCredentialStore().setEmail(new MediaFireCredentialsStore.EmailCredentials("badtestemail@badtestemail.com", "badtestemail"));

        store = new MFUploadStore();
        uploader = new MFUploader(mediaFire, store, 3);
    }

    public void tearDown() throws Exception {

    }

    public void testSingleWebUpload() throws Exception {
        uploader.resume();
        MediaFireWebUpload webUpload = new MFWebUpload(URLS.get(0), URI.create(URLS.get(0)).toURL().getFile().replaceFirst("/", ""), null);
        uploader.schedule(webUpload);

        while (uploader.areJobsInProgress()) {
            Thread.sleep(250);
        }

        assertTrue(true);
    }

    public void testSingleWebUploadLarge() throws Exception {
        String url = "https://upload.wikimedia.org/wikipedia/commons/3/3d/LARGE_elevation.jpg";

        uploader.resume();
        MediaFireWebUpload webUpload = new MFWebUpload(url, "LARGE_elevation.jpg", null);
        uploader.schedule(webUpload);

        while (uploader.areJobsInProgress()) {
            Thread.sleep(250);
        }

        assertTrue(true);
    }

    public void testMultiWebUpload() throws Exception {
        uploader.resume();
        for (String url : URLS) {
            uploader.schedule(new MFWebUpload(url, URI.create(url).toURL().getFile().replaceFirst("/", ""), null));
        }

        while (uploader.areJobsInProgress()) {
            Thread.sleep(250);
        }

        assertTrue(true);
    }
}