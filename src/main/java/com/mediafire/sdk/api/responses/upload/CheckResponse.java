package com.mediafire.sdk.api.responses.upload;

import com.mediafire.sdk.api.responses.ApiResponse;

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
            return 0;
        }
        return Long.parseLong(used_storage_size);
    }

    public long getStorageLimit() {
        if (storage_limit == null || used_storage_size.isEmpty()) {
            return 0;
        }
        return Long.parseLong(storage_limit);
    }


    public String getHashExists() {
        return hash_exists;
    }

    public String getInAccount() {
        return in_account;
    }

    public String getInFolder() {
        return in_folder;
    }

    public String getFileExists() {
        return file_exists;
    }

    public String getDifferentHash() {
        return different_hash;
    }

    public String getStorageLimitExceeded() {
        return storage_limit_exceeded;
    }

    public ResumableUpload getResumableUpload() {
        return resumable_upload;
    }

    public String getDuplicateQuickkey() {
        return duplicate_quickkey;
    }

    public long getAvailableSpace() {
        if (available_space == null || available_space.isEmpty()) {
            return 0;
        }
        return Long.parseLong(available_space);
    }

}
