package com.mediafire.sdk.uploading;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Chris on 12/22/2014.
 */
public class Upload {
    private long mId;
    private final File mFile;
    private final Options mOptions;
    private final HashMap<String, Object> mInfo;

    public Upload(long id, File file, Options options) {
        mId = id;
        mFile = file;
        if (options == null) {
            options = new Options.Builder().build();
        }
        mOptions = options;
        mInfo = new HashMap<String, Object>();
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

    public File getFile() {
        return mFile;
    }

    public Options getOptions() {
        return mOptions;
    }

    public long getId() {
        return mId;
    }

    public void addInfo(String key, Object value) {
        mInfo.put(key, value);
    }

    public HashMap<String, Object> getInfo() {
        return mInfo;
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
