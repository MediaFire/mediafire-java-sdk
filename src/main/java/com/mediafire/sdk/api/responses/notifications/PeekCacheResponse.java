package com.mediafire.sdk.api.responses.notifications;

import com.mediafire.sdk.api.responses.ApiResponse;

/**
 * Created by Chris on 1/19/2015.
 */
public class PeekCacheResponse extends ApiResponse {
    private String num_total;
    private String num_unread;

    public int getNumTotal() {
        if (num_total == null || num_total.isEmpty()) {
            num_total = "0";
        }
        return Integer.parseInt(num_total);
    }

    public int getNumUnread() {
        if (num_unread == null || num_unread.isEmpty()) {
            num_unread = "0";
        }
        return Integer.parseInt(num_unread);
    }
}
