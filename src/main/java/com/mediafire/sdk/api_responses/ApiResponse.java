package com.mediafire.sdk.api_responses;

public class ApiResponse {
    private String action;
    private String message;
    private String result;
    private String error;
    private String current_api_version;
    private String new_key;

    public final String getAction() {
        return action;
    }

    public final String getMessage() {
        return message;
    }

    public final int getError() {
        int intValueOfError;
        if (error == null) {
            intValueOfError = 0;
        } else {
            intValueOfError = Integer.valueOf(error);
        }
        return intValueOfError;
    }

    public final ResponseCode getErrorCode() {
        return ResponseCode.fromInt(getError());
    }

    public final String getResult() {
        return result;
    }

    public final String getCurrentApiVersion() {
        return current_api_version;
    }

    public final boolean hasError() {
        return error != null;
    }

    public boolean needNewKey() {
        return new_key != null && "yes".equals(new_key);
    }

}
