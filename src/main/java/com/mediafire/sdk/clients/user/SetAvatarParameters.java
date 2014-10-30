package com.mediafire.sdk.clients.user;

/**
 * Created by Chris Najar on 10/29/2014.
 */
public class SetAvatarParameters {
    String mAction;
    String mQuickKey;
    String mUrl;

    public SetAvatarParameters(String resourceValue, boolean isUrl) {
        if (isUrl) {
            mUrl = resourceValue;
        } else {
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
