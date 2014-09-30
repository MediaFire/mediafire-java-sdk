package com.mediafire.sdk.config;

/**
 * Created by Chris Najar on 9/16/2014.
 */
abstract interface MFAbstractContainerInterface {
    /**
     * called when tokens need to be filled (populated)
     */
    public void fill();

    /**
     * called when tokens need to be emptied (cleared)
     */
    public void empty();
}
