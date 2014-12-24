package com.mediafire.sdk.api.responses.upload;

import com.mediafire.sdk.api.responses.ApiResponse;

public class PollResponse extends ApiResponse {
    private DoUpload doupload;

    public class DoUpload {
        private String result;
        private String status;
        private String description;
        private String fileerror;
        private String quickkey;
        private String size;
        private String revision;
        private String created;
        private String filename;
        private String hash;

        public int getResultCode() {
            if (result == null || result.isEmpty()) {
                result = "0";
            }
            return Integer.parseInt(result);
        }

        public int getStatusCode() {
            if (status == null || status.isEmpty()) {
                status = "0";
            }
            return Integer.parseInt(status);
        }

        public String getDescription() {
            if (description == null) {
                return "";
            }
            return description;
        }

        public int getFileErrorCode() {
            if (fileerror == null || fileerror.isEmpty()) {
                fileerror = "0";
            }
            return Integer.parseInt(fileerror);
        }

        public String getQuickKey() {
            if (quickkey == null) {
                return "";
            }
            return quickkey;
        }

        public long getSize() {
            if (size == null || size.isEmpty()) {
                return 0;
            }
            return Long.parseLong(size);
        }

        public String getRevision() {
            if (revision == null) {
                return "";
            }
            return revision;
        }

        public String getCreated() {
            if (created == null) {
                return "";
            }
            return created;
        }

        public String getFilename() {
            if (filename == null) {
                return "";
            }
            return filename;
        }

        public String getHash() {
            if (hash == null) {
                return "";
            }
            return hash;
        }
    }

    public DoUpload getDoUpload() {
        if (doupload == null) {
            return new DoUpload();
        }
        return doupload;
    }

}
