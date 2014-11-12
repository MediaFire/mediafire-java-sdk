package com.mediafire.sdk.api.clients.upload;

/**
 * Created by Chris on 11/11/2014.
 */
public class HeaderParameters {
    private final  long mFileSize;
    private final  String mFileHash;
    private final  long mUnitSize;
    private final  int mUnitId;
    private final  String mUnitHash;

    public HeaderParameters(long filesize, String filehash, long unitSize, int unitId, String unitHash) {
        mFileSize = filesize;
        mFileHash = filehash;
        mUnitSize = unitSize;
        mUnitId = unitId;
        mUnitHash = unitHash;
    }

    public long getFileSize() {
        return mFileSize;
    }

    public String getFileHash() {
        return mFileHash;
    }

    public long getUnitSize() {
        return mUnitSize;
    }

    public int getUnitId() {
        return mUnitId;
    }

    public String getUnitHash() {
        return mUnitHash;
    }
}
