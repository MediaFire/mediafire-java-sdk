package com.mediafire.sdk.api.responses.user;

import com.mediafire.sdk.api.responses.ApiResponse;

public class GetAvatarResponse extends ApiResponse {
    private String avatar;

    public String getAvatarUrl() {
        if (avatar == null) {
            avatar = "";
        }
        return avatar;
    }
}
