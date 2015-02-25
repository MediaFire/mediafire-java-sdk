package com.mediafire.sdk.api.responses.contact;

import com.mediafire.sdk.api.responses.ApiResponse;

/**
 * Created by Chris on 2/25/2015.
 */
public class AddResponse extends ApiResponse {
    private String contact_keys;

    public String getContactKey() {
        return contact_keys;
    }
}
