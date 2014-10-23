package com.mediafire.sdk.uploader.uploaditem;

public class ChunkData {
    private int unitSize;
    private int numberOfUnits;

    public ChunkData() {
        unitSize = 0;
        numberOfUnits = 0;
    }

    public int getUnitSize() {
        return unitSize;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setUnitSize(int unitSize) {
        this.unitSize = unitSize;
    }

    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

}
