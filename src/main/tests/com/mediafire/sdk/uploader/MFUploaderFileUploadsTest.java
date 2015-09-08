package com.mediafire.sdk.uploader;

import com.mediafire.sdk.MFClient;
import com.mediafire.sdk.MediaFireClient;
import com.mediafire.sdk.MediaFireCredentialsStore;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MFUploaderFileUploadsTest  extends TestCase {
    private MediaFireClient mediaFire;
    private MFUploader uploader;
    private MFUploadStore store;
    private PausableExecutor executor;

    public void setUp() throws Exception {
        super.setUp();
        MFClient.Builder builder = new MFClient.Builder("40767", null);
        builder.apiVersion("1.5");
        mediaFire = builder.build();
        mediaFire.getCredentialStore().setEmail(new MediaFireCredentialsStore.EmailCredentials("badtestemail@badtestemail.com", "badtestemail"));

        executor = new PausableExecutor(3, 3, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public void tearDown() throws Exception {

    }

    public void testSingleFileSmallUpload() throws Exception {
        store = new MFUploadStore(1);
        uploader = new MFUploader(mediaFire, store, executor);
        executor.resume();
        File file = createTemporaryFile("testfile", ".tmp", 10000);

        MFFileUpload.Builder builder = new MFFileUpload.Builder(file, "testSingleFileSmallUpload" + System.currentTimeMillis() + ".tmp");
        builder.setResumable(true);
        builder.setActionOnInAccount(MediaFireFileUpload.ActionOnInAccount.UPLOAD_ALWAYS);
        builder.setFileSize(file.length());
        builder.setSha256Hash(mediaFire.getHasher().sha256(file));
        uploader.schedule(builder.build());

        while (store.isWaitingForUploads()) {
            Thread.sleep(250);
            System.out.println("uploader still running");
        }

        assertTrue(deleteTemporaryFile(file));
    }

    public void testMultiFileSmallUpload() throws Exception {
        store = new MFUploadStore(6);
        uploader = new MFUploader(mediaFire, store, executor);
        executor.resume();

        List<File> files = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            File file = createTemporaryFile("testfile", ".tmp", 10000);
            files.add(file);
        }

        List<MFFileUpload> uploads = new ArrayList<>();
        for (File file : files) {
            MFFileUpload.Builder builder = new MFFileUpload.Builder(file, "testMultiFileSmallUpload" + System.currentTimeMillis() + ".tmp");
            builder.setResumable(true);
            builder.setActionOnInAccount(MediaFireFileUpload.ActionOnInAccount.UPLOAD_ALWAYS);
            builder.setFileSize(file.length());
            builder.setSha256Hash(mediaFire.getHasher().sha256(file));
            MFFileUpload upload = builder.build();
            uploads.add(upload);
        }

        for (MFFileUpload upload : uploads) {
            uploader.schedule(upload);
        }

        while (store.isWaitingForUploads()) {
            Thread.sleep(250);
            System.out.println("uploader still running");
        }

        for (File file : files) {
            assertTrue("could not delete all temporary files", deleteTemporaryFile(file));
        }
    }

    public void testSingleFileLargeUpload() throws Exception {
        store = new MFUploadStore(1);
        uploader = new MFUploader(mediaFire, store, executor);
        executor.resume();
        File file = createTemporaryFile("testfile", ".tmp", 20000000);

        MFFileUpload.Builder builder = new MFFileUpload.Builder(file, "testSingleFileLargeUpload" + System.currentTimeMillis() + ".tmp");
        builder.setResumable(true);
        builder.setActionOnInAccount(MediaFireFileUpload.ActionOnInAccount.UPLOAD_ALWAYS);
        builder.setFileSize(file.length());
        builder.setSha256Hash(mediaFire.getHasher().sha256(file));
        uploader.schedule(builder.build());

        while (store.isWaitingForUploads()) {
            Thread.sleep(250);
            System.out.println("uploader still running");
        }

        assertTrue(deleteTemporaryFile(file));
    }

    public void testMultiFileLargeUpload() throws Exception {
        store = new MFUploadStore(6);
        uploader = new MFUploader(mediaFire, store, executor);
        executor.resume();

        List<File> files = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            File file = createTemporaryFile("testfile", ".tmp", 20000000);
            files.add(file);
        }

        List<MFFileUpload> uploads = new ArrayList<>();
        for (File file : files) {
            MFFileUpload.Builder builder = new MFFileUpload.Builder(file, "testMultiFileLargeUpload" + System.currentTimeMillis() + ".tmp");
            builder.setResumable(true);
            builder.setActionOnInAccount(MediaFireFileUpload.ActionOnInAccount.UPLOAD_ALWAYS);
            builder.setFileSize(file.length());
            builder.setSha256Hash(mediaFire.getHasher().sha256(file));
            MFFileUpload upload = builder.build();
            uploads.add(upload);
        }

        for (MFFileUpload upload : uploads) {
            uploader.schedule(upload);
        }

        while (store.isWaitingForUploads()) {
            Thread.sleep(250);
            System.out.println("uploader still running");
        }

        for (File file : files) {
            assertTrue("could not delete all temporary files", deleteTemporaryFile(file));
        }
    }

    private File createTemporaryFile(String filename, String extension, int size) throws IOException {
        Random r = new Random();
        String prefix = filename != null ? filename : "foobar" + r.nextInt();
        String suffix = extension != null ? extension : ".tmp";
        File file = File.createTempFile(prefix, suffix);
        int fileSize = size + r.nextInt(1000);
        byte[] data = new byte[fileSize];
        r.nextBytes(data);
        FileOutputStream out = new FileOutputStream(file);
        out.write(data);
        out.close();
        return file;
    }

    private boolean deleteTemporaryFile(File file) {
        if (file != null) {
            return file.delete();
        }

        return false;
    }
}