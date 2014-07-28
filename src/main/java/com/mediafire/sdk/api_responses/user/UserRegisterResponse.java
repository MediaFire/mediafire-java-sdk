package com.mediafire.sdk.api_responses.user;


import com.mediafire.sdk.api_responses.ApiResponse;

/**
 * response class for user registration.
 *
 * @author
 */
public class UserRegisterResponse extends ApiResponse {
    String email;
    String created;

    public String getEmail() {
        if (email == null) {
            email = "";
        }
        return email;
    }

    public String getCreated() {
        if (created == null) {
            created = "";
        }
        return created;
    }
}
