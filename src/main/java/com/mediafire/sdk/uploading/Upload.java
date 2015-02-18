package com.mediafire.sdk.uploading;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Chris on 12/22/2014.
 */
public class Upload {
    private final long mId;
    private final File mFile;
    private final Options mOptions;
    private String mHash;
    private String mPollKey;
    private boolean mAllUnitsReady;
    private int mNumUnits;
    private int mUnitSize;
    private List<Boolean> mUploadUnits;
    private boolean mHashInMediaFire;
    private boolean mHashInAccount;
    private boolean mHashInFolder;
    private boolean mFileNameInFolder;
    private boolean mFileNameInFolderWithDifferentHash;
    private String mDuplicateQuickKey;
    private String mNewQuickKey;
    private final HashMap<String, Object> mInfo;

    Upload(long id, File file, Options options, HashMap<String, Object> info) {
        mId = id;
        mFile = file;
        if (options == null) {
            options = new Options.Builder().build();
        }
        mOptions = options;
        mInfo = info;
    }

    public Upload(long id, File file, Options options) {
        this(id, file, options, new HashMap<String, Object>());
    }

    public Upload(long id, File file) {
        this(id, file, null);
    }

    public Upload(long id, String path, Options options) {
        this(id, new File(path), options);
    }

    public Upload(long id, String path) {
        this(id, new File(path), null);
    }

    // GETTERS

    public File getFile() {
        return mFile;
    }

    public Options getOptions() {
        return mOptions;
    }

    public long getId() {
        return mId;
    }

    public HashMap<String, Object> getInfo() {
        return mInfo;
    }

    public String getHash() {
        return mHash;
    }

    public String getPollKey() {
        return mPollKey;
    }

    public int getNumberOfUnits() {
        return mNumUnits;
    }

    public int getUnitSize() {
        return mUnitSize;
    }

    public boolean isChunkUploaded(int chunkId) {
        if (mUploadUnits.isEmpty()) {
            return false;
        }
        return mUploadUnits.get(chunkId);
    }

    public boolean areAllUnitsReady() {
        return mAllUnitsReady;
    }

    // SETTERS

    public void setHash(String hash) {
        mHash = hash;
    }

    public void setPollKey(String key) {
        mPollKey = key;
    }

    public void addInfo(String key, Object value) {
        mInfo.put(key, value);
    }

    public void setNumberOfUnits(int numberOfUnits) {
        mNumUnits = numberOfUnits;
    }

    public void setUnitSize(int unitSize) {
        mUnitSize = unitSize;
    }

    public void updateUploadBitmap(int count, List<Integer> words) {
        List<Boolean> uploadUnits = new LinkedList<Boolean>();

        if (words == null || words.isEmpty()) {
            mUploadUnits = uploadUnits;
            return;
        }

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

        mUploadUnits = uploadUnits;
    }

    public void setAllUnitsReady(boolean allUnitsReady) {
        mAllUnitsReady = allUnitsReady;
    }

    public void setHashInMediaFire(boolean mHashInMediaFire) {
        this.mHashInMediaFire = mHashInMediaFire;
    }

    public void setHashInAccount(boolean mHashInAccount) {
        this.mHashInAccount = mHashInAccount;
    }

    public void setHashInFolder(boolean mHashInFolder) {
        this.mHashInFolder = mHashInFolder;
    }

    public void setFileNameInFolder(boolean mFileNameInFolder) {
        this.mFileNameInFolder = mFileNameInFolder;
    }

    public void setFileNameInFolderWithDifferentHash(boolean mFileNameInFolderWithDifferentHash) {
        this.mFileNameInFolderWithDifferentHash = mFileNameInFolderWithDifferentHash;
    }

    public void setDuplicateQuickKey(String mDuplicateQuickKey) {
        this.mDuplicateQuickKey = mDuplicateQuickKey;
    }

    public void setNewQuickKey(String newQuickKey) {
        mNewQuickKey = newQuickKey;
    }

    // GETTERS

    public boolean isHashInMediaFire() {
        return mHashInMediaFire;
    }

    public boolean isHashInAccount() {
        return mHashInAccount;
    }

    public boolean isHashInFolder() {
        return mHashInFolder;
    }

    public boolean isFileNameInFolder() {
        return mFileNameInFolder;
    }

    public boolean isFileNameInFolderWithDifferentHash() {
        return mFileNameInFolderWithDifferentHash;
    }

    public String getDuplicateQuickKey() {
        return mDuplicateQuickKey;
    }

    public String getNewQuickKey() {
        return mNewQuickKey;
    }

    private List<Boolean> decodeBitmap(int count, List<Integer> words) {
        List<Boolean> uploadUnits = new LinkedList<Boolean>();

        if (words == null || words.isEmpty()) {
            return uploadUnits;
        }

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

    @Override
    public String toString() {
        return "[id:" + mId + "]" +
                "[file:" + mFile + "]" +
                "[options:" + mOptions + "]" +
                "[hash:" + mHash + "]" +
                "[poll_key:" + mPollKey + "]" +
                "[all_units_ready:" + mAllUnitsReady + "]" +
                "[number_of_units:" + mNumUnits + "]" +
                "[unit_size:" + mUnitSize + "]" +
                "[hash exists:" + mHashInMediaFire + "]" +
                "[in_account:" + mHashInAccount + "]" +
                "[in_folder:" + mHashInFolder + "]" +
                "[file_exists:" + mFileNameInFolder + "]" +
                "[different_hash:" + mFileNameInFolderWithDifferentHash + "]" +
                "[duplicate_quick_key:" + mDuplicateQuickKey + "]" +
                "[new_quick_key:" + mNewQuickKey + "]" +
                "[user_info:" + mInfo + "]";
    }

    /**
     * Created by Chris on 12/22/2014.
     */
    public static class Options {
        private final String mUploadFolderKey;
        private final String mUploadPath;
        private final String mCustomFileName;
        private final ActionOnInAccount mActionOnInAccount;

        private Options(Builder builder) {
            mUploadFolderKey = builder.mUploadFolderKey;
            mUploadPath = builder.mUploadPath;
            mCustomFileName = builder.mCustomFileName;
            mActionOnInAccount = builder.mActionOnInAccount;
        }

        public String getUploadFolderKey() {
            return mUploadFolderKey;
        }

        public String getUploadPath() {
            return mUploadPath;
        }

        public String getCustomFileName() {
            return mCustomFileName;
        }

        public ActionOnInAccount getActionOnInAccount() {
            return mActionOnInAccount;
        }

        @Override
        public String toString() {
            return "[upload_folder_key:" + mUploadFolderKey + "]" +
                    "[upload_path:" + mUploadPath + "]" +
                    "[custom_file_name:" + mCustomFileName + "]" +
                    "[action_on_in_account:" + mActionOnInAccount + "]";
        }

        public static class Builder {
            private static final ActionOnInAccount DEFAULT_ACTION_ON_IN_ACCOUNT = ActionOnInAccount.UPLOAD_ALWAYS;

            private ActionOnInAccount mActionOnInAccount = DEFAULT_ACTION_ON_IN_ACCOUNT;
            private String mUploadFolderKey;
            private String mUploadPath;
            private String mCustomFileName;

            public Builder() {}

            public Builder uploadFolderKey(String uploadFolderKey) {
                if (uploadFolderKey == null) {
                    return this;
                }

                mUploadFolderKey = uploadFolderKey;
                return this;
            }

            public Builder actionOnInAccount(ActionOnInAccount actionOnInAccount) {
                if (actionOnInAccount == null) {
                    return this;
                }

                mActionOnInAccount = actionOnInAccount;
                return this;
            }

            public Builder uploadPath(String uploadPath) {
                if (uploadPath == null) {
                    return this;
                }

                mUploadPath = uploadPath;
                return this;
            }

            public Builder customFileName(String customFileName) {
                if (customFileName == null) {
                    return this;
                }

                mCustomFileName = customFileName;
                return this;
            }
            public Options build() {
                return new Options(this);
            }
        }

        public enum ActionOnInAccount {
            UPLOAD_ALWAYS,
            UPLOAD_IF_NOT_IN_FOLDER,
            DO_NOT_UPLOAD,
        }
    }
}
