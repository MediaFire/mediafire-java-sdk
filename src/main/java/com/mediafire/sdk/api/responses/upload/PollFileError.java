package com.mediafire.sdk.api.responses.upload;

/**
* Created by Chris on 11/13/2014.
*/
public enum PollFileError {
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

    PollFileError(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PollFileError fromInt(int value) {
        for (final PollFileError e : values()) {
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
