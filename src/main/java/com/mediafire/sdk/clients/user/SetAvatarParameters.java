package com.mediafire.sdk.clients.user;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class SetAvatarParameters {
    String mAction;
    String mQuickKey;
    String mUrl;

    public SetAvatarParameters(String resourceValue) {
        try {
            new URL(resourceValue);
            mUrl = resourceValue;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            mQuickKey = resourceValue;
        }
    }

    public SetAvatarParameters action(boolean remove) {
        if (remove) {
            mAction = "remove";
        } else {
            mAction = "set_default";
        }

        return this;
    }
}
