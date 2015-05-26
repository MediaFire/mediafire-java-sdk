package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MFApiException;
import com.mediafire.sdk.MFException;
import com.mediafire.sdk.MFSessionNotStartedException;
import com.mediafire.sdk.MediaFire;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MediaFireUploadTest extends TestCase implements MediaFireUploadHandler {

    private MediaFire mediaFire;

    public void setUp() throws Exception {
        super.setUp();
        mediaFire = new MediaFire("40767");

    }

    public void tearDown() throws Exception {

    }

    public void testWebUpload() {
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        } catch (MFApiException e) {
            fail("Exception should not have been thrown: " + e);
        } catch (MFException e) {
            fail("Exception should not have been thrown: " + e);
        }

        MediaFireUpload upload = new MediaFireUpload(mediaFire, 98, "http://i.imgur.com/cGIyKyR.gif", "cGIyKyR.gif", this, 0);
        Thread thread = new Thread(upload);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            fail("Exception should not have been thrown: " + e);
        }

        assertTrue(true);
    }

    public void testSimpleUpload() {
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        } catch (MFApiException e) {
            fail("Exception should not have been thrown: " + e);
        } catch (MFException e) {
            fail("Exception should not have been thrown: " + e);
        }

        String prefix = "foobar2mb";
        String suffix = ".tmp";
        File file = null;
        try {
            file = File.createTempFile(prefix, suffix);
            Random random = new Random();
            byte dataToWrite[] = new byte[100000];
            random.nextBytes(dataToWrite);
            FileOutputStream out = new FileOutputStream(file);
            out.write(dataToWrite);
            out.close();
        } catch (IOException e) {
            fail("Exception should not have been thrown: " + e);
        }

        System.out.println(getName() + " file: " + file + ", size: " + file.length());
        MediaFireUpload upload = new MediaFireUpload(mediaFire, 98, file, file.getName(), MediaFireUpload.ActionOnInAccount.UPLOAD_ALWAYS, MediaFireUploadTest.this, 1);
        Thread thread = new Thread(upload);
        thread.start();
        try {
            thread.join();
            boolean deleted = file.delete();
        } catch (InterruptedException e) {
            fail("Exception should not have been thrown: " + e);
        }

        assertTrue(true);
    }

    public void testResumableUpload() {
        try {
            mediaFire.startSessionWithEmail("badtestemail@badtestemail.com", "badtestemail", null);
        } catch (MFApiException e) {
            fail("Exception should not have been thrown: " + e);
        } catch (MFException e) {
            fail("Exception should not have been thrown: " + e);
        }

        String prefix = "foobar20mb";
        String suffix = ".tmp";
        File file = null;
        try {
            file = File.createTempFile(prefix, suffix);
            Random random = new Random();
            byte dataToWrite[] = new byte[20000000];
            random.nextBytes(dataToWrite);
            FileOutputStream out = new FileOutputStream(file);
            out.write(dataToWrite);
            out.close();
        } catch (IOException e) {
            fail("Exception should not have been thrown: " + e);
        }
        System.out.println(getName() + " file: " + file + ", size: " + file.length());
        MediaFireUpload upload = new MediaFireUpload(mediaFire, 98, file, file.getName(), MediaFireUpload.ActionOnInAccount.UPLOAD_ALWAYS, MediaFireUploadTest.this, 1);
        Thread thread = new Thread(upload);
        thread.start();
        try {
            thread.join();
            boolean deleted = file.delete();
        } catch (InterruptedException e) {
            fail("Exception should not have been thrown: " + e);
        }

        assertTrue(true);
    }

    @Override
    public void uploadFailed(long id, MFException e) {
        System.out.println("uploadFailed() id: " + id + ", exception: " + e);
    }

    @Override
    public void uploadFailed(long id, MFApiException e) {
        System.out.println("uploadFailed() id: " + id + ", exception: " + e);

    }

    @Override
    public void uploadFailed(long id, MFSessionNotStartedException e) {
        System.out.println("uploadFailed() id: " + id + ", exception: " + e);
    }

    @Override
    public void uploadFailed(long id, IOException e) {
        System.out.println("uploadFailed() id: " + id + ", exception: " + e);

    }

    @Override
    public void uploadFailed(long id, InterruptedException e) {
        System.out.println("uploadFailed() id: " + id + ", exception: " + e);

    }

    @Override
    public void uploadProgress(long id, double percentFinished) {
        System.out.println("uploadProgress() id: " + id + ", percent: " + percentFinished);

    }

    @Override
    public void uploadFinished(long id, String quickKey, String fileName) {
        System.out.println("uploadFinished() id: " + id + ", quickKey: " + quickKey + ", fileName: " + fileName);
    }

    @Override
    public void uploadPolling(long id, int statusCode, String description) {
        System.out.println("uploadPolling() id: " + id + ", status code: " + statusCode + ", description: " + description);

    }
}