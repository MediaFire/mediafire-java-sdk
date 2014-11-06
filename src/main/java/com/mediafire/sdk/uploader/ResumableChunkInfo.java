package com.mediafire.sdk.uploader;

/**
* Created by  on 7/8/2014.
* ResumableChunkInfo
*/
class ResumableChunkInfo {
    private final String chunkHash;
    private final byte[] uploadChunk;

    /**
     * ResumableChunkInfo Constructor
     * @param chunkHash - hash of the chunk
     * @param uploadChunk - the chunk as a byte array
     */
    public ResumableChunkInfo(String chunkHash, byte[] uploadChunk) {
        this.chunkHash = chunkHash;
        this.uploadChunk = uploadChunk;
    }

    /**
     * Gets the chunk hash
     * @return String chunkHash
     */
    public String getChunkHash() {
        return chunkHash;
    }

    /**
     * Gets the upload chunk
     * @return byte[] uploadChunk
     */
    public byte[] getUploadChunk() {
        return uploadChunk;
    }
}
