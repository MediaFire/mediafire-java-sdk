package com.mediafire.sdk.uploading;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

public class HasherTest extends TestCase {

    private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Integer nisi nisl, pretium in rhoncus id, mattis ac ligula. " +
            "Curabitur leo nisi, molestie sed ullamcorper vitae, mattis at lectus. " +
            "Cras efficitur libero sed risus laoreet pellentesque. " +
            "Nam suscipit quam ex, interdum imperdiet justo pharetra a. " +
            "Vivamus laoreet ex massa, iaculis placerat est efficitur quis. " +
            "Nullam nec nulla vitae lorem suscipit vehicula. " +
            "In tincidunt vitae lacus a finibus. In a tempor magna, vel ultrices massa.";

    public void setUp() throws Exception {
        super.setUp();
        File file = new File("HasherTest.txt");
        file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(TEXT.getBytes());
        fileOutputStream.close();
    }

    public void tearDown() throws Exception {
        File file = new File("HasherTest.txt");
        file.delete();
    }

    @Test
    public void testGetSHA256HashFile() throws Exception {
        String actual = Hasher.getSHA256Hash(new File("HasherTest.txt"));

        String expected = "5c2781ead10335aab511f92230861d2601e96e2affca1abae22ad35e02f37e24";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSHA256HashByteArray() throws Exception {
        String actual = Hasher.getSHA256Hash(TEXT.getBytes());

        String expected = "5c2781ead10335aab511f92230861d2601e96e2affca1abae22ad35e02f37e24";
        assertEquals(expected, actual);
    }
}