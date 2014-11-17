package com.mediafire.sdk.uploader.upload_items;

import com.mediafire.sdk.uploader.uploaditem.ResumableBitmap;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Chris on 11/13/2014.
 */
public class ResumableUpload extends Upload {

    private final int mNumUnits;
    private final int mUnitSize;
    private final ResumableBitmap mResumableBitmap;

    public ResumableUpload(Upload upload, int numUnits, int unitSize, ResumableBitmap bitmap) throws IOException, NoSuchAlgorithmException {
        super(upload.getFile(), upload.getOptions());
        mNumUnits = numUnits;
        mUnitSize = unitSize;
        mResumableBitmap = bitmap;
    }

    public int getNumUnits() {
        return mNumUnits;
    }

    public int getUnitSize() {
        return mUnitSize;
    }

    public ResumableBitmap getResumableBitmap() {
        return mResumableBitmap;
    }
}
