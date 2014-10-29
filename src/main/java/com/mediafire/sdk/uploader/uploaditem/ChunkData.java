package com.mediafire.sdk.uploader.uploaditem;

/**
 * ChunkData keeps track of chunk data
 */
public class ChunkData {
    private int unitSize;
    private int numberOfUnits;

    /**
     * ChunkData Constructor
     */
    public ChunkData() {
        unitSize = 0;
        numberOfUnits = 0;
    }

    /**
     * Gets the unit size for the chunk
     * @return int unit size
     */
    public int getUnitSize() {
        return unitSize;
    }

    /**
     * Gets the number of unit for the chunk
     * @return int number of units
     */
    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    /**
     * Sets the unit size for the chunk
     * @param unitSize int for the unit size
     */
    public void setUnitSize(int unitSize) {
        this.unitSize = unitSize;
    }

    /**
     * Sets the number of units for the chunk
     * @param numberOfUnits int for the number of chunks
     */
    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

}
