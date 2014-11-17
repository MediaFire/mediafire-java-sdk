package com.mediafire.sdk.uploader.upload_items;

/**
 * Created by Chris on 11/13/2014.
 */
public class PollUpload extends Upload {
    private String mPollKey;

    public PollUpload(Upload upload, String pollKey) {
        super(upload.getFile(), upload.getOptions(), upload.getFilehash(), upload.getFilesize());
        mPollKey = pollKey;
    }

    public String getPollKey() {
        return mPollKey;
    }
}
