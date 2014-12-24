package com.mediafire.sdk.uploading;

import java.util.List;

/**
 * Created by Chris on 12/23/2014.
 */
interface IUploadManager<T extends Upload> {
    public void addUpload(T t);

    public void purge(boolean shutdown);

    public void pause();

    public void resume();

    public List<T> getQueuedUploads();

    public List<UploadRunnable> getRunningUploads();

    public void startNextAvailableUpload();

    public void sortQueueByFileSize(boolean ascending);

    public void moveToFrontOfQueue(long id);

    public void moveToEndOfQueue(long id);
}
