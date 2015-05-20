package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MediaFire;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MediaFireUploaderTest extends TestCase {

    private static List<String> URLS = new ArrayList<String>();
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

    private MediaFire mediaFire;

    public void setUp() throws Exception {
        super.setUp();
        mediaFire = new MediaFire("40767");
    }

    public void testWebUploadsWithUploader() {
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        } catch (MFApiException e) {
            fail("Exception should not have been thrown: " + e);
        } catch (MFException e) {
            fail("Exception should not have been thrown: " + e);
        }

        MediaFireUploader uploader = new MediaFireUploader(3);

        for (String url : URLS) {
            try {
                uploader.schedule(new MediaFireUpload(mediaFire, 98, url, URI.create(url).toURL().getFile().replaceFirst("/", ""), null, 0));
                System.out.println(URI.create(url).toURL().getFile().replaceFirst("/", ""));
            } catch (MalformedURLException e) {
                fail("Exception should not have been thrown: " + e);
            }
        }

        uploader.resume();

        while (uploader.getQueueSize() != 0) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                fail("Exception should not have been thrown: " + e);
            }
        }

        assertTrue(true);
    }

    public void testSimpleUploadsWithUploader() {

        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        } catch (MFApiException e) {
            fail("Exception should not have been thrown: " + e);
        } catch (MFException e) {
            fail("Exception should not have been thrown: " + e);
        }

        MediaFireUploader uploader = new MediaFireUploader(3);

        List<File> fileList = new ArrayList<File>();
        for (int i = 0; i < 20; i++) {
            String prefix = "foobar" + i;
            String suffix = ".tmp";
            File file = null;
            try {
                file = File.createTempFile(prefix, suffix);
                Random random = new Random();
                byte dataToWrite[] = new byte[new Random().nextInt(2000000) + 100000];
                random.nextBytes(dataToWrite);
                FileOutputStream out = new FileOutputStream(file);
                out.write(dataToWrite);
                out.close();
            } catch (IOException e) {
                fail("Exception should not have been thrown: " + e);
            }

            fileList.add(file);
        }

        for (File f : fileList) {
            MediaFireUpload upload = new MediaFireUpload(mediaFire, 98, f, f.getName(), MediaFireUpload.ActionOnInAccount.UPLOAD_ALWAYS, null, 1);
            uploader.schedule(upload);
        }

        uploader.resume();

        while (uploader.getQueueSize() != 0) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                for (File f : fileList) {
                    f.delete();
                }
                fail("Exception should not have been thrown: " + e);
            }
        }

        for (File f : fileList) {
            f.delete();
        }

        assertTrue(true);
    }

    public void testResumableUploadsWithUploader() {

        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        } catch (MFApiException e) {
            fail("Exception should not have been thrown: " + e);
        } catch (MFException e) {
            fail("Exception should not have been thrown: " + e);
        }

        MediaFireUploader uploader = new MediaFireUploader(3);

        List<File> fileList = new ArrayList<File>();
        for (int i = 0; i < 20; i++) {
            String prefix = "foobar" + i;
            String suffix = ".tmp";
            File file = null;
            try {
                file = File.createTempFile(prefix, suffix);
                Random random = new Random();
                byte dataToWrite[] = new byte[new Random().nextInt(20000000) + 100000];
                random.nextBytes(dataToWrite);
                FileOutputStream out = new FileOutputStream(file);
                out.write(dataToWrite);
                out.close();
            } catch (IOException e) {
                fail("Exception should not have been thrown: " + e);
            }

            fileList.add(file);
        }

        for (File f : fileList) {
            MediaFireUpload upload = new MediaFireUpload(mediaFire, 98, f, f.getName(), MediaFireUpload.ActionOnInAccount.UPLOAD_ALWAYS, null, 1);
            uploader.schedule(upload);
        }

        uploader.resume();

        while (uploader.getQueueSize() != 0) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                for (File f : fileList) {
                    f.delete();
                }
                fail("Exception should not have been thrown: " + e);
            }
        }

        for (File f : fileList) {
            f.delete();
        }

        assertTrue(true);
    }

    public void testMultiUpload() {
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        } catch (MFApiException e) {
            fail("Exception should not have been thrown: " + e);
        } catch (MFException e) {
            fail("Exception should not have been thrown: " + e);
        }

        MediaFireUploader uploader = new MediaFireUploader(3);

        for (String url : URLS) {
            try {
                uploader.schedule(new MediaFireUpload(mediaFire, 98, url, URI.create(url).toURL().getFile().replaceFirst("/", ""), null, 0));
                System.out.println(URI.create(url).toURL().getFile().replaceFirst("/", ""));
            } catch (MalformedURLException e) {
                fail("Exception should not have been thrown: " + e);
            }
        }

        List<File> simpleUploads = new ArrayList<File>();
        for (int i = 0; i < 20; i++) {
            String prefix = "foobar" + i;
            String suffix = ".tmp";
            File file = null;
            try {
                file = File.createTempFile(prefix, suffix);
                Random random = new Random();
                byte dataToWrite[] = new byte[new Random().nextInt(2000000) + 100000];
                random.nextBytes(dataToWrite);
                FileOutputStream out = new FileOutputStream(file);
                out.write(dataToWrite);
                out.close();
            } catch (IOException e) {
                fail("Exception should not have been thrown: " + e);
            }

            simpleUploads.add(file);
        }

        for (File f : simpleUploads) {
            MediaFireUpload upload = new MediaFireUpload(mediaFire, 98, f, f.getName(), MediaFireUpload.ActionOnInAccount.UPLOAD_ALWAYS, null, 1);
            uploader.schedule(upload);
        }

        List<File> resumableUploads = new ArrayList<File>();
        for (int i = 0; i < 20; i++) {
            String prefix = "foobar" + i;
            String suffix = ".tmp";
            File file = null;
            try {
                file = File.createTempFile(prefix, suffix);
                Random random = new Random();
                byte dataToWrite[] = new byte[new Random().nextInt(20000000) + 100000];
                random.nextBytes(dataToWrite);
                FileOutputStream out = new FileOutputStream(file);
                out.write(dataToWrite);
                out.close();
            } catch (IOException e) {
                fail("Exception should not have been thrown: " + e);
            }

            resumableUploads.add(file);
        }

        for (File f : resumableUploads) {
            MediaFireUpload upload = new MediaFireUpload(mediaFire, 98, f, f.getName(), MediaFireUpload.ActionOnInAccount.UPLOAD_ALWAYS, null, 1);
            uploader.schedule(upload);
        }

        uploader.resume();

        while (uploader.getQueueSize() != 0) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                fail("Exception should not have been thrown: " + e);
            }
        }

        for (File f : simpleUploads) {
            f.delete();
        }

        for (File f : resumableUploads) {
            f.delete();
        }

        assertTrue(true);
    }

}