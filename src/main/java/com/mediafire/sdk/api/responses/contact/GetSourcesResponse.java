package com.mediafire.sdk.api.responses.contact;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.List;

/**
 * Created by Chris on 2/24/2015.
 */
public class GetSourcesResponse extends ApiResponse {
    private List<String> sources;

    public List<String> getSources() {
        return sources;
    }
}
