package com.mediafire.sdk.uploader.upload_items;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Chris on 11/13/2014.
 */
public class Upload {
    private final String mFilename;
    private final String mFilehash;
    private final long mFilesize;
    private final Options mOptions;
    private final File mFile;

    public Upload(File file, Options options, String knownHash, long knownSize) {
        mFile = file;
        mOptions = options;
        mFilehash = knownHash;
        mFilesize = knownSize;
        mFilename = options.getCustomFileName() == null ? file.getName() : options.getCustomFileName();
    }

    public Upload(File file, Options options) throws IOException, NoSuchAlgorithmException {
        this(file, options, getSHA256HashOfFile(file), getSizeOfFileInBytes(file));
    }

    public Upload(String path, Options options) throws IOException, NoSuchAlgorithmException {
        this(new File(path), options);
    }

    public String getFilename(boolean utf8Encoded) {
        if (utf8Encoded) {
            try {
                return new String(mFilename.getBytes("UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return mFilename;
            }
        } else {
            return mFilename;
        }
    }

    public String getFilename() {
        return getFilename(false);
    }

    public String getFilehash() {
        return mFilehash;
    }

    public long getFilesize() {
        return mFilesize;
    }

    public Options getOptions() {
        return mOptions;
    }

    private static long getSizeOfFileInBytes(File file) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            long sizeBytes = fileInputStream.getChannel().size();
            return sizeBytes;
        } finally {
            fileInputStream.close();
        }
    }

    private static String getSHA256HashOfFile(File file) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream;
        BufferedInputStream fileUri;
        BufferedInputStream in;
        fileInputStream = new FileInputStream(file);


        fileUri = new BufferedInputStream(fileInputStream);

        MessageDigest digest;
        String fileHash;
        digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = new byte[8192];
        in = new BufferedInputStream(fileUri);
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

        fileHash = sb.toString();
        fileInputStream.close();
        fileUri.close();
        in.close();
        return fileHash;
    }

    public File getFile() {
        return mFile;
    }

}
