package com.mediafire.sdk.uploader;

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
