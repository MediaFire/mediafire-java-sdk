package com.mediafire.sdk.requests;

/**
 * Created by Chris on 5/15/2015.
 */
public class ImageRequest {

    private final String hash;
    private final String quickKey;
    private final String sizeId;
    private final boolean conversionOnly;

    public ImageRequest(String hash, String quickKey, String sizeId, boolean conversionOnly) {
        this.hash = hash;
        this.quickKey = quickKey;
        this.sizeId = sizeId;
        this.conversionOnly = conversionOnly;
    }

    public String getHash() {
        return hash;
    }

    public String getQuickKey() {
        return quickKey;
    }

    public String getSizeId() {
        return sizeId;
    }

    public boolean isConversionOnly() {
        return conversionOnly;
    }
}
