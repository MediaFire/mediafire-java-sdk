package com.mediafire.sdk.api.responses.notifications;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 1/19/2015.
 */
public class GetCacheResponse extends ApiResponse {
    private List<MediaFireNotification> notifications;
    private String num_older;

    public List<MediaFireNotification> getNotifications() {
        if (notifications == null) {
            notifications = new ArrayList<MediaFireNotification>();
        }
        return notifications;
    }

    public int getNumOlder() {
        if (num_older == null || num_older.isEmpty()) {
            num_older = "0";
        }
        return Integer.parseInt(num_older);
    }

}
