package com.mediafire.sdk.uploader.uploaditem;

/**
 * UploadItemOptions contains options used for an UploadItem
 */
public class UploadItemOptions {
    private final boolean resumable;
    private final String uploadFolderKey;
    private final ActionOnDuplicate actionOnDuplicate;
    private final VersionControl versionControl;
    private final String uploadPath;
    private final String customFileName;
    private final String quickKey;
    private final String modificationTime;
    private final ActionOnInAccount actionOnInAccount;

    protected UploadItemOptions(Builder builder) {
        this.resumable = builder.resumable;
        this.uploadFolderKey = builder.uploadFolderKey;
        this.actionOnDuplicate = builder.actionOnDuplicate;
        this.versionControl = builder.versionControl;
        this.uploadPath = builder.uploadPath;
        this.customFileName = builder.customFileName;
        this.quickKey = builder.quickKey;
        this.modificationTime = builder.modificationTime;
        this.actionOnInAccount = builder.actionOnInAccount;
    }

    /**
     * Gets the custom file name
     * @return String customFileName
     */
    public String getCustomFileName() {
        return customFileName;
    }

    /**
     * Gets the action on in account
     * @return ActionOnInAccount actionOnInAccount
     */
    public ActionOnInAccount getActionOnInAccount() {
        if (actionOnInAccount == null) {
            return null;
        }
        return actionOnInAccount;
    }

    /**
     * Gets the upload folder key
     * @return String uploadFolderKey
     */
    public String getUploadFolderKey() {
        return uploadFolderKey;
    }

    /**
     * Gets the action on duplicate value
     * @return String actionOnDuplicates' value
     */
    public String getActionOnDuplicate() {
        if (actionOnDuplicate == null) {
            return null;
        }
        return actionOnDuplicate.getValue();
    }

    /**
     * Gets the version control value
     * @return String versionControls' value
     */
    public String getVersionControl() {
        if (versionControl == null) {
            return null;
        }
        return versionControl.getValue();
    }

    /**
     * Gets if the upload is resumable
     * @return String is resumable
     */
    public String getResumable() {
        if (resumable) {
            return "yes";
        } else {
            return "no";
        }
    }

    /**
     * Gets the upload path
     * @return String uploadPath
     */
    public String getUploadPath() {
        return uploadPath;
    }

    /**
     * Gets the modification time
     * @return String modificationTime
     */
    public String getModificationTime() {
        return modificationTime;
    }

    /**
     * Gets the quick key
     * @return String quickKey
     */
    public String getQuickKey() {
        return quickKey;
    }

    /**
     * Builder is a class to build on UploadItemOptions Object (see builder pattern)
     */
    public static class Builder {
        private final boolean DEFAULT_RESUMABLE = true;
        private final ActionOnDuplicate DEFAULT_ACTION_ON_DUPLICATE = ActionOnDuplicate.KEEP;
        private final VersionControl DEFAULT_VERSION_CONTROL = VersionControl.NONE;
        private final ActionOnInAccount DEFAULT_ACTION_ON_IN_ACCOUNT = ActionOnInAccount.UPLOAD_ALWAYS;

        private boolean resumable = DEFAULT_RESUMABLE;
        private ActionOnDuplicate actionOnDuplicate = DEFAULT_ACTION_ON_DUPLICATE;
        private VersionControl versionControl = DEFAULT_VERSION_CONTROL;
        private ActionOnInAccount actionOnInAccount = DEFAULT_ACTION_ON_IN_ACCOUNT;
        private String uploadFolderKey;
        private String uploadPath;
        private String customFileName;
        private String quickKey;
        private String modificationTime;

        /**
         * Builder Constructor
         */
        public Builder() {}

        /**
         * Builder Constructor
         * @param oldOptions UploadItemOptions to copy for a new Builder
         */
        public Builder(UploadItemOptions oldOptions) {
            this.resumable = oldOptions.resumable;
            this.actionOnDuplicate = oldOptions.actionOnDuplicate;
            this.versionControl = oldOptions.versionControl;
            this.actionOnInAccount = oldOptions.actionOnInAccount;
            this.uploadFolderKey = oldOptions.uploadFolderKey;
            this.uploadPath = oldOptions.uploadPath;
            this.customFileName = oldOptions.customFileName;
            this.quickKey = oldOptions.quickKey;
            this.modificationTime = oldOptions.modificationTime;
        }

        /**
         * Sets if the upload is resumable
         * @param resumable boolean resumable
         * @return Builder
         */
        public Builder resumable(boolean resumable) {
            this.resumable = resumable;
            return this;
        }

        /**
         * Sets the upload folder key
         * @param uploadFolderKey String upload folder key
         * @return Builder
         */
        public Builder uploadFolderKey(String uploadFolderKey) {
            if (uploadFolderKey == null) {
                throw new IllegalArgumentException("uploadFolderKey cannot be passed as a null value");
            }
            this.uploadFolderKey = uploadFolderKey;
            return this;
        }

        /**
         * Sets the action on duplicate
         * @param actionOnDuplicate ActionOnDuplicate
         * @return Builder
         */
        public Builder actionOnDuplicate(ActionOnDuplicate actionOnDuplicate) {
            if (actionOnDuplicate == null) {
                throw new IllegalArgumentException("ActionOnDuplicate cannot be passed as a null value");
            }
            this.actionOnDuplicate = actionOnDuplicate;
            return this;
        }

        /**
         * Sets the action on in account
         * @param actionOnInAccount ActionOnInAccount
         * @return Builder
         */
        public Builder actionOnInAccount(ActionOnInAccount actionOnInAccount) {
            if (actionOnInAccount == null) {
                throw new IllegalArgumentException("ActionOnInAccount cannot be passed as a null value");
            }
            this.actionOnInAccount = actionOnInAccount;
            return this;
        }

        /**
         * Sets the version control
         * @param versionControl VersionControl
         * @return Builder
         */
        public Builder versionControl(VersionControl versionControl) {
            if (versionControl == null) {
                throw new IllegalArgumentException("VersionControl cannot be passed as a null value");
            }
            this.versionControl = versionControl;
            return this;
        }

        /**
         * Sets the upload path
         * @param uploadPath String upload path
         * @return Builder
         */
        public Builder uploadPath(String uploadPath) {
            if (uploadPath == null) {
                throw new IllegalArgumentException("uploadPath cannot be passed as a null value");
            }
            this.uploadPath = uploadPath;
            return this;
        }

        /**
         * Sets the custom file name
         * @param customFileName String custom file name
         * @return Builder
         */
        public Builder customFileName(String customFileName) {
            if (customFileName == null) {
                throw new IllegalArgumentException("customFileName cannot be passed as a null value");
            }
            this.customFileName = customFileName;
            return this;
        }

        /**
         * Sets the quick key
         * @param quickKey String quick key
         * @return Builder
         */
        public Builder quickKey(String quickKey) {
            if (quickKey == null) {
                throw new IllegalArgumentException("quickKey cannot be passed as a null value");
            }
            this.quickKey = quickKey;
            return this;
        }

        /**
         * Sets the modification time
         * @param modificationTime String modification time
         * @return Builder
         */
        public Builder modificationTime(String modificationTime) {
            if (modificationTime == null) {
                throw new IllegalArgumentException("modificationTime cannot be passed as a null value");
            }

            this.modificationTime = modificationTime;
            return this;
        }

        /**
         * Builds an UploadItemOptions from this class
         * @return UploadItemOptions
         */
        public UploadItemOptions build() {
            return new UploadItemOptions(this);
        }
    }

    /**
     * ActionOnDuplicate is an Enum for what to do with a duplicate file
     */
    public enum ActionOnDuplicate {
        KEEP("keep"),
        SKIP("skip"),
        REPLACE("replace");

        private final String value;

        private ActionOnDuplicate(String value) {
            this.value = value;
        }

        /**
         * gets the value of the enum
         * @return String value
         */
        public String getValue() {
            return value;
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

    /**
     * VersionControl is an Enum for version control of uploads
     */
    public enum VersionControl {
        CREATE_PATCHES("create_patches"),
        KEEP_REVISION("keep_revision"),
        NONE("none");

        private final String value;

        private VersionControl(String value) {
            this.value = value;
        }

        /**
         * gets the value of the enum
         * @return String value
         */
        public String getValue() {
            return value;
        }
    }
}
