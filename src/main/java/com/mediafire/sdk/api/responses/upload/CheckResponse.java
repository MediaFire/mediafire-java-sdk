package com.mediafire.sdk.api.responses.upload;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.ArrayList;
import java.util.List;

public class CheckResponse extends ApiResponse {
    private String hash_exists;
    private String in_account;
    private String in_folder;
    private String file_exists;
    private String different_hash;
    private String duplicate_quickkey;
    private String available_space;
    private String used_storage_size;
    private String storage_limit;
    private String storage_limit_exceeded;
    private ResumableUpload resumable_upload;

    public long getUsedStorageSize() {
        if (used_storage_size == null || used_storage_size.isEmpty()) {
            used_storage_size = "0";
        }

        return Long.parseLong(used_storage_size);
    }

    public long getStorageLimit() {
        if (storage_limit == null || storage_limit.isEmpty()) {
            storage_limit = "0";
        }

        return Long.parseLong(storage_limit);
    }

    public boolean isStorageLimitExceeded() {
        if (storage_limit_exceeded == null) {
            return false;
        }

        return "yes".equals(storage_limit_exceeded);
    }

    public ResumableUpload getResumableUpload() {
        if (resumable_upload == null) {
            return new ResumableUpload();
        }

        return resumable_upload;
    }

    public boolean doesHashExist() {
        if (hash_exists == null) {
            return false;
        }

        return "yes".equals(hash_exists);
    }

    public boolean doesHashExistInAccount() {
        if (in_account == null) {
            return false;
        }

        return "yes".equals(in_account);
    }

    public boolean isInFolder() {
        if (in_folder == null) {
            return false;
        }

        return "yes".equals(in_folder);
    }

    public boolean doesFileExist() {
        if (file_exists == null) {
            return false;
        }

        return "yes".equals(file_exists);
    }

    public boolean isDifferentHash() {
        if (different_hash == null) {
            return false;
        }

        return "yes".equals(different_hash);
    }

    public String getDuplicateQuickkey() {
        if (duplicate_quickkey == null) {
            return "";
        }
        return this.duplicate_quickkey;
    }

    public long getAvailableSpace() {
        if (available_space == null) {
            return 0;
        }
        return Long.parseLong(this.available_space);
    }

}
