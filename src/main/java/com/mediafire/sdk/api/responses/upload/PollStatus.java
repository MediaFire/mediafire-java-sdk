package com.mediafire.sdk.api.responses.upload;

/**
* Created by Chris on 11/13/2014.
*/
public enum PollStatus {
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

    PollStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PollStatus fromInt(int value) {
        for (final PollStatus e : values()) {
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
