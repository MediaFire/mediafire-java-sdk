package com.mediafire.sdk.uploader;

/**
 * Created by christophernajar on 9/2/15.
 */
public interface Pausable {

    /**
     * is work paused
     * @return true if work is paused
     */
    boolean isPaused();

    /**
     * is work running
     * @return true if work is running
     */
    boolean isRunning();

    /**
     * pause work
     */
    void pause();

    /**
     * resume work
     */
    void resume();
}
