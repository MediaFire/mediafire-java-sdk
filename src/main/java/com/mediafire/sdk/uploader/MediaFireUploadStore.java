package com.mediafire.sdk.uploader;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Chris on 5/18/2015.
 */
public interface MediaFireUploadStore {

    public long insert(File file, MediaFireUpload.ActionOnInAccount actionOnInAccount, HashMap<String, Object> info);
}
