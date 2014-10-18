package com.mediafire.sdk.uploader.uploaditem;

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

    private UploadItemOptions(Builder builder) {
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

    public String getCustomFileName() {
        return customFileName;
    }

    public ActionOnInAccount getActionOnInAccount() {
        if (actionOnInAccount == null) {
            return null;
        }
        return actionOnInAccount;
    }

    public String getUploadFolderKey() {
        return uploadFolderKey;
    }

    public String getActionOnDuplicate() {
        if (actionOnDuplicate == null) {
            return null;
        }
        return actionOnDuplicate.getValue();
    }

    public String getVersionControl() {
        if (versionControl == null) {
            return null;
        }
        return versionControl.getValue();
    }

    public String getResumable() {
        if (resumable) {
            return "yes";
        } else {
            return "no";
        }
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public String getQuickKey() {
        return quickKey;
    }

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

        public Builder() {}

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

        public Builder resumable(boolean resumable) {
            this.resumable = resumable;
            return this;
        }

        public Builder uploadFolderKey(String uploadFolderKey) {
            if (uploadFolderKey == null) {
                throw new IllegalArgumentException("uploadFolderKey cannot be passed as a null value");
            }
            this.uploadFolderKey = uploadFolderKey;
            return this;
        }

        public Builder actionOnDuplicate(ActionOnDuplicate actionOnDuplicate) {
            if (actionOnDuplicate == null) {
                throw new IllegalArgumentException("ActionOnDuplicate cannot be passed as a null value");
            }
            this.actionOnDuplicate = actionOnDuplicate;
            return this;
        }

        public Builder actionOnInAccount(ActionOnInAccount actionOnInAccount) {
            if (actionOnInAccount == null) {
                throw new IllegalArgumentException("ActionOnInAccount cannot be passed as a null value");
            }
            this.actionOnInAccount = actionOnInAccount;
            return this;
        }

        public Builder versionControl(VersionControl versionControl) {
            if (versionControl == null) {
                throw new IllegalArgumentException("VersionControl cannot be passed as a null value");
            }
            this.versionControl = versionControl;
            return this;
        }

        public Builder uploadPath(String uploadPath) {
            if (uploadPath == null) {
                throw new IllegalArgumentException("uploadPath cannot be passed as a null value");
            }
            this.uploadPath = uploadPath;
            return this;
        }

        public Builder customFileName(String customFileName) {
            if (customFileName == null) {
                throw new IllegalArgumentException("customFileName cannot be passed as a null value");
            }
            this.customFileName = customFileName;
            return this;
        }

        public Builder quickKey(String quickKey) {
            if (quickKey == null) {
                throw new IllegalArgumentException("quickKey cannot be passed as a null value");
            }
            this.quickKey = quickKey;
            return this;
        }

        public Builder modificationTime(String modificationTime) {
            if (modificationTime == null) {
                throw new IllegalArgumentException("modificationTime cannot be passed as a null value");
            }

            this.modificationTime = modificationTime;
            return this;
        }

        public UploadItemOptions build() {
            return new UploadItemOptions(this);
        }
    }

    public enum ActionOnDuplicate {
        KEEP("keep"),
        SKIP("skip"),
        REPLACE("replace");

        private final String value;

        private ActionOnDuplicate(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ActionOnInAccount {
        UPLOAD_ALWAYS,
        UPLOAD_IF_NOT_IN_FOLDER,
        DO_NOT_UPLOAD,
    }

    public enum VersionControl {
        CREATE_PATCHES("create_patches"),
        KEEP_REVISION("keep_revision"),
        NONE("none");

        private final String value;

        private VersionControl(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
