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

        public Result getResultCode() {
            if (result == null || result.isEmpty()) {
                result = "0";
            }
            return Result.fromInt(Integer.parseInt(result));
        }

        public Status getStatusCode() {
            if (status == null || status.isEmpty()) {
                return Status.fromInt(0);
            }
            return Status.fromInt(Integer.parseInt(status));
        }

        public String getDescription() {
            if (description == null) {
                return "";
            }
            return description;
        }

        public FileError getFileErrorCode() {
            if (fileerror == null || fileerror.isEmpty()) {
                fileerror = "0";
            }
            return FileError.fromInt(Integer.parseInt(fileerror));
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

    public enum FileError {
        NO_ERROR(0),
        FILESIZE_TOO_LARGE(1),
        FILESIZE_CANNOT_BE_ZERO(2),
        FOUND_BAD_RAR(3),
        FOUND_BAD_RAR1(4),
        VIRUS_FOUND(5),
        UNKNOWN_ERROR(6),
        FILE_HASH_MISMATCH(7),
        UNKNOWN_ERROR1(8),
        FOUND_BAD_RAR2(9),
        UNKNOWN_ERROR2(10),
        DATABASE_ERROR(12),
        FILE_ALREADY_EXISTS(13),
        DESTINATION_DOES_NOT_EXIST(14),
        ACCOUNT_STORAGE_LIMIT_REACHED(15),
        UPDATE_REVISION_CONFLICT(16),
        ERROR_PATCHING_FILE(17),
        ACCOUNT_BLOCKED(18),
        FAILED_TO_CREATE_PATH(19),;

        private final int value;

        private FileError(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static FileError fromInt(int value) {
            for (final FileError e : values()) {
                if (e.getValue() == value) {
                    return e;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            String returnMessage;
            switch (this.value) {
                case 0:
                    returnMessage = "No Error";
                    break;
                case 1:
                    returnMessage = "File is larger than the maximum file size allowed";
                    break;
                case 2:
                    returnMessage = "File size cannot be 0";
                    break;
                case 3:
                case 4:
                case 9:
                    returnMessage = "Found bad RAR file";
                    break;
                case 5:
                    returnMessage = "Virus found";
                    break;
                case 6:
                case 8:
                case 10:
                    returnMessage = "Unknown internal error";
                    break;
                case 7:
                    returnMessage = "File hash or size mismatch";
                    break;
                case 12:
                    returnMessage = "Failed to insert data into database";
                    break;
                case 13:
                    returnMessage = "File name already exists in the same parent folder, skipping";
                    break;
                case 14:
                    returnMessage = "Destination folder does not exist";
                    break;
                case 15:
                    returnMessage = "Account storage limit reached";
                    break;
                case 16:
                    returnMessage = "There was an update revision conflict";
                    break;
                case 17:
                    returnMessage = "Error patching delta file";
                    break;
                case 18:
                    returnMessage = "Account is blocked";
                    break;
                case 19:
                    returnMessage = "Failure to create path";
                    break;
                default:
                    returnMessage = "No error code associated with: " + this.value;
                    break;
            }
            return returnMessage;
        }
    }

    public enum Result {
        SUCCESS(0),
        INVALID_UPLOAD_KEY(-20),
        UPLOAD_KEY_NOT_FOUND(-80),;

        private final int value;

        private Result(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Result fromInt(int value) {
            for (final Result e : values()) {
                if (e.getValue() == value) {
                    return e;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            String returnMessage;
            switch (this.value) {
                case 0:
                    returnMessage = "Success";
                    break;
                case -20:
                    returnMessage = "Invalid Upload Key";
                    break;
                case -80:
                    returnMessage = "Upload Key not found";
                    break;
                default:
                    returnMessage = "No result code associated with: " + this.value;
                    break;
            }
            return returnMessage;
        }
    }

    public enum Status {
        UNKNOWN_OR_NO_STATUS_AVAILABLE_FOR_THIS_KEY(0),
        KEY_IS_READY_FOR_USE(2),
        UPLOAD_IN_PROGRESS(3),
        UPLOAD_COMPLETED(4),
        WAITING_FOR_VERIFICATION(5),
        VERIFYING_FILE(6),
        FINISHED_VERIFICATION(11),
        UPLOAD_IS_IN_PROGRESS(17),
        WAITING_FOR_ASSEMBLY(18),
        ASSEMBLING_FILE(19),
        NO_MORE_REQUESTS_FOR_THIS_KEY(99),;

        private final int value;

        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Status fromInt(int value) {
            for (final Status e : values()) {
                if (e.getValue() == value) {
                    return e;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            String returnMessage;
            switch (this.value) {
                case 0:
                    returnMessage = "Unknown or no status available for this key";
                    break;
                case 2:
                    returnMessage = "Key is ready for use";
                    break;
                case 3:
                    returnMessage = "Upload is in progress";
                    break;
                case 4:
                    returnMessage = "Upload is completed";
                    break;
                case 5:
                    returnMessage = "Waiting for verification";
                    break;
                case 6:
                    returnMessage = "Verifying File";
                    break;
                case 11:
                    returnMessage = "Finished verification";
                    break;
                case 17:
                    returnMessage = "Upload is in progress";
                    break;
                case 18:
                    returnMessage = "Waiting for assembly";
                    break;
                case 19:
                    returnMessage = "Assembling File";
                    break;
                case 99:
                    returnMessage = "No more requests for this key";
                    break;
                default:
                    returnMessage = "No error code associated with: " + this.value;
                    break;
            }
            return returnMessage;
        }
    }
}
