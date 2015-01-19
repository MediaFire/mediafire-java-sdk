package com.mediafire.sdk.api.responses.notifications;

import com.mediafire.sdk.api.responses.ApiResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 1/19/2015.
 */
public class GetCacheResponse extends ApiResponse {
    private List<Notification> notifications;
    private String num_older;

    public List<Notification> getNotifications() {
        if (notifications == null) {
            notifications = new ArrayList<Notification>();
        }
        return notifications;
    }

    public int getNumOlder() {
        if (num_older == null || num_older.isEmpty()) {
            num_older = "0";
        }
        return Integer.parseInt(num_older);
    }

    public class Notification {
        private String actor;
        private String timestamp;
        private String resource;
        private String viewable;
        private String message;

        public String getActor() {
            return actor;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getResource() {
            return resource;
        }

        public String getViewable() {
            return viewable;
        }

        public String getMessage() {
            return message;
        }
    }
}
