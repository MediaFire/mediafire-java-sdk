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

    public class ResumableUpload {
        private String all_units_ready;
        private String number_of_units;
        private String unit_size;
        private Bitmap bitmap;

        public boolean areAllUnitsReady() {
            return "yes".equals(all_units_ready);
        }

        public int getNumberOfUnits() {
            if (number_of_units == null || number_of_units.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(number_of_units);
        }

        public int getUnitSize() {
            if (unit_size == null || unit_size.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(unit_size);
        }

        public Bitmap getBitmap() {
            if (bitmap == null) {
                return new Bitmap();
            }
            return bitmap;
        }
    }

    public class Bitmap {
        private String count;
        private String[] words;

        public int getCount() {
            if (count == null || count.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(count);
        }

        public List<Integer> getWords() {
            if (words == null || words.length == 0) {
                return new ArrayList<Integer>();
            }
            return convert(words);
        }

        private List<Integer> convert(String[] words) {
            List<Integer> ret = new ArrayList<Integer>();
            for (String str : words) {
                ret.add(Integer.parseInt(str));
            }

            if (ret.size() == words.length) {
                return ret;
            } else {
                return new ArrayList<Integer>();
            }
        }
    }

    public long getUsedStorageSize() {
        if ("".equals(used_storage_size)) {
            return 0;
        }
        return Long.parseLong(used_storage_size);
    }

    public long getStorageLimit() {
        if ("".equals(storage_limit)) {
            return 0;
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

    public boolean doesHashExists() {
        if (hash_exists == null) {
            return false;
        }

        return "yes".equals(hash_exists);
    }

    public boolean isInAccount() {
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
