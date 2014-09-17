package com.mediafire.sdk.token;

/**
 * Created by Chris Najar on 9/16/2014.
 */
public interface MFTokenFarmInterface {
    /**
     * called when tokens need to be filled (populated)
     */
    public void fill();

    /**
     * called when tokens need to be emptied (cleared)
     */
    public void empty();
}
