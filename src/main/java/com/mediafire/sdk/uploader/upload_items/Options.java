package com.mediafire.sdk.uploader.upload_items;

import com.mediafire.sdk.api.clients.upload.ActionOnDuplicate;
import com.mediafire.sdk.api.clients.upload.VersionControl;

/**
* Created by Chris on 11/13/2014.
*/
public class Options {
    private final String mResumable;
    private final String mUploadFolderKey;
    private final String mActionOnDuplicate;
    private final String mVersionControl;
    private final String mUploadPath;
    private final String mCustomFilename;
    private final String mQuickkey;
    private final String mMtime;
    private final ActionOnInAccount mActionOnInAccount;
    private final String mFiledropKey;
    private final String mPreviousHash;
    private final String mSourceHash;
    private final String mTargetHash;
    private final String mTargetSize;

    private Options(Builder builder) {
        mResumable = builder.mResumable;
        mUploadFolderKey = builder.mUploadFolderKey;
        mActionOnDuplicate = builder.mActionOnDuplicate;
        mVersionControl = builder.mVersionControl;
        mUploadPath = builder.mUploadPath;
        mCustomFilename = builder.mCustomFilename;
        mQuickkey = builder.mQuickkey;
        mMtime = builder.mMtime;
        mActionOnInAccount = builder.mActionOnInAccount;
        mFiledropKey = builder.mFiledropKey;
        mPreviousHash = builder.mPreviousHash;
        mSourceHash = builder.mSourceHash;
        mTargetHash = builder.mTargetHash;
        mTargetSize = builder.mTargetSize;
    }

    /**
     * Gets the custom file name
     * @return String mCustomFilename
     */
    public String getCustomFileName() {
        return mCustomFilename;
    }

    /**
     * Gets the action on in account
     * @return ActionOnInAccount mActionOnInAccount
     */
    public ActionOnInAccount getActionOnInAccount() {
        return mActionOnInAccount;
    }

    /**
     * Gets the upload folder key
     * @return String mUploadFolderKey
     */
    public String getUploadFolderKey() {
        return mUploadFolderKey;
    }

    /**
     * Gets the action on duplicate value
     * @return String actionOnDuplicates' value
     */
    public String getActionOnDuplicate() {
        return mActionOnDuplicate;
    }

    /**
     * Gets the version control value
     * @return String versionControls' value
     */
    public String getVersionControl() {
        return mVersionControl;
    }

    /**
     * Gets if the upload is mResumable
     * @return String is mResumable
     */
    public String getResumable() {
        return mResumable;
    }

    /**
     * Gets the upload path
     * @return String mUploadPath
     */
    public String getUploadPath() {
        return mUploadPath;
    }

    /**
     * Gets the modification time
     * @return String mMtime
     */
    public String getModificationTime() {
        return mMtime;
    }

    /**
     * Gets the quick key
     * @return String mQuickkey
     */
    public String getQuickKey() {
        return mQuickkey;
    }

    public String getFiledropKey() {
        return mFiledropKey;
    }

    public String getPreviousHash() {
        return mPreviousHash;
    }

    public String getSourceHash() {
        return mSourceHash;
    }

    public String getTargetHash() {
        return mTargetHash;
    }

    public String getTargetSize() {
        return mTargetSize;
    }

    /**
     * Builder is a class to build on UploadItemOptions Object (see builder pattern)
     */
    public static class Builder {
        private final String DEFAULT_RESUMABLE = "yes";
        private final String DEFAULT_ACTION_ON_DUPLICATE = ActionOnDuplicate.KEEP.getValue();
        private final String DEFAULT_VERSION_CONTROL = VersionControl.NONE.getValue();
        private final ActionOnInAccount DEFAULT_ACTION_ON_IN_ACCOUNT = ActionOnInAccount.UPLOAD_ALWAYS;

        private String mResumable = DEFAULT_RESUMABLE;
        private String mActionOnDuplicate = DEFAULT_ACTION_ON_DUPLICATE;
        private String mVersionControl = DEFAULT_VERSION_CONTROL;
        private ActionOnInAccount mActionOnInAccount = DEFAULT_ACTION_ON_IN_ACCOUNT;

        private String mUploadFolderKey;
        private String mUploadPath;
        private String mCustomFilename;
        private String mQuickkey;
        private String mMtime;
        private String mFiledropKey;
        private String mPreviousHash;
        private String mSourceHash;
        private String mTargetHash;
        private String mTargetSize;

        public Builder() {}

        public Builder previousHash(String previousHash) {
            mPreviousHash = previousHash;
            return this;
        }

        public Builder sourceHash(String sourceHash) {
            mSourceHash = sourceHash;
            return this;
        }

        public Builder targetHash(String targetHash) {
            mTargetHash = targetHash;
            return this;
        }

        public Builder targetSize(String targetSize) {
            mTargetSize = targetSize;
            return this;
        }

        /**
         * Sets if the upload is mResumable
         * @param resumable boolean mResumable
         * @return Builder
         */
        public Builder resumable(boolean resumable) {
            if (resumable) {
                mResumable = "yes";
            } else {
                mResumable = "no";
            }

            return this;
        }

        /**
         * Sets the upload folder key
         * @param uploadFolderKey String upload folder key
         * @return Builder
         */
        public Builder uploadFolderKey(String uploadFolderKey) {
            mUploadFolderKey = uploadFolderKey;
            return this;
        }


        public Builder filedropKey(String filedropKey) {
            mFiledropKey = filedropKey;
            return this;
        }

        /**
         * Sets the action on duplicate
         * @param actionOnDuplicate ActionOnDuplicate
         * @return Builder
         */
        public Builder actionOnDuplicate(ActionOnDuplicate actionOnDuplicate) {
            if (actionOnDuplicate == null) {
                return this;
            }
            mActionOnDuplicate = actionOnDuplicate.getValue();
            return this;
        }

        /**
         * Sets the action on in account
         * @param actionOnInAccount ActionOnInAccount
         * @return Builder
         */
        public Builder actionOnInAccount(ActionOnInAccount actionOnInAccount) {
            if (actionOnInAccount == null) {
                return this;
            }
            mActionOnInAccount = actionOnInAccount;
            return this;
        }

        /**
         * Sets the version control
         * @param versionControl VersionControl
         * @return Builder
         */
        public Builder versionControl(VersionControl versionControl) {
            if (versionControl == null) {
                return this;
            }
            mVersionControl = versionControl.getValue();
            return this;
        }

        /**
         * Sets the upload path
         * @param uploadPath String upload path
         * @return Builder
         */
        public Builder uploadPath(String uploadPath) {
            mUploadPath = uploadPath;
            return this;
        }

        /**
         * Sets the custom file name
         * @param customFileName String custom file name
         * @return Builder
         */
        public Builder customFileName(String customFileName) {
            mCustomFilename = customFileName;
            return this;
        }

        /**
         * Sets the quick key
         * @param quickKey String quick key
         * @return Builder
         */
        public Builder quickKey(String quickKey) {
            mQuickkey = quickKey;
            return this;
        }

        /**
         * Sets the modification time
         * @param modificationTime String modification time
         * @return Builder
         */
        public Builder modificationTime(String modificationTime) {
            mMtime = modificationTime;
            return this;
        }

        /**
         * Builds an UploadItemOptions from this class
         * @return UploadItemOptions
         */
        public Options build() {
            return new Options(this);
        }
    }

    /**
     * ActionOnInAccount is an Enum for what to do when a file is already in the account
     */
    public enum ActionOnInAccount {
        UPLOAD_ALWAYS,
        UPLOAD_IF_NOT_IN_FOLDER,
        DO_NOT_UPLOAD,
    }

}
