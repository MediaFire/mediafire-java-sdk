package com.mediafire.sdk.uploader.uploaditem;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * FileData contains file information for an upload item.
 */
public class FileData {
    private static final String TAG = FileData.class.getCanonicalName();
    private final String filePath;
    private long fileSize;
    private String fileHash;

    /**
     * FileData Constructor
     * @param filePath String for the file path
     */
    public FileData(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("invalid filePath (cannot be null)");
        }
        this.filePath = filePath;
    }

    /**
     * Gets the file path
     * @return String filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Gets the file size for the filePath
     * @return long fileSize
     */
    public long getFileSize() {
        if (fileSize == 0) {
            setFileSize();
        }
        return fileSize;
    }

    /**
     * Gets the file hast for the filePath
     * @return String fileHash
     */
    public String getFileHash() {
        if (fileHash == null) {
            setFileHash();
        }
        return fileHash;
    }

    /**
     * Sets the file size for the filePath
     */
    public void setFileSize() {
        File file = new File(getFilePath());
        fileSize = file.length();
    }

    /**
     * Sets the file hash for the filePath
     */
    public void setFileHash() {
        File file = new File(filePath);
        FileInputStream fileInputStream;
        BufferedInputStream fileUri;
        BufferedInputStream in;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileHash = "";
            return;
        }

        fileUri = new BufferedInputStream(fileInputStream);

        MessageDigest digest;
        try {
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            fileHash = "";
        } catch (IOException e) {
            e.printStackTrace();
            fileHash = "";
        } finally {
            //noinspection UnusedAssignment
            fileInputStream = null;
            //noinspection UnusedAssignment
            fileUri = null;
            //noinspection UnusedAssignment
            in = null;
        }
    }
}
