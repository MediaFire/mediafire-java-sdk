package com.mediafire.sdk.clients.folder;

/**
 * Created by jondh on 11/4/14.
 */
public class MovementParameters {
    public String mFolderKeySrc;
    public String mFolderKeyDst;

    public MovementParameters folderKeySrc(String folderKeySrc){
        if(folderKeySrc == null) {
            return this;
        }

        mFolderKeySrc = folderKeySrc;
        return this;
    }

    public MovementParameters folderKeyDst(String folderKeyDst){
        if(folderKeyDst == null) {
            return this;
        }

        mFolderKeyDst = folderKeyDst;
        return this;
    }

}
