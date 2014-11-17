package com.mediafire.sdk.api.responses.upload;

/**
* Created by Chris on 11/13/2014.
*/
public enum CheckError {
    NON_OWNER_UPLOAD_WITHOUT_WRITE_PERMISSIONS_TO_FOLDER(114),
    NO_ERROR(0),;

    private final int value;

    CheckError(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CheckError fromInt(int value) {
        for (final CheckError e : values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String response;
        switch (this.value) {
            case 114:
                response = "Success";
                break;
            default:
                response = "No error code associated with: " + this.value;
                break;
        }
        return response;
    }
}
