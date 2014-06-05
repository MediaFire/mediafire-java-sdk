package com.arkhive.components.uploadmanager.uploaditem;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This data structure represents an item to be uploaded.
 * The only mandatory parameter that needs to be passed to
 * the constructor is a path, but the implementer of this
 * data structure can also pass specific upload options.
 * @author Chris Najar
 *
 */
public class UploadItem {
    private static final String TAG = UploadItem.class.getSimpleName();
    private int count;
    private String fileName;
    private String quickKey;
    private String modificationTime;
    private UploadOptions options;
    private final FileData fileData;
    private ChunkData chunkData;
    private ResumableBitmap bitmap;
    private String pollUploadKey;
    private final Logger logger = LoggerFactory.getLogger(UploadItem.class);

    /**
     * Constructor which takes a path and upload attempts.
     * Use this method when you want to customize the upload options for this UploadItem.
     * @param path - file path on the device
     * @param uploadOptions - upload options to use for the upload item.
     * Should use the single or dual argument constructor for the most part.
     */
    public UploadItem(String path, UploadOptions uploadOptions) {
        logger.info("UploadItem created");
        if (path == null) {
            throw new IllegalArgumentException("path must not be null");
        }
        if (uploadOptions == null) {
            options = new UploadOptions();
        } else {
            this.options = uploadOptions;
        }

        if (options.getCustomFileName().isEmpty()) {
            setFileName(path);
        } else {
            fileName = options.getCustomFileName();
        }

        //set Object fields so they won't be null
        fileData = new FileData(path);
        quickKey = "";
        modificationTime = "";
        pollUploadKey = "";
        chunkData = new ChunkData();
        bitmap = new ResumableBitmap(0, new ArrayList<Integer>());
        count = 0;
    }

    /**
     * Constructor which takes a path and an image id
     * Use this method when you want to use default upload options for this UploadItem.
     * @param path - path to data
     */
    public UploadItem(String path) {
        this(path, null);
    }

    public int getCheckCount() {
        logger.info("getCheckCount()");
        count++;
        return count;
    }

    /**
     * Called to get the quick key.
     * @return the quick key.
     */
    public String getQuickKey() {
        logger.info("getQuickKey()");
        return quickKey;
    }

    /**
     * Called to get the Short file name.
     * @return the filename.
     */
    public String getFileName() {
        logger.info("getFileName()");
        return fileName;
    }

    /**
     * CAlled to get the UploadItemFileData.
     * @return the file data struct.
     */
    public FileData getFileData() {
        logger.info("getFileData()");
        return fileData;
    }

    /**
     * Called to get the poll upload key.
     * @return - the poll upload key.
     */
    public String getPollUploadKey() {
        logger.info("getPollUploadKey()");
        return pollUploadKey;
    }

    /**
     * Called to get the UploadItemFileUploadOptions.
     * @return - the upload options struct.
     */
    public UploadOptions getUploadOptions() {
        logger.info("getUploadOptions()");
        if (options == null) {
            options = new UploadOptions();
        }
        return options;
    }

    /**
     * Called to get the UploadItemChunkData.
     * @return - the chunkdata struct.
     */
    public ChunkData getChunkData() {
        logger.info("getChunkData()");
        if (chunkData == null) {
            chunkData = new ChunkData();
        }
        return chunkData;
    }

    /**
     * Called to get the ResumableUploadBitmap.
     * @return - the resumablebitmap struct.
     */
    public ResumableBitmap getBitmap() {
        logger.info("getBitmap()");
        if (bitmap == null) {
            logger.error(TAG + "   resumable bitmap reference lost");
            bitmap = new ResumableBitmap(0, new ArrayList<Integer>());
        }
        return bitmap;
    }

    /**
     * Called to get the Modification Time.
     * @return - the modification time.
     */
    public String getModificationTime() {
        logger.info("getModificationTime()");
        return modificationTime;
    }

    /**
     * Sets the quick key.
     * @param quickKey - the quickkey to set.
     */
    public void setQuickKey(String quickKey) {
        logger.info("setQuickKey()");
        this.quickKey = quickKey;
    }

    /**
     * Sets the ResumableUploadBitmap.
     * @param bitmap - the resumablebitmap to set.
     */
    public void setBitmap(ResumableBitmap bitmap) {
        logger.info("setBitmap()");
        this.bitmap = bitmap;
    }

    /**
     * Sets the poll upload key.
     * @param pollUploadKey - the polluploadkey to set.
     */
    public void setPollUploadKey(String pollUploadKey) {
        logger.info("setPollUploadKey()");
        this.pollUploadKey = pollUploadKey;
    }

    /**
     * Sets the modification time. A valid format must be entered.
     * @param modificationTime - the modification time to set.
     */
    public void setModificationTime(String modificationTime) {
        logger.info("setModificationTime()");
        String timeString;
        if (modificationTime == null || modificationTime.isEmpty()) {
            timeString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US).format(new Date());
        } else {
            timeString = modificationTime;
        }

        try {
            this.modificationTime = URLEncoder.encode(timeString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalStateException("UTF-8 encoding not available");
        }
    }

    /**
     * sets the short file name given the path.
     * @param path
     */
    private void setFileName(String path) {
        logger.info("setFileName()");
        String[] splitName = path.split("/");
        //just throwing the unsupportedcoding exception to whoever creates the upload item
        try {
            this.fileName = URLEncoder.encode(splitName[splitName.length - 1], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported");
        }
    }

    /**
     * Determines whether an Upload Item has the same hash as another Upload Item.
     * @param item
     * @return true if hashes match, false otherwise.
     */
    public boolean equalTo(UploadItem item) {
        logger.info("equalTo()");
        return fileData.getFileHash().equals(item.fileData.getFileHash());
    }

    @Override
    public boolean equals(Object object) {
        logger.info("equals()");
        if (object == null) {
            return false;
        }

        if (this.getClass() != object.getClass()) {
            return false;
        }

        if (!(object instanceof UploadItem)) {
            return false;
        }

        if (!fileData.equals(((UploadItem) object).getFileData())) {
            return false;
        }

        return true;
    }

}
