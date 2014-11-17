package com.mediafire.sdk.api.responses.upload;

/**
* Created by Chris on 11/13/2014.
*/
public enum PollResult {
    SUCCESS(0),
    INVALID_UPLOAD_KEY(-20),
    UPLOAD_KEY_NOT_FOUND(-80),;

    private final int value;

    PollResult(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PollResult fromInt(int value) {
        for (final PollResult e : values()) {
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
