package com.mediafire.sdk.api.responses.upload;

/**
* Created by Chris on 12/23/2014.
*/
public class ResumableUpload {
    private String all_units_ready;
    private String number_of_units;
    private String unit_size;
    private ResumableBitmap bitmap;

    public boolean areAllUnitsReady() {
        return "yes".equals(all_units_ready);
    }

    public int getNumberOfUnits() {
        if (number_of_units == null || number_of_units.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(number_of_units);
    }

    public int getUnitSize() {
        if (unit_size == null || unit_size.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(unit_size);
    }

    public ResumableBitmap getBitmap() {
        if (bitmap == null) {
            return new ResumableBitmap();
        }
        return bitmap;
    }
}
