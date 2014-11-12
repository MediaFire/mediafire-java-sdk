package com.mediafire.sdk.api.responses.user;

import com.mediafire.sdk.api.responses.ApiResponse;

public class GetActionTokenResponse extends ApiResponse {
    public String action_token;

    public String getActionToken() {
        return action_token;
    }
}
