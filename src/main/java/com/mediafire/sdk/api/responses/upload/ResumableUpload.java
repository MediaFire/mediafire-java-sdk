package com.mediafire.sdk.api.responses.upload;

/**
* Created by Chris on 12/23/2014.
*/
public class ResumableUpload {
    private String all_units_ready;
    private int number_of_units;
    private int unit_size;
    private ResumableBitmap bitmap;

    public String getAllUnitsReady() {
        return all_units_ready;
    }

    public int getNumberOfUnits() {
        return number_of_units;
    }

    public int getUnitSize() {
        return unit_size;
    }

    public ResumableBitmap getBitmap() {
        return bitmap;
    }
}
