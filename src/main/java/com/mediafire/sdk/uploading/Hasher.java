package com.mediafire.sdk.uploading;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Chris on 12/22/2014.
 */
class Hasher {
    public static String getSHA256Hash(File file) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream fileUri = new BufferedInputStream(fileInputStream);
        BufferedInputStream in = new BufferedInputStream(fileUri);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = new byte[8192];
        int byteCount;

        while ((byteCount = in.read(bytes)) > 0) {
            digest.update(bytes, 0, byteCount);
        }

        byte[] digestBytes = digest.digest();
        StringBuilder sb = new StringBuilder();

        for (byte digestByte : digestBytes) {
            String tempString = Integer.toHexString((digestByte & 0xFF) | 0x100).substring(1, 3);
            sb.append(tempString);
        }

        String hash = sb.toString();
        fileInputStream.close();
        fileUri.close();
        in.close();

        fileInputStream = null;
        fileUri = null;
        in = null;

        return hash;
    }

    public static String getSHA256Hash(byte[] chunkData) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        //test code
        InputStream in = new ByteArrayInputStream(chunkData, 0, chunkData.length);
        byte[] bytes = new byte[8192];
        int byteCount;
        while ((byteCount = in.read(bytes)) > 0) {
            md.update(bytes, 0, byteCount);
        }
        byte[] hashBytes = md.digest();
        //test code
        //byte[] hashBytes = md.digest(chunkData); //original code

        StringBuilder sb = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String tempString = Integer.toHexString((hashByte & 0xFF) | 0x100).substring(1, 3);
            sb.append(tempString);
        }

        return sb.toString();
    }
}
