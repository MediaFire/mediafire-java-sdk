package com.mediafire.sdk.uploader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by christophernajar on 9/2/15.
 */
public class MFUploadStore implements MediaFireUploadStore {
    private final Logger logger = LoggerFactory.getLogger(MFUploadStore.class);

    private final LinkedBlockingQueue<MediaFireUpload> uploads = new LinkedBlockingQueue<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public void insert(MediaFireWebUpload upload) {
        int id = currentId.addAndGet(1);
        upload.setId(id);
        boolean added = uploads.offer(upload);
        logger.info("inserted new web upload: " + upload + ": " + added);
    }

    @Override
    public void insert(MediaFireFileUpload upload) {
        int id = currentId.addAndGet(1);
        upload.setId(id);
        uploads.offer(upload);
        boolean added = uploads.offer(upload);
        logger.info("inserted new file upload: " + upload + ": " + added);
    }

    @Override
    public void update(MediaFireFileUpload mediaFireUpload, Map<String, Object> valuesMap) {

    }

    @Override
    public void update(MediaFireWebUpload mediaFireUpload, Map<String, Object> valuesMap) {

    }

    @Override
    public MediaFireUpload getNextUpload() {
        MediaFireUpload upload =  uploads.poll();

        logger.info("getting next upload: " + upload);

        return upload;
    }
}
