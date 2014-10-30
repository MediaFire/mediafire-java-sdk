package com.mediafire.sdk.uploader.uploaditem;

import java.util.LinkedList;
import java.util.List;

/**
 * ResumableBitmap
 */
public class ResumableBitmap {
    private final int count;
    private final List<Integer> words;
    private final List<Boolean> uploadUnits;

    /**
     * ResumableBitmap Constructor
     * @param count int the count
     * @param words List<Integer> words
     */
    public ResumableBitmap(int count, List<Integer> words) {
        this.count = count;
        this.words = words;
        uploadUnits = decodeBitmap(count, words);
    }

    private List<Boolean> decodeBitmap(int count, List<Integer> words) {
        List<Boolean> uploadUnits = new LinkedList<Boolean>();

        //loop count times
        for (int i = 0; i < count; i++) {
            //convert words to binary string
            String word = Integer.toBinaryString(words.get(i));

            //ensure number is 16 bit by adding 0 until there are 16 bits
            while (word.length() < 16) {
                word = "0" + word;
            }

            //add boolean to collection depending on bit value
            for (int j = 0; j < word.length(); j++) {
                uploadUnits.add(i * 16 + j, word.charAt(15 - j) == '1');
            }
        }

        return uploadUnits;
    }

    /**
     * Checks if the specified chunk id is uploaded
     * @param chunkId int for the chunk id to check
     * @return boolean if the chunk is uploaded
     */
    public boolean isUploaded(int chunkId) {
        if (uploadUnits.isEmpty()) {
            return false;
        }
        return uploadUnits.get(chunkId);
    }

    /**
     * Gets the uploaded unit
     * @return List<Boolean> upload units
     */
    public List<Boolean> getUploadUnits() {
        return uploadUnits;
    }

    /**
     * Gets the count
     * @return int count
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets the words
     * @return List<Integer> words
     */
    public List<Integer> getWords() {
        return words;
    }
}
